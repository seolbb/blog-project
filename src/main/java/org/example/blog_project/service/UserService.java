package org.example.blog_project.service;

import lombok.RequiredArgsConstructor;
import org.example.blog_project.domain.Role;
import org.example.blog_project.domain.User;
import org.example.blog_project.repository.RoleRepository;
import org.example.blog_project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // 아이디 중복체크
    @Transactional(readOnly = true)
    public Boolean checkUsername(String username){
        return userRepository.existsByUsername(username);
    }

    // 이메일 중복체크
    @Transactional(readOnly = true)
    public Boolean checkEmail(String email){
        return userRepository.existsByEmail(email);
    }

    // 회원가입
    @Transactional
    public User createUser(User user){
        // role 설정
        Role userRole = roleRepository.findByName("USER");
        user.setRoles(Collections.singleton(userRole));
        // password 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // id로 유저 찾기
    @Transactional(readOnly = true)
    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    // username으로 유저 찾기
    @Transactional(readOnly = true)
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    // 회원 정보 수정
    @Transactional
    public User updateUser(Long id, User updateUser){
        User findUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        findUser.setUsername(updateUser.getUsername());
        findUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        findUser.setEmail(updateUser.getEmail());
        findUser.setBlogTitle(updateUser.getBlogTitle());
        findUser.setProfileImage(updateUser.getProfileImage());

        return userRepository.save(findUser);
    }

    // 유저 삭제
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
