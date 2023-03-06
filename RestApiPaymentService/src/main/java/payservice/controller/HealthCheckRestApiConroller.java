package payservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payservice.service.HealthCheckRestApiService;

import java.util.concurrent.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class HealthCheckRestApiConroller {
    @Value("${health.check.response.timeout}")
    private int timeoutResp;
    private final HealthCheckRestApiService service;

    public HealthCheckRestApiConroller(HealthCheckRestApiService service) {
        this.service = service;
    }

    @GetMapping("healthcheck")
    public String healthCheck() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule((() -> service), timeoutResp, TimeUnit.SECONDS);

        Future future = executorService.submit(service);
        try {
            future.get(timeoutResp, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            log.error("\nStatus check timeout");
        } catch (Exception e) {
            log.error("\nError executorService.submit(service)");
        } finally {
            executorService.shutdownNow();
        }

        return service.getStatus();
    }
}