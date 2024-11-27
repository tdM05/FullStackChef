package use_case.store_meal;

public interface StoreMealInputBoundary {
    /**
     * Executes the Store Meal Use Case.
     * @param inputData the input data for the use case
     */
    void execute(StoreMealInputData inputData);
}
