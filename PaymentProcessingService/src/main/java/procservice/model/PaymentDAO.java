package procservice.model;

import javax.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

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

    public static PaymentDAO of(String nameService, int amount, String metadate,
                                String statusPayment, String userCreater, Boolean testPay) {
        PaymentDAO dao = new PaymentDAO();
        dao.nameService = nameService;
        dao.amount = amount;
        dao.metadate = metadate;
        dao.correlationId = UUID.randomUUID();
        dao.datePayment = new Date();
        dao.statusPayment = statusPayment;
        dao.userCreater = userCreater;
        dao.testPay = testPay;

        return dao;
    }

    public static PaymentDAO of(String nameService, int amount, String metadate,
                                String statusPayment, String userCreater) {
        return of(nameService, amount, metadate, statusPayment, userCreater, false);
    }
}