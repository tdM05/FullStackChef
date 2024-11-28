package util;

import data_access.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModifyUserJSONInfo {
    /**
     * Adds a key-value pair to JSON info based on key and value by modifying userJSON.
     * Also returns the newly updated JSONArray that was updated.
     * @param userJSON The user JSON object to modify.
     * @param key The key to add.
     * @param value The value to add.
     * @param <T> The type of the value.
     * @return The updated JSONArray.
     * @throws IllegalArgumentException if the key is not found in the JSON object.
     */
    public static <T> JSONArray modifyUserJSONInfo(JSONObject userJSON, String key, T value) throws IllegalArgumentException {
        try {
            final JSONObject info = userJSON.getJSONObject(Constants.INFO);
            final JSONArray jsonArray = info.getJSONArray(key);
            jsonArray.put(value);
            return jsonArray;
        }
        catch (JSONException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
