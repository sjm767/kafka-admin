package com.jaeshim.sample.kafka.admin;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import com.jaeshim.sample.kafka.admin.repository.MemberRepository;
import com.jaeshim.sample.kafka.admin.role.MemberRole;
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
        .loginId("user")
        .password("user")
        .age(20)
        .userName("user")
        .role(MemberRole.USER)
        .build();

    Member admin = Member.builder()
        .loginId("admin")
        .password("admin")
        .age(20)
        .userName("admin")
        .role(MemberRole.ADMIN)
        .build();

    memberRepository.save(user);
    memberRepository.save(admin);
  }

}
