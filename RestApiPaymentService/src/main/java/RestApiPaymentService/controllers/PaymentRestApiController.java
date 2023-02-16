package RestApiPaymentService.controllers;

import RestApiPaymentService.model.MetadataDTO;
import RestApiPaymentService.model.PaymentDAO;
import RestApiPaymentService.model.PaymentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentRestApiController {

    @GetMapping("/get")
    public List<PaymentDAO> getAll() {
        List<PaymentDAO> listPayment = new ArrayList<>();
        List<MetadataDTO> listMeta = new ArrayList<>();

        MetadataDTO meta = new MetadataDTO();
        meta.setCoefficient(5);
        meta.setSity("MSK");

        MetadataDTO meta2 = new MetadataDTO();
        meta2.setCoefficient(10);
        meta2.setSity("MSK2");

        listMeta.add(meta);
        listMeta.add(meta2);

        PaymentDAO pay = PaymentDAO.of("pay", 500, listMeta, "Pupkin");
        listPayment.add(pay);
        listPayment.add(pay);
        return listPayment;
    }

    @GetMapping("/get/{id}")
    public PaymentDAO get() {
        List<MetadataDTO> listMeta = new ArrayList<>();

        MetadataDTO meta = new MetadataDTO();
        meta.setCoefficient(5);
        meta.setSity("MSK");

        MetadataDTO meta2 = new MetadataDTO();
        meta2.setCoefficient(10);
        meta2.setSity("MSK2");

        listMeta.add(meta);
        listMeta.add(meta2);

        PaymentDAO pay = PaymentDAO.of("pay", 500, listMeta, "Pupkin");
        return pay;
    }

    @GetMapping("/getbyfilter")
    public List<PaymentDAO> getByFilter(@RequestParam(required = false) String nameService,
                                        @RequestParam(required = false) String statusPayment,
                                        @RequestParam(required = false, defaultValue = "0") int amount) {
        List<PaymentDAO> listPayment = new ArrayList<>();
        List<MetadataDTO> listMeta = new ArrayList<>();


        MetadataDTO meta = new MetadataDTO();
        meta.setCoefficient(5);
        meta.setSity("MSK");

        MetadataDTO meta2 = new MetadataDTO();
        meta2.setCoefficient(10);
        meta2.setSity("MSK2");

        listMeta.add(meta);
        listMeta.add(meta2);

        PaymentDAO pay = PaymentDAO.of("pay", 500, listMeta, "Pupkin");
        pay.setStatusPayment(statusPayment);
        PaymentDAO pay2 = PaymentDAO.of(nameService, amount, listMeta, "Pupkin");
        listPayment.add(pay);
        listPayment.add(pay2);
        return listPayment;
    }

    @PostMapping("/add")
    public PaymentDAO add(@RequestBody PaymentDTO paymentDTO) {
        return PaymentDAO.of(paymentDTO.nameService, paymentDTO.amount, paymentDTO.metadate, "Vasia");
    }
}