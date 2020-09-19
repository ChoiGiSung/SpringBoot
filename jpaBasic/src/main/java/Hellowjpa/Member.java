package Hellowjpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //identity로 하면 persist할때 인서트 쿼리가 날라간다,
    //내가 직접생성 오토는 알아서 생성 오라클은- 시퀀스, 마이sql은 -identity
    private Long id;

    @Column(name="name")
    private String name;

    public Member() {
    }



//
//    @Enumerated(EnumType.STRING) //이넘터입은 varchar로
//    private RoleType roleType;
//
//    @Temporal(TemporalType.TIMESTAMP) //날짜 관련는 timestamp
//    private Date createDate;
//
//    @Temporal(TemporalType.DATE)//날짜 관련는 timestamp
//    private Date lastModifiedDate;
//
//    @Lob// 문자는 clob 나머진 blob 타입으로
//    private String descripton; //clob
//
//    private LocalDate test1; //data 년월
//    private LocalDateTime test2; //timestamp 년월시간
//
//    @Transient//메모리에서만 계산 db와 매핑 x
//    private int temp;
}
