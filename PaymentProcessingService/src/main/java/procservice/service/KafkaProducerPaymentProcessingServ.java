package procservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import procservice.model.PayUpdateDTO;

@Service
public class KafkaProducerPaymentProcessingServ {
    private final KafkaTemplate<Integer, PayUpdateDTO> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    public KafkaProducerPaymentProcessingServ(KafkaTemplate<Integer, PayUpdateDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(int id, PayUpdateDTO payUpdateDTO) {
        ListenableFuture<SendResult<Integer, PayUpdateDTO>> future = kafkaTemplate.send(topic, id, payUpdateDTO);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }
}