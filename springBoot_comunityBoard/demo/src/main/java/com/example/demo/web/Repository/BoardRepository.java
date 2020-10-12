package com.example.demo.web.Repository;

import com.example.demo.web.domain.Board;
import com.example.demo.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    Board findByUser(User user);
}
