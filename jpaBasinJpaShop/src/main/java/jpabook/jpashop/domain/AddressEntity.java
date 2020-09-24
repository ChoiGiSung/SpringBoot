package jpabook.jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    //멤버에서 값 타입 컬렉션을 쓴느 대신에 엔티티를 쓴다
    //값 타입 컬렉션도 어차피 테이블을 만들어 내니까
    
    @Id
    @GeneratedValue
    private Long id;
    
    
    //이게 값 타입
    private Address address;

    public AddressEntity() {
    }

    //어드레스 생성해서 넣어주기
    public AddressEntity(String das2, String ad, String asd) {
        this.address=new Address(das2,ad,asd);
    }
}
