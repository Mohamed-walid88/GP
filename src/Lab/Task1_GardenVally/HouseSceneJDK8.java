package Lab.Task1_GardenVally;

import javax.media.opengl.*;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.glu.GLU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HouseSceneJDK8 extends GLCanvas implements GLEventListener {

    private GLU glu;

    public HouseSceneJDK8() {
        this.addGLEventListener(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        glu = new GLU();
        gl.glClearColor(0.6f, 0.8f, 1.0f, 1.0f); // Sky blue background
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        if (height == 0) height = 1;
        float aspect = (float) width / height;

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, aspect, 0.1, 100.0);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

        // --- Drawing the Scene ---

        // Draw Clouds
        drawCloud(gl, 1.5f, 1.2f, 1.0f); // Right cloud
        drawCloud(gl, -0.8f, 1.5f, 0.8f); // Left, smaller cloud

        // Draw Sun
        gl.glColor3f(1.0f, 1.0f, 0.0f); // Yellow
        drawCircle(gl, -1.5f, 1.2f, 0.3f, 30);

        // Draw Grass
        gl.glColor3f(0.4f, 0.8f, 0.4f); // Green
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex3f(-3.0f, -1.0f, 0.0f);
        gl.glVertex3f(3.0f, -1.0f, 0.0f);
        gl.glVertex3f(3.0f, -3.0f, 0.0f);
        gl.glVertex3f(-3.0f, -3.0f, 0.0f);
        gl.glEnd();

        // Draw House Body (Lowered by 0.5f)
        gl.glColor3f(1.0f, 1.0f, 0.4f); // Light yellow
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2f(-0.5f, -1.0f); // y = -0.5 -> -1.0
        gl.glVertex2f(0.5f, -1.0f);  // y = -0.5 -> -1.0
        gl.glVertex2f(0.5f, 0.0f);   // y = 0.5 -> 0.0
        gl.glVertex2f(-0.5f, 0.0f);   // y = 0.5 -> 0.0
        gl.glEnd();

        // Draw Roof (Lowered by 0.5f)
        gl.glColor3f(0.8f, 0.2f, 0.2f); // Red
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex2f(-0.6f, 0.0f);   // y = 0.5 -> 0.0
        gl.glVertex2f(0.6f, 0.0f);    // y = 0.5 -> 0.0
        gl.glVertex2f(0.0f, 0.5f);    // y = 1.0 -> 0.5
        gl.glEnd();

        // Draw Door (Lowered by 0.5f)
        gl.glColor3f(0.6f, 0.1f, 0.1f); // Dark red
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2f(-0.15f, -1.0f); // y = -0.5 -> -1.0
        gl.glVertex2f(0.15f, -1.0f);  // y = -0.5 -> -1.0
        gl.glVertex2f(0.15f, -0.6f);  // y = -0.1 -> -0.6
        gl.glVertex2f(-0.15f, -0.6f);  // y = -0.1 -> -0.6
        gl.glEnd();

        // Draw Trees (Lowered to sit on the grass)
        drawTree(gl, -1.2f, -1.0f, 0.3f, 0.7f); // Left tree, y = -0.9 -> -1.0
        drawTree(gl, 1.2f, -1.0f, 0.3f, 0.7f);  // Right tree, y = -0.9 -> -1.0

        gl.glFlush();
    }

    private void drawCloud(GL gl, float x, float y, float scale) {
        gl.glColor3f(1.0f, 1.0f, 1.0f); // White
        // Draw a few overlapping circles to make a cloud shape
        drawCircle(gl, x, y, 0.2f * scale, 20);
        drawCircle(gl, x + 0.15f * scale, y - 0.1f * scale, 0.25f * scale, 20);
        drawCircle(gl, x - 0.2f * scale, y, 0.22f * scale, 20);
        drawCircle(gl, x + 0.3f * scale, y, 0.2f * scale, 20);
    }

    private void drawTree(GL gl, float x, float y, float trunkWidth, float treeHeight) {
        // Draw Trunk
        gl.glColor3f(0.5f, 0.3f, 0.1f);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2f(x - trunkWidth / 2, y);
        gl.glVertex2f(x + trunkWidth / 2, y);
        gl.glVertex2f(x + trunkWidth / 2, y + treeHeight * 0.4f);
        gl.glVertex2f(x - trunkWidth / 2, y + treeHeight * 0.4f);
        gl.glEnd();

        // Draw Leaves
        gl.glColor3f(0.2f, 0.6f, 0.2f);
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex2f(x, y + treeHeight);
        gl.glVertex2f(x - trunkWidth * 1.5f, y + treeHeight * 0.3f);
        gl.glVertex2f(x + trunkWidth * 1.5f, y + treeHeight * 0.3f);
        gl.glEnd();
    }

    private void drawCircle(GL gl, float x, float y, float radius, int segments) {
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex2f(x, y);
        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * (double) i / (double) segments;
            gl.glVertex2f(x + (float) (Math.cos(angle) * radius),
                    y + (float) (Math.sin(angle) * radius));
        }
        gl.glEnd();
    }

    public static void main(String[] args) {
        GLCapabilities caps = new GLCapabilities();
        GLCanvas canvas = new HouseSceneJDK8();
        canvas.setPreferredSize(new Dimension(800, 600));

        final JFrame frame = new JFrame("JOGL House Scene (JDK 8)");

        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}