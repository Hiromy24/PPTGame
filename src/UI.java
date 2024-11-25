import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UI extends JDialog {
    //region ClassAttributes
    private final UI actualUI = this;
    private Client client;
    private MusicManager musicManager, sfx;
    private Timer timer;
    private JPanel contentPane, searchingPane, menuPane, gamePane, VeredictoPane, botonesPane,loadingPane, playMenuPane, privateGamePane, playMenuSettingsPane;
    private JLabel gameTitle, searchingLbl, eleccionActualLbl, resultadoRondaLbl, pswLbl;
    private JButton playButton, exitButton,publicGameButton,privateGameButton ,backButton, back2Button;
    private JButton piedraButton, papelButton, tijeraButton, enemigoPiedraButton, enemigoPapelButton, enemigoTijeraButton;
    private JLabel scoreLbl, enemyScoreLbl;
    private JTextField pswTxtField;
    private String[] music = {"src/audioClips/Darkness.wav","src/audioClips/Forest.wav", "src/audioClips/Happy.wav", "src/audioClips/Mystery.wav", "src/audioClips/Space Walk.wav"};
    private int score = 0, enemyScore= 0;
    //endregion
    public UI() {

        musicManager = new MusicManager();
        sfx = new MusicManager();
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(playButton);
        //region FontsApplied
        try{
            Font customFont50 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            Font customFont35 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            Font customFont25 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            Font customFont80 = Font.createFont(Font.TRUETYPE_FONT, new File("src/Fonts/BlitzBold.otf"));
            customFont50 = customFont50.deriveFont(50f);
            customFont35 = customFont35.deriveFont(35f);
            customFont25 = customFont25.deriveFont(25f);
            customFont80 = customFont80.deriveFont(80f);
            gameTitle.setFont(customFont50);
            playButton.setFont(customFont50);
            exitButton.setFont(customFont50);
            publicGameButton.setFont(customFont35);
            privateGameButton.setFont(customFont35);
            backButton.setFont(customFont35);
            back2Button.setFont(customFont35);
            pswTxtField.setFont(customFont80);

            searchingLbl.setFont(customFont50);
            eleccionActualLbl.setFont(customFont50);
            eleccionActualLbl.setFont(customFont35);
            resultadoRondaLbl.setFont(customFont35);
            scoreLbl.setFont(customFont25);
            enemyScoreLbl.setFont(customFont25);
            pswLbl.setFont(customFont35);
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

        piedraButton.setFocusPainted(false);
        papelButton.setFocusPainted(false);
        tijeraButton.setFocusPainted(false);
        enemigoPiedraButton.setFocusPainted(false);
        enemigoPapelButton.setFocusPainted(false);
        enemigoTijeraButton.setFocusPainted(false);
        piedraButton.setBorderPainted(false);
        papelButton.setBorderPainted(false);
        tijeraButton.setBorderPainted(false);
        enemigoPiedraButton.setBorderPainted(false);
        enemigoPapelButton.setBorderPainted(false);
        enemigoTijeraButton.setBorderPainted(false);


        //endregion
        musicManager.playMusic(music[2],true);

        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("src/audioClips/sfx/Click2.wav");
                menuPane.setVisible(false);
                playMenuPane.setVisible(true);

            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        publicGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("src/audioClips/sfx/Click2.wav");
                publicGameButton.setVisible(false);
                privateGameButton.setVisible(false);
                backButton.setVisible(false);
                publicGameButton.setEnabled(false);
                privateGameButton.setEnabled(false);
                backButton.setEnabled(false);

                playMenuPane.setVisible(false);
                searchingPane.setVisible(true);
                //searchingPane.setVisible(true);
                client = new Client(actualUI);
                client.start();
            }
        });
        privateGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playMenuSettingsPane.setVisible(false);
                privateGamePane.setVisible(true);

            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playMenuPane.setVisible(false);
                menuPane.setVisible(true);
            }
        });
        back2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                privateGamePane.setVisible(false);
                playMenuSettingsPane.setVisible(true);
            }
        });

        pswTxtField.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str != null && str.matches("\\d*") && (getLength() + str.length() <= 3)) {
                    super.insertString(offs, str, a);
                }
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
                    addPoints(1,scoreLbl);
                }else {
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/RoundLose.wav", false);
                    addPoints(1,enemyScoreLbl);
                }
            }
            case "Paper" -> {
                enemigoPapelButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/RoundLose.wav", false);
                    addPoints(1,enemyScoreLbl);
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Es un empate!");
                }else {
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/LevelUp.wav", false);
                    addPoints(1,scoreLbl);
                }
            }
            case "Scissors" -> {
                enemigoTijeraButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/LevelUp.wav", false);
                    addPoints(1,scoreLbl);
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                    sfx.playMusic("src/audioClips/sfx/RoundLose.wav", false);
                    addPoints(1,enemyScoreLbl);
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

    public void addPoints(int points, JLabel player) {
        if (player == scoreLbl){
            score+= points;
            scoreLbl.setText("Tu Puntaje: "+score);
        }else{
            enemyScore+= points;
            enemyScoreLbl.setText(enemyScore+" :Puntaje Enemigo");
        }
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
        piedraButton = new RoundedButton("", 30);
        tijeraButton = new RoundedButton("", 30);
        papelButton = new RoundedButton("", 30);
        publicGameButton = new RoundedButton("Partida Publica", 30);
        privateGameButton =new RoundedButton("Partida Privada", 30);
        backButton = new RoundedButton("Atras", 30);
        back2Button = new RoundedButton("Atras", 30);
        pswTxtField = new RoundedTextField( 30);
        enemigoPiedraButton = new RoundedButton("",30);
        enemigoPapelButton = new RoundedButton("",30);
        enemigoTijeraButton = new RoundedButton("",30);
        loadingPane = new LoadingCircle();

    }
}
