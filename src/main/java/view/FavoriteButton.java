package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class FavoriteButton extends JButton {
    private boolean selected = false;
    private Color selectedColor = Color.RED;
    private Color unselectedColor = Color.GRAY;

    public FavoriteButton() {
        // Make the button transparent
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        // Set preferred size
        setPreferredSize(new Dimension(50, 50));

    }

    @Override
    protected void paintComponent(Graphics g) {
        // Enable anti-aliasing for smoother edges
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set color based on selection state
        if (isSelected()) {
            g2.setColor(selectedColor);
        } else {
            g2.setColor(unselectedColor);
        }

        // Draw the heart shape
        int margin = 5;   // Margin around the heart shape
        int width = getWidth();
        int height = getHeight();
        int boxSize = Math.min(width, height) - margin * 2;
        float boxX = (width - boxSize) / 2.0f;
        float boxY = (height - boxSize) / 2.0f;

        if (boxSize > 0) {
            Path2D heartPath = createHeartPath(boxX, boxY, boxSize, boxSize);
            g2.fill(heartPath);
        }

        g2.dispose();
    }

    private Path2D createHeartPath(float x, float y, float width, float height) {
        float beX = x + width / 2;  // Bottom endpoint X
        float beY = y + height;     // Bottom endpoint Y

        float c1DX = width  * 0.968f;  // Delta X of control point 1
        float c1DY = height * 0.672f;  // Delta Y of control point 1
        float c2DX = width  * 0.281f;  // Delta X of control point 2
        float c2DY = height * 1.295f;  // Delta Y of control point 2
        float teDY = height * 0.850f;  // Delta Y of top endpoint

        Path2D.Float heartPath = new Path2D.Float();
        heartPath.moveTo(beX, beY);       // Bottom endpoint

        // Left side of heart
        heartPath.curveTo(
                beX - c1DX, beY - c1DY,   // Control point 1
                beX - c2DX, beY - c2DY,   // Control point 2
                beX       , beY - teDY);  // Top endpoint

        // Right side of heart
        heartPath.curveTo(
                beX + c2DX, beY - c2DY,   // Control point 2
                beX + c1DX, beY - c1DY,   // Control point 1
                beX       , beY);         // Bottom endpoint

        return heartPath;
    }

    @Override
    public boolean contains(int x, int y) {
        int margin = 5;
        int width = getWidth();
        int height = getHeight();
        int boxSize = Math.min(width, height) - margin * 2;
        float boxX = (width - boxSize) / 2.0f;
        float boxY = (height - boxSize) / 2.0f;

        if (boxSize > 0) {
            Shape shape = createHeartPath(boxX, boxY, boxSize, boxSize);
            return shape.contains(x, y);
        }
        return false;
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