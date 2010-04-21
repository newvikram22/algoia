package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import javax.xml.crypto.Data;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 * Classe permettant de convertir un fichier de données en un fichier arff
 * @author remi
 *
 */
public class DataToArff {

	/**
	 * le fichier de données
	 */
	File file;
	
	/**
	 * constructeur par paramètres
	 * @param file le fichier de données
	 */
	public DataToArff(File file) {
		this.file = file;
	}

	/**
	 * construit un fichier arff à partir du fichier de données
	 * @return un Reader sur le fichier arff
	 */
	public Reader buildArffFile() {
		
		if (file != null) {
			
			try {
				File tempFile = File.createTempFile("classification-C45-"+file.hashCode(), ".arff");
				tempFile.deleteOnExit();
				
				// load CSV
				CSVLoader loader = new CSVLoader();

				loader.setSource(file);				
				
				Instances data = loader.getDataSet();
				data.setClassIndex(data.numAttributes()-1);

				// save ARFF
				ArffSaver saver = new ArffSaver();
				saver.setInstances(data);
				saver.setFile(tempFile);
				saver.setDestination(tempFile);
				saver.writeBatch();
				
				FileReader fr = new FileReader(tempFile);
				
				return fr;
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}




		} else {
			System.err.println("DataToArff::buildArffFile : null file");
			System.exit(-1);
			return null;
		}
		
	}

}
