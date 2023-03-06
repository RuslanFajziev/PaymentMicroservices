package procservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import procservice.repository.PaymentProcessingRepos;
import procservice.model.PaymentDAO;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentProcessingService {
    private final PaymentProcessingRepos repos;

    public PaymentDAO save(PaymentDAO paymentDAO) {
        return repos.save(paymentDAO);
    }

    public int update(UUID correlationId, String statusPayment) {
        return repos.update(correlationId, statusPayment);
    }
}