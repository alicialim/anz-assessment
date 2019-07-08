package anz.assessment.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;

@Slf4j
public class MoneySerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        try {
            jsonGenerator.writeString(bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
        } catch (IOException e) {
            log.error("Unable to serialize", e);
        }
    }
}
