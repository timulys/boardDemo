package com.example.boarddemo.dto;

import com.example.boarddemo.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentSavedTime;

    public static CommentDTO of(CommentEntity commentEntity) {
        return new CommentDTO(
                commentEntity.getId(),
                commentEntity.getCommentWriter(),
                commentEntity.getCommentContents(),
                commentEntity.getBoardEntity().getId(),
                commentEntity.getCreatedTime()
        );
    }
}
