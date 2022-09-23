


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LineClippingPanel extends JPanel {

    public static final int INSIDE = 0;//0000
    public static final int LEFT   = 1;//0001
    public static final int RIGHT  = 2;//0010
    public static final int BOTTOM = 4;//0100
    public static final int TOP    = 8;//1000

    public static final int COHEN_SUTHERLAND = 0;
    public static final int LIANG_BARSKY = 1;

    public double getxMin() {
		return xMin;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public double getxMax() {
		return xMax;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public double getyMin() {
		return yMin;
	}

	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	public double getyMax() {
		return yMax;
	}

	public void setyMax(double yMax) {
		this.yMax = yMax;
	}

	private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
        /**
         * Computes OutCode for given point (x,y)
         * @param x Horizontal coordinate
         * @param y Vertical coordinate
         * @return Computed OutCode
         */
        private int computeOutCode(double x, double y) {
            int code = INSIDE;

            if (x < xMin) {
                code |= LEFT;
            } else if (x > xMax) {
                code |= RIGHT;
            }
            if (y < yMin) {
                code |= BOTTOM;
            } else if (y > yMax) {
                code |= TOP;
            }

            return code;
        }

        /**
         * Execute line clipping using Cohen-Sutherland
         * Taken from: http://en.wikipedia.org/wiki/Cohen-Sutherland
         * @param line LineSegment to work with
         * @return Clipped line
         */
        public LineSegment clip(LineSegment line) {
            System.out.println("\nExecuting Cohen-Sutherland...");
            double x0 = line.x0, x1 = line.x1, y0 = line.y0, y1 = line.y1;
            int outCode0 = computeOutCode(x0, y0);
            int outCode1 = computeOutCode(x1, y1);
            System.out.println("OutCode0: " + outCode0 + ", OutCode1: " + outCode1);
            boolean accept = false;

            while (true) {
                if ((outCode0 | outCode1) == 0) { // Bitwise OR is 0. Trivially accept
                    accept = true;
                    break;
                } else if ((outCode0 & outCode1) != 0) { // Bitwise AND is not 0. Trivially reject
                    break;
                } else {
                    double x, y;

                    // Pick at least one point outside rectangle
                    int outCodeOut = (outCode0 != 0) ? outCode0 : outCode1;

                    // Now find the intersection point;
                    // use formulas y = y0 + slope * (x - x0), x = x0 + (1 / slope) * (y - y0)
                    if ((outCodeOut & TOP) != 0) { //8
                        x = x0 + (x1 - x0) * (yMax - y0) / (y1 - y0);
                        y = yMax;
                    } else if ((outCodeOut & BOTTOM) != 0) { //4
                        x = x0 + (x1 - x0) * (yMin - y0) / (y1 - y0);
                        y = yMin;
                    } else if ((outCodeOut & RIGHT) != 0) { //2
                        y = y0 + (y1 - y0) * (xMax - x0) / (x1 - x0);
                        x = xMax;
                    } else { //1
                        y = y0 + (y1 - y0) * (xMin - x0) / (x1 - x0);
                        x = xMin;
                    }

                    // Now we move outside point to intersection point to clip
                    if (outCodeOut == outCode0) {
                        x0 = x;
                        y0 = y;
                        outCode0 = computeOutCode(x0, y0);
                    } else {
                        x1 = x;
                        y1 = y;
                        outCode1 = computeOutCode(x1, y1);
                    }
                }
            }
            if (accept) {
                return new LineSegment(x0, y0, x1, y1);
            }
            return null;
        }

    /**
     * Constructor
     * @param xMin Bottom side of rectangle
     * @param yMin Left side of rectangle
     * @param xMax Top side of rectangle
     * @param yMax Right side of rectangle
     * @param clipperOption Code for LineClipper algorithm to use (0: Cohen-Sutherland, 1: Liang-Barsky)
     */
    public LineClippingPanel(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.blue);
        drawLine(g2d, xMin, 0, xMin, getHeight());
        drawLine(g2d, xMax, 0, xMax, getHeight());
        drawLine(g2d, 0, yMin, getWidth(), yMin);
        drawLine(g2d, 0, yMax, getWidth(), yMax);

        double x0, y0, x1, y1;
        
        LineSegment line[] = new LineSegment[1];
        LineSegment clippedLine[] = new LineSegment[line.length];
        
        for (int i = 0; i < line.length; i++) {
        
        	x0= 493.0; y0 = 232.0; x1= 272.0; y1=336.0;
            line[i] = new LineSegment(x0, y0, x1, y1);
            clippedLine[i] = clip(line[i]);

            System.out.println("Original: " + line[i]);
            System.out.println("Clipped: " + clippedLine[i]);

            if (clippedLine[i] == null) {
                g2d.setColor(Color.red);
                drawLine(g2d, line[i].x0, line[i].y0, line[i].x1, line[i].y1);
            } else {
                g2d.setColor(Color.red);
                drawLine(g2d, line[i].x0, line[i].y0, clippedLine[i].x0, clippedLine[i].y0);
                drawLine(g2d, clippedLine[i].x1, clippedLine[i].y1, line[i].x1, line[i].y1);
                g2d.setColor(Color.green);
                drawLine(g2d, clippedLine[i].x0, clippedLine[i].y0, clippedLine[i].x1, clippedLine[i].y1);
            }
        }
    }

    public void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
        g.drawLine((int)x1, (int)(getHeight() - y1), (int)x2, (int)(getHeight() - y2));
    }

  
}