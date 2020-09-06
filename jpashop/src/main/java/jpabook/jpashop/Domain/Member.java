package jpabook.jpashop.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    @NotEmpty //무조건 값이 있어야함
    private String name;
    
    @Embedded //내장 타입이다 둘 중 하나만 적어주면 된다
    private Address address;

    //@JsonIgnore api에서 orders안보이게
    @OneToMany(mappedBy = "member") //일대다 관계 order와 서로 반대이다
    //mappedBy -> 나는 읽기 전용이야 연관관계의 거울이야
    //오더 테이블에 있는 member로 매핑된거야
    private List<Order> orders=new ArrayList<>();
}
