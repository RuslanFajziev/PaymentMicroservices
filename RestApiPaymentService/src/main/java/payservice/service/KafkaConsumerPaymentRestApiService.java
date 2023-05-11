package payservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import payservice.model.PayUpdateDTO;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumerPaymentRestApiService {
    private final PaymentRestApiService service;

    @KafkaListener(topics = {"#{'${spring.kafka.consumer.topic}'.split(',')}"})
    public void orderListener(ConsumerRecord<Integer, PayUpdateDTO> record) {
        var payUpdateDTO = record.value();

        log.info("\npartition:{}, key{}, value:{}", record.partition(), record.key(), payUpdateDTO);

        var rowUpdated = service.update(payUpdateDTO.correlationId, payUpdateDTO.statusPayment);
        if (rowUpdated > 0) {
            log.info("\nPaymentDAO update status:\n{}", payUpdateDTO);
        }
    }
}