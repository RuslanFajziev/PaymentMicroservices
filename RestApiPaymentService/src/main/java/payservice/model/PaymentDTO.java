package payservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class PaymentDTO {
    @NotBlank(message = "nameService must be not empty")
    public String nameService;
    @Min(value = 1, message = "amount must be more than 0")
    public int amount;
    @NotNull(message = "metadate must be not null")
    public List<MetadataDTO> metadate;

    public static PaymentDTO of(String nameService, int amount, List<MetadataDTO> metadate) {
        var paymentDTO = new PaymentDTO();
        paymentDTO.setNameService(nameService);
        paymentDTO.setAmount(amount);
        paymentDTO.setMetadate(metadate);
        return paymentDTO;
    }
}