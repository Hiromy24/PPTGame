import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class UI extends JDialog {
    private JPanel contentPane;
    private JLabel gameTitle;
    private JButton playButton;
    private JButton exitButton;
    private JLabel searchingLbl;
    private JPanel searchingPane;
    private JPanel menuPane;
    private JPanel gamePane;
    private JButton piedraButton;
    private JButton papelButton;
    private JButton tijeraButton;

    public UI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(playButton);
        try{
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            customFont = customFont.deriveFont(50f);


            gameTitle.setFont(customFont);
            playButton.setFont(customFont);
            exitButton.setFont(customFont);
            searchingLbl.setFont(customFont);
        }catch (FontFormatException | IOException e) {
            e.printStackTrace();
            gameTitle.setText("No se pudo cargar la fuente.");
        }
        playButton.setSize(300,150);
        playButton.setFocusPainted(false);
        exitButton.setSize(300,150);
        exitButton.setFocusPainted(false);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                playButton.setVisible(false);
                exitButton.setVisible(false);
                playButton.setEnabled(false);
                exitButton.setEnabled(false);

                menuPane.setVisible(false);
                //searchingPane.setVisible(true);

                Client client = new Client();
                client.start();
            }
        });

        piedraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Rock");
            }
        });
        papelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Paper");
            }
        });
        tijeraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Scissors");
            }
        });
    }
    public static void main(String[] args){

        UI dialog = new UI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
