package algorithms;

import java.io.File;
import java.io.Reader;

import common.DataToArff;

import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 * Classe implémentant l'algorithme C4.5 en adaptant l'algorithme J48 de Weka 
 * 
 * @author remi
 *
 */
public class C45 implements IClassifier {
	
	/**
	 * l'algorithme C4.5 de Weka
	 */
	private J48 adapted;
	
	/**
	 * l'ensemble des instances
	 */
	private Instances instances;

	/**
	 * lit les données et initialise l'algorithme
	 * @param inputFile le fichier comportant les données
	 */
	@Override
	public void readData(String inputFile) {
		try {
			
			File file = new File(inputFile);
			if (file.isFile()) {
				DataToArff dta = new DataToArff(file);
				Reader reader = dta.buildArffFile();
				
				instances = new Instances(reader);
				adapted = new J48();
				adapted.buildClassifier(instances);
				
			} else {
				System.err.println("C45::readData : could not read data file");
				System.exit(-1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * classifie les instances
	 */
	@Override
	public void classify() {
		// TODO Auto-generated method stub
		
	}	

	/**
	 * affiche les résultats
	 */
	@Override
	public void printResults() {
		// TODO Auto-generated method stub
		
	}

}
