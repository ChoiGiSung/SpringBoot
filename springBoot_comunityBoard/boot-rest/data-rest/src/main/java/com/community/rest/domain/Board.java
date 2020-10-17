package com.community.rest.domain;


import com.community.rest.domain.enums.BoardType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boardss")
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String title;
    @Column
    private String subTitle;
    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updateDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;


    @Builder
    public Board(String title, String subTitle, String content, BoardType boardType, LocalDateTime createdDate, LocalDateTime updateDate, User user) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.boardType = boardType;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.user = user;
    }
    public void setCreatedDateNow() {
        this.createdDate = LocalDateTime.now();
    }

    public void setupdate(Board board) {
        this.title=board.getTitle();
        this.subTitle = board.getSubTitle();
        this.content = board.getContent();
        this.boardType = board.getBoardType();
        this.updateDate = LocalDateTime.now();
    }
}