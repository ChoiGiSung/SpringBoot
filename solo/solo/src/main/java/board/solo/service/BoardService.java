package board.solo.service;

import board.solo.entity.Board;
import board.solo.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Long wirtePost(String title,String content){
        Board board=new Board(title,content);
        Long findId = boardRepository.save(board);
        return findId;
    }
}
