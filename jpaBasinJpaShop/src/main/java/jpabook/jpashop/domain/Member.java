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

    //값 타입 컬렉션 은 영속성 전이 + 고아객체의 기능을 가진다
    @ElementCollection
    @CollectionTable(name = "FaVORITE_FOOD",joinColumns = @JoinColumn(name = "MEMBER_ID"))
    //테이블을 새로 만든다 외래키는 멤버 id를 받아서
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods=new HashSet<>();

    //값 타입 컬렉션 은 지연로딩이다 처음 멤버를 조회하면 join문이 안나가고 나중에 사용하면 그때 접근한다
//    @ElementCollection
//    @CollectionTable(name = "ADDRESS",joinColumns = @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory=new ArrayList<>();
    //쓰지말자 대안 방안

    //그래서 어드레스를 엔티티로 승격시키고 그걸 사용한다.
    public List<AddressEntity> getAddressEntities() {
        return addressEntities;
    }

    public void setAddressEntities(List<AddressEntity> addressEntities) {
        this.addressEntities = addressEntities;
    }

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID") //일대다 단방향
    private List<AddressEntity> addressEntities=new ArrayList<>();


    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

//    public List<Address> getAddressHistory() {
//        return addressHistory;
//    }
//
//    public void setAddressHistory(List<Address> addressHistory) {
//        this.addressHistory = addressHistory;
//    }

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
