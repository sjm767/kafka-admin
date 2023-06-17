package com.jaeshim.sample.kafka.admin.service;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import com.jaeshim.sample.kafka.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

  private final MemberRepository memberRepository;

  public Member join(Member member) {
    if (memberRepository.findByLoginId(member.getLoginId()) != null) {
      return null;
    }

    return memberRepository.save(member);
  }
}
