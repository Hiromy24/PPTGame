import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class UI extends JDialog {
    private final UI actualUI = this;
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
    private Client client;

    public UI() {
        MusicManager musicManager = new MusicManager();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(playButton);
        //region FontsApplied
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
        //endregion
        playButton.setSize(300,150);
        exitButton.setSize(300,150);
        //region RockPaperScissorsButtons
        ImageIcon iconRock = new ImageIcon("src/img/Rock.png");
        Image imgRock = iconRock.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        piedraButton.setIcon(new ImageIcon(imgRock));

        ImageIcon iconTijera = new ImageIcon("src/img/Scissors.png");
        Image imgTijera = iconTijera.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        tijeraButton.setIcon(new ImageIcon(imgTijera));

        ImageIcon iconPapel = new ImageIcon("src/img/Paper.png");
        Image imgPapel = iconPapel.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        papelButton.setIcon(new ImageIcon(imgPapel));

        piedraButton.setBackground(Color.decode("#FCBA03"));
        tijeraButton.setBackground(Color.decode("#FCBA03"));
        papelButton.setBackground(Color.decode("#FCBA03"));
        //endregion
        musicManager.playMusic("src/audioClips/Happy.wav");


        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                playButton.setVisible(false);
                exitButton.setVisible(false);
                playButton.setEnabled(false);
                exitButton.setEnabled(false);

                menuPane.setVisible(false);
                searchingPane.setVisible(true);
                //searchingPane.setVisible(true);
                client = new Client(actualUI);
                client.start();
            }
        });
        //region OptionsRockPaperScissorsListeners
        piedraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.setOption("Rock");
                System.out.println("Rock");
            }
        });
        papelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.setOption("Paper");
                System.out.println("Paper");
            }
        });
        tijeraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.setOption("Scissors");
                System.out.println("Scissors");
            }
        });
        //endregion
    }

    public void UpdateSearching(String s) {
        switch (s) {
            case "Partida Encontrada!" -> {
                searchingPane.setVisible(false);
                gamePane.setVisible(true);
            }
            case "Partida Finalizada!" -> {
                playButton.setVisible(true);
                exitButton.setVisible(true);
                playButton.setEnabled(true);
                exitButton.setEnabled(true);
                menuPane.setVisible(true);
                searchingPane.setVisible(false);
                gamePane.setVisible(false);
            }
        }
    }

    public static void main(String[] args){

        UI dialog = new UI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    //Custom Components
    private void createUIComponents() {
        // TODO: place custom component creation code here
        playButton = new RoundedButton("Jugar", 30);
        exitButton = new RoundedButton("Salir", 30);
        piedraButton = new RoundedButton("", 50);
        tijeraButton = new RoundedButton("", 50);
        papelButton = new RoundedButton("", 50);
    }
}
