package Controlador;

import Utils.InstantAdapter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

public class InstantAdapterTest {

    private final InstantAdapter adapter = new InstantAdapter();

    @Test
    public void testSerializationProducesCorrectJsonFormat() {
        // Given: An Instant representing a specific date and time
        Instant instant = Instant.parse("2023-05-15T10:30:00Z");

        // When: Serializing the Instant to JSON
        JsonElement result = adapter.serialize(instant, Instant.class, null);

        // Then: The result should be a JsonPrimitive containing the ISO-8601 string
        assertTrue("Result should be a JsonPrimitive", result.isJsonPrimitive());
        JsonPrimitive primitive = result.getAsJsonPrimitive();
        assertTrue("Result should be a string", primitive.isString());
        assertEquals("Serialized value should match ISO-8601 format", "2023-05-15T10:30:00Z", primitive.getAsString());
    }

    @Test(expected = JsonParseException.class)
    public void testDeserializationWithInvalidJsonFormatThrowsException() {
        // Given: An invalid JSON string (not in ISO-8601 format)
        JsonElement jsonElement = new JsonPrimitive("invalid-date-format");

        // When: Attempting to deserialize invalid JSON
        // Then: Should throw JsonParseException
        adapter.deserialize(jsonElement, Instant.class, null);
    }

    @Test
    public void testSerializeAndDeserializeReturnsSameValue() {
        // Given: An original Instant
        Instant original = Instant.parse("2024-01-20T15:45:30Z");

        // When: Serializing and then deserializing
        JsonElement serialized = adapter.serialize(original, Instant.class, null);
        Instant deserialized = adapter.deserialize(serialized, Instant.class, null);

        // Then: The deserialized value should equal the original
        assertEquals("Round-trip serialization should preserve the value", original, deserialized);
    }
}
