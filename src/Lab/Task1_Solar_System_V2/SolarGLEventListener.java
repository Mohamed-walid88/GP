package Lab.Task1_Solar_System_V2;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class SolarGLEventListener implements GLEventListener {
    private final double THREE_SIXTY = Math.PI * 2;
    private final double STEP = Math.PI / 360.0;
    private final double e = 0.9;
    private final int ORBIT_1_RADIUS = 50;
    private final int ORBIT_2_RADIUS = 70;
    private final double a = ORBIT_1_RADIUS*1.0 / (1 - (e*e));
    private final double SUN_X = -1.0 * (a * e) / 2;
    private final int SUN_Y = 0;
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

        Orbits(gl, ORBIT_1_RADIUS);
        Orbits(gl, ORBIT_2_RADIUS);

        setHexColor(gl, "#FFBF00");
        drewCircle(gl, (int) SUN_X, SUN_Y, 50);

        if (RotationDirection.equals("Clockwise")) {
            gl.glPushMatrix();
            RotateOrbit1(gl);
            setHexColor(gl, "#006994");
            drewCircle(gl, 0, 0, 30);
            gl.glPopMatrix();

            gl.glPushMatrix();
            RotateOrbit2(gl);
            setHexColor(gl, "#8B0000");
            drewCircle(gl, 0, 0, 20);
            gl.glPopMatrix();

            if (!Zooming) {
                CurrentPositionOrbit1 -= RotationStep;
                CurrentPositionOrbit2 -= RotationStep;
            }
        }
        else {
            gl.glPushMatrix();
            RotateOrbit1(gl);
            setHexColor(gl, "#006994");
            drewCircle(gl, 0, 0, 30);
            gl.glPopMatrix();

            gl.glPushMatrix();
            RotateOrbit2(gl);
            setHexColor(gl, "#8B0000");
            drewCircle(gl, 0, 0, 20);
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

    private void drewCircle(GL gl, int x_center, int y_center, int radius) {
        gl.glBegin(GL.GL_POLYGON);

        for (double i = 0; i < THREE_SIXTY; i += STEP) {
            int x = (int) (radius * Math.cos(i)) +  x_center;
            int y = (int) (radius * Math.sin(i)) +  y_center;
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    private void drawEllipse(GL gl, int x_center, int y_center, int l) {
        double i = 0;

        double step = 2 * Math.PI;

        double centerOffset = centerOffset(l);

        double radius = l / (1.0 + e * Math.cos(i));

        while (i < THREE_SIXTY) {
            int x = (int) (radius * Math.cos(i) + x_center + (int)centerOffset);
            int y = (int) (radius * Math.sin(i)) +  y_center;
            drewCircle(gl, x, y, 2);
            i += (step / radius);
            radius = l / (1 + e * Math.cos(i));
        }
    }

    private void Orbits(GL gl, int radius) {
        setHexColor(gl, "#FFFFFF");

        drawEllipse(gl, 0, 0, radius);
    }

    private void RotateOrbit1(GL gl) {
        double radius = ORBIT_1_RADIUS / (1.0 + e * Math.cos(Math.toRadians(CurrentPositionOrbit1)));
        double centerOffset = centerOffset(ORBIT_1_RADIUS);
        float x = (float)(radius * Math.cos(Math.toRadians(CurrentPositionOrbit1)) + centerOffset);// r * cos(theta) + centerOffset
        float y = (float)(radius * Math.sin(Math.toRadians(CurrentPositionOrbit1)));
        gl.glTranslatef(x, y, 0);
    }
    private void RotateOrbit2(GL gl) {
        double radius = ORBIT_2_RADIUS / (1.0 + e * Math.cos(Math.toRadians(CurrentPositionOrbit2/2.0)));
        double centerOffset = centerOffset(ORBIT_2_RADIUS);
        float x = (float)(radius * Math.cos(Math.toRadians(CurrentPositionOrbit2/2.0)) + centerOffset);
        float y = (float)(radius * Math.sin(Math.toRadians(CurrentPositionOrbit2/2.0)));
        gl.glTranslatef(x, y, 0);
    }

    private double centerOffset(int l) {
        return (l * e) / (1.0 - (e * e));
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