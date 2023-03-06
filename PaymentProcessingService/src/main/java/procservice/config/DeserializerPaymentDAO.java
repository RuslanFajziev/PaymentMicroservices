package procservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import procservice.model.PaymentDAO;

@Slf4j
public class DeserializerPaymentDAO implements Deserializer<PaymentDAO> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PaymentDAO deserialize(String s, byte[] data) {
        try {
            if (data == null) {
                log.error("\nError: Null received at deserializing");
                return null;
            }

            return objectMapper.readValue(new String(data, "UTF-8"), PaymentDAO.class);
        } catch (Exception e) {
            log.error("\nError when deserializing byte[] to PaymentDAO");
            throw new SerializationException("Error when deserializing byte[] to PaymentDAO");
        }
    }
}