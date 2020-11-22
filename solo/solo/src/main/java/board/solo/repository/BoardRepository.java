package board.solo.repository;

import board.solo.entity.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BoardRepository {

    private EntityManager entityManager;
    public BoardRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Long save(Board board){
        entityManager.persist(board);
        return board.getId();
    }

    public Board find(Long id){
        Board findBoard = entityManager.find(Board.class, id);
        return findBoard;
    }

}
