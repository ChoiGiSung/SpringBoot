package com.example.demo;

import com.example.demo.web.domain.Board;
import com.example.demo.web.Repository.*;
import com.example.demo.web.domain.User;
import com.example.demo.web.domain.enums.BoardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JpaMappingTest {
    private final String boardTestTitle="테스트";
    private final String email="test@gmail.com";

    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public  void init(){
        User user=userRepository.save(User.builder()
        .name("havi")
        .password("test")
        .email(email)
        .createDate(LocalDateTime.now())
        .build());

        boardRepository.save(Board.builder()
        .title(boardTestTitle)
        .subTitle("서브 타이틀")
        .content("컨텐츠")
        .boardType(BoardType.free)
        .createDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .user(user).build());
    }

    @Test
    public void 제대로_생성_됐는지_테스트(){
        User user=userRepository.findByEmail(email);
        assertEquals(user.getName(),"havi");
        assertEquals(user.getPassword(),"test");
        assertEquals(user.getEmail(),email);

        Board board=boardRepository.findByUser(user);
        assertEquals(board.getTitle(),boardTestTitle);
        assertEquals(board.getSubTitle(),"서브 타이틀");
        assertEquals(board.getContent(),"컨텐츠");
        assertEquals(board.getBoardType(),BoardType.free);
    }
}
