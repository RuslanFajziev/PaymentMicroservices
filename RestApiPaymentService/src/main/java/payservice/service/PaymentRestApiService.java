package payservice.service;

import lombok.AllArgsConstructor;
import payservice.model.PaymentDAO;
import payservice.repository.PaymentRestApiRepos;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentRestApiService {
    private final PaymentRestApiRepos repos;
    private final KafkaProducerPaymentRestApiService serviceKafkaProducer;

    public List<PaymentDAO> findAll() {
        List<PaymentDAO> listPayment = new ArrayList<>();
        repos.findAll().forEach(listPayment::add);
        return listPayment;
    }

    public Optional<PaymentDAO> findById(int id) {
        return repos.findById(id);
    }

    public List<PaymentDAO> findByFilter(String nameService, int amount, String statusPayment) {
        List<PaymentDAO> listPayment;

        if (!nameService.isEmpty() && !statusPayment.isEmpty() && amount != 0) {
            listPayment = repos.findByNameServiceAndStatusPaymentAndAmount(nameService, statusPayment, amount);
        } else if (!nameService.isEmpty() && statusPayment.isEmpty() && amount == 0) {
            listPayment = repos.findByNameService(nameService);
        } else if (nameService.isEmpty() && !statusPayment.isEmpty() && amount == 0) {
            listPayment = repos.findByStatusPayment(statusPayment);
        } else if (nameService.isEmpty() && statusPayment.isEmpty() && amount != 0) {
            listPayment = repos.findByAmount(amount);
        } else if (!nameService.isEmpty() && !statusPayment.isEmpty() && amount == 0) {
            listPayment = repos.findByNameServiceAndStatusPayment(nameService, statusPayment);
        } else if (!nameService.isEmpty() && statusPayment.isEmpty() && amount != 0) {
            listPayment = repos.findByNameServiceAndAmount(nameService, amount);
        } else if (nameService.isEmpty() && !statusPayment.isEmpty() && amount != 0) {
            listPayment = repos.findByStatusPaymentAndAmount(statusPayment, amount);
        } else {
            listPayment = new ArrayList<>();
        }

        return listPayment;
    }

    public PaymentDAO save(PaymentDAO paymentDAO) {
        var payDAO = repos.save(paymentDAO);
        serviceKafkaProducer.send(payDAO.getId(), payDAO);

        return payDAO;
    }

    public int update(UUID correlationId, String statusPayment) {
        return repos.update(correlationId, statusPayment);
    }
}