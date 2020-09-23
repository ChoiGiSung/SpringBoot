package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Member extends BaseEntity{

    @Id //기본 키야
    @GeneratedValue //숫자 자동증가
    @Column(name="MEMBER_ID") //db에 컬럼명 매칭
    private Long id;

    private String name;


    @OneToMany(mappedBy = "member") //연관관계의 주인은 member야
    private List<Order> orders=new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    //period 기간
    @Embedded
    private Period period;

    //주소 값 타입으로 사용
    @Embedded
    private Address address;

    //값 타입 컬렉션
    @ElementCollection
    @CollectionTable(name = "FaVORITE_FOOD",joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods=new HashSet<>();

    //값 타입 컬렉션
    @ElementCollection
    @CollectionTable(name = "ADDRESS",joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory=new ArrayList<>();


    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
