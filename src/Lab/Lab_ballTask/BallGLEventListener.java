package Lab.Lab_ballTask;

import Lab.Pair.Pair;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BallGLEventListener implements GLEventListener {
    private final int[] OPP_UP = {3, 4, 5};
    private final int[] OPP_DOWN = {0, 1, 2};
    private final int[] OPP_RIGHT = {2, 5, 7};
    private final int[] OPP_LEFT = {1, 4, 6};

    private final int[] OPP_UP_RIGHT = {2, 5, 7};
    private final int[] OPP_UP_LEFT = {1, 4, 6};
    private final int[] OPP_DOWN_RIGHT = {2, 5, 7};
    private final int[] OPP_DOWN_LEFT = {1, 4, 6};

    private final double X_MIN = -350.0;
    private final double X_MAX = 350.0;
    private final double Y_MIN = -350.0;
    private final double Y_MAX = 350.0;
    private final int NUMBER_OF_DIRECTIONS = 4;
    private final int MAX_STEPS = 30;
    private final int MAX_BALLS = 5;
    private final int FISH_SIZE = 25;
    private final double ONE_DEGREE = (Math.PI / 180);
    private final double THREE_SIXTY = 2 * Math.PI;
    private List<Pair> positions = new ArrayList<>(MAX_BALLS);
    private double ballRadius;
    private boolean[] fishMoveDirection = new boolean[NUMBER_OF_DIRECTIONS];
    private double fishX = 0;
    private double fishY = 0;
    private int[] direction = new int[MAX_BALLS];
    private int steps;


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(X_MIN, X_MAX, Y_MIN, Y_MAX, -1.0, 1.0);

        play();

        ballRadius = 30;

        steps = 1 + (int) (Math.random() * MAX_STEPS);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        if (checkWin()) {
            String[] options = {"Play", "Exit"};

            int choice = JOptionPane.showOptionDialog(null,
                    "Do you want to play again",
                    "Play again?",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                play();
            }
            else  if (choice == 1) {
                System.exit(0);
            }
        }
        for(int i = 0; i < positions.size(); i++) {
            updateBallPosition(i);
            drawBall(gl, positions.get(i).getFirst(), positions.get(i).getSecond());
        }

        Square(gl, fishX, fishY, FISH_SIZE);
        updateFishPosition();

        checkCollision();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    private void drawBall(GL gl, double xBall, double yBall) {
        gl.glColor3f(0.5f, 0.0f, 0.5f);
        gl.glBegin(GL.GL_POLYGON);
        for (double a = 0; a < THREE_SIXTY; a += ONE_DEGREE) {
            double x = xBall + ballRadius * (Math.cos(a));
            double y = yBall + ballRadius * (Math.sin(a));
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    private void Square(GL gl, double startX, double startY, int size){
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(startX, startY);
        gl.glVertex2d(startX + size, startY);
        gl.glVertex2d(startX + size, startY + size);
        gl.glVertex2d(startX, startY + size);

        gl.glEnd();
    }

    private void updateBallPosition(int idx) {
        if(direction[idx] == 0) {
            positions.get(idx).incrementSecond(3);
        }else if(direction[idx] == 1) {
            positions.get(idx).incrementFirst(3);
            positions.get(idx).incrementSecond(3);
        }else if(direction[idx] == 2) {
            positions.get(idx).incrementFirst(-3);
            positions.get(idx).incrementSecond(3);
        }else if(direction[idx] == 3) {
            positions.get(idx).incrementSecond(-3);
        }else if(direction[idx] == 4) {
            positions.get(idx).incrementFirst(3);
            positions.get(idx).incrementSecond(-3);
        }else if(direction[idx] == 5) {
            positions.get(idx).incrementFirst(-3);
            positions.get(idx).incrementSecond(-3);
        }else if(direction[idx] == 6) {
            positions.get(idx).incrementFirst(3);
        }else {
            positions.get(idx).incrementFirst(-3);
        }

        if(direction[idx] == 0) {
            if(positions.get(idx).getSecond() > Y_MAX - ballRadius) {
                direction[idx] = OPP_UP[randNum(0, 3)];
            }
        }

        if(direction[idx] == 1) {
            if(positions.get(idx).getFirst() > X_MAX - ballRadius || positions.get(idx).getSecond() > Y_MAX - ballRadius) {
                direction[idx] = OPP_UP_RIGHT[randNum(0, 3)];
            }
        }

        if(direction[idx] == 2) {
            if(positions.get(idx).getFirst() < X_MIN + ballRadius || positions.get(idx).getSecond() > Y_MAX - ballRadius) {
                direction[idx] = OPP_UP_LEFT[randNum(0, 3)];
            }
        }

        if(direction[idx] == 3) {
            if(positions.get(idx).getSecond() < Y_MIN + ballRadius) {
                direction[idx] = OPP_DOWN[randNum(0, 3)];
            }
        }

        if(direction[idx] == 4) {
            if(positions.get(idx).getSecond() < Y_MIN + ballRadius || positions.get(idx).getFirst() > X_MAX - ballRadius) {
                direction[idx] = OPP_DOWN_RIGHT[randNum(0, 3)];
            }
        }


        if(direction[idx] == 5 || positions.get(idx).getFirst() < X_MAX - ballRadius) {
            if(positions.get(idx).getSecond() < Y_MIN + ballRadius || positions.get(idx).getFirst() < X_MIN +  ballRadius) {
                direction[idx] = OPP_DOWN_LEFT[randNum(0, 3)];
            }
        }

        if(direction[idx] == 6) {
            if(positions.get(idx).getFirst() > X_MAX - ballRadius) {
                direction[idx] = OPP_RIGHT[randNum(0, 3)];
            }
        }

        if(direction[idx] == 7) {
            if(positions.get(idx).getFirst() < X_MIN + ballRadius) {
                direction[idx] = OPP_LEFT[randNum(0, 3)];
            }
        }
    }

    private void updateFishPosition() {
        if (fishMoveDirection[0]) setFishY(3);

        if (fishMoveDirection[1]) setFishY(-3);

        if (fishMoveDirection[2]) setFishX(3);

        if (fishMoveDirection[3]) setFishX(-3);
    }

    private void checkCollision() {
        for (int idx = 0; idx < positions.size(); idx++) {
            double ballX = positions.get(idx).getFirst();
            double ballY = positions.get(idx).getSecond();
            double distance = Math.pow((Math.pow((ballX - fishX), 2) +  Math.pow((ballY - fishY), 2)), 0.5);

            if (distance < FISH_SIZE + ballRadius) {
                positions.remove(idx);
            }
        }
    }

    private boolean checkWin() {
        return positions.isEmpty();
    }

    private void play() {
        for(int i = 0; i < MAX_BALLS; i++) {
            positions.add(new Pair(randNum(X_MIN, X_MAX), randNum(Y_MIN, Y_MAX)));
            direction[i] = (int) (Math.random() * NUMBER_OF_DIRECTIONS);
        }
        fishX = 0;
        fishY = 0;

        Arrays.fill(fishMoveDirection, false);
    }

    private int randNum(double a, double b) {
        return (int) (a + (int) (Math.random() * (b - a)));
    }

    private void setFishX(double fishX) {
        if (this.fishX + fishX < X_MAX - FISH_SIZE &&  this.fishX + fishX > X_MIN) {
            this.fishX += fishX;
        }
    }

    private void setFishY(double fishY) {
        if (this.fishY + fishY < Y_MAX - FISH_SIZE &&  this.fishY + fishY > Y_MIN) {
            this.fishY += fishY;
        }
    }

    public void setFishMoveDirection(int direction, boolean moving) {
        this.fishMoveDirection[direction] = moving;
    }

}
