package Lab;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleJOGLApp extends JFrame implements ActionListener {
    SimpleGLEventListener listener = new SimpleGLEventListener();
    GLCanvas glcanvas = new GLCanvas();

    public static void main(String[] args) {
        new SimpleJOGLApp();
    }
    public SimpleJOGLApp() {
        setTitle("Simple JOGL App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting the JOGL screen
        glcanvas.addGLEventListener(listener);

        add(glcanvas,  BorderLayout.CENTER);

        JPanel panel = new JPanel();

        JButton button = new JButton("Triangle");
        button.addActionListener(this);
        button.setActionCommand("Triangle");
//        add(button, BorderLayout.NORTH);

        JButton button1 = new JButton("Circle");
        button1.addActionListener(this);
        button1.setActionCommand("Circle");
//        add(button1, BorderLayout.SOUTH);

        panel.add(button, BorderLayout.EAST);
        panel.add(button1, BorderLayout.WEST);

        add(panel, BorderLayout.SOUTH);
        setSize(600, 300);

        setLocationRelativeTo(this);
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e){
        switch (e.getActionCommand()){
            case "Triangle":
                listener.setWhatToDraw("Triangle");
                glcanvas.repaint();
                break;
            case "Circle":
                listener.setWhatToDraw("Circle");
                glcanvas.repaint();
                listener.move += 5;
                break;
        }
    }
}
