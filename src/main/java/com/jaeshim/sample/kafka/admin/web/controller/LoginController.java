package com.jaeshim.sample.kafka.admin.web.controller;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import com.jaeshim.sample.kafka.admin.domain.member.SessionMember;
import com.jaeshim.sample.kafka.admin.service.LoginService;
import com.jaeshim.sample.kafka.admin.web.SessionConst;
import com.jaeshim.sample.kafka.admin.web.form.LoginForm;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
  public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, Model model, HttpServletRequest request,
      HttpServletResponse response,@RequestParam(defaultValue = "/") String redirectURL) {
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
    HttpSession session = request.getSession();
    SessionMember sessionMember = new SessionMember(result.getUserName(),result.getRole());
    session.setAttribute(SessionConst.SESSION_COOKIE_NAME, sessionMember);
    return "redirect:"+redirectURL;
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request){
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }

    return "redirect:/";
  }
  private void expireCookie(HttpServletResponse response,String cookieName) {
    Cookie cookie = new Cookie(cookieName, null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
