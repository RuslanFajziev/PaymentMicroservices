package payservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MetadataDTO {
    public int coefficient;
    public String sity;

    public static MetadataDTO of(int coefficient, String sity) {
        var metadate = new MetadataDTO();
        metadate.setCoefficient(coefficient);
        metadate.setSity(sity);
        return metadate;
    }
}