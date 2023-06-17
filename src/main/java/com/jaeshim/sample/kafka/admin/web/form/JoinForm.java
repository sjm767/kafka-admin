package com.jaeshim.sample.kafka.admin.web.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinForm {

  @NotBlank
  private String loginId;

  @NotBlank
  private String password;

  @NotBlank
  private String userName;

  @Max(200)
  @NotNull
  private Integer age;
}
