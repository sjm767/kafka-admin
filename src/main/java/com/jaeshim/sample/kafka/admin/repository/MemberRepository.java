package com.jaeshim.sample.kafka.admin.repository;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

  Member save(Member member);

  Optional<Member> findById(Long id);
  Optional<Member> findByLoginId(String loginId);

  List<Member> findMembers();

}
