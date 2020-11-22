package board.solo.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Board {
    @Id
    @GeneratedValue
    private Long id;

    private String board_type;
    private String content;
    private String sub_title;
    private String title;

    private LocalDateTime created_date;
    private LocalDateTime update_date;


    protected Board() {
    }

    public Board(String content, String title) {
        this.content = content;
        this.title = title;
    }
}
