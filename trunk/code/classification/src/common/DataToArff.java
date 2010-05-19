package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import javax.xml.crypto.Data;

import com.sun.net.httpserver.Filter;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.unsupervised.attribute.NumericToNominal;

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
	private int classIndex;
	
	/**
	 * constructeur par paramètres
	 * @param file le fichier de données
	 * @param classIndex 
	 */
	public DataToArff(File file, int classIndex) {
		this.file = file;
		this.classIndex = classIndex;
	}

	/**
	 * construit un fichier arff à partir du fichier de données
	 * @return un Reader sur le fichier arff
	 */
	public Reader buildArffFile() {
		
		if (file != null) {
			
			try {
				File tempFile = File.createTempFile("classification-C45-"+file.hashCode(), ".arff");
				//tempFile.deleteOnExit();
				
				// load CSV
				CSVLoader loader = new CSVLoader();

				loader.setSource(file);				
				
				Instances data = loader.getDataSet();
				data.setClassIndex(classIndex);	
				
				NumericToNominal numToNom = new NumericToNominal();
				String[] options = {""}; // Where options is of form -R <Orbit_kms,Diameter_kms,Mass_kgs>
				try {
					
					numToNom.setOptions(options);
					numToNom.setInputFormat(data);
					int indices[] = {classIndex};
					numToNom.setAttributeIndicesArray(indices);
					data = NumericToNominal.useFilter(data, numToNom); 
					
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
				

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
