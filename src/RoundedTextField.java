import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedTextField extends JTextField {
    private int radius;

    public RoundedTextField(int radius) {
        super();
        this.radius = radius;
        setOpaque(false); // Permite la transparencia para que podamos dibujar el fondo nosotros mismos
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo del JTextField
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Dibuja el borde
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.dispose();
        super.paintComponent(g); // Dibuja el texto encima
    }

    @Override
    public void paintBorder(Graphics g) {
        // No hacer nada para evitar que se dibuje el borde predeterminado
    }

    @Override
    public Insets getInsets() {
        // Ajusta los márgenes para evitar que el texto se dibuje fuera del área redondeada
        return new Insets(5, 10, 5, 10);
    }

    @Override
    public boolean contains(int x, int y) {
        // Define un área de clic redondeada
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        return shape.contains(x, y);
    }
}
