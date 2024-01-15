package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


/*
    Prostokaty opadajace reaguja na myszke, kiedy wcisniemy przycisk do zmiany stanu canvy
 */
public class MyPanel extends JPanel implements MouseListener, MouseMotionListener {
    private Rectangle currentRectangle;
    private boolean drawingMode;
    private List<Rectangle> rectangles;

    public MyPanel() {
        currentRectangle = new Rectangle();
        drawingMode = true;
        rectangles = new ArrayList<>();

        addMouseListener(this);
        addMouseMotionListener(this);
    }


    private void moveRectangle() {

        for (Rectangle rectangle : rectangles) {
            rectangle.y = rectangle.y + 20;
            checkBounds();
            repaint();
        }
    }

    private void checkBounds() {
        for (Rectangle rectangle : rectangles) {
            if (rectangle.x < 0) {
                rectangle.x = 0;
            }
            if (rectangle.x + rectangle.width > getWidth()) {
                rectangle.x = getWidth() - rectangle.width;
            }
            if (rectangle.y < 0) {
                rectangle.y = 0;
            }
            if (rectangle.y + rectangle.height > getHeight()) {
                rectangle.y = getHeight() - rectangle.height;
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (drawingMode) {
            currentRectangle.x = e.getX();
            currentRectangle.y = e.getY();

        } else {
            moveRectangle();


        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (drawingMode) {
            int width = e.getX() - currentRectangle.x;
            int height = e.getY() - currentRectangle.y;
            rectangles.add(new Rectangle(currentRectangle.x, currentRectangle.y, width, height));
            currentRectangle.setBounds(0, 0, 0, 0);
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (drawingMode) {
            int width = e.getX() - currentRectangle.x;
            int height = e.getY() - currentRectangle.y;
            currentRectangle.setSize(width, height);
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}
