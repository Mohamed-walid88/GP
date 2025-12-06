package Lab.Cube;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.opengl.GLCanvas;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CubeApp extends JFrame implements ActionListener {

    private GLCanvas glcanvas;
    private CubeGLEventListener listener = new CubeGLEventListener();

    private JButton scale = new JButton("Scale");
    private JButton translate = new JButton("Translate");
    private JButton rotate = new JButton("Rotate");

    private JTextField scaleXValue = new JTextField(3);
    private JTextField scaleYValue = new JTextField(3);
    private JTextField translateXValue = new JTextField(3);
    private JTextField translateYValue = new JTextField(3);
    private JTextField rotateAngleValue = new JTextField(3);

    public static void main(String[] args) {
        new CubeApp();
    }

    public CubeApp() {
        super("Lab.Cube Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(scaleXValue);
        jPanel.add(scaleYValue);
        jPanel.add(scale);
        jPanel.add(translateXValue);
        jPanel.add(translateYValue);
        jPanel.add(translate);
        jPanel.add(rotateAngleValue);
        jPanel.add(rotate);

        scale.addActionListener(this);
        translate.addActionListener(this);
        rotate.addActionListener(this);

        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);

        add(glcanvas, BorderLayout.CENTER);
        add(jPanel, BorderLayout.SOUTH);

        setSize(500, 300);
        setLocationRelativeTo(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(scale)) {
                System.out.println("Scale");
                listener.scaleXValue = Double.parseDouble(scaleXValue.getText());
                listener.scaleYValue = Double.parseDouble(scaleYValue.getText());
            } else if (e.getSource().equals(translate)) {
                System.out.println("Translate");
                listener.translateXValue = Double.parseDouble(translateXValue.getText());
                listener.translateYValue = Double.parseDouble(translateYValue.getText());
            } else if (e.getSource().equals(rotate)) {
                System.out.println("Rotate");
                listener.rotateAngleValue = Double.parseDouble(rotateAngleValue.getText());
            }
            glcanvas.repaint();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
