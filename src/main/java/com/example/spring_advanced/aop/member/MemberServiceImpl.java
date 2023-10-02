package com.example.spring_advanced.aop.member;

import com.example.spring_advanced.aop.member.annotation.ClassAop;
import com.example.spring_advanced.aop.member.annotation.MethodApp;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {
    @Override
    @MethodApp("test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param) {
        return "ok";
    }
}
