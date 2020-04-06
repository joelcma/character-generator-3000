package com.company;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class PaintBox extends JPanel {
    private int WIDTH, HEIGHT;

    private int GRIDSIZE;
    private static int PIXELSIZE = 30;

    private int mousex = 0;
    private int mousey = 0;
    private boolean holding = false;
    private int hardness = 200;

    private ArrayList<Point> points;


    public PaintBox(int gridSize){
        GRIDSIZE = gridSize;
        WIDTH = PIXELSIZE*GRIDSIZE;
        HEIGHT = PIXELSIZE*GRIDSIZE;

        setFocusable(true);
        setPreferredSize(new Dimension(PIXELSIZE*GRIDSIZE, PIXELSIZE*GRIDSIZE));

        points = new ArrayList<>();

        MouseListener listener = new MouseListener();
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }

    public BufferedImage getImage() {
        BufferedImage img = new BufferedImage(GRIDSIZE, GRIDSIZE, BufferedImage.TYPE_INT_RGB);

        Color whitePixel = new Color(255,255,255);
        Color blackPixel = new Color(0,0,0);

        // Set them all to white
        for(int i=0; i<GRIDSIZE; i++) {
            for(int j=0; j<GRIDSIZE; j++) {
                img.setRGB(i, j, whitePixel.getRGB());
            }
        }

        // Set the pixels on points to black
        for(Point point: points) {
            int x = min(point.x/28, 27);
            int y = min(point.y/28, 27);
            System.out.printf("x,y = [%d, %d]\n", x, y);
            img.setRGB(x, y, blackPixel.getRGB());
        }

        return img;
    }

    private int min(int a, int b) {
        if(a < b) {
            return a;
        }
        return b;
    }

    public void addPixel(Point point) {
        if(!points.contains(point)) {
            points.add(point);
            repaint();
        }
    }

    public void removePixel(Point point) {
        if(points.contains(point)) {
            points.remove(point);
            repaint();
        }
    }

    public void clear() {
        this.points = new ArrayList<>();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2);

        // Clear out old picture?
        g2.clearRect(0,0, WIDTH, HEIGHT);

        // Draw things here
        this.drawBackground(g2);

        // Draw black pixels
        for (Point point: points) {
            this.drawPixel(g2, point);
        }

        g2.dispose();
    }

    private void drawPixel(Graphics2D g2, Point point) {
        int x = 28*(point.x/28);
        int y = 28*(point.y/28);
        //System.out.printf("hardness: %d\n", hardness);
        Color c = new Color(0,0,0, hardness);
        g2.setColor(c);
        g2.fillRect(x, y, PIXELSIZE, PIXELSIZE);
    }

    private void drawBackground(Graphics2D g2) {
        // Draw background color
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void drawGrid(Graphics2D g2) {
        // Draw grid
        g2.setColor(Color.BLACK);
        for (int i=0; i<GRIDSIZE; i++) {
            // Draw horizontal line
            int xStart = 0;
            int xEnd = WIDTH;
            int yStart = i*PIXELSIZE;
            int yEnd = i*PIXELSIZE;
            g2.drawLine(xStart, yStart, xEnd, yEnd);

            // Draw vertical line
            xStart = i*PIXELSIZE;
            xEnd = i*PIXELSIZE;
            yStart = 0;
            yEnd = HEIGHT;
            g2.drawLine(xStart, yStart, xEnd, yEnd);
        }
    }

    private void toggleMouseHold(boolean holding) {
        System.out.printf("Holding: %b\n", holding);
        this.holding = holding;
    }

    private void mouseMove() {
        //System.out.printf("Mouse: (%d, %d)\n", mousex, mousey);
        if (this.holding) {
            Point point = new Point(mousex, mousey);
            this.addPixel(point);
        }
    }


    // Paint configurables

    // Paint configurables


    private double min(double a, double b) {
        if (a > b) {
            return b;
        }
        return a;
    }



    // MOUSE LISTENER
    class MouseListener implements MouseInputListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            mousex = e.getX();
            mousey = e.getY();
            mouseMove();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //System.out.println("moved!");
            mousex = e.getX();
            mousey = e.getY();
            mouseMove();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Point point = new Point(e.getX(), e.getY());
            addPixel(point);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            toggleMouseHold(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            toggleMouseHold(false);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("entered!");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("exited!");
        }
    }
}
