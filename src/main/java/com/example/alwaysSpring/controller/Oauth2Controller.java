package com.example.alwaysSpring.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class Oauth2Controller {
//    private final HttpSession httpSession;
    @Transactional
    @GetMapping("/redirect")
    public String redirect() {
        System.out.println("여기로 오시면 됩니다.");

//        SessionUsers users = (SessionUsers) httpSession.getAttribute("users");

//        System.out.println("유저 이름 : " + users.getName());
//        System.out.println("유저 메일 : " + users.getEmail());
//        System.out.println("유저 사진 : " + users.getPicture());

        return "redirect:http://localhost:3000";
    }
}
