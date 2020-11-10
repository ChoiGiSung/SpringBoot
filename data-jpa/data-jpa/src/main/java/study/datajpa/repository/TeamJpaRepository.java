package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class TeamJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Team save(Team team){
        entityManager.persist(team);
        return team;
    }

    public void delete(Team team){
        entityManager.remove(team);
    }

    public List<Team> findAll(){
        return entityManager.createQuery("select t from Member Team t",Team.class).getResultList();
    }

    //옵셔널로 조회
    public Optional<Team> findById(Long id){
        Team team=entityManager.find(Team.class,id);
        return Optional.ofNullable(team); //널일 수도 아닐수도 있다
    }
    //카운트
    public Team count(){
        return entityManager.createQuery("select count(t) from Team t",Team.class).getSingleResult();
    }


}
