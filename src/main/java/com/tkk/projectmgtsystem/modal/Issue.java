package com.tkk.projectmgtsystem.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

@Entity
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 課題のタイトル
    private String title;

    // 課題の説明
    private String description;

    // 課題のステータス
    private String status;

    // 関連するプロジェクトのID
    private Long projectID;

    // 課題の優先度
    private String priority;

    // 課題の期限日
    private LocalDate dueData;

    // 課題に関連するタグのリスト
    private List<String> tags = new ArrayList<>();

    // 課題を担当するユーザー（多対1のリレーション）
    @ManyToOne
    private User assignee;

    // 課題が関連するプロジェクト（多対1のリレーション）
    @JsonIgnore
    @ManyToOne
    private Project project;

    @JsonIgnore
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
