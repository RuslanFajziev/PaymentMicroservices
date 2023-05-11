package payservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payment")
@Tag(name = "Система обработки платежей", description = "Принимает платежи для обработки, выводит данные по платежам")
public class PaymentRestApiController {
    private final PaymentRestApiService service;
    private final Gson gson;

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('user_full', 'user_read')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Вывод платежей", description = "Позволяет вывести список всех платежей")
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

        return getResponse(body);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('user_full', 'user_read')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Вывод платежа по id", description = "Позволяет вывести платеж по id")
    public ResponseEntity<String> get(@PathVariable @Parameter(description = "id платежа") int id, HttpServletRequest request) {
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

        return getResponse(body);
    }

    @GetMapping("/getbyfilter")
    @PreAuthorize("hasAnyAuthority('user_full', 'user_read')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Вывод платежей по фильтру", description = "Позволяет вывести список платежей по фильтру, данные передаем в параметрах запроса")
    public ResponseEntity<String> getByFilter(@RequestParam(required = false) @Parameter(description = "имя платежа") String nameService,
                                              @RequestParam(required = false) @Parameter(description = "статус платежа") String statusPayment,
                                              @RequestParam(required = false, defaultValue = "0") @Parameter(description = "сумма платежа") int amount,
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

        return getResponse(body);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('user_full')")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Добавление платежей", description = "Позволяет загрузить платеж в систему")
    public PaymentDAO add(@Valid @RequestBody PaymentDTO paymentDTO, Authentication auth) {
        var login = auth.getPrincipal().toString();

        PaymentDAO paymentDAO = PaymentDAO.of(paymentDTO.nameService, paymentDTO.amount,
                gson.toJson(paymentDTO.metadate), "created", login);
        return service.save(paymentDAO);
    }

    private ResponseEntity<String> getResponse(String body) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}