package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawAndMoveRectanglesPanel extends JPanel {
    private Rectangle currentRectangle;
    private Rectangle movingRectangle;
    private boolean drawingMode;
    private java.util.List<Rectangle> rectangles = new java.util.ArrayList<>();
    private int offsetX;
    private int offsetY;

    public DrawAndMoveRectanglesPanel() {
        currentRectangle = new Rectangle();
        movingRectangle = null;
        drawingMode = true;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (drawingMode) {
                    currentRectangle.x = e.getX();
                    currentRectangle.y = e.getY();
                    offsetX = e.getX() - currentRectangle.x;
                    offsetY = e.getY() - currentRectangle.y;
                } else {
                    for (Rectangle rectangle : rectangles) {
                        if (rectangle.contains(e.getPoint())) {
                            movingRectangle = rectangle;

                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawingMode) {
                    int width = e.getX() - currentRectangle.x;
                    int height = e.getY() - currentRectangle.y;
                    rectangles.add(new Rectangle(currentRectangle.x, currentRectangle.y, width, height));
                    currentRectangle.setBounds(0, 0, 0, 0);  // Reset the current rectangle
                } else {
                    movingRectangle = null;
                }
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if (drawingMode) {
                    int width = e.getX() - currentRectangle.x;
                    int height = e.getY() - currentRectangle.y;
                    currentRectangle.setSize(width, height);
                    repaint();
                } else if (movingRectangle != null) {
                    movingRectangle.x = e.getX() - offsetX - movingRectangle.width /2;;
                    movingRectangle.y = e.getY() - offsetY - movingRectangle.height/2;;
                    checkBounds();
                    repaint();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Draw and Move Rectangles Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            DrawAndMoveRectanglesPanel panel = new DrawAndMoveRectanglesPanel();
            frame.add(panel, BorderLayout.CENTER);

            JButton toggleModeButton = new JButton("Toggle Mode");
            toggleModeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.toggleMode();
                }
            });
            frame.add(toggleModeButton, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }

    private void checkBounds() {
        // Adjust the bounds checking logic as needed
        if (movingRectangle != null) {
            if (movingRectangle.x < 0) {
                movingRectangle.x = 0;
            }
            if (movingRectangle.x + movingRectangle.width > getWidth()) {
                movingRectangle.x = getWidth() - movingRectangle.width;
            }
            if (movingRectangle.y < 0) {
                movingRectangle.y = 0;
            }
            if (movingRectangle.y + movingRectangle.height > getHeight()) {
                movingRectangle.y = getHeight() - movingRectangle.height;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        for (Rectangle rectangle : rectangles) {
            g2d.fill(rectangle);
            g2d.draw(rectangle);
        }
        if (drawingMode) {
            g2d.setColor(Color.RED);
            g2d.draw(currentRectangle);
        }
    }

    public void toggleMode() {
        drawingMode = !drawingMode;
    }
}
