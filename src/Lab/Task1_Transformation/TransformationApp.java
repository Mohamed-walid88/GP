package Lab.Task1_Transformation;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class TransformationApp extends JFrame {

    GLCanvas glcanvas = new GLCanvas();
    TransformationGLEventListener listener = new TransformationGLEventListener();

    public TransformationApp() {
        setTitle("Transform App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        glcanvas.addGLEventListener(listener);
        add(glcanvas,  BorderLayout.CENTER);

        setSize(1000, 600);

        setLocationRelativeTo(this);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TransformationApp();
    }
}