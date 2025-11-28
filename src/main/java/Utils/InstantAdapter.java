package Utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Adaptador de Gson para serializar y deserializar objetos Instant.
 */
public class InstantAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {

    /**
     * Serializa un objeto Instant a su representación en cadena.
     */
    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    /**
     * Deserializa una cadena JSON a un objeto Instant.
     */
    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return Instant.parse(json.getAsString());
    }
}