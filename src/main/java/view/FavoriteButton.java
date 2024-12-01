package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FavoriteButton extends JButton {

    private boolean selected = false; // Toggle state

    private Color selectedColor = Color.YELLOW; // Color when selected
    private Color unselectedColor = Color.GRAY; // Color when not selected

    public FavoriteButton() {
        // Make the button transparent
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        // Set preferred size
        setPreferredSize(new Dimension(50, 50));

    }

    private void toggleSelected() {
        selected = !selected;
        repaint(); // Repaint the button to reflect the new state
    }

    private void fireActionPerformed() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null);
        for (ActionListener listener : getActionListeners()) {
            listener.actionPerformed(event);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Enable anti-aliasing for smoother edges
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set color based on selection state
        if (selected) {
            g2.setColor(selectedColor);
        } else {
            g2.setColor(unselectedColor);
        }

        // Draw the custom shape
        int width = getWidth();
        int height = getHeight();

        Shape customShape = createCustomShape(width, height);

        // Fill the custom shape
        g2.fill(customShape);

        g2.dispose();
    }

    private Shape createCustomShape(int width, int height) {
        // Center coordinates
        int midX = width / 2;
        int midY = height / 2;

        int[] radius = {22,15,22,15};


        int nPoints = 16;
        int[] X = new int[nPoints];
        int[] Y = new int[nPoints];

        double max = nPoints;

        for (int i = 0; i < nPoints; i++) {
            double angle = i * ((2 * Math.PI) / max);
            double x = Math.cos(angle) * radius[i % 4];
            double y = Math.sin(angle) * radius[i % 4];

            X[i] = (int) x + midX;
            Y[i] = (int) y + midY;
        }

        // Create the polygon shape
        Polygon polygon = new Polygon(X, Y, nPoints);

        return polygon;
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = createCustomShape(getWidth(), getHeight());
        return shape.contains(x, y);
    }

    // Methods to get and set the selected state
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

}