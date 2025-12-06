package Lab.Lab_Task2;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public class TestGLEventListener implements GLEventListener {
    private int Move = 0;
    private float ZoomDegree = 1;
    private float RotateDegree = 5;
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();


        gl.glClearColor(0.2f, 0.827f, 1.0f, 1.0f);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(-400.0, 400.0, -225.0, 225.0, -1.0, 1.0);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        setHexColor(gl, "#006994");
        Square(gl, -400, -225, 400, -225, 400, 0, -400, 0);

        if (ZoomDegree < 0) ZoomDegree = 0;

        gl.glPushMatrix();
        gl.glScalef(ZoomDegree, ZoomDegree, 1);
        gl.glTranslatef(Move, 0, 0);
        // sail
        gl.glPushMatrix();
        gl.glRotatef(RotateDegree, 0f, 1f, 0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        Square(gl, 0, 0, -5, 0, -5, 50, 0, 50);

        // flag
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        Triangle(gl, 20, 35, 0, 20, 0, 50);
        gl.glPopMatrix();

        // boat
        gl.glColor3f(0.568f, 0.284f, 0.0f);
        Square(gl, 30, 0, -30, 0, -40, 20, 40, 20);
        gl.glPopMatrix();
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

    public void Square(GL gl, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);
        gl.glVertex2d(x4, y4);

        gl.glEnd();
    }

    private void Triangle(GL gl, int x1, int y1, int x2, int y2, int x3, int y3) {
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2d(x1, y1);
        gl.glVertex2d(x2, y2);
        gl.glVertex2d(x3, y3);

        gl.glEnd();
    }

    public void ChangeMove(int move) {
        this.Move += move;
    }
    public void AddZoom(float zoom) {
        this.ZoomDegree += zoom;
    }
    public void AddRotate(float rotate) {
        this.RotateDegree += rotate;
    }
}
