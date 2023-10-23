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
		short bitReductionFactor = 1;

		String inFilename = null;
		String outFilename = null;
		String outTxtFilename = null;

		WavFile readWavFile = null;
		short[] reducedSamples = new short[samples / 2];

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

 */
			// Quote out to stop sample print in terminal

			// Zugriff auf die einzelne Samples mit readWavFile.sound[i]
	
	//Aufgabe 05
			//Write samples to file
			String outpuString = "";
			//New counter for samples
			//Reduce Bits
			for(int i=0; i<samples; i++) {
				short sample = readWavFile.sound[i];
				//

					outpuString = outpuString + Integer.toString(sample) + "\n";
					//reduce samples 
					readWavFile.sound[i] = (short)(readWavFile.sound[i] / Math.pow(2, bitReductionFactor));
				
				
			}

		
			// Angepasste Headerangaben für Ausgabe
			validBits = validBits - bitReductionFactor;
		/* 	sampleRate = sampleRate;
			
			numFrames = numFrames; */

		
			outTxtFilename = args[2];
			BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(outTxtFilename));
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
			WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, readWavFile.sound);
		} catch (IOException | WavFileException e) {
			e.printStackTrace();
		}
	}
}
