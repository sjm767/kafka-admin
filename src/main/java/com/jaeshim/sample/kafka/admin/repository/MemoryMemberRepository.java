package com.jaeshim.sample.kafka.admin.repository;

import com.jaeshim.sample.kafka.admin.domain.member.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryMemberRepository implements MemberRepository {

  private static Map<Long, Member> store = new HashMap<>();

  private static Long sequence = 0L;

  @Override
  public Member save(Member member) {
    member.setId(++sequence);
    store.put(member.getId(), member);
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    return Optional.of(store.get(id));
  }

  @Override
  public Optional<Member> findByLoginId(String loginId) {
    return findMembers().stream()
        .filter(member -> loginId.equals(member.getLoginId()))
        .findFirst();
  }

  @Override
  public List<Member> findMembers() {
    return new ArrayList<>(store.values());
  }
}
