import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UI extends JFrame {
    //region ClassAttributes
    private final UI actualUI = this;
    private Client client;
    private MusicManager musicManager, sfx;
    private Timer timer;
    private JPanel contentPane, searchingPane, menuPane, gamePane, VeredictoPane, botonesPane,loadingPane, playMenuPane, privateGamePane, playMenuSettingsPane, panel1, panel2, winnerGamePane, scorePane, enemyScorePane;
    private JLabel gameTitle, searchingLbl, eleccionActualLbl, resultadoRondaLbl, pswLbl, winnerName;
    private JButton playButton,privatePlayButton, exitButton,publicGameButton,privateGameButton ,backButton, back2Button, goToMenuButton;
    private JButton piedraButton, papelButton, tijeraButton, enemigoPiedraButton, enemigoPapelButton, enemigoTijeraButton;
    private JLabel scoreLbl, enemyScoreLbl, yourResult, enemyResult;
    private JTextField pswTxtField;
    private String[] music = {
            "/audioClips/Darkness.wav",
            "/audioClips/Forest.wav",
            "/audioClips/Happy.wav",
            "/audioClips/Mystery.wav",
            "/audioClips/Space Walk.wav"
    };
    private int score = 0, enemyScore= 0;
    //endregion
    public UI() {

        musicManager = new MusicManager();
        sfx = new MusicManager();
        musicManager.playMusic(music[2],true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(contentPane);
        setVisible(true);
        setTitle("Piedra papel... o TIJERAS!");
        setResizable(false);
        getRootPane().setDefaultButton(playButton);
        setExtendedState(JFrame.NORMAL);

        //region FontsApplied
        try{
            Font customFont50 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/Fonts/BlitzBold.otf")));
            Font customFont35 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/Fonts/BlitzBold.otf")));
            Font customFont25 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/Fonts/BlitzBold.otf")));
            Font customFont80 = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/Fonts/BlitzBold.otf")));
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
            winnerName.setFont(customFont35);
            privatePlayButton.setFont(customFont35);
            goToMenuButton.setFont(customFont35);

        }catch (FontFormatException | IOException e) {
            e.printStackTrace();
            gameTitle.setText("No se pudo cargar la fuente.");
        }
        //endregion
        playButton.setSize(300,150);
        exitButton.setSize(300,150);

        //region RockPaperScissorsButtons
        ImageIcon iconRock = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Rock.png")));
        Image imgRock = iconRock.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        piedraButton.setIcon(new ImageIcon(imgRock));
        enemigoPiedraButton.setIcon(new ImageIcon(imgRock));

        ImageIcon iconTijera = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Scissors.png")));
        Image imgTijera = iconTijera.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        tijeraButton.setIcon(new ImageIcon(imgTijera));
        enemigoTijeraButton.setIcon(new ImageIcon(imgTijera));

        ImageIcon iconPapel = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Paper.png")));
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


        //region menuPaneListeners
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("/audioClips/sfx/Click2.wav");
                menuPane.setVisible(false);
                playMenuPane.setVisible(true);

            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //endregion
        //region playMenuPaneListeners
        publicGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("/audioClips/sfx/Click2.wav");


                playMenuPane.setVisible(false);
                searchingPane.setVisible(true);
                //searchingPane.setVisible(true);
                client = new Client(actualUI);
                client.start();
            }
        });
        privateGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("/audioClips/sfx/Click2.wav");
                playMenuSettingsPane.setVisible(false);
                privateGamePane.setVisible(true);

            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("/audioClips/sfx/Click2.wav");
                playMenuPane.setVisible(false);
                menuPane.setVisible(true);
            }
        });
        //endregion
        //region privateGamePaneListeners
        pswTxtField.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str != null && str.matches("\\d*") && (getLength() + str.length() <= 3)) {
                    super.insertString(offs, str, a);
                }
            }
        });
        privatePlayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("/audioClips/sfx/Click2.wav");
                playMenuPane.setVisible(false);
                searchingPane.setVisible(true);
                client = new Client(actualUI);
                client.start();
                try {
                    client.setGamePsw(Integer.parseInt(pswTxtField.getText()));
                }catch (NumberFormatException e1) {
                    client.setGamePsw(0);
                }
            }
        });
        back2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("/audioClips/sfx/Click2.wav");
                privateGamePane.setVisible(false);
                playMenuSettingsPane.setVisible(true);
                pswTxtField.setText("");
            }
        });
        //endregion
        //region OptionsRockPaperScissorsListeners
        piedraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("/audioClips/sfx/Click3.wav");
                tijeraButton.setVisible(false);
                papelButton.setVisible(false);
                client.setOption("Rock");
                System.out.println("Rock");
                eleccionActualLbl.setText("Eleccion Actual: Piedra");
                setWaitingPlayer();

                piedraButton.setEnabled(false);

            }
        });
        papelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("/audioClips/sfx/Click3.wav");
                piedraButton.setVisible(false);
                tijeraButton.setVisible(false);
                client.setOption("Paper");
                System.out.println("Paper");
                eleccionActualLbl.setText("Eleccion Actual: Papel");
                setWaitingPlayer();
                papelButton.setEnabled(false);

            }
        });
        tijeraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sfx.playMusic("/audioClips/sfx/Click3.wav");
                piedraButton.setVisible(false);
                papelButton.setVisible(false);
                client.setOption("Scissors");
                System.out.println("Scissors");
                eleccionActualLbl.setText("Eleccion Actual: Tijeras");
                setWaitingPlayer();

                tijeraButton.setEnabled(false);
            }
        });
        //endregion

        goToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePane.setVisible(false);
                menuPane.setVisible(true);
                botonesPane.setVisible(true);
                eleccionActualLbl.setVisible(true);
                resultadoRondaLbl.setVisible(true);
                winnerName.setVisible(false);
                goToMenuButton.setVisible(false);
                scorePane.setBorder(new EmptyBorder(0,0,0,0));
                enemyScorePane.setBorder(new EmptyBorder(0,0,0,0));
                scoreLbl.setText("Tu Puntaje: 0");
                enemyScoreLbl.setText("0 :Puntaje Enemigo");
                scoreLbl.setHorizontalAlignment(SwingConstants.RIGHT);
                enemyScoreLbl.setHorizontalAlignment(SwingConstants.LEFT);
            }
        });
    }

    public void UpdateSearching(String s) {
        switch (s) {
            case "Partida Encontrada!" -> {
                musicManager.stopMusic();
                sfx.playMusic("/audioClips/sfx/Click1.wav", false);
                timer = new Timer(500, e -> {
                    searchingPane.setVisible(false);
                    gamePane.setVisible(true);
                    scoreLbl.setText("Tu Puntaje: 0");
                    enemyScoreLbl.setText("0 :Puntaje Enemigo");
                    sfx.playMusic("/audioClips/sfx/Switch.wav", false);

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
                    sfx.playMusic("/audioClips/sfx/LevelUP.wav", false);
                    addPoints(1,scoreLbl);
                }else {
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                    sfx.playMusic("/audioClips/sfx/RoundLose.wav", false);
                    addPoints(1,enemyScoreLbl);
                }
            }
            case "Paper" -> {
                enemigoPapelButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                    sfx.playMusic("/audioClips/sfx/RoundLose.wav", false);
                    addPoints(1,enemyScoreLbl);
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Es un empate!");
                }else {
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                    sfx.playMusic("/audioClips/sfx/LevelUP.wav", false);
                    addPoints(1,scoreLbl);
                }
            }
            case "Scissors" -> {
                enemigoTijeraButton.setVisible(true);
                if (piedraButton.isVisible()){
                    resultadoRondaLbl.setText("Ganaste la Ronda!");
                    sfx.playMusic("/audioClips/sfx/LevelUP.wav", false);
                    addPoints(1,scoreLbl);
                }else if (papelButton.isVisible()){
                    resultadoRondaLbl.setText("Perdiste la Ronda!");
                    sfx.playMusic("/audioClips/sfx/RoundLose.wav", false);
                    addPoints(1,enemyScoreLbl);
                }else {
                    resultadoRondaLbl.setText("Es un empate!");
                }
            }
            case "Partida Finalizada!" -> {
                musicManager.stopMusic();
                musicManager.playMusic(music[2], true,"fadeIn",500);
                if (score == 3){
                    winnerName.setText("Has Ganado!");
                }else {
                    winnerName.setText("Gano el rival!");
                }
                botonesPane.setVisible(false);
                eleccionActualLbl.setVisible(false);
                resultadoRondaLbl.setVisible(false);
                winnerName.setVisible(true);
                goToMenuButton.setVisible(true);

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

    //region cleanGamepPane
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
    //endregion

    public static void main(String[] args){

        UI dialog = new UI();
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
    }

    //Custom Components
    private void createUIComponents() {

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
        privatePlayButton = new RoundedButton("",30);
        goToMenuButton = new RoundedButton("",30);
    }
}
