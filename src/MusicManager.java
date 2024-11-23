import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicManager {
    private Clip clip;

    public void playMusic(String filePath, Boolean loop, int delayMs) {
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
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir en bucle
            }

            // Retrasar el inicio
            if (delayMs > 0) {
                long delayMicros = delayMs * 1000L; // Convertir a microsegundos
                clip.setMicrosecondPosition(delayMicros);
            }
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void playMusic(String filePath, Boolean loop) {
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
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir en bucle
            }
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
