package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.domain2.Category;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;
    private int stocQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories=new ArrayList<>();



    //==비지니스 로직==//

    /*
    * 제고 증가
    * */
    public void addStock(int quantity){
        this.stocQuantity+=quantity;
    }
    /*
     * 제고감소
     * */
    public void removeStock(int quantity){
        int restStock=this.stocQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stocQuantity=restStock;
    }

    /*
    * 수정 사항 업데이트
    * */
    public void change(Long id,String name,int price,int stocQuantity){
        this.name=name;
        this.stocQuantity= stocQuantity;
        this.price=price;
    }
}
