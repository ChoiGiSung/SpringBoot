package jpabook.jpashop.Domain;

import lombok.Getter;

import javax.persistence.Embeddable;

//어딘가의 내장이 될 수 이따
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
        //jpa에서 기본생성자를 생성해야하는 제약이 있다
        //그래야 jpa에서 사용 가능하다
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
