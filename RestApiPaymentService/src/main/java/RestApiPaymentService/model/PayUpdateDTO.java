package RestApiPaymentService.model;

import lombok.ToString;
import java.util.UUID;
@ToString
public class PayUpdateDTO {
    public UUID correlationId;
    public String statusPayment;

    public static PayUpdateDTO of(UUID correlationId, String statusPayment) {
        PayUpdateDTO payUpdateDTO = new PayUpdateDTO();
        payUpdateDTO.correlationId = correlationId;
        payUpdateDTO.statusPayment = statusPayment;

        return payUpdateDTO;
    }
}