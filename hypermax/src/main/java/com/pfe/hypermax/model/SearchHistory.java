package com.pfe.hypermax.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "search_history")
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String query;

    @CreationTimestamp
    @Column(name = "search_date", nullable = false)
    private LocalDateTime searchDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDtls user;
}