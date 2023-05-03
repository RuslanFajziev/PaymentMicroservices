package payservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность платежа")
public class PaymentDTO {
    @NotBlank(message = "nameService must be not empty")
    @Schema(description = "Имя платежа", example = "my pay")
    public String nameService;
    @Min(value = 1, message = "amount must be more than 0")
    @Schema(description = "Сумма платежа", example = "500")
    public int amount;
    @NotNull(message = "metadate must be not null")
    @Schema(description = "Список метаданных платежа")
    public List<MetadataDTO> metadate;

    public static PaymentDTO of(String nameService, int amount, List<MetadataDTO> metadate) {
        var paymentDTO = new PaymentDTO();
        paymentDTO.setNameService(nameService);
        paymentDTO.setAmount(amount);
        paymentDTO.setMetadate(metadate);
        return paymentDTO;
    }
}