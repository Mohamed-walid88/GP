package Lab.Task2_SlidingBall;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class SlidingBallGLEventListener implements GLEventListener {

    private final int SQUARE_WIDTH = 50;
    private final int SCREEN_WIDTH = 350;
    private final int SCREEN_HEIGHT = 350;
    // UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3
    private int direction = 0;
    private int squareX = 0;
    private int squareY = 0;


    /**
     * Take care of initialization here.
     */
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

//        gl.glViewport(0, 0, 600, 300);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-SCREEN_WIDTH, SCREEN_WIDTH, -SCREEN_HEIGHT, SCREEN_HEIGHT, -1.0, 1.0);

    }

    /**
     * Take care of drawing here.
     */
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        drawSquare(gl, squareX, squareY, SQUARE_WIDTH);
        updateDirection();
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
     * Nowadays, this doesn't happen much, unless
     * a programmer has his program do it.
     */
    public void displayChanged(
            GLAutoDrawable drawable,
            boolean modeChanged,
            boolean deviceChanged
    ) {
    }

    private void updateDirection() {
        if (direction == 0) {
            squareY+=5;
        }
        if (direction == 1) {
            squareY-=5;
        }
        if (direction == 2) {
            squareX+=5;
        }
        if (direction == 3) {
            squareX-=5;
        }

        if (squareY >  SCREEN_HEIGHT) {
            squareY = -SCREEN_HEIGHT;
        } else if (squareY < -SCREEN_HEIGHT) {
            squareY = SCREEN_HEIGHT;
        } else if (squareX >  SCREEN_WIDTH) {
            squareX = -SCREEN_WIDTH;
        } else if (squareX < -SCREEN_WIDTH) {
            squareX = SCREEN_WIDTH;
        }
    }

    private void drawSquare(GL gl, int centerX, int centerY, int width) {
        gl.glBegin(GL.GL_POLYGON);

        int incrementation = width / 2;// 25

        gl.glVertex2d(centerX - incrementation, centerY - incrementation);//(-25, -25)
        gl.glVertex2d(centerX + incrementation, centerY - incrementation);//(25, -25)
        gl.glVertex2d(centerX + incrementation, centerY + incrementation);//(25, 25)
        gl.glVertex2d(centerX - incrementation, centerY + incrementation);//(-25, 25)

        gl.glEnd();
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
