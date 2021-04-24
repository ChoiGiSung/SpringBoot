package net.honux.springbootdemo;

import org.springframework.data.annotation.Id;

public class Agent {

    @Id
    private Long id;

    private Long team;
    private Long user;

    public Agent() {
    }

    public Agent(Long team) {
        this.user = team;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", team=" + team +
                ", user=" + user +
                '}';
    }
}
