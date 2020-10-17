package com.community.rest.controller;

import com.community.rest.domain.Board;
import com.community.rest.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class BoardRestController {
    private BoardRepository boardRepository;

    public BoardRestController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/boards")
    public @ResponseBody PagedModel<Board> simpleBoard(@PageableDefault Pageable pageable){
        Page<Board> boardList=boardRepository.findAll(pageable);

        PagedModel.PageMetadata pageMetadata=new PagedModel.PageMetadata(pageable.getPageSize(),
                                                                         boardList.getNumber(),
                                                                         boardList.getTotalElements());
        PagedModel<Board> resource=new PagedModel<>(boardList.getContent(),pageMetadata);
        resource.add(linkTo(methodOn(BoardRestController.class).simpleBoard(pageable)).withSelfRel());
        return resource;
    }

}
