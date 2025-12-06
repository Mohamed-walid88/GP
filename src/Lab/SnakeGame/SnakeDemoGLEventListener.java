package Lab.SnakeGame;

import com.sun.opengl.util.GLUT;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class SnakeDemoGLEventListener implements GLEventListener {

    private final int SNAKE_SPEED = 5;
    private final int SNAKE_SIZE = 25;
    private final int APPLE_RADIUS = 10;

    private int snakeX = 0;
    private int snakeY = 0;

    private int velocityX = 0;
    private int velocityY = 0;

    private int appleX = 0;
    private int appleY = 0;
    private boolean isAppleExist = false;

    private boolean isGameOver = false;
    private final GLUT glut = new GLUT();

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-400.0, 400.0, -250.0, 250.0, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        if (isGameOver) {
            gl.glClearColor(0.5f, 0.0f, 0.0f, 1.0f);
            gl.glClear(GL.GL_COLOR_BUFFER_BIT);

            gl.glColor3f(1.0f, 1.0f, 1.0f);
            drawText(gl);
        }
        else {
            gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            gl.glClear(GL.GL_COLOR_BUFFER_BIT);

            ensureAppleExists(gl);
            gl.glColor3f(1.0f, 0.0f, 0.0f);
            drawCircle(gl, appleX, appleY, APPLE_RADIUS);

            gl.glColor3f(0.0f, 1.0f, 0.0f);
            drawSquare(gl,
                    snakeX,
                    snakeY,
                    snakeX + SNAKE_SIZE,
                    snakeY,
                    snakeX + SNAKE_SIZE,
                    snakeY + SNAKE_SIZE,
                    snakeX,
                    snakeY + SNAKE_SIZE);

            snakeX += (SNAKE_SPEED * velocityX);
            snakeY += (SNAKE_SPEED * velocityY);

            checkAppleCollision();
            checkSnakeCollision();
        }
    }

    private void ensureAppleExists(GL gl) {
        if (isAppleExist) return;

        while (true) {
            int randomX = (int) ((Math.random() * 750) - 400);
            int randomY = (int) ((Math.random() * 450) - 250);

            if (randomX <= -400) randomX = -380;
            if (randomY <= -250) randomY = -230;

            boolean isAppleOnSnakeX = (randomX >= snakeX && randomX <= snakeX + SNAKE_SIZE);
            boolean isAppleOnSnakeY = (randomY >= snakeY && randomY <= snakeY + SNAKE_SIZE);

            if (!(isAppleOnSnakeX && isAppleOnSnakeY)) {
                setApplePosition(randomX, randomY);
                isAppleExist = true;
                break;
            }
        }
    }

    public void checkAppleCollision() {
        boolean isAppleOnSnakeX = (appleX >= snakeX && appleX <= snakeX + SNAKE_SIZE);
        boolean isAppleOnSnakeY = (appleY >= snakeY && appleY <= snakeY + SNAKE_SIZE);

        if (isAppleOnSnakeX && isAppleOnSnakeY) isAppleExist = false;
    }

    public void checkSnakeCollision() {
        boolean isSnakeOnBorderX = (snakeX >= 400 || snakeX <= -400);
        boolean isSnakeOnBorderY = (snakeY >= 250 || snakeY <= -250);

        if (isSnakeOnBorderX || isSnakeOnBorderY) {
            isGameOver = true;
        }
    }

    private void drawText(GL gl) {
        gl.glRasterPos2i(-90, 0);
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "GAME OVER");
    }

    public void setDirectionUp() {
        velocityY = 1;
        velocityX = 0;
    }

    public void setDirectionDown() {
        velocityY = -1;
        velocityX = 0;
    }

    public void setDirectionLeft() {
        velocityX = -1;
        velocityY = 0;
    }

    public void setDirectionRight() {
        velocityX = 1;
        velocityY = 0;
    }

    public void setApplePosition(int x, int y) {
        this.appleX = x;
        this.appleY = y;
    }

    public void drawSquare(GL gl, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);
        gl.glVertex2d(x4, y4);
        gl.glEnd();
    }

    private void drawCircleLoop(GL gl, int x_center, int y_center, int radius) {
        double THREE_SIXTY = Math.PI * 2;
        double Step = Math.PI / 180.0;

        for (double i = 0; i < THREE_SIXTY; i += Step) {
            int x = (int) (radius * Math.cos(i)) + x_center;
            int y = (int) (radius * Math.sin(i)) + y_center;
            gl.glVertex2d(x, y);
        }
    }

    private void drawCircle(GL gl, int x_center, int y_center, int radius) {
        gl.glBegin(GL.GL_POLYGON);
        drawCircleLoop(gl, x_center, y_center, radius);
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {}

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {}
}