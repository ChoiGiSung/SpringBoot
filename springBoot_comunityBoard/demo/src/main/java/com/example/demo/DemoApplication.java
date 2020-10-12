package com.example.demo;

import com.example.demo.pojo.Fruit;
import com.example.demo.web.Repository.BoardRepository;
import com.example.demo.web.Repository.UserRepository;
import com.example.demo.web.domain.Board;
import com.example.demo.web.domain.User;
import com.example.demo.web.domain.enums.BoardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	//test 데이터 넣기
	@Bean
	public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository){
		return (args) ->{
			User user=userRepository.save(User.builder()
			.name("havi")
			.password("test")
			.email("havi@gmail.com")
			.createDate(LocalDateTime.now())
			.build());

			IntStream.rangeClosed(1,200).forEach(index->
					boardRepository.save(Board.builder()
					.title("게시글"+index)
					.subTitle("순서"+index)
					.content("컨텐츠")
					.boardType(BoardType.free)
					.createｄDate(LocalDateTime.now())
					.updateDate(LocalDateTime.now())
					.user(user).build()));
		};
	}
}
