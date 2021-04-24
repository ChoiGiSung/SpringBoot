package net.honux.springbootdemo;


import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

public class User {

    @Id
    private Long id;

    private String nickname;
    private Set<Agent> agents = new HashSet<>();

    public User(String nickname) {
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }


}