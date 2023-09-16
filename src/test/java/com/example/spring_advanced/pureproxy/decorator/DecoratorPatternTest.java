package com.example.spring_advanced.pureproxy.decorator;

import com.example.spring_advanced.pureproxy.decorator.code.Component;
import com.example.spring_advanced.pureproxy.decorator.code.DecoratorPatternClient;
import com.example.spring_advanced.pureproxy.decorator.code.RealComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        Component realComponent = new RealComponent();
        DecoratorPatternClient decoratorPatternClient = new DecoratorPatternClient(realComponent);
        decoratorPatternClient.execute();
    }
}
