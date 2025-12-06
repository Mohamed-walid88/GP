package Lab.Task1_GardenVally;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class DayGLEventListener implements GLEventListener {
    private String time = "";

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0, 0, 300, 300);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        // Set top-left origin (notice the flipped top and bottom)
        gl.glOrtho(0, 900.0, 0.0, 600.0, -1.0, 1.0);
    }

    GL gl;
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        boolean isDay = !time.equals("Night");

        if (isDay) {
            gl.glClearColor(0.518f, 0.765f, 0.886f, 1.0f);
        } else {
            gl.glClearColor(0.098f, 0.0f, 0.235f, 1.0f);
        }

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);


        if (isDay) {
            Sun(800, 500, 50);
            Clouds(150, 500);
            Clouds(450, 500);
        } else {
            gl.glColor3f(0.9f, 0.9f, 0.9f);
            Circle(800, 500, 40);
        }

        if (isDay) {
            gl.glColor3f(0.678f, 0.918f, 0.631f);
        } else {
            gl.glColor3f(0.3f, 0.5f, 0.25f);
        }
        Square(0, 150, 900, 150, 900, 250, 0, 250);

        if (isDay) {
            gl.glColor3f(0.871f, 0.851f, 0.690f);
        } else {
            gl.glColor3f(0.4f, 0.4f, 0.3f);
        }
        Square(0, 0, 900, 0, 900, 150, 0, 150);


        House(50, 150, isDay);
        Fence(30, 120, isDay);
        Bush(300, 200, false, isDay);
        House(350, 165, isDay);
        Tree(550, 160, true, isDay);
        Bush(650, 200, false, isDay);
        House(700, 165, isDay);
        Bush(830, 200, false, isDay);
        Tree(220, 160, false, isDay);
        Tree(880, 160, true, isDay);

        if (isDay) {
            Humans(480, 130, 0.0f, 0.0f, 1.0f);
            Humans(680, 130, 1.0f, 0.0f, 0.0f);
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }





    public void CircleLoop(int x_center, int y_center, int radius) {
        double THREE_SIXTY = Math.PI * 2;
        double Step = Math.PI / 180;

        for (double i = 0; i < THREE_SIXTY; i += Step) {
            int x = (int) (radius * Math.cos(i)) +  x_center;
            int y = (int) (radius * Math.sin(i)) +  y_center;
            gl.glVertex2d(x, y);
        }
    }

    public void Circle(int x_center, int y_center, int radius) {
        gl.glBegin(GL.GL_POLYGON);
        CircleLoop(x_center, y_center, radius);
        gl.glEnd();
    }

    public void Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);

        gl.glEnd();
    }

    public void Square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);
        gl.glVertex2d(x4, y4);

        gl.glEnd();
    }

    public void Window(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        gl.glColor3f(0.0f, 0.0f, 0.0f);

        gl.glBegin(1);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);

        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);

        gl.glVertex2d(x3, y3);
        gl.glVertex2d(x4, y4);

        gl.glVertex2d(x4, y4);
        gl.glVertex2d(x1, y1);

        int midBottomX = (x1 + x2) / 2;
        int midBottomY = (y1 + y2) / 2;

        int midRightX = (x2 + x3) / 2;
        int midRightY = (y2 + y3) / 2;

        int midTopX = (x3 + x4) / 2;
        int midTopY = (y3 + y4) / 2;

        int midLeftX = (x4 + x1) / 2;
        int midLeftY = (y4 + y1) / 2;


        gl.glVertex2d(midBottomX, midBottomY);
        gl.glVertex2d(midTopX, midTopY);

        gl.glVertex2d(midLeftX, midLeftY);
        gl.glVertex2d(midRightX, midRightY);

        gl.glEnd();
    }

    public void Door(int x1, int y1, int x2, int y2, int x3, int y3,  int x4, int y4) {
        gl.glColor3f(0.431f, 0.306f, 0.188f);
        Square(x1, y1, x2, y2, x3, y3, x4, y4);

        gl.glColor3f(0.992f, 0.816f, 0.090f);
        Circle(x1 + 31, y1 + 34, 5);
    }

    public void Sun(int x_center, int y_center, int radius) {
        gl.glColor3f(1.0f, 1.0f, 0.0f);

        gl.glBegin(GL.GL_POLYGON);
        CircleLoop(x_center, y_center, radius);
        gl.glEnd();

        double THREE_SIXTY = Math.PI * 2;
        double ShineStep = Math.PI / 4;

        gl.glBegin(GL.GL_LINES);
        for (double i = 0; i < THREE_SIXTY; i += ShineStep) {
            int x = (int) ((radius + 5) * Math.cos(i)) + x_center;
            int y = (int) ((radius + 5) * Math.sin(i)) + y_center;
            int x2 = (int) ((radius + 10) * Math.cos(i)) + x_center;
            int y2 = (int) ((radius + 10) * Math.sin(i)) + y_center;

            gl.glVertex2d(x, y);
            gl.glVertex2d(x2, y2);
        }
        gl.glEnd();
    }

    public void Tree(int x_start, int y_start, boolean circle, boolean day) {
        if (day) {
            gl.glColor3f(0.545f, 0.271f, 0.075f);
        } else {
            gl.glColor3f(0.3f, 0.15f, 0.0f);
        }

        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2i(x_start, y_start);
        gl.glVertex2i(x_start + 15, y_start);
        gl.glVertex2i(x_start + 15, y_start + 80);
        gl.glVertex2i(x_start, y_start + 80);
        gl.glEnd();

        if (day) {
            gl.glColor3f(0.486f, 0.988f, 0.0f);
        } else {
            gl.glColor3f(0.1f, 0.4f, 0.0f);
        }

        if (circle) {
            Circle(x_start + 7, y_start + 104, 37);
            Circle(x_start + 36, y_start + 72, 37);
            Circle(x_start - 22, y_start + 72, 37);
        }
        else {
            Triangle(x_start - 23, y_start + 53, x_start + 42, y_start + 53, x_start+ 11, y_start + 108);
            Triangle(x_start - 33, y_start + 78, x_start + 53, y_start + 78, x_start + 11, y_start + 147);
        }
    }

    public void House(int x_start, int y_start, boolean day) {
        if (day) {
            gl.glColor3f(0.831f, 0.722f, 0.580f);
        } else {
            gl.glColor3f(0.6f, 0.5f, 0.4f);
        }
        Square(x_start, y_start, x_start + 128, y_start, x_start + 128, y_start + 115, x_start, y_start + 115);

        if (day) {
            gl.glColor3f(0.647f, 0.392f, 0.235f);
        } else {
            gl.glColor3f(0.4f, 0.2f, 0.1f);
        }
        Triangle(x_start, y_start + 115, x_start + 128, y_start + 115, x_start + 64, y_start + 174);

        int winX1 = x_start + 76;
        int winY1 = y_start + 60;
        int winX2 = x_start + 117;
        int winY2 = y_start + 60;
        int winX3 = x_start + 117;
        int winY3 = y_start + 95;
        int winX4 = x_start + 76;
        int winY4 = y_start + 95;

        if (day) {
            gl.glColor3f(0.518f, 0.765f, 0.886f);
        } else {
            gl.glColor3f(1.0f, 1.0f, 0.0f);
        }
        Square(winX1, winY1, winX2, winY2, winX3, winY3, winX4, winY4);
        Window(winX1, winY1, winX2, winY2, winX3, winY3, winX4, winY4);

        Door(x_start + 21, y_start, x_start + 63, y_start, x_start + 63, y_start + 70, x_start + 21, y_start + 70);
    }

    public void Clouds(int x_center, int y_center) {
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        Circle(x_center, y_center, 40);
        Circle(x_center + 30, y_center - 10, 35);
        Circle(x_center - 30, y_center - 10, 35);
    }

    public void Bush(int x_center, int y_center, boolean Twin, boolean day) {
        if (day) {
            gl.glColor3f(0.290f, 0.663f, 0.310f);
        } else {
            gl.glColor3f(0.1f, 0.3f, 0.1f);
        }

        Circle(x_center, y_center, 40);
        if  (Twin) {
            Circle(x_center - 40, y_center - 5, 35);
        }
    }

    public void Humans(int x_center, int y_center, float r,  float g, float b) {
        gl.glColor3f(r, g, b);
        Square(x_center, y_center, x_center + 16, y_center, x_center + 16, y_center + 26, x_center, y_center + 26);

        gl.glBegin(GL.GL_LINES);

        gl.glVertex2d(x_center, y_center);
        gl.glVertex2d(x_center - 7, y_center - 24);

        gl.glVertex2d(x_center + 16, y_center);
        gl.glVertex2d(x_center + 26, y_center - 24);

        gl.glVertex2d(x_center + 16, y_center + 21);
        gl.glVertex2d(x_center + 36, y_center + 16);

        gl.glVertex2d(x_center, y_center + 21);
        gl.glVertex2d(x_center - 15, y_center + 12);

        gl.glEnd();

        gl.glColor3f(1.0f, 0.878f, 0.741f);
        Circle(x_center + 8, y_center + 38, 10);

    }

    public void Fence(int x_center, int y_center, boolean day) {
        if (day) {
            gl.glColor3f(0.545f, 0.271f, 0.075f);
        } else {
            gl.glColor3f(0.3f, 0.15f, 0.0f);
        }

        int new_x = x_center;
        for (int i = 0 ; i < 5 ; i++) {
            Square(new_x, y_center, new_x + 6, y_center, new_x + 6, y_center + 59, new_x, y_center + 59);
            new_x += 52;
        }

        int x = x_center - 21;
        int new_y = y_center + 26;
        for (int i = 0 ; i < 2 ; i++) {
            Square(x, new_y, x + 244, new_y, x + 244, new_y + 8, x, new_y + 8);
            new_y += 18;
        }
    }
}