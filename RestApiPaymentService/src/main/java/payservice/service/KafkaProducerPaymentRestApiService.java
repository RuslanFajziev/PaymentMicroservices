package payservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.concurrent.ListenableFutureCallback;
import payservice.config.StatusHealth;
import payservice.model.PaymentDAO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaProducerPaymentRestApiService {
    private final KafkaTemplate<Integer, PaymentDAO> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    public KafkaProducerPaymentRestApiService(KafkaTemplate<Integer, PaymentDAO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public StatusHealth send(int id, PaymentDAO paymentDAO) {
        final StatusHealth[] rsl = new StatusHealth[1];
        ListenableFuture<SendResult<Integer, PaymentDAO>> future = kafkaTemplate.send(topic, id, paymentDAO);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<Integer, PaymentDAO> result) {
                rsl[0] = StatusHealth.HEALTHY;
            }

            @Override
            public void onFailure(Throwable ex) {
                rsl[0] = StatusHealth.UNHEALTHY;
            }
        });

        kafkaTemplate.flush();
        return rsl[0];
    }
}