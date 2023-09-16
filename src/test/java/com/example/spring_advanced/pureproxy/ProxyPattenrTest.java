package com.example.spring_advanced.pureproxy;

import com.example.spring_advanced.pureproxy.code.CacheProxy;
import com.example.spring_advanced.pureproxy.code.ProxyPatternClient;
import com.example.spring_advanced.pureproxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPattenrTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        client.execute();
        client.execute();
        client.execute();
    }
}
