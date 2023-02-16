package RestApiPaymentService.model;

import lombok.ToString;

@ToString
public class FilterPayDTO {
    public String nameService;
    public int amount;
    public String statusPayment;

    public FilterPayDTO of(String nameService, int amount, String statusPayment) {
        FilterPayDTO filterPayDTO = new FilterPayDTO();
        filterPayDTO.nameService = nameService;
        filterPayDTO.amount = amount;
        filterPayDTO.statusPayment = statusPayment;

        return filterPayDTO;
    }
}