package Lab.Samples;

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;

/**
 * This is a basic JOGL app. Feel free to
 * reuse this code or modify it.
 */
public class SimpleJoglApp extends JFrame {

    GLCanvas glcanvas;
    SimpleGLEventListener listener = new SimpleGLEventListener();

    public static void main(String[] args) {
        new SimpleJoglApp();
    }

    public SimpleJoglApp() {
        super("Simple JOGL Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        add(glcanvas, BorderLayout.CENTER);
        setSize(700, 700);

        // TODO logic here

        setLocationRelativeTo(this);
        setVisible(true);
    }
}
