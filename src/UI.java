import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class UI extends JDialog {
    private final UI actualUI = this;
    private JPanel contentPane;
    private JPanel searchingPane;
    private JPanel menuPane;
    private JPanel gamePane;
    private JPanel VeredictoPane;
    private JLabel gameTitle;
    private JLabel searchingLbl;
    private JButton playButton;
    private JButton exitButton;
    private JPanel botonesPane;
    private JLabel eleccionActualLbl;
    private JLabel resultadoRondaLbl;
    private JButton piedraButton;
    private JButton papelButton;
    private JButton tijeraButton;
    private JButton enemigoPiedraButton;
    private JButton enemigoPapelButton;
    private JButton enemigoTijeraButton;
    private JPanel loadingPane;
    private Client client;


    public UI() {
        MusicManager musicManager = new MusicManager();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(playButton);
        //region FontsApplied
        try{
            Font customFont50 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            Font customFont35 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            customFont50 = customFont50.deriveFont(50f);
            customFont35 = customFont35.deriveFont(35f);
            gameTitle.setFont(customFont50);
            playButton.setFont(customFont50);
            exitButton.setFont(customFont50);
            searchingLbl.setFont(customFont50);
            eleccionActualLbl.setFont(customFont50);
            eleccionActualLbl.setFont(customFont35);
            resultadoRondaLbl.setFont(customFont35);
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
        enemigoPiedraButton.setIcon(new ImageIcon(imgRock));

        ImageIcon iconTijera = new ImageIcon("src/img/Scissors.png");
        Image imgTijera = iconTijera.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        tijeraButton.setIcon(new ImageIcon(imgTijera));
        enemigoTijeraButton.setIcon(new ImageIcon(imgTijera));

        ImageIcon iconPapel = new ImageIcon("src/img/Paper.png");
        Image imgPapel = iconPapel.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        papelButton.setIcon(new ImageIcon(imgPapel));
        enemigoPapelButton.setIcon(new ImageIcon(imgPapel));

        piedraButton.setBackground(Color.decode("#FCBA03"));
        tijeraButton.setBackground(Color.decode("#FCBA03"));
        papelButton.setBackground(Color.decode("#FCBA03"));
        enemigoPiedraButton.setBackground(Color.decode("#FCBA03"));
        enemigoTijeraButton.setBackground(Color.decode("#FCBA03"));
        enemigoPapelButton.setBackground(Color.decode("#FCBA03"));
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
                eleccionActualLbl.setText("Eleccion Actual: Piedra");
                setWaitingPlayer();
                tijeraButton.setVisible(false);
                papelButton.setVisible(false);

                piedraButton.setEnabled(false);

            }
        });
        papelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.setOption("Paper");
                System.out.println("Paper");
                eleccionActualLbl.setText("Eleccion Actual: Papel");
                setWaitingPlayer();
                piedraButton.setVisible(false);
                tijeraButton.setVisible(false);
                papelButton.setEnabled(false);

            }
        });
        tijeraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.setOption("Scissors");
                System.out.println("Scissors");
                eleccionActualLbl.setText("Eleccion Actual: Tijeras");
                setWaitingPlayer();
                piedraButton.setVisible(false);
                papelButton.setVisible(false);

                tijeraButton.setEnabled(false);
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
            case "Siguiente Ronda!" -> {
                piedraButton.setVisible(true);
                papelButton.setVisible(true);
                tijeraButton.setVisible(true);
                piedraButton.setEnabled(true);
                papelButton.setEnabled(true);
                tijeraButton.setEnabled(true);

                enemigoPiedraButton.setVisible(false);
                enemigoPapelButton.setVisible(false);
                enemigoTijeraButton.setVisible(false);
            }
            case "Rock" -> {
                enemigoPiedraButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Es un empate!");
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                }else {
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                }
            }
            case "Paper" -> {
                enemigoPapelButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Es un empate!");
                }else {
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                }
            }
            case "Scissors" -> {
                enemigoTijeraButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                }else {
                    resultadoRondaLbl.setText("Es un empate!");
                }
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

    public void setWaitingPlayer() {
                resultadoRondaLbl.setText("Esperando al otro Jugador...");
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

        enemigoPiedraButton = new RoundedButton("",50);
        enemigoPapelButton = new RoundedButton("",50);
        enemigoTijeraButton = new RoundedButton("",50);
        loadingPane = new LoadingCircle();
    }
}
