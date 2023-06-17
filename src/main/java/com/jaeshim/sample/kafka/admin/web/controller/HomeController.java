package com.jaeshim.sample.kafka.admin.web.controller;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import com.jaeshim.sample.kafka.admin.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

  private final LoginService loginService;

  @GetMapping("/")
  public String home(@CookieValue(name = "memberId", required = false) Long memberId, Model model){

    if (memberId == null) {
      return "/home/home";
    }

    //로그인
    Member loginMember = loginService.getMemberById(memberId);
    if (loginMember == null) {
      return "/home/home";
    }

    model.addAttribute("userName", loginMember.getUserName());
    return "/home/homeLogin";
  }
}
