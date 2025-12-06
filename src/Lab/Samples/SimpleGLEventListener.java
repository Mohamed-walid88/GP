package Lab.Samples;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class SimpleGLEventListener implements GLEventListener {
    /**
     * Take care of initialization here.
     */
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-350.0, 350.0, -350.0, 350.0, -1.0, 1.0);

    }

    /**
     * Take care of drawing here.
     */
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glPointSize(13.0f);

        gl.glColor3f(1.0f, 0.0f, 0.0f);

        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2i(300, 150);
        gl.glEnd();

        gl.glPointSize(33.0f);

        gl.glColor3f(0.0f, 1.0f, 0.0f);

        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2i(200, 150);
        gl.glEnd();

        gl.glColor3f(0.0f, 0.0f, 1.0f);

        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2i(400, 150);
        gl.glEnd();
    }

    /**
     * Called when the GLDrawable (GLCanvas
     * or GLJPanel) has changed in size. We
     * won't need this, but you may eventually
     * need it -- just not yet.
     */
    public void reshape(
            GLAutoDrawable drawable,
            int x,
            int y,
            int width,
            int height
    ) {
    }

    /**
     * If the display depth is changed while the
     * program is running this method is called.
     * Nowadays this doesn't happen much, unless
     * a programmer has his program do it.
     */
    public void displayChanged(
            GLAutoDrawable drawable,
            boolean modeChanged,
            boolean deviceChanged
    ) {
    }

    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
    }

    private void setHexColor(GL gl, String hex) {
        if(hex.length() < 7) {
            System.err.println("Hex string length is less than 7 Characters");
            return;
        }
        try {
            int r =  Integer.parseInt(hex.substring(1, 3), 16);
            int g = Integer.parseInt(hex.substring(3, 5), 16);
            int b = Integer.parseInt(hex.substring(5, 7), 16);
            gl.glColor3f(r / 255f,g / 255f,b /  255f);
        }catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void drawSquare(GL gl, int centerX, int centerY, int width) {
        gl.glBegin(GL.GL_POLYGON);

        int incrementation = width / 2;

        gl.glVertex2d(centerX - incrementation, centerY - incrementation);
        gl.glVertex2d(centerX + incrementation, centerY - incrementation);
        gl.glVertex2d(centerX + incrementation, centerY + incrementation);
        gl.glVertex2d(centerX - incrementation, centerY + incrementation);

        gl.glEnd();
    }

    private void drawCircle(GL gl, int x_center, int y_center, int radius) {
        gl.glBegin(GL.GL_POLYGON);
        double THREE_SIXTY = Math.PI * 2;
        double Step = Math.PI / 180.0;

        for (double i = 0; i < THREE_SIXTY; i += Step/radius) {
            int x = (int) (radius * Math.cos(i)) +  x_center;
            int y = (int) (radius * Math.sin(i)) +  y_center;
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    private void drawTriangle(GL gl, int x1, int y1, int x2, int y2, int x3, int y3) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);

        gl.glEnd();
    }

    private void drawEllipse(GL gl, int x_center, int y_center, int l) {
        gl.glBegin(GL.GL_POLYGON);
        double THREE_SIXTY = Math.PI * 2;
        double Step = Math.PI / 360.0;
        double i = 0;
        double e = 0.9;

        double centerOffset = (l * e) / (1.0 - (e * e));

        double radius = l / (1 + e * Math.cos(i));

        while (i < THREE_SIXTY) {
            int x = (int) (radius * Math.cos(i) + x_center + (int)centerOffset);
            int y = (int) (radius * Math.sin(i)) +  y_center;
            gl.glVertex2d(x, y);
            i += Step / radius;
            radius = l / (1 + e * Math.cos(i));
        }
        gl.glEnd();
    }
}
