package org.example.blog_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.blog_project.domain.Post;
import org.example.blog_project.domain.User;
import org.example.blog_project.service.PostService;
import org.example.blog_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;
    private final PostService postService;

    private final PasswordEncoder passwordEncoder;

    // 아이디 중복체크
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userService.checkUsername(username);
        return ResponseEntity.ok(exists);
    }

    // 이메일 중복체크
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userService.checkEmail(email);
        return ResponseEntity.ok(exists);
    }

    // 회원 정보 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        User user = userService.updateUser(userId, userDetails);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long userId, @RequestBody Map<String, String> payload) {
        String password = payload.get("password");
        User user = userService.findById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        userService.deleteUser(userId);
        return ResponseEntity.ok(true);
    }

    // 사용자 글 목록 조회
    @GetMapping("/{username}/posts")
    public ResponseEntity<List<Post>> findAllPostsByUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Post> posts = postService.findAllPostsByUser(user);
        return ResponseEntity.ok(posts);
    }

}
