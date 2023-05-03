package payservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "Метаданные платежа")
public class MetadataDTO {
    @Schema(description = "коэффициент", example = "7777")
    public int coefficient;
    @Schema(description = "город", example = "Moscow")
    public String sity;

    public static MetadataDTO of(int coefficient, String sity) {
        var metadate = new MetadataDTO();
        metadate.setCoefficient(coefficient);
        metadate.setSity(sity);
        return metadate;
    }
}