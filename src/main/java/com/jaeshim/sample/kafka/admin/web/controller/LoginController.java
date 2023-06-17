package com.jaeshim.sample.kafka.admin.web.controller;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import com.jaeshim.sample.kafka.admin.service.LoginService;
import com.jaeshim.sample.kafka.admin.web.form.LoginForm;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @GetMapping("/login")
  public String login(Model model){
    LoginForm loginForm = new LoginForm();
    model.addAttribute("loginForm", loginForm);
    return "/login/loginForm";
  }

  @PostMapping("/login")
  public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, Model model,
      HttpServletResponse response) {
    if (bindingResult.hasErrors()) {
      log.info("errors={}", bindingResult);
      return "/login/loginForm";
    }

    Member result = loginService.process(loginForm.getLoginId(), loginForm.getPassword());
    if(result == null){
      bindingResult.reject("login.fail");
      return "/login/loginForm";
    }

    //로그인 성공 처리
    //쿠키에 시간 정보를 주지 않으면 세션 쿠키 (브라우저 종료시 모두 종료)
    Cookie idCookie = new Cookie("memberId", String.valueOf(result.getId()));
    response.addCookie(idCookie);
    return "redirect:/";
  }

  @GetMapping("/logout")
  public String logout(HttpServletResponse response){
    expireCookie(response,"memberId");
    return "redirect:/";
  }
  private void expireCookie(HttpServletResponse response,String cookieName) {
    Cookie cookie = new Cookie(cookieName, null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
