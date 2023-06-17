package com.jaeshim.sample.kafka.admin.domain.member;

import com.jaeshim.sample.kafka.admin.role.MemberRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {

  private Long id;

  private String loginId;

  private String password;

  private String userName;

  private Integer age;

  @Builder.Default
  private MemberRole role=MemberRole.USER;

}
