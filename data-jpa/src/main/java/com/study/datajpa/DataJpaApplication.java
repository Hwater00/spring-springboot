package com.study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
public class DataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }

    // 등록, 수정 시마다 빈으로 등록한 auditorProvider를 호출해서 자동으로 채워짐
    @Bean
    public AuditorAware<String> auditorProvider(){
        return () -> Optional.of(UUID.randomUUID().toString());
    }
    /* 위 코드의 람다식을 풀면 아래와 같다.
    return new AuditorAware<String>{
        @Override
        public Optional<String> getCurrentAuditor(){
            return Optional.of(UUID.randomUUID().toString());
        }
    };
    인터페이스에서 메서드 하나면 람다로 바꿀 수 있습니다.
    (#) 실제 사용성은 시큐리티를 사용하시면 세션에서 정보를 꺼내서 사용합니다!!
    * */
}
