package org.example.blog_project.repository;

import org.example.blog_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA에서 제공하는 메소드
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<User> findById(Long id);
    User findByUsername(String username);
}
