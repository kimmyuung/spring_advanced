package com.example.spring_advanced;

import com.example.spring_advanced.config.AppV6Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AppV6Config.class)
// 클래스를 스프링 빈으로 등록
// 일반적으로 설정 파일 등록 시 사용되나 스프링 빈 등록 시에도 사용이 가능
@SpringBootApplication(scanBasePackages = "com.example.spring_advanced.app.v6_proxy")
// @ComponentScan의 기능과 같음 : 컴포넌트 스캔을 시작할 위치를 지정
// 사용하지 않으면 SpringAdvancedApplication 있는 패키지와 그 하위 패키지를 스캔
public class SpringAdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAdvancedApplication.class, args);
    }

}
