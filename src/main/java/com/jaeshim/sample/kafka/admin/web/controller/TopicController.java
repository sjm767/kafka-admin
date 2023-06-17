package com.jaeshim.sample.kafka.admin.web.controller;


import com.jaeshim.sample.kafka.admin.dto.TopicDto;
import com.jaeshim.sample.kafka.admin.service.KafkaService;
import com.jaeshim.sample.kafka.admin.web.form.AddTopicForm;
import java.util.List;
import java.util.concurrent.ExecutionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartitionInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    return "/topic/topics";
  }

  @GetMapping("/topic/{topicName}")
  public String topic(@PathVariable String topicName, Model model)
      throws ExecutionException, InterruptedException {

    List<TopicPartitionInfo> topicPartitions = kafkaService.getTopicPartitionInfo(topicName);
    model.addAttribute("topicPartitions", topicPartitions);
    model.addAttribute("topicName", topicName);

    return "/topic/topic";
  }

  @GetMapping("/topic/add")
  public String topicAddForm(Model model){
    model.addAttribute("addTopicForm", new AddTopicForm());
    return "/topic/topicAddForm";
  }

  @PostMapping("/topic/add")
  public String addTopic(@Validated @ModelAttribute AddTopicForm addTopicForm, BindingResult bindingResult, Model model)
      throws InterruptedException {
    try {
      if (bindingResult.hasErrors()) {
        log.info("errors={}", bindingResult);
        return "/topic/topicAddForm";
      }

      kafkaService.createTopic(addTopicForm.getTopicName(), addTopicForm.getPartition(),
          addTopicForm.getReplication());
    } catch (ExecutionException e){
      model.addAttribute("message", e.getMessage());
      model.addAttribute("nextUrl", "/kafka/topic/add");
      return "/alert/messageAlert";
    }
    return "redirect:/kafka/topic/" + addTopicForm.getTopicName();
  }

}
