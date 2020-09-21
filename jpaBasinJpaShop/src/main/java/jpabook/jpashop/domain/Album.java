package jpabook.jpashop.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") //자식테이블의 이름을 바꿔야 할떄 사용
public class Album  extends Item{
    private String Artist;
}
