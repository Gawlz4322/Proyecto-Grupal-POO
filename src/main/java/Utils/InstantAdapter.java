package Utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Adaptador de Gson para serializar y deserializar objetos Instant.
 * Permite manejar fechas y horas en formato JSON de manera correcta.
 */
public class InstantAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {

    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return Instant.parse(json.getAsString());
    }
}