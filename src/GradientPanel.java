import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    // Constructor
    private Color inColor;
    private Color outColor;
    public GradientPanel(Color inColor, Color outColor) {
        this.inColor = inColor;
        this.outColor = outColor;
    }

    public GradientPanel() {
        this.inColor = new Color(255, 255, 255);
        this.outColor = new Color(255, 255, 255);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        GradientPaint gradient = new GradientPaint(
                0, 0, inColor,
                0, height, outColor
        );

        // Configura el paint del Graphics2D con el degradado
        g2d.setPaint(gradient);

        // Rellena el fondo con el degradado
        g2d.fillRect(0, 0, width, height);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Panel con Degradado");
        GradientPanel gradientPanel = new GradientPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(gradientPanel); // Añadimos el panel personalizado al marco
        frame.setVisible(true);
    }
}
