package payservice.repository;

import org.springframework.data.repository.CrudRepository;
import payservice.model.PaymentDAO;

import java.util.Optional;

public interface HealthchekRestApiRepos extends CrudRepository<PaymentDAO, Integer> {
    Optional<PaymentDAO> findTopByOrderByIdAsc();
}