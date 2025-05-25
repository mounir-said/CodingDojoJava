package com.pfe.hypermax.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "blog_posts")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String content;

    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDtls author;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public BlogPost() {
    }

    public BlogPost(Long id, String title, String content, PostStatus status, UserDtls author, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.author = author;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public UserDtls getAuthor() {
        return author;
    }

    public void setAuthor(UserDtls author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}