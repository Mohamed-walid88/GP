package Lab.Task1_Solar_System;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class SolarGLEventListener implements GLEventListener {
    private String RotationDirection = "Clockwise";
    private int RotationStep = 0;
    private int CurrentPositionOrbit1 = 0;
    private int CurrentPositionOrbit2 = 0;
    private float ZoomDegree = 1;
    private float PrevZoom = 1;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();


        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(-400.0, 400.0, -225.0, 225.0, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        boolean Zooming = (PrevZoom != ZoomDegree);

        if (ZoomDegree < 0) ZoomDegree = 0;

        gl.glPushMatrix();
        gl.glScalef(ZoomDegree, ZoomDegree, 1);

        Orbits(gl, 130);
        Orbits(gl, 200);

        setHexColor(gl, "#FFBF00");
        Circle(gl, 0, 0, 50);

        if (RotationDirection.equals("Clockwise")) {
            gl.glPushMatrix();
            RotateClockWiseOrbit1(gl);
            setHexColor(gl, "#006994");
            Circle(gl, 0, 0, 30);
            gl.glPopMatrix();

            gl.glPushMatrix();
            RotateClockWiseOrbit2(gl);
            setHexColor(gl, "#8B0000");
            Circle(gl, 0, 0, 20);
            gl.glPopMatrix();

            if (!Zooming) {
                CurrentPositionOrbit1 -= RotationStep;
                CurrentPositionOrbit2 -= RotationStep;
            }
        }
        else {
            gl.glPushMatrix();
            RotateAntiClockWiseOrbit1(gl);
            setHexColor(gl, "#006994");
            Circle(gl, 0, 0, 30);
            gl.glPopMatrix();

            gl.glPushMatrix();
            RotateAntiClockWiseOrbit2(gl);
            setHexColor(gl, "#8B0000");
            Circle(gl, 0, 0, 20);
            gl.glPopMatrix();

            if (!Zooming) {
                CurrentPositionOrbit1 += RotationStep;
                CurrentPositionOrbit2 += RotationStep;
            }
        }
        CurrentPositionOrbit1 = (CurrentPositionOrbit1 + 360) % 360;
        CurrentPositionOrbit2 = (CurrentPositionOrbit2 + 720) % 720;

        gl.glPopMatrix();
        if (Zooming) PrevZoom = ZoomDegree;
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

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

    private void CircleLoop(GL gl, int x_center, int y_center, int radius) {
        double THREE_SIXTY = Math.PI * 2;
        double Step = Math.PI / 180.0;

        for (double i = 0; i < THREE_SIXTY; i += Step) {
            int x = (int) (radius * Math.cos(i)) +  x_center;
            int y = (int) (radius * Math.sin(i)) +  y_center;
            gl.glVertex2d(x, y);
        }
    }

    private void Circle(GL gl, int x_center, int y_center, int radius) {
        gl.glBegin(GL.GL_POLYGON);
        CircleLoop(gl, x_center, y_center, radius);
        gl.glEnd();
    }

    private void Orbits(GL gl, int radius) {
        setHexColor(gl, "#FFFFFF");
        double THREE_SIXTY = Math.PI * 2;
        double Step = Math.PI / 45.0;

        for (double i = 0; i < THREE_SIXTY; i += Step) {
            int x = (int) (radius * Math.cos(i));
            int y = (int) (radius * Math.sin(i));
            Circle(gl, x, y, 2);
        }
    }

    private void RotateClockWiseOrbit1(GL gl) {
        float x = (float)(130 * Math.cos(Math.toRadians(CurrentPositionOrbit1)));
        float y = (float)(130 * Math.sin(Math.toRadians(CurrentPositionOrbit1)));
        gl.glTranslatef(x, y, 0);
    }
    private void RotateClockWiseOrbit2(GL gl) {
        float x = (float)(200 * Math.cos(Math.toRadians(CurrentPositionOrbit2/2.0)));
        float y = (float)(200 * Math.sin(Math.toRadians(CurrentPositionOrbit2/2.0)));
        gl.glTranslatef(x, y, 0);
    }
    private void RotateAntiClockWiseOrbit1(GL gl) {
        float x = (float)(130 * Math.cos(Math.toRadians(CurrentPositionOrbit1)));
        float y = (float)(130 * Math.sin(Math.toRadians(CurrentPositionOrbit1)));
        gl.glTranslatef(x, y, 0);
    }
    private void RotateAntiClockWiseOrbit2(GL gl) {
        float x = (float)(200 * Math.cos(Math.toRadians(CurrentPositionOrbit2/2.0)));
        float y = (float)(200 * Math.sin(Math.toRadians(CurrentPositionOrbit2/2.0)));
        gl.glTranslatef(x, y, 0);
    }


    public void setRotationDirection(String rotationDirection) {
        RotationDirection = rotationDirection;
    }

    public void ChangeRotationStep(int rotationStep) {
        this.RotationStep += rotationStep;
    }

    public void ChangeZoomDegree(float zoomDegree) {
        ZoomDegree += zoomDegree;
    }

    public int getRotationStep() {
        return RotationStep;
    }
}
