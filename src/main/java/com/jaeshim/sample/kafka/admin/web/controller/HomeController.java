package com.jaeshim.sample.kafka.admin.web.controller;

import com.jaeshim.sample.kafka.admin.domain.member.SessionMember;
import com.jaeshim.sample.kafka.admin.service.LoginService;
import com.jaeshim.sample.kafka.admin.web.argumentresolver.Login;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

  private final LoginService loginService;

  @GetMapping("/")
  public String home(@Login SessionMember loginMember
      ,HttpServletRequest request
      ,Model model){

    if (loginMember == null) {
      return "/home/home";
    }

    model.addAttribute("loginMember", loginMember);
    return "/home/homeLogin";
  }
}
