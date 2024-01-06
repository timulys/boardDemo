package com.example.boarddemo.entity;

import com.example.boarddemo.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "board_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String boardWriter;
    @Column
    private String boardPass;
    @Column
    private String boardTitle;
    @Column(length = 500)
    private String boardContents;
    @Column
    private int boardHits;

    private BoardEntity(Long id, String boardWriter, String boardPass, String boardTitle, String boardContents, int boardHits) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardPass = boardPass;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
    }

    public static BoardEntity of(BoardDTO boardDTO) {
        return new BoardEntity(
                boardDTO.getId(),
                boardDTO.getBoardWriter(),
                boardDTO.getBoardPass(),
                boardDTO.getBoardTitle(),
                boardDTO.getBoardContents(),
                boardDTO.getBoardHits()
        );
    }
}
