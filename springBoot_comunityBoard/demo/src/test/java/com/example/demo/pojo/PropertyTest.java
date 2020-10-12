//package com.example.demo.pojo;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@ActiveProfiles("local") //서로 다른 프로파일 값을 줄 수 있다.
//public class PropertyTest {
//
//    @Autowired
//    FruitProperty fruitProperty;
//
//    @Test
//    public void test(){
//        List<Fruit> fruitData=fruitProperty.getList();
//        //원래는 List<Map> 을 사용 했는데 POJO(자바 객체) 를 사용 할 수 도 있다
//
////        Assertions.assertEquals(fruitData.get(0).get("name"),"banana");
////        Assertions.assertEquals(fruitData.get(0).get("color"),"yellow");
//
//        Assertions.assertEquals(fruitData.get(0).getName(),"banana");
//        Assertions.assertEquals(fruitData.get(0).getColor(),"yellow");
//    }
//}
