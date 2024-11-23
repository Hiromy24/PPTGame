import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicManager {
    private Clip clip;
    private FloatControl gainControl;

    public void playMusic(String filepath){
        playMusic(filepath,false);
    }
    public void playMusic(String filepath, Boolean loop) {
        playMusic(filepath, loop,"");
    }
    public void playMusic(String filePath, Boolean loop, String effect) {
        playMusic(filePath, loop, effect, 0);
    }
    public void playMusic(String filePath, Boolean loop,String effect, int effectDelayMs) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Obtén el formato del audio original
            AudioFormat baseFormat = audioStream.getFormat();

            // Configura un formato compatible (44.1 kHz, 16 bits, estéreo)
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100,
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    44100,
                    false
            );

            // Convierte el audio al nuevo formato
            AudioInputStream decodedAudioStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

            clip = AudioSystem.getClip();
            clip.open(decodedAudioStream);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);;
            if (effect.equals("fadeIn")){
                fadeIn(effectDelayMs);
                setVolume(-30.0f);
            }else if (effect.equals("fadeOut")){
                fadeOut(effectDelayMs);
            }
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir en bucle
            }
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void setVolume(float value) {
        if (gainControl != null) {
            gainControl.setValue(value); // Valor en decibelios (-80.0f es silencio, 0.0f es el volumen máximo)
        }
    }
    private void fadeIn(int durationMs) {
        new Thread(() -> {
            try {
                float startVolume = -30.0f;
                float endVolume = 0.0f;
                int steps = 50;
                float increment = (endVolume - startVolume) / steps;
                int delay = durationMs / steps;

                for (int i = 0; i < steps; i++) {
                    setVolume(startVolume + (i * increment));
                    Thread.sleep(delay);
                }
                setVolume(endVolume); // Asegurar el volumen final
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void fadeOut(int durationMs) {
        new Thread(() -> {
            try {
                float startVolume = gainControl.getValue();
                float endVolume = -80.0f;
                int steps = 50;
                float increment = (endVolume - startVolume) / steps;
                int delay = durationMs / steps;

                for (int i = 0; i < steps; i++) {
                    setVolume(startVolume + (i * increment));
                    Thread.sleep(delay);
                }
                setVolume(endVolume); // Asegurar el volumen final
                clip.stop(); // Detener el clip al finalizar el fade out
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
