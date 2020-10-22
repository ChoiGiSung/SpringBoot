package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "caregory_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items=new ArrayList<>();

    //카테고리 구조 자기 자신을 갖는다
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child=new ArrayList<>();
}
