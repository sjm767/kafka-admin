package com.jaeshim.sample.kafka.admin.web.controller;


import com.jaeshim.sample.kafka.admin.dto.BrokerConfigDto;
import com.jaeshim.sample.kafka.admin.dto.BrokerDto;
import com.jaeshim.sample.kafka.admin.service.KafkaService;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class BrokerController {

  private final KafkaService kafkaService;

  @GetMapping("/brokers")
  public String brokers(Model model) throws ExecutionException, InterruptedException {
    List<BrokerDto> brokers = kafkaService.getBrokers();

    model.addAttribute("brokers", brokers);
    return "/broker/brokers";
  }

  @GetMapping("/broker/{brokerId}")
  public String broker(@PathVariable String brokerId, Model model) throws ExecutionException, InterruptedException {
    List<BrokerConfigDto> brokerConfigs = kafkaService.getBrokerConfigs(brokerId);

    model.addAttribute("brokerConfigs", brokerConfigs);
    return "/broker/broker";
  }

}
