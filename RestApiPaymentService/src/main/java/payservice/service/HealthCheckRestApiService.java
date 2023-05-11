package payservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import payservice.config.StatusHealth;
import payservice.model.PaymentDAO;
import payservice.repository.HealthchekRestApiRepos;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HealthCheckRestApiService implements Runnable {
    private final HealthchekRestApiRepos repos;
    private final KafkaProducerPaymentRestApiService serviceKafkaProducer;
    private Map<String, StatusHealth> mapStatus;

    public HealthCheckRestApiService(HealthchekRestApiRepos repos, KafkaProducerPaymentRestApiService serviceKafkaProducer) {
        this.repos = repos;
        this.serviceKafkaProducer = serviceKafkaProducer;
        this.mapStatus = new HashMap<>();
    }

    public String getStatus() {
        return mapStatus.get("Status").toString();
    }

    public StatusHealth checkHealthSql() {
        try {
            var optPay = repos.findTopByOrderByIdAsc();
            return StatusHealth.HEALTHY;
        } catch (Exception ex) {
            log.error("\nfailed to deliver query\n{}", ex);
            return StatusHealth.DEGRADED;
        }
    }

    public StatusHealth checkHealthKafka() {
        var pay = new PaymentDAO();
        pay.setTestPay(true);

        try {
            return serviceKafkaProducer.send(0, pay);
        } catch (Exception ex) {
            log.error("\nfailed to deliver message\n{}", ex);
            return StatusHealth.DEGRADED;
        }
    }

    public void checkHealthAll() {
        var rslSql = checkHealthSql();
        var rslKafka = checkHealthKafka();

        if (rslSql.equals(StatusHealth.DEGRADED) || rslKafka.equals(StatusHealth.DEGRADED)) {
            mapStatus.put("Status", StatusHealth.DEGRADED);
            return;
        } else if (rslSql.equals(StatusHealth.UNHEALTHY) || rslKafka.equals(StatusHealth.UNHEALTHY)) {
            mapStatus.put("Status", StatusHealth.UNHEALTHY);
            return;
        }
        mapStatus.put("Status", StatusHealth.HEALTHY);
    }

    @Override
    public void run() {
        mapStatus.put("Status", StatusHealth.DEGRADED);
        checkHealthAll();
    }
}