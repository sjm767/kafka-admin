package com.jaeshim.sample.kafka.admin.service;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import com.jaeshim.sample.kafka.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final MemberRepository memberRepository;

  public Member process(String loginId, String password){
    return memberRepository.findByLoginId(loginId)
        .filter(m->m.getPassword().equals(password))
        .orElse(null);
  }
  public Member getMemberById(Long id){
    return memberRepository.findById(id)
        .orElse(null);
  }

}
