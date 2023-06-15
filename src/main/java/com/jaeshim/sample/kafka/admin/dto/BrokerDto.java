package com.jaeshim.sample.kafka.admin.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BrokerDto {

  private String brokerId;

  private String host;

  private Integer port;

  private String ip;

  @Builder.Default
  private Boolean isController = false;

}
