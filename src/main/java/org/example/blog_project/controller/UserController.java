package org.example.blog_project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.example.blog_project.domain.User;
import org.example.blog_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 메인화면
    @GetMapping("/")
    public String mainPage(){
        return "blog/home";
    }

    // 회원가입 화면 이동
    @GetMapping("/userregform")
    public String userregform(){
        return "auth/userregform";
    }

    // 회원가입
    @PostMapping("/userreg")
    public String createUser(@ModelAttribute User user, @RequestParam("confirmPassword") String confirmPassword, Model model) {
        // 비밀번호 확인 검증
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "“Validation errors”");
            return "auth/regerror";
        }

        // 아이디 중복체크
        if(userService.checkUsername(user.getUsername())){
            model.addAttribute("error", "Username is already taken");
            return "auth/regerror";
        }
        // 이메일 중복체크
        if(userService.checkEmail(user.getEmail())){
            model.addAttribute("error", "Email is already taken");
            return "auth/regerror";
        }

        // 모든 유효성 검사 완료
        userService.createUser(user);

        return "redirect:/welcome";
    }

    // 회원가입 성공 화면 이동
    @GetMapping("/welcome")
    public String welcomeform(){
        return "auth/welcome";
    }

    // 로그인 화면 이동
    @GetMapping("/loginform")
    public String loginform(){
        return "auth/loginform";
    }

    // 로그인 실패 화면 이동
    @GetMapping("/loginerror")
    public String loginerror(Model model) {
        model.addAttribute("error", "아이디/비밀번호가 일치하지 않습니다.");
        return "auth/loginerror";
    }

    // 마이페이지 이동
    // TODO : username 수정 후 마이페이지로 이동하면 에러 발생함.
    @GetMapping("/mypage")
    public String mypage(Model model, Authentication authentication){
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "auth/mypage";
    }


    // 회원 정보 수정
    @GetMapping("/userupdateform")
    public String userupdate(Authentication authentication, Model model){
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "auth/userupdateform";
    }

    @GetMapping("/userdelete")
    public String userdelete(Authentication authentication, Model model){
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("userId", user.getId());
        return "auth/deleteUser";
    }




}
