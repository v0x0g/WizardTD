package WizardTD.Ext;

import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.data.*;

import java.util.stream.*;

@UtilityClass
public class JsonExt {
    /**
     * Converts a JSON array to a stream
     */
    @NonNull
    public Stream<JSONObject> jsonArrayToStream(final JSONArray arr) {
        return IntStream.range(0, arr.size())
                        .mapToObj(arr::getJSONObject);
    }
}
