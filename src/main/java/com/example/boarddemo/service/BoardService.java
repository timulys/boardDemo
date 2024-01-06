package com.example.boarddemo.service;

import com.example.boarddemo.dto.BoardDTO;
import com.example.boarddemo.entity.BoardEntity;
import com.example.boarddemo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void save(BoardDTO boardDTO) {
        boardRepository.save(BoardEntity.of(boardDTO));
    }

    @Transactional(readOnly = true)
    public List<BoardDTO> findAll() {
        return boardRepository.findAll().stream().map(entity -> BoardDTO.of(entity)).collect(Collectors.toList());
    }

    @Transactional
    public BoardDTO findById(Long id) {
        // hits ++
        boardRepository.updateHits(id);
        // BoardEntity
        return BoardDTO.of(boardRepository.findById(id).orElseGet(null));
    }

    @Transactional
    public BoardDTO update(BoardDTO boardDTO) {
        boardRepository.save(BoardEntity.of(boardDTO));
        return findById(boardDTO.getId());
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3;
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        return null;
    }
}
