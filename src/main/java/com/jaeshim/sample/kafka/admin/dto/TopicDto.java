package com.jaeshim.sample.kafka.admin.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TopicDto {

  private String name;

  private Integer partitionCount;
}
