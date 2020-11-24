package board.solo.repository;

import board.solo.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {


  //  private EntityManager entityManager;
//    public BoardRepository(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    public Long save(Board board){
//        entityManager.persist(board);
//        return board.getId();
//    }
//
//    public Board find(Long id){
//        Board findBoard = entityManager.find(Board.class, id);
//        return findBoard;
//    }
//
//    //페이징
//    public Page<Board> findAll(Pageable pageable) {
//        List<Board> select_b_from_board_m = entityManager.createQuery("select b from Board m", Board.class)
//                .setFirstResult(pageable.getPageNumber())
//                .setMaxResults(pageable.getPageSize())
//                .getResultList();
//
//    }




}
