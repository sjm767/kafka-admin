package com.jaeshim.sample.kafka.admin.web.validator;

import com.jaeshim.sample.kafka.admin.variables.KafkaGlobalVariables;
import com.jaeshim.sample.kafka.admin.web.form.AddTopicForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AddTopicValidator implements Validator {

  @Autowired
  private KafkaGlobalVariables kafkaGlobalVariables;

  @Override
  public boolean supports(Class<?> clazz) {
    return AddTopicForm.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    AddTopicForm addTopicForm = (AddTopicForm) target;

    if(!StringUtils.hasText(addTopicForm.getTopicName())){
      errors.rejectValue("topicName","required");
    }

    if(addTopicForm.getPartition() == null || addTopicForm.getPartition() > 999){
      errors.rejectValue("partition","max",new Object[]{999},"파티션 수는 999까지 가능합니다");
    }

    if(addTopicForm.getReplication() ==null || addTopicForm.getReplication() > kafkaGlobalVariables.BROKER_COUNT){
      errors.rejectValue("replication","max",new Object[]{kafkaGlobalVariables.BROKER_COUNT},"Replication Factor는 브로커 수만큼 가능합니다");
    }
  }
}
