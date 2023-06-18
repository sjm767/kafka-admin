package com.jaeshim.sample.kafka.admin.domain.member;

import com.jaeshim.sample.kafka.admin.role.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionMember {

  private String userName;

  private MemberRole role;

}
