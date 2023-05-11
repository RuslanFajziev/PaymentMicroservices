package payservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import payservice.model.PaymentDAO;
import payservice.repository.PaymentRestApiRepos;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class PaymentRestApiService {
    private final PaymentRestApiRepos repos;
    private final JWTRestApiService service;
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
        paymentDAO.setUserCreator(service.getFioUser(paymentDAO.getUserCreator()));

        var payDAO = repos.save(paymentDAO);
        try {
            serviceKafkaProducer.send(payDAO.getId(), payDAO);
        } catch (Exception ex) {
            log.error("\nfailed to deliver pay\n{}", ex);
        }

        return payDAO;
    }

    public int update(UUID correlationId, String statusPayment) {
        return repos.update(correlationId, statusPayment);
    }
}