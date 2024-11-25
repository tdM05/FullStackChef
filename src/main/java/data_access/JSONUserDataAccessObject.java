package data_access;

import entity.UserProfile;
import entity.UserProfileFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.DataAccessException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JSONUserDataAccessObject implements UserDataAccessObject {
    private static final String FILE_PATH = "users.json";
    private final UserProfileFactory userProfileFactory;

    public JSONUserDataAccessObject(UserProfileFactory userProfileFactory) {
        this.userProfileFactory = userProfileFactory;
    }

    @Override
    public void saveUser(UserProfile user) throws DataAccessException {
        try {
            Map<String, UserProfile> users = loadAllUsers();
            users.put(user.getName(), user);

            JSONArray jsonArray = new JSONArray();
            for (UserProfile u : users.values()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", u.getName());
                jsonObject.put("password", u.getPassword());
                jsonObject.put("displayName", u.getDisplayName());
                jsonArray.put(jsonObject);
            }

            try (FileWriter file = new FileWriter(FILE_PATH)) {
                file.write(jsonArray.toString());
            }
        } catch (IOException e) {
            throw new DataAccessException("Error saving user data: " + e.getMessage());
        }
    }

    @Override
    public UserProfile loadUser(String username) throws DataAccessException {
        try {
            Map<String, UserProfile> users = loadAllUsers();
            if (users.containsKey(username)) {
                return users.get(username);
            } else {
                throw new DataAccessException("User not found");
            }
        } catch (IOException e) {
            throw new DataAccessException("Error loading user data: " + e.getMessage());
        }
    }

    private Map<String, UserProfile> loadAllUsers() throws IOException {
        Map<String, UserProfile> users = new HashMap<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                String displayName = jsonObject.getString("displayName");
                UserProfile user = (UserProfile) userProfileFactory.create(username, password);
                user.setDisplayName(displayName);
                users.put(username, user);
            }
        }
        return users;
    }
}