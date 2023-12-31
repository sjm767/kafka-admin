package com.jaeshim.sample.kafka.admin.web.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddTopicForm {

  @NotBlank
  private String topicName;

  @Max(999)
  @NotNull
  private Integer partition;

  @Max(999)
  @NotNull
  private Short replication;

}
