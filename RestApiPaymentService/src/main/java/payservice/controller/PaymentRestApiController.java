package payservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import payservice.model.PaymentDAO;
import payservice.model.PaymentDTO;
import payservice.service.PaymentRestApiService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentRestApiController {
    private final PaymentRestApiService service;
    private final Gson gson;

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('user_full', 'user_read')")
    public ResponseEntity<String> getAll(HttpServletRequest request) {
        List<PaymentDAO> listPayment = service.findAll();
        String body;

        if (!listPayment.isEmpty()) {
            body = gson.toJson(listPayment);
        } else {
            Map<String, String> value = new HashMap<>();
            value.put("result", "payments not found");
            value.put("path", request.getRequestURI());
            body = gson.toJson(value);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('user_full', 'user_read')")
    public ResponseEntity<String> get(@PathVariable int id, HttpServletRequest request) {
        Optional<PaymentDAO> optionalPaymentDAO = service.findById(id);
        String body;

        if (optionalPaymentDAO.isPresent()) {
            body = gson.toJson(optionalPaymentDAO.get());
        } else {
            Map<String, String> value = new HashMap<>();
            value.put("result", "payment not found");
            value.put("path", request.getRequestURI());
            body = gson.toJson(value);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @GetMapping("/getbyfilter")
    @PreAuthorize("hasAnyAuthority('user_full', 'user_read')")
    public ResponseEntity<String> getByFilter(@RequestParam(required = false) String nameService,
                                              @RequestParam(required = false) String statusPayment,
                                              @RequestParam(required = false, defaultValue = "0") int amount,
                                              HttpServletRequest request) {
        List<PaymentDAO> listPayment = service.findByFilter(nameService, amount, statusPayment);
        String body;

        if (!listPayment.isEmpty()) {
            body = gson.toJson(listPayment);
        } else {
            Map<String, String> value = new HashMap<>();
            value.put("result", "payments not found");
            value.put("path", request.getRequestURI());
            body = gson.toJson(value);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('user_full')")
    public PaymentDAO add(@RequestBody PaymentDTO paymentDTO, Authentication auth) {
        var login = auth.getPrincipal().toString();

        PaymentDAO paymentDAO = PaymentDAO.of(paymentDTO.nameService, paymentDTO.amount,
                gson.toJson(paymentDTO.metadate), "created", login);
        return service.save(paymentDAO);
    }
}