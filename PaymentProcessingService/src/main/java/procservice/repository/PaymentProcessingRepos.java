package procservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import procservice.model.PaymentDAO;

import java.util.UUID;

public interface PaymentProcessingRepos extends CrudRepository<PaymentDAO, Integer> {
    @Modifying
    @Transactional
    @Query("update PaymentDAO pay set pay.statusPayment = :statusPay where pay.correlationId = :corrId")
    int update(UUID corrId, String statusPay);
}