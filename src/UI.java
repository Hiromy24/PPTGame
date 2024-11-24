import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UI extends JDialog {
    //region ClassAttributes
    private final UI actualUI = this;
    private Client client;
    private MusicManager musicManager, sfx;
    private Timer timer;
    private JPanel contentPane, searchingPane, menuPane, gamePane, VeredictoPane, botonesPane,loadingPane;
    private JLabel gameTitle, searchingLbl, eleccionActualLbl, resultadoRondaLbl;
    private JButton playButton, exitButton;
    private JButton piedraButton, papelButton, tijeraButton, enemigoPiedraButton, enemigoPapelButton, enemigoTijeraButton;
    private JLabel enemyScoreLbl;
    private JLabel scoreLbl;
    private String[] music = {"src/audioClips/Darkness.wav","src/audioClips/Forest.wav", "src/audioClips/Happy.wav", "src/audioClips/Mystery.wav", "src/audioClips/Space Walk.wav"};
    //endregion
    public UI() {

        musicManager = new MusicManager();
        sfx = new MusicManager();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(playButton);
        //region FontsApplied
        try{
            Font customFont50 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            Font customFont35 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            Font customFont30 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            customFont50 = customFont50.deriveFont(50f);
            customFont35 = customFont35.deriveFont(35f);
            customFont30 = customFont30.deriveFont(25f);
            gameTitle.setFont(customFont50);
            playButton.setFont(customFont50);
            exitButton.setFont(customFont50);
            searchingLbl.setFont(customFont50);
            eleccionActualLbl.setFont(customFont50);
            eleccionActualLbl.setFont(customFont35);
            resultadoRondaLbl.setFont(customFont35);
            scoreLbl.setFont(customFont30);
            enemyScoreLbl.setFont(customFont30);
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
        musicManager.playMusic(music[2],true);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("src/audioClips/sfx/Click2.wav");
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
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //region OptionsRockPaperScissorsListeners
        piedraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("src/audioClips/sfx/Click3.wav");
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
                sfx.playMusic("src/audioClips/sfx/Click3.wav");
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
                sfx.playMusic("src/audioClips/sfx/Click3.wav");
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
                musicManager.stopMusic();
                sfx.playMusic("src/audioClips/sfx/Click1.wav", false);
                timer = new Timer(500, e -> {
                    searchingPane.setVisible(false);
                    gamePane.setVisible(true);
                    sfx.playMusic("src/audioClips/sfx/Switch.wav", false);
                });
                timer.setRepeats(false);
                timer.start();
                timer = new Timer(700, e ->{
                    musicManager.playMusic(music[3], true, "fadeIn",500);
                });
                timer.setRepeats(false);
                timer.start();
            }
            case "Siguiente Ronda!" -> {
                resetPlayerButtons();
                cleanVeredictPane();
                resetOponentButtons();
            }
            case "Rock" -> {
                enemigoPiedraButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Es un empate!");
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/LevelUp.wav", false);
                }else {
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/RoundLose.wav", false);
                }
            }
            case "Paper" -> {
                enemigoPapelButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/RoundLose.wav", false);
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Es un empate!");
                }else {
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/LevelUp.wav", false);
                }
            }
            case "Scissors" -> {
                enemigoTijeraButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/LevelUp.wav", false);
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/RoundLose.wav", false);
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
                musicManager.stopMusic();
                musicManager.playMusic(music[2], true);
            }
        }
    }


    public void setWaitingPlayer() {
        resultadoRondaLbl.setText("Esperando al otro Jugador...");
    }

    public void cleanVeredictPane(){
        resultadoRondaLbl.setText("");
        eleccionActualLbl.setText("");
    }

    public void resetPlayerButtons(){
        piedraButton.setVisible(true);
        papelButton.setVisible(true);
        tijeraButton.setVisible(true);
        piedraButton.setEnabled(true);
        papelButton.setEnabled(true);
        tijeraButton.setEnabled(true);
    }
    public void resetOponentButtons(){
        enemigoPiedraButton.setVisible(false);
        enemigoPapelButton.setVisible(false);
        enemigoTijeraButton.setVisible(false);
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
