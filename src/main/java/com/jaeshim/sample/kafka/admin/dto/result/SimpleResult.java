package com.jaeshim.sample.kafka.admin.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleResult {

  private Integer resultCode;

  private String reason;


}
