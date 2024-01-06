package com.example.boarddemo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "board_file_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardFileEntity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public BoardFileEntity(String originalFileName, String storedFileName, BoardEntity boardEntity) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.boardEntity = boardEntity;
    }

    public static BoardFileEntity of(BoardEntity boardEntity, String originalFileName, String storedFileName) {
        return new BoardFileEntity(originalFileName, storedFileName, boardEntity);
    }

}
