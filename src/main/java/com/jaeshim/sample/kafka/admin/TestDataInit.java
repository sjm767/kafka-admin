package com.jaeshim.sample.kafka.admin;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import com.jaeshim.sample.kafka.admin.repository.MemberRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

  private final MemberRepository memberRepository;

  @PostConstruct
  public void init(){
    //user
    Member user = Member.builder()
        .loginId("test")
        .password("test123")
        .age(20)
        .userName("user")
        .build();

    Member admin = Member.builder()
        .loginId("admin")
        .password("admin")
        .age(20)
        .userName("admin")
        .build();

    memberRepository.save(user);
    memberRepository.save(admin);
  }

}
