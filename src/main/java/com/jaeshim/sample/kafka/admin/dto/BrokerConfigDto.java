package com.jaeshim.sample.kafka.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BrokerConfigDto {

  private String key;

  private String value;

}
