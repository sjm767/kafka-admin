package com.jaeshim.sample.kafka.admin.web.form;

import lombok.Data;

@Data
public class AddTopicForm {

  private String topicName;

  private Integer partition;

  private Short replication;

}
