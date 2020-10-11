package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AutoconfigurationApplicationTests {

    @Value("${property.test.name}")
    private String propertyTestName;

    @Value("${propertyTest}")
    private String propertyTest; //yml에 정의 한 그대로 나오네

    @Value("${noKey:default value}")
    private String defaultValue;  //이건 디폴트 값인가봐

    @Value("${propertyTestList}")
    private String[] propertyTestArray;

    @Value("#{'${propertyTestList}'.split(',')}")
    private List<String> propertyTestList;

    @Test
    public void testValue(){
        Assertions.assertEquals(propertyTestName,"property depth test");
        Assertions.assertEquals(propertyTest,"test");
        Assertions.assertEquals(defaultValue,"default value");

        //이건 ,로 여러개의 값을 넣었는데 그거또 String 배열로 뽑아올 수 있네
        Assertions.assertEquals(propertyTestArray[0],"a");
        Assertions.assertEquals(propertyTestArray[1],"b");
        Assertions.assertEquals(propertyTestArray[2],"c");

        Assertions.assertEquals(propertyTestList.get(0),"a");
        Assertions.assertEquals(propertyTestList.get(1),"b");
        Assertions.assertEquals(propertyTestList.get(2),"c");
    }
}
