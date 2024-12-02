package use_case.history;

import app.SessionUser;
import entity.User;

import java.util.List;

public class HistoryInteractor implements HistoryInputBoundary {
    private final HistoryDataAccessInterface dataAccess;
    private final HistoryOutputBoundary presenter;

    public HistoryInteractor(HistoryDataAccessInterface dataAccess,
                             HistoryOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(HistoryInputData historyInputData) {
        User user = SessionUser.getInstance().getUser();

        if (user == null) {
            presenter.prepareFailView("Error with retrieving user");
            return;
        }

        List<Integer> history = dataAccess.getHistory(user);

        if (history.contains(historyInputData.getRecipeId())) {
            history.remove((Integer) historyInputData.getRecipeId());
            history.add(0,historyInputData.getRecipeId());
        } else {
            history.add(0,historyInputData.getRecipeId());
        }

        dataAccess.saveHistory(user, history);

        final HistoryOutputData outputData = new HistoryOutputData(history.contains(historyInputData.getRecipeId()));

        // Always true
        presenter.prepareSuccessView(outputData);
    }

}
