import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import algorithms.C45;
import algorithms.IClassifier;
import algorithms.NaiveBayes;

/**
 * Programme principal
 * 
 * @author remi
 *
 */
public class Main {
	
	/**
	 * Instance de l'algorithme de classification
	 */
	public static IClassifier algorithm;
	
	/**
	 * Fichier d'entrée
	 */
	public static String inputFile;
	
	/**
	 * Retourne l'usage du programme
	 * @return l'usage du programme
	 */
	public static String getUsage() {
		String str;
		str = "Usage : java -jar classification.jar --algorithm <algorithm> --source <sourceFile>\n";
		str += 	"\t <algorithm> = C45 | NaiveBayes : the algorithm to use\n";
		str += 	"\t <sourceFile> : the input data\n";
		
		return str;
	}
	
	/**
	 * Traite les arguments du programme
	 * @param args les arguments du programme
	 */
	public static void processOptionInline(String[] args) {
		
		List<String> argList = Arrays.asList(args);
		
		int idAlgorithm = argList.indexOf("--algorithm")+1;
		int idSourceFile = argList.indexOf("--source")+1;
		
		if (	(idAlgorithm <= 0) || 
				(idSourceFile <= 0) || 
				(idSourceFile >= argList.size()) || 
				(idAlgorithm >= argList.size())) {
			
			System.err.println("Bad usage");
			System.err.println(getUsage());
			
		} else {
			
			String algoStr = argList.get(idAlgorithm);
			inputFile = argList.get(idSourceFile);
			
			
			//discrimination de la classe à instancier
			if (algoStr == "C45") {
				
				algorithm = new C45();
				
			} else if (algoStr == "NaiveBayes"){
				
				algorithm = new NaiveBayes();					
				
			} else {
				System.err.println("Bad usage");
				System.err.println(getUsage());
			}			
		}
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		processOptionInline(args);
		
		algorithm.readData(inputFile);
		algorithm.classify();
		algorithm.printResults();
		
		
		
	}

}
