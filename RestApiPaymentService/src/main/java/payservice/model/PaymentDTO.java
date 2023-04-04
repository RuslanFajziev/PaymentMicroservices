package payservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PaymentDTO {
    public String nameService;
    public int amount;
    public List<MetadataDTO> metadate;

    public static PaymentDTO of(String nameService, int amount, List<MetadataDTO> metadate) {
        var paymentDTO = new PaymentDTO();
        paymentDTO.setNameService(nameService);
        paymentDTO.setAmount(amount);
        paymentDTO.setMetadate(metadate);
        return paymentDTO;
    }
}