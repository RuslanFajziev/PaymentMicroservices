package payservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import payservice.model.PaymentDAO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRestApiRepos extends CrudRepository<PaymentDAO, Integer> {
    Optional<PaymentDAO> findByCorrelationId(UUID correlationId);

    List<PaymentDAO> findByNameService(String nameService);

    List<PaymentDAO> findByStatusPayment(String statusPayment);

    List<PaymentDAO> findByAmount(int amount);

    List<PaymentDAO> findByNameServiceAndStatusPayment(String nameService, String statusPayment);

    List<PaymentDAO> findByNameServiceAndAmount(String nameService, int amount);

    List<PaymentDAO> findByStatusPaymentAndAmount(String statusPayment, int amount);

    List<PaymentDAO> findByNameServiceAndStatusPaymentAndAmount(String nameService, String statusPayment, int amount);

    @Modifying
    @Transactional
    @Query("update PaymentDAO pay set pay.statusPayment = :statusPay where pay.correlationId = :corrId")
    int update(UUID corrId, String statusPay);
}