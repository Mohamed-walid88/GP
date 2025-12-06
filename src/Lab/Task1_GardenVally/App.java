package Lab.Task1_GardenVally;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    DayGLEventListener listener = new DayGLEventListener();
    GLCanvas glcanvas = new GLCanvas();
    JButton button;

    public App() {
        setTitle("Quiz Program");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        glcanvas.addGLEventListener(listener);
        add(glcanvas,  BorderLayout.CENTER);

        button = new JButton("Night");
        button.addActionListener(this);
        button.setActionCommand("setNight");

        add(button, BorderLayout.SOUTH);


        setLocationRelativeTo(this);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "setNight":
                listener.setTime("Night");
                button.setText("Day");
                button.setActionCommand("setDay");
                setTitle("Happy Night");
                glcanvas.repaint();
                break;
            case "setDay":
                listener.setTime("Day");
                button.setText("Night");
                button.setActionCommand("setNight");
                setTitle("Sunny Day");
                glcanvas.repaint();
                break;
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
