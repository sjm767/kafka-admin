package com.jaeshim.sample.kafka.admin.web.controller;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import com.jaeshim.sample.kafka.admin.service.JoinService;
import com.jaeshim.sample.kafka.admin.web.form.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class JoinController {

  private final JoinService joinService;

  @GetMapping("/join")
  public String join(Model model){
    JoinForm joinForm = new JoinForm();
    model.addAttribute("joinForm", joinForm);

    return "/join/joinForm";
  }

  @PostMapping("/join")
  public String join(@Validated @ModelAttribute JoinForm joinForm, BindingResult bindingResult,Model model) {
    if (bindingResult.hasErrors()) {
      return "/join/joinForm";
    }

    Member member = Member.builder()
        .loginId(joinForm.getLoginId())
        .age(joinForm.getAge())
        .password((joinForm.getPassword()))
        .build();

    Member result = joinService.join(member);

    if(result == null){
      model.addAttribute("message", "이미 존재하는 회원입니다");
      model.addAttribute("nextUrl", "/join");
      return "/alert/messageAlert";
    }

    return "redirect:/login";
  }
}
