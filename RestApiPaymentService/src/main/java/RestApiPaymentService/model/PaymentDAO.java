package RestApiPaymentService.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "Payment_DAO")
@Data
public class PaymentDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name_service")
    private String nameService;
    private int amount;
    private String metadate;
    @GeneratedValue
    @Column(name = "correlation_id")
    private UUID correlationId;
    @Column(name = "date_payment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePayment;
    @Column(name = "status_payment")
    private String statusPayment;
    @Column(name = "user_creator")
    private String userCreater;
    @Transient
    private Boolean testPay;

    public static PaymentDAO of(String nameService, int amount, List<MetadataDTO> metadate, String userCreater) {
        PaymentDAO dao = new PaymentDAO();
        dao.nameService = nameService;
        dao.amount = amount;

        Gson gson = new GsonBuilder().create();
        dao.metadate = gson.toJson(metadate);
        dao.userCreater = userCreater;

        return dao;
    }
}