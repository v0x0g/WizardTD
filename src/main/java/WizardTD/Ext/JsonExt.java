package WizardTD.Ext;

import lombok.experimental.*;
import processing.data.*;

import java.util.stream.*;

@UtilityClass
public class JsonExt {
    /**
     * Converts a JSON array to a stream
     */
    public Stream<JSONObject> jsonArrayToStream(final JSONArray arr) {
        return IntStream.range(0, arr.size())
                        .mapToObj(arr::getJSONObject);
    }
}
