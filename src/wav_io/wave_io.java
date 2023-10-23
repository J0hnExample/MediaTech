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
		int bitReductionFactor = 1;

		String inFilename = null;
		String outFilename = null;

		WavFile readWavFile = null;
		short[] reducedSamples = null;

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

/* 			// Reduziere die Bitzahl der Samples
	 		reducedSamples = new short[samples];
			for (int i = 0; i < samples; i++) {
				reducedSamples[i] = (short) ((short) readWavFile.sound[i] / bitReductionFactor);
				// reducedSamples[i] = (short)Math.pow(bitReductionFactor, validBits);
			} 

			reducedSamples = new short[samples];
			for (int i = 0; i < samples; i++) {
				reducedSamples[i] = (short) (readWavFile.sound[i] >> (16 - bitReductionFactor));
			}


			// Multipliziere die Samples, um die Lautstärke zu kompensieren
			//short compensation = (short) Math.pow(bitReductionFactor, validBits);
			for (int i = 0; i < samples; i++) {

				reducedSamples[i] *= bitReductionFactor;
			} 
 */
			// Quote out to stop sample print in terminal

			// Zugriff auf die einzelne Samples mit readWavFile.sound[i]
			
			//Write samples to file
			String outpuString = "";
			
			for(int i=0; i<samples; i++) {
				short sample = readWavFile.sound[i];
				//Write to file
				
				outpuString = outpuString + Integer.toString(sample) + "\n";
			}
			BufferedWriter bw = new BufferedWriter(new java.io.FileWriter("C:\\Users\\sasch\\OneDrive\\Studium\\2324\\Medientechologie\\MediaTech\\src\\wav_io\\apogee_sine_loXX.txt"));
			bw.write(outpuString);
			bw.close();
		} catch (IOException | WavFileException e1) {
			// TODO Auto-generated catch block
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
			WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, reducedSamples);
		} catch (IOException | WavFileException e) {
			e.printStackTrace();
		}
	}
}
