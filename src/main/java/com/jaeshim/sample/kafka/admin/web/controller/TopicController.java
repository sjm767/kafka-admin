package com.jaeshim.sample.kafka.admin.web.controller;


import com.jaeshim.sample.kafka.admin.dto.TopicDto;
import com.jaeshim.sample.kafka.admin.service.KafkaService;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartitionInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/kafka")
public class TopicController {

  private final KafkaService kafkaService;

  @GetMapping("/topics")
  public String topics(Model model) throws ExecutionException, InterruptedException {
    List<TopicDto> topics = kafkaService.getTopics();
    model.addAttribute("topics", topics);

    return "topics";
  }

  @GetMapping("/topic/{topicName}")
  public String topic(@PathVariable String topicName, Model model)
      throws ExecutionException, InterruptedException {

    List<TopicPartitionInfo> topicPartitions = kafkaService.getTopicPartitionInfo(topicName);
    model.addAttribute("topicPartitions", topicPartitions);
    model.addAttribute("topicName", topicName);

    return "topic";
  }

}
