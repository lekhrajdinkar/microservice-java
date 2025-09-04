package microservice.courseApp.controller.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;




public class LocalDateDeSerializer extends StdDeserializer<LocalDate>
{
    public LocalDateDeSerializer() {
        this(null);
    }

    public LocalDateDeSerializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(
            JsonParser jsonparser, DeserializationContext context)
            throws IOException {

        String year = jsonparser.getText();
        return LocalDate.ofYearDay(Integer.parseInt(year),5);
    }
}

