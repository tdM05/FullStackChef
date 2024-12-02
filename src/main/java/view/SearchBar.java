package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class SearchBar extends JTextField {

    private final int cornerRadius = 50;
    private Shape shape;
    private final String placeholderText;

    public SearchBar(String placeholderText) {
        super(placeholderText);
        this.placeholderText = placeholderText;

        // Set search bar properties
        setPreferredSize(new Dimension(800, 60));
        setFont(new Font("SansSerif", Font.ITALIC, 24));
        setForeground(Color.GRAY);
        setBackground(Color.WHITE);
        setOpaque(false);

        // Placeholder text logic
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().trim().equals(placeholderText)) {
                    setText("");
                    setForeground(Color.BLACK);
                    setFont(new Font("SansSerif", Font.PLAIN, 24)); // Normal font
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().trim().isEmpty()) {
                    setText(placeholderText);
                    setForeground(Color.GRAY);
                    setFont(new Font("SansSerif", Font.ITALIC, 24)); // Italic font for placeholder
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable antialiasing for smooth edges
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
        int iconX = 20; // Left padding
        int iconY = (getHeight() - circleSize - handleLength) / 2 - 4; // Center vertically

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.GRAY);

        // Draw the circle part of the magnifying glass
        g2.drawOval(iconX, iconY, circleSize, circleSize);

        // Draw the handle
        int lineX1 = iconX + circleSize - 1;
        int lineY1 = iconY + circleSize - 1;
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
}