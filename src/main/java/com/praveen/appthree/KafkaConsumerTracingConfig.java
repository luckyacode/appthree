package com.praveen.appthree;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.kafka.autoconfigure.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import java.nio.charset.StandardCharsets;

@Configuration
public class KafkaConsumerTracingConfig {

    private final Tracer tracer;

    public KafkaConsumerTracingConfig(Tracer tracer) {
        this.tracer = tracer;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConsumerFactory<Object, Object> kafkaConsumerFactory,
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        // Apply default boot settings (including your application.yml observations)
        configurer.configure(factory, kafkaConsumerFactory);

        // 🚀 THE PRODUCTION PLUG: Intercept the record right after extraction from Kafka
        factory.setRecordInterceptor((record, consumer) -> { // 🚀 FIX: Added 'consumer' parameter
            org.apache.kafka.common.header.Header traceHeader = record.headers().lastHeader("traceparent");

            if (traceHeader != null) {
                String rawHeader = new String(traceHeader.value(), StandardCharsets.UTF_8);
                String cleanTraceId = rawHeader.contains("-") ? rawHeader.split("-")[1] : rawHeader;

                if (cleanTraceId.length() == 32) {
                    // 1. Rebuild our TraceContext from the outbox raw hex string
                    TraceContext parentContext = tracer.traceContextBuilder()
                            .traceId(cleanTraceId)
                            .spanId(tracer.nextSpan().context().spanId())
                            .sampled(true)
                            .build();

                    // 2. Tie a new child span directly to that parent context
                    Span consumerSpan = this.tracer.spanBuilder()
                            .setParent(parentContext)
                            .name("kafka-consumer-receive")
                            .start();

                    // 3. Open the scope so the thread local logging context catches it
                    Tracer.SpanInScope scope = tracer.withSpan(consumerSpan);
                }
            }
            return record;
        });
        return factory;
    }
}