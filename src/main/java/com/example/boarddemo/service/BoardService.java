package com.example.boarddemo.service;

import com.example.boarddemo.dto.BoardDTO;
import com.example.boarddemo.entity.BoardEntity;
import com.example.boarddemo.entity.BoardFileEntity;
import com.example.boarddemo.repository.BoardFileRepository;
import com.example.boarddemo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    // repositories
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    @Transactional
    public void save(BoardDTO boardDTO) {
        if (boardDTO.getBoardFile().isEmpty()) {
            boardRepository.save(BoardEntity.ofSaveEntity(boardDTO));
        } else {
            try {
                BoardEntity boardEntity = BoardEntity.ofSaveFileEntity(boardDTO);
                Long savedId = boardRepository.save(boardEntity).getId();
                BoardEntity saveBoardEntity = boardRepository.findById(savedId).get();
                for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                    String originalFilename = boardFile.getOriginalFilename();
                    String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                    String directoryPath = "C:\\springboot_images\\";
                    File dir = new File(directoryPath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    String savePath = directoryPath + storedFileName;
                    boardFile.transferTo(new File(savePath)); // Attached File Save Complete!
                    BoardFileEntity boardFileEntity = BoardFileEntity.of(saveBoardEntity, originalFilename, storedFileName);
                    boardFileRepository.save(boardFileEntity);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        Page<BoardDTO> boardDTOs = boardEntities.map(board -> BoardDTO.of(board));

        return boardDTOs;
    }
}
