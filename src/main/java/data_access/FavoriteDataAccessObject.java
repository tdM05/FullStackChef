package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.check_favorite.CheckFavoriteDataAccessInterface;
import use_case.favorite.FavoriteDataAccessInterface;

/**
 * The DAO for accessing favorite recipes stored in the database.
 */
public class FavoriteDataAccessObject implements CheckFavoriteDataAccessInterface, FavoriteDataAccessInterface {
    private static final int SUCCESS_CODE = 200;
    private static final int CREDENTIAL_ERROR = 401;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";

    private final OkHttpClient client;

    /**
     * Constructs a new FavoriteDataAccessObject with a default OkHttpClient.
     */
    public FavoriteDataAccessObject() {
        this.client = new OkHttpClient();
    }

    /**
     * Constructs a new RecipeDataAccessObject with a provided OkHttpClient.
     * Useful for injecting mock clients during testing.
     *
     * @param client the OkHttpClient to use for API requests
     */
    public FavoriteDataAccessObject(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public List<Integer> getFavorites(User user) throws FavoriteException {
        final String username = user.getName();

        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                final JSONObject userJSONObject = responseBody.getJSONObject("user");
                final JSONObject info = userJSONObject.getJSONObject("info");

                final List<Integer> favorites = new ArrayList<>();

                // Check if "favorites" field exists
                if (info.has("favorites")) {
                    JSONArray favoritesArray = info.getJSONArray("favorites");
                    for (int i = 0; i < favoritesArray.length(); i++) {
                        favorites.add(favoritesArray.getInt(i));
                    }
                }
                return favorites;
            } else if (responseBody.getInt(STATUS_CODE_LABEL) == CREDENTIAL_ERROR) {
                throw new FavoriteException("Invalid credentials provided.");
            } else {
                throw new FavoriteException("Error retrieving favorites: " + responseBody.getString(MESSAGE));
            }
        } catch (IOException | JSONException ex) {
            throw new FavoriteException("Error during API call: " + ex.getMessage());
        }
    }

    @Override
    public void saveFavorites(User user, List<Integer> favorites) throws FavoriteException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();

        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, user.getPassword());

        // Convert favorites list to JSON array
        JSONArray favoritesArray = new JSONArray(favorites);
        final JSONObject info = new JSONObject();
        info.put("favorites", favoritesArray);
        requestBody.put("info", info);

        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/modifyUserInfo")
                .method("PUT", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getInt(STATUS_CODE_LABEL) == SUCCESS_CODE) {
                getFavorites(user);
            } else if (responseBody.getInt(STATUS_CODE_LABEL) == CREDENTIAL_ERROR) {
                throw new FavoriteException("Invalid credentials provided.");
            } else {
                throw new FavoriteException("Error saving favorites: " + responseBody.getString(MESSAGE));
            }
        } catch (IOException | JSONException ex) {
            throw new FavoriteException("Error during API call: " + ex.getMessage());
        }
    }
}