package wav_io;

import java.io.BufferedWriter;
import java.io.IOException;

public class wave_io {

    public static void main(String[] args) {

        int samples = 0;
        int validBits = 0;
        long sampleRate = 0;
        long numFrames = 0;
        int numChannels = 0;

        // Reduzieren der Sample Bits
        short bitReductionFactor = 8;

        String inFilename = null;
        String outFilename = null;
        String outTxtFilename = null;

        WavFile readWavFile = null;

        if (args.length < 1) {
            try {
                throw new WavFileException("At least one filename specified  (" + args.length + ")");
            } catch (WavFileException e1) {
                e1.printStackTrace();
            }
        }

        // ********************************************************
        // Implementierung bei einem Eingabeparameter

        inFilename = args[0];

        try {
            readWavFile = WavFile.read_wav(inFilename);

            // headerangaben
            numFrames = readWavFile.getNumFrames();

            // Anzahl der Kanaäle (mono/stereo)
            numChannels = readWavFile.getNumChannels();

            // Anzahl Abtastpunkte
            samples = (int) numFrames * numChannels;

            // Bitszahl
            validBits = readWavFile.getValidBits();

            // Abtastrate
            sampleRate = readWavFile.getSampleRate();

           
				for(int i=0; i<samples; i++) {
					short value = readWavFile.sound[i];
					readWavFile.sound[i] /= (short)Math.pow(2, bitReductionFactor);
					readWavFile.sound[i] *= (short)Math.pow(2, bitReductionFactor);
					readWavFile.sound[i] -= value;
					readWavFile.sound[i] *= (short)Math.pow(2, 16-bitReductionFactor-1);
				}
            

            // Angepasste Headerangaben für Ausgabe
            //validBits -= bitReductionFactor;

            outTxtFilename = args[2];
            BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(outTxtFilename));

            // Schreibe das Differenzsignal (Quantisierungsfehler) in die Textdatei
            for (int i = 0; i < samples; i++) {
                bw.write(readWavFile.sound[i] + "\n ");
            }
            bw.close();

            
        } catch (IOException | WavFileException e1) {
            e1.printStackTrace();
        }

        if (args.length == 1)
            System.exit(0);

        // ***********************************************************
        // Implementierung bei Ein-und Ausgabeparameter (Speichern der Ausgabedatei)

        outFilename = args[1];

        // Implementierung

        // Speicherung
        try {
            WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, readWavFile.sound);
        } catch (IOException | WavFileException e) {
            e.printStackTrace();
        }
    }
}
