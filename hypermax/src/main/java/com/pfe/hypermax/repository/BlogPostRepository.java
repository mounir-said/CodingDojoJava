package com.pfe.hypermax.repository;

import com.pfe.hypermax.model.BlogPost;
import com.pfe.hypermax.model.PostStatus;
import com.pfe.hypermax.model.UserDtls;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    Page<BlogPost> findByStatus(PostStatus status, Pageable pageable);
    Page<BlogPost> findByAuthor(UserDtls author, Pageable pageable);

    Page<BlogPost> findAll(Pageable pageable);

}