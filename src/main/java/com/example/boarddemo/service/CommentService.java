package com.example.boarddemo.service;

import com.example.boarddemo.dto.CommentDTO;
import com.example.boarddemo.entity.BoardEntity;
import com.example.boarddemo.entity.CommentEntity;
import com.example.boarddemo.repository.BoardRepository;
import com.example.boarddemo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long save(CommentDTO commentDTO) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardId);
        if (optionalBoardEntity.isPresent()) {
            return commentRepository.findAllByBoardEntityOrderByIdDesc(optionalBoardEntity.get())
                    .stream().map(comment -> CommentDTO.of(comment)).collect(Collectors.toList());
        }
        return null;
    }
}
