package payservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import payservice.model.PaymentDAO;
import payservice.model.PaymentDTO;
import payservice.service.PaymentRestApiService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/payment")
public class PaymentRestApiController {
    private final PaymentRestApiService service;
    private final Gson gson;

    @GetMapping("/get")
    public ResponseEntity<String> getAll(HttpServletRequest request, HttpServletResponse response) {
        List<PaymentDAO> listPayment = service.findAll();
        String body;

        if (!listPayment.isEmpty()) {
            body = gson.toJson(listPayment);
        } else {
            Map<String, String> value = new HashMap<>();
            value.put("result", "payments not found");
            value.put("path", request.getRequestURI());
//        response.setContentType(MediaType.APPLICATION_JSON.toString());
            body = gson.toJson(value);
        }

        var entity = ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);

        log.info("\nresponse: \n{}", body);

        return entity;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<String> get(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
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

        var entity = ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);

        log.info("\nresponse: \n{}", body);

        return entity;
    }

    @GetMapping("/getbyfilter")
    public ResponseEntity<String> getByFilter(@RequestParam(required = false) String nameService,
                                              @RequestParam(required = false) String statusPayment,
                                              @RequestParam(required = false, defaultValue = "0") int amount,
                                              HttpServletRequest request, HttpServletResponse response) {
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

        var entity = ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);

        log.info("\nresponse: \n{}", body);

        return entity;
    }

    @PostMapping("/add")
    public PaymentDAO add(@RequestBody PaymentDTO paymentDTO) {
        PaymentDAO paymentDAO = PaymentDAO.of(paymentDTO.nameService, paymentDTO.amount,
                gson.toJson(paymentDTO.metadate), "created", "Vasia");

        var pay = service.save(paymentDAO);

        log.info("\nresponse: \n{}", pay);

        return pay;
    }
}