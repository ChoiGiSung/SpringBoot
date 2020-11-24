package board.solo.service;

import board.solo.entity.Board;
import board.solo.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //글쓰기
    public Long wirtePost(String title,String content){
        Board board=new Board(title,content);
        Long id = boardRepository.save(board).getId();
        return id;
    }

    //페이징
    public Page<Board> findBoardList(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber()<=0 ?0:pageable.getPageNumber()-1, pageable.getPageSize());
        return boardRepository.findAll(pageable);
    }
}
