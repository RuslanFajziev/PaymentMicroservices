package payservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import payservice.model.PayUpdateDTO;

@Slf4j
public class DeserializerPayUpdateDTO implements Deserializer<PayUpdateDTO> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PayUpdateDTO deserialize(String s, byte[] data) {
        try {
            if (data == null) {
                log.error("\nError: Null received at deserializing");
                return null;
            }

            return objectMapper.readValue(new String(data, "UTF-8"), PayUpdateDTO.class);
        } catch (Exception e) {
            log.error("\nError when deserializing byte[] to PayUpdateDTO");
            throw new SerializationException("Error when deserializing byte[] to PayUpdateDTO");
        }
    }
}