package com.example.boarddemo.dto;

import com.example.boarddemo.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    public static BoardDTO of(BoardEntity boardEntity) {
        return new BoardDTO(
                boardEntity.getId(),
                boardEntity.getBoardWriter(),
                boardEntity.getBoardPass(),
                boardEntity.getBoardTitle(),
                boardEntity.getBoardContents(),
                boardEntity.getBoardHits(),
                boardEntity.getCreatedTime(),
                boardEntity.getUpdatedTime()
        );
    }
}
