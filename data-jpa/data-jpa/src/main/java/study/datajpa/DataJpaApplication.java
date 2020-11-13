package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	//생성자와 사용자 찍기
	@Bean
	public AuditorAware<String> auditorAware(){
		return ()-> Optional.of(UUID.randomUUID().toString());
		//실제는 random이 아니라 세션에서 꺼낸다.
	}

}
