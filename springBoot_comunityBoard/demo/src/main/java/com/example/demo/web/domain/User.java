package com.example.demo.web.domain;

import com.example.demo.web.domain.enums.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table
@NoArgsConstructor
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String email;

    @Column
    private LocalDateTime createDate;
    @Column
    private LocalDateTime updateDate;
    
    //OAuth2 사용기
    @Column
    private String pincipal;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    
    @Builder
    public User(Long idx, String name, String password, String email, LocalDateTime createDate, LocalDateTime updateDate, String pincipal, SocialType socialType) {
        this.idx = idx;
        this.name = name;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.pincipal = pincipal;
        this.socialType = socialType;
    }
}
