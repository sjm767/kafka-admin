package com.jaeshim.sample.kafka.admin.service;

import com.jaeshim.sample.kafka.admin.constant.SimpleResultConsts;
import com.jaeshim.sample.kafka.admin.dto.BrokerConfigDto;
import com.jaeshim.sample.kafka.admin.dto.BrokerDto;
import com.jaeshim.sample.kafka.admin.dto.result.SimpleResult;
import com.jaeshim.sample.kafka.admin.dto.TopicDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.DescribeConfigsOptions;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.ConfigResource.Type;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

  private final AdminClient adminClient;


  public List<BrokerDto> getBrokers() throws InterruptedException, ExecutionException {
    Collection<Node> nodes = adminClient.describeCluster().nodes().get();
    Node controller = adminClient.describeCluster().controller().get();

    List<BrokerDto> brokers = new ArrayList<>();
    for (Node node : nodes) {
      BrokerDto brokerDto = BrokerDto.builder()
          .brokerId(node.idString())
          .host(node.host())
          .port(node.port())
          .build();

      if (node.idString().equals(controller.idString())) {
        brokerDto.setIsController(true);
      }

      brokers.add(brokerDto);
    }
    return brokers;
  }

  public List<BrokerConfigDto> getBrokerConfigs(String brokerId)
      throws ExecutionException, InterruptedException {
    List<BrokerConfigDto> result = new ArrayList<>();

    ConfigResource brokerResource = new ConfigResource(Type.BROKER, brokerId);
    DescribeConfigsOptions brokerOptions = new DescribeConfigsOptions().includeSynonyms(false);
    Map<ConfigResource, Config> brokerResourceConfigMap = adminClient.describeConfigs(
        Collections.singletonList(brokerResource), brokerOptions).all().get();

    Collection<ConfigEntry> entries = brokerResourceConfigMap.get(brokerResource).entries();

    entries.stream().forEach(config -> {
      result.add(new BrokerConfigDto(config.name(), config.value()));
    });

    return result;
  }

  public List<TopicDto> getTopics() throws ExecutionException, InterruptedException {
    List<TopicDto> result = new ArrayList<>();

    Collection<TopicListing> topics = adminClient.listTopics().namesToListings()
        .get().values();

    for (TopicListing topic : topics) {
      result.add(TopicDto.builder()
          .name(topic.name())
          .partitionCount(getPartitionCount(topic.name()))
          .build());
    }
    return result;
  }

  public List<TopicPartitionInfo> getTopicPartitionInfo(String topicName)
      throws ExecutionException, InterruptedException {
    DescribeTopicsResult topic = adminClient.describeTopics(
        Collections.singletonList(topicName));
    return topic.allTopicNames().get().get(topicName).partitions();
  }

  public SimpleResult createTopic(String topicName, Integer partition, Short replicationFactor) {
    NewTopic newTopic = new NewTopic(topicName, partition, replicationFactor);
    try {
      adminClient.createTopics(Collections.singleton(newTopic)).all().get();
    } catch (Exception e) {
      return new SimpleResult(SimpleResultConsts.BAD, e.getMessage());
    }
    return new SimpleResult(SimpleResultConsts.OK, "");
  }

  private Integer getPartitionCount(String topicName) {
    try {
      return getTopicPartitionInfo(topicName).size();
    } catch (ExecutionException e) {
      return 0;
    } catch (InterruptedException e) {
      return 0;
    }
  }
}
