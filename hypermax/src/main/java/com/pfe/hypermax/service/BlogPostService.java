package com.pfe.hypermax.service;

import com.pfe.hypermax.dto.BlogPostDto;
import com.pfe.hypermax.model.BlogPost;
import com.pfe.hypermax.model.PostStatus;
import com.pfe.hypermax.model.UserDtls;
import com.pfe.hypermax.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserService userService;

    // Constructor-based injection
    public BlogPostService(BlogPostRepository blogPostRepository,
                           UserService userDtlsService) {
        this.blogPostRepository = blogPostRepository;
        this.userService = userDtlsService;
    }

    public BlogPost createPost(BlogPostDto dto, String email) {
        // Service now throws exception if user not found
        UserDtls author = userService.getUserByEmail(email);

        BlogPost post = new BlogPost();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAuthor(author);
        post.setStatus(PostStatus.PENDING);

        return blogPostRepository.save(post);
    }

    public Page<BlogPost> getPostsByUser(UserDtls user, Pageable pageable) {
        return blogPostRepository.findByAuthor(user, pageable);
    }

    // Add admin methods
    public Page<BlogPost> getAllPosts(Pageable pageable) {
        return blogPostRepository.findAll(pageable);
    }

    public Page<BlogPost> getPostsByStatus(PostStatus status, Pageable pageable) {
        return blogPostRepository.findByStatus(status, pageable);
    }

    public BlogPost getPostById(Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + id));
    }

    public void updatePostStatus(Long id, PostStatus status) {
        BlogPost post = getPostById(id);
        post.setStatus(status);
        // No need to call save() with @Transactional
    }

    // Optional: Add delete functionality
    public void deletePost(Long id) {
        blogPostRepository.deleteById(id);
    }
}