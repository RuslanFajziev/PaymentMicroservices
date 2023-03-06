package payservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import payservice.model.PaymentDAO;
import payservice.repository.HealthchekRestApiRepos;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HealthCheckRestApiService implements Runnable {
    private final HealthchekRestApiRepos repos;
    private final KafkaProducerPaymentRestApiService serviceKafkaProducer;
    private Map<String, String> mapStatus;

    public HealthCheckRestApiService(HealthchekRestApiRepos repos, KafkaProducerPaymentRestApiService serviceKafkaProducer) {
        this.repos = repos;
        this.serviceKafkaProducer = serviceKafkaProducer;
        this.mapStatus = new HashMap<>();
    }

    public String getStatus() {
        return mapStatus.get("Status");
    }

    public String checkHealthSql() {
        try {
            var optPay = repos.findTopByOrderByIdAsc();
            return "Healthy";
        } catch (Exception ex) {
            log.error("\nfailed to deliver query\n{}", ex);
            return "Degraded";
        }
    }

    public String checkHealthKafka() {
        var pay = new PaymentDAO();
        pay.setTestPay(true);

        try {
            return serviceKafkaProducer.send(0, pay);
        } catch (Exception ex) {
            log.error("\nfailed to deliver message\n{}", ex);
            return "Degraded";
        }
    }

    public void checkHealthAll() {
        var rslSql = checkHealthSql();
        var rslKafka = checkHealthKafka();
        var statusArray = new String[]{"Degraded", "Unhealthy", "Healthy"};

        if (rslSql.equals(statusArray[0]) || rslKafka.equals(statusArray[0])) {
            mapStatus.put("Status", statusArray[0]);
            return;
        } else if (rslSql.equals(statusArray[1]) || rslKafka.equals(statusArray[1])) {
            mapStatus.put("Status", statusArray[1]);
            return;
        }
        mapStatus.put("Status", statusArray[2]);
    }

    @Override
    public void run() {
        mapStatus.put("Status", "Degraded");
        checkHealthAll();
    }
}