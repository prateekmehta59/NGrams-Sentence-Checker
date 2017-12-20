import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class audioCapture {
    private float SAMPLE_RATE = 16000;
    private int SAMPLE_SIZE_IN_BITS = 16;
    private int NUMBER_OF_CHANNELS = 1;

    File wavFile = new File("./RecordAudio.wav");
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;

    AudioFormat getAudioFormat() {
        float sampleRate = SAMPLE_RATE;
        int sampleSizeInBits = SAMPLE_SIZE_IN_BITS;
        int channels = NUMBER_OF_CHANNELS;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
            Thread recording = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("recording ...");
                    AudioInputStream ais = new AudioInputStream(line);
                    try{
                        AudioSystem.write(ais, fileType, wavFile);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            recording.start();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    void finish() {
        line.stop();
        line.close();
    }

}
