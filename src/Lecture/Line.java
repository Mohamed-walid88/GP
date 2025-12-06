package Lecture;

import javafx.geometry.Point2D;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.util.ArrayList;

public class Line implements GLEventListener {

    private final int X_MIN = -350;
    private final int Y_MIN = -350;
    private final int X_MAX = 350;
    private final int Y_MAX = 350;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(X_MIN, X_MAX, Y_MIN, Y_MAX, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        drawGraph(gl, X_MIN, X_MAX, Y_MIN, Y_MAX);

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        SlopeInterceptLine(gl, new Point2D(-50, -20),  new Point2D(50, 20));
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    public void SlopeInterceptLine(GL gl, Point2D a, Point2D b) {
        if (b.getX() - a.getX() == 0) {
            int y_min = (int) Math.min(a.getY(), b.getY());
            int y_max = (int) Math.max(a.getY(), b.getY());
            gl.glBegin(GL.GL_LINES);
            for (int i = y_min; i  <= y_max; i++) {
                gl.glVertex2d(a.getX(), i);
            }
            gl.glEnd();
        }

        if (b.getY() - a.getY() == 0) {
            int x_min = (int) Math.min(a.getX(), b.getX());
            int x_max = (int) Math.max(a.getX(), b.getX());
            gl.glBegin(GL.GL_LINES);
            for (int i = x_min; i  <= x_max; i++) {
                gl.glVertex2d(i, a.getY());
            }
            gl.glEnd();
        }


        double m = (b.getY() - a.getY()) / (b.getX() - a.getX());
        double c = a.getY() - (m * a.getX());
        ArrayList<Point2D> points = new ArrayList<>();

        if (m < 1) {// priority
            if (b.getX() < a.getX()) {
                Point2D temp = a;
                a = b;
                b = temp;
            }
            while (points.get(points.size() - 1).getX() < b.getX()) {
                int x = (int) (points.get(points.size() - 1).getX() + 1);
                int y = (int) (Math.round(m * x + c));
                points.add(new Point2D(x, y));
            }
        } else {// priority
            while (points.get(points.size() - 1).getY() > b.getY()) {
                int y = (int) (points.get(points.size() - 1).getY() + 1);
                int x = (int) (Math.round((y * 1.0 / m) - c));
                points.add(new Point2D(x, y));
            }
        }
        gl.glBegin(GL.GL_LINES);
        for (int i = 1; i < points.size(); i++) {
            gl.glVertex2d(points.get(i - 1).getX(), points.get(i - 1).getY());
            gl.glVertex2d(points.get(i).getX(), points.get(i).getY());
        }
        gl.glEnd();
    }

    private void drawGraph(GL gl, int x_min, int x_max, int y_min, int y_max) {
        float red;
        float green;
        float blue;
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
////////////////////
//drawing the grid
        red = 0.2f;
        green = 0.2f;
        blue = 0.2f;
        gl.glColor3f(red, green, blue);
//You may notice I'm using GL_LINES here.
//Details of glBegin() will be discussed latter.
        gl.glBegin(GL.GL_LINES);
//draw the vertical lines
        for (int x = x_min; x <= x_max; x += (x_max / 25)) {
            gl.glVertex2d(x, y_min);
            gl.glVertex2d(x, y_max);
        }
//draw the horizontal lines
        for (int y = y_min; y <= y_max; y += (y_max / 25)) {
            gl.glVertex2d(x_min, y);
            gl.glVertex2d(x_max, y);
        }
        gl.glEnd();
//////////////////////////////
// draw the x-axis and y-axis
        red = 0.0f;
        green = 0.2f;
        blue = 0.4f;
        gl.glColor3f(red, green, blue);
        gl.glBegin(GL.GL_LINES);
//line for y-axis
        gl.glVertex2d(0, (14/15.0 * y_max));
        gl.glVertex2d(0, -(14/15.0 * y_max));
//line for x-axis
        gl.glVertex2d((24/25.0 * x_max), 0);
        gl.glVertex2d(-(24/25.0 * x_max), 0);
        gl.glEnd();
/////////////////////
// draw arrow heads
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex2d(0, y_max);
        gl.glVertex2d(-10, (14/15.0 * y_max));
        gl.glVertex2d(10, (14/15.0 * y_max));
        gl.glVertex2d(0, y_min);
        gl.glVertex2d(-10, -(14/15.0 * y_max));
        gl.glVertex2d(10, -(14/15.0 * y_max));
        gl.glVertex2d(x_max, 0);
        gl.glVertex2d((24/25.0 * x_max), -10);
        gl.glVertex2d((24/25.0 * x_max), 10);
        gl.glVertex2d(x_min, 0);
        gl.glVertex2d(-(24/25.0 * x_max), -10);
        gl.glVertex2d(-(24/25.0 * x_max), 10);
        gl.glEnd();
    }
}
