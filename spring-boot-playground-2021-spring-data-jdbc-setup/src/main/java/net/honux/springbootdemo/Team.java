package net.honux.springbootdemo;

import org.springframework.data.annotation.Id;

import java.util.*;

public class Team {

    @Id
    private Long id;

    private Set<Agent> agents = new HashSet<>();
    private String teamName;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public void addAgent(Agent agent){
        agents.add(agent);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", agents=" + agents +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}