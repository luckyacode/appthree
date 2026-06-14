package com.praveen.appthree;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BeanInspector {

    public BeanInspector(ApplicationContext ctx) {
        System.out.println("Tracer: "+Arrays.toString(
                ctx.getBeanNamesForType(io.micrometer.tracing.Tracer.class)
        ));
    }
}