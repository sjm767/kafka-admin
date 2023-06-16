package com.jaeshim.sample.kafka.admin.variables;


import com.jaeshim.sample.kafka.admin.service.KafkaService;
import java.util.concurrent.ExecutionException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaGlobalVariables {

  private final KafkaService kafkaService;

  public Integer BROKER_COUNT;

  @PostConstruct
  public void init() throws ExecutionException, InterruptedException {
    BROKER_COUNT =kafkaService.getBrokers().size();
  }

}
