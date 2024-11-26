package view;

import data_access.Constants;
import data_access.RecipeDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewManagerState;
import interface_adapter.display_recipe.DisplayRecipeController;
import interface_adapter.display_recipe.DisplayRecipePresenter;
import interface_adapter.display_recipe.DisplayRecipeViewModel;
import interface_adapter.search_recipe.SearchRecipeViewModel;
import interface_adapter.search_recipe.SearchRecipeController;
import use_case.display_recipe.DisplayRecipeDataAccessInterface;
import use_case.display_recipe.DisplayRecipeInputBoundary;
import use_case.display_recipe.DisplayRecipeInteractor;
import use_case.search_recipe.SearchRecipeOutputData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

/**
 * The View for the Search Recipe Use Case.
 */
public class SearchRecipeView extends JPanel {

    private final SearchRecipeViewModel viewModel;
    private SearchRecipeController controller;

    private Profile profile;
    private JTextField searchBar;
    private JPanel topPanel;
    private JPanel centerPanel;

    // Adjust the roundness
    private final int cornerRadius = 50;
    private Consumer<Integer> recipeClickListener;

    //
    private JMenuItem profileButton;

    private ViewManagerModel viewManagerModel;
    /**
     * Constructor for the SearchRecipeView.
     * @param viewModel the ViewModel for the Search Recipe Use Case
     * @param viewManagerModel the ViewManagerModel
     */
    public SearchRecipeView(SearchRecipeViewModel viewModel,
                            ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        setLayout(new BorderLayout());
        this.viewManagerModel = viewManagerModel;
        loadScreen();
        registerPropertyChangeListeners();
    }


    private void loadScreen() {

        // Create the profile component
        profile = new Profile(this.viewManagerModel);

        // Create the search bar with rounded corners and shadow
        searchBar = createSearchBar();

        // Create a panel to hold the search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchPanel.add(searchBar);

        // Center the search panel in the centerPanel
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(searchPanel);

        // The top panel will hold both the profile and search bar initially
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20)); // Add padding
        topPanel.add(profile, BorderLayout.LINE_END);

        // Add panels to the view
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Force UI to update
        revalidate();
        repaint();
    }

    private JTextField createSearchBar() {

        JTextField searchBar = new JTextField("Search for recipes...") {
            private Shape shape;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw shadow
                g2.setColor(new Color(0, 0, 0, 30)); // Shadow color
                g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius);

                // Draw the background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius);

                // Draw the border
                g2.setColor(Color.GRAY);
                g2.drawRoundRect(0, 0, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius);

                // Draw the search icon
                int circleSize = 12;
                int handleLength = 6;
                int iconSize = circleSize + handleLength - 2; // Total icon size (approximate)
                int iconX = 20; // Left padding
                int iconY = (getHeight() - iconSize) / 2 - 4; // Center the icon vertically

                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.GRAY);

                // Draw the circle part of the magnifying glass
                int circleX = iconX;
                int circleY = iconY;

                g2.drawOval(circleX, circleY, circleSize, circleSize);

                // Draw the handle
                int lineX1 = circleX + circleSize - 1;
                int lineY1 = circleY + circleSize - 1;
                int lineX2 = lineX1 + handleLength;
                int lineY2 = lineY1 + handleLength;

                g2.drawLine(lineX1, lineY1, lineX2, lineY2);

                // Set clipping area for text
                g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius));

                // Draw the text
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            public Insets getInsets() {
                return new Insets(10, 60, 10, 20); // Top, Left, Bottom, Right
            }

            @Override
            public void setBorder(Border border) {
                // No border
            }

            @Override
            public boolean contains(int x, int y) {
                if (shape == null || !shape.getBounds().equals(getBounds())) {
                    shape = new RoundRectangle2D.Float(0, 0, getWidth() - 6, getHeight() - 6, cornerRadius, cornerRadius);
                }
                return shape.contains(x, y);
            }
        };

        // Set search bar properties
        searchBar.setPreferredSize(new Dimension(800, 60));
        searchBar.setFont(new Font("SansSerif", Font.ITALIC, 24));
        searchBar.setForeground(Color.GRAY);
        searchBar.setBackground(Color.WHITE);
        searchBar.setOpaque(false);

        // Placeholder text logic
        searchBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().trim().equals("Search for recipes...")) {
                    searchBar.setText("");
                    searchBar.setForeground(Color.BLACK);
                    searchBar.setFont(new Font("SansSerif", Font.PLAIN, 24)); // Normal font
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().trim().isEmpty()) {
                    searchBar.setText("Search for recipes...");
                    searchBar.setForeground(Color.GRAY);
                    searchBar.setFont(new Font("SansSerif", Font.ITALIC, 24)); // Italic font for placeholder
                }
            }
        });

        // Add action listener for Enter key
        searchBar.addActionListener(e -> onSearch());

        return searchBar;
    }

    private void onSearch() {
        if (controller != null) {
            String query = searchBar.getText();
            controller.searchRecipe(query);
        }
    }


    private void registerPropertyChangeListeners() {
        viewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("recipes".equals(evt.getPropertyName())) {
                    updateResults();
                } else if ("error".equals(evt.getPropertyName())) {
                    updateError();
                }
            }
        });
    }

    private void updateError() {
        String error = viewModel.getError();
        if (error != null && !error.isEmpty()) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateResults() {
        List<SearchRecipeOutputData> recipes = viewModel.getRecipes();
        displayRecipes(recipes);
    }

    public void addRecipeClickListener(Consumer<Integer> listener) {
        this.recipeClickListener = listener;
    }

    private void displayRecipes(List<SearchRecipeOutputData> recipes) {
        // Remove all existing components from the center panel
        centerPanel.removeAll();

        // Create a new panel for the top search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchPanel.add(searchBar);

        // Update the top panel
        topPanel.removeAll();
        topPanel.add(profile, BorderLayout.LINE_END);
        topPanel.add(searchPanel, BorderLayout.CENTER);

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

        // Add the scroll pane to the center panel
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Refresh the UI
        revalidate();
        repaint();
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

//        final ViewManagerModel viewManagerModel = new ViewManagerModel();
//        DisplayRecipePresenter displayRecipePresenter = new DisplayRecipePresenter(viewManagerModel,
//                new DisplayRecipeViewModel());
//        DisplayRecipeDataAccessInterface displayRecipeDataAccessInterface = new RecipeDataAccessObject();
//        DisplayRecipeInputBoundary displayRecipeUseCaseInteractor = new DisplayRecipeInteractor(
//                displayRecipePresenter, displayRecipeDataAccessInterface);
//
//        final DisplayRecipeController displayRecipeController = new DisplayRecipeController(displayRecipeUseCaseInteractor);

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

    public void setRecipeController(SearchRecipeController controller) {
        this.controller = controller;
    }
}