package procservice.service;

import java.util.LinkedList;
import java.util.Queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import procservice.model.PaymentDAO;
import procservice.model.PayUpdateDTO;

@Service
@Slf4j
public class QueuePaymentProcessingServ {
    final private KafkaProducerPaymentProcessingServ serviceKafkaProducer;
    final private PaymentProcessingService paymanetService;
    final private Queue<PaymentDAO> queuePay = new LinkedList<>();

    public QueuePaymentProcessingServ(KafkaProducerPaymentProcessingServ serviceKafkaProducer, PaymentProcessingService paymanetService) {
        this.serviceKafkaProducer = serviceKafkaProducer;
        this.paymanetService = paymanetService;
    }

    public void enqueue(PaymentDAO paymentDAO) {
        queuePay.add(paymentDAO);
        log.info("\nPaymentDAO queued for processing:\n{}", paymentDAO);
        processing();
    }

    public void processing() {
        PaymentDAO paymentDAO;
        while ((paymentDAO = queuePay.poll()) != null) {
            paymentDAO.setStatusPayment("complete");
            int rsl = paymanetService.update(paymentDAO.getCorrelationId(), paymentDAO.getStatusPayment());
            if (rsl > 0) {
                log.info("\nPaymentDAO complete:\n{}", paymentDAO);
                PayUpdateDTO payUpdateDTO = PayUpdateDTO.of(paymentDAO.getCorrelationId(), paymentDAO.getStatusPayment());

                serviceKafkaProducer.send(paymentDAO.getId(), payUpdateDTO);
            }
        }
    }
}