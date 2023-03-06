package procservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import procservice.model.PaymentDAO;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumerPaymentProcessingServ {
    private final PaymentProcessingService paymanetService;
    private final QueuePaymentProcessingServ queuePayment;

    @KafkaListener(topics = {"#{'${spring.kafka.consumer.topic}'.split(',')}"})
    public void orderListener(ConsumerRecord<Integer, PaymentDAO> record) {
        var paymentDAO = record.value();

        System.out.printf("partition:%s, key:%s, value:%s\n",
                record.partition(), record.key(), paymentDAO);
        if (!paymentDAO.getTestPay()) {
            log.info("\nPaymentDAO received:\n{}", paymentDAO);

            paymentDAO.setStatusPayment("processing");
            paymentDAO.setId(0);
            paymanetService.save(paymentDAO);
            queuePayment.enqueue(paymentDAO);
        }
    }
}