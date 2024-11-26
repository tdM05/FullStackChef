package data_access.dietaryrestrictions;

import data_access.DietaryRestrictionDataAccessInterface;
import entity.DietaryRestriction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Data Access Object for Dietary Restrictions.
 * Implements the DietaryRestrictionDataAccessInterface.
 */
public class DietaryRestrictionDataAccessObject implements DietaryRestrictionDataAccessInterface {
    private static final String FILE_PATH = "dietary_restrictions.txt";

    @Override
    public void saveDietaryRestrictions(DietaryRestriction dietaryRestriction) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String diet : dietaryRestriction.getDiets()) {
                writer.write(diet);
                writer.newLine();
            }
        }
        System.out.println("Dietary restrictions saved successfully.");
    }

    @Override
    public DietaryRestriction loadDietaryRestrictions() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No dietary restrictions found. Returning empty list.");
            return new DietaryRestriction(new ArrayList<>());
        }

        List<String> diets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                diets.add(line.trim());
            }
        }
        System.out.println("Dietary restrictions loaded successfully.");
        return new DietaryRestriction(diets);
    }
}
