package view;

import data_access.Constants;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;

import use_case.search_recipe.SearchRecipeOutputData;

import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import java.net.URL;
import java.util.List;

public class RecipePanel extends JPanel {

    private final List<SearchRecipeOutputData> recipes;
    private final ViewManagerModel viewManagerModel;

    public RecipePanel(List<SearchRecipeOutputData> recipes, ViewManagerModel viewManagerModel) {
        this.recipes = recipes;
        this.viewManagerModel = viewManagerModel;
        initialize();
    }

    private void initialize() {
        // Create a panel to hold the recipe cards
        JPanel resultsPanel = new JPanel();
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setLayout(new GridLayout(0, 3, 20, 20)); // 3 columns, variable rows
        resultsPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding

        // Add recipe cards to the results panel
        for (SearchRecipeOutputData recipe : recipes) {
            JPanel recipeCard = createRecipeCard(recipe);
            resultsPanel.add(recipeCard);
        }

        // Wrap the results panel in a scroll pane with vertical-only scrolling
        JScrollPane scrollPane = new JScrollPane(resultsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrolling

        scrollPane.setBorder(null); // Remove border if desired
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Adjust scroll speed

        // Ensure the resultsPanel matches the viewport's width to prevent horizontal scrolling
        resultsPanel.setPreferredSize(new Dimension(scrollPane.getViewport().getWidth(), resultsPanel.getPreferredSize().height));
        scrollPane.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resultsPanel.setPreferredSize(new Dimension(scrollPane.getViewport().getWidth(), resultsPanel.getHeight()));
                resultsPanel.revalidate();
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createRecipeCard(SearchRecipeOutputData recipe) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Add the image with specified size 312x231
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(312, 231));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Load the image asynchronously with the specified dimensions
        new ImageLoader(recipe.getImageUrl(), imageLabel, 312, 231).execute();

        card.add(imageLabel, BorderLayout.CENTER);

        // Add the recipe name
        JLabel nameLabel = new JLabel(recipe.getRecipeName());
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        card.add(nameLabel, BorderLayout.SOUTH);

        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Recipe card clicked. Recipe ID: " + recipe.id());

                final ViewManagerState state = new ViewManagerState(
                        Constants.DISPLAY_RECIPE_VIEW,
                        recipe.id());
                viewManagerModel.setState(state);
                viewManagerModel.setContext(recipe.id());
                viewManagerModel.firePropertyChanged();
            }
        });

        return card;
    }

    /**
     * SwingWorker to load images asynchronously to prevent UI freezing.
     */
    private static class ImageLoader extends SwingWorker<ImageIcon, Void> {
        private final String imageUrl;
        private final JLabel imageLabel;
        private final int width;
        private final int height;

        public ImageLoader(String imageUrl, JLabel imageLabel, int width, int height) {
            this.imageUrl = imageUrl;
            this.imageLabel = imageLabel;
            this.width = width;
            this.height = height;
        }

        @Override
        protected ImageIcon doInBackground() throws Exception {
            URL url = new URL(imageUrl);
            Image image = ImageIO.read(url);
            if (image != null) {
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                ImageIcon icon = get();
                if (icon != null) {
                    imageLabel.setIcon(icon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}