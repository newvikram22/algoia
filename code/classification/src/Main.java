import java.util.Arrays;
import java.util.List;

import algorithms.AbstractClassifier;
import algorithms.C45;
import algorithms.NaiveBayes;
import algorithms.NaiveBayesV2;

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
	public static AbstractClassifier algorithm;
	
	/**
	 * Fichier d'entrée
	 */
	public static String inputFile;
	
	/**
	 * le pourcentage de donnée à considérer comme ensemble d'apprentissage
	 */
	public static int percentage;
	
	/**
	 * l'index de la classe
	 */
	public static int classIndex;

	/**
	 * nombre d'intervalles pour la discrétisation
	 */
	public static int intervalNumber;
	
	/**
	 * the discretization method
	 */
	public static String discretizationMethod;
	
	/**
	 * Retourne l'usage du programme
	 * @return l'usage du programme
	 */
	public static String getUsage() {
		String str;
		str = "Usage : java -jar classification.jar --algorithm <algorithm> --source <sourceFile> --percentage <percentage> --classIndex <intervalNumber> --classIndex <classIndex> --discretizationMethod <discretizationMethod>\n";
		str += 	"\t <algorithm> = C45 | NaiveBayes : the algorithm to use\n";
		str += 	"\t <sourceFile> : the input data\n";
		str += 	"\t <percentage> : the percentage of data to consider as the training set\n";
		str += 	"\t <classIndex> : the index of the class\n";
		str += 	"\t <intervalNumber> : the number of intervals k\n";
		str += 	"\t <discretizationMethod> = EWD | EFD: the discretization method\n";

		
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
		int idPercentage = argList.indexOf("--percentage")+1;
		int idClassIndex= argList.indexOf("--classIndex")+1;
		int idIntervalNumber= argList.indexOf("--intervalNumber")+1;
		int idDiscretizationMethod= argList.indexOf("--discretizationMethod")+1;
		
		if (	(idAlgorithm <= 0) || 
				(idSourceFile <= 0) || 
				(idPercentage <= 0) || 
				(idClassIndex <= 0) || 
				(idIntervalNumber <= 0) ||
				(idDiscretizationMethod <= 0) ||
				(idSourceFile >= argList.size()) ||
				(idPercentage >= argList.size()) ||
				(idAlgorithm >= argList.size()) ||
				(idClassIndex >= argList.size())||
				(idIntervalNumber >= argList.size())||
				(idDiscretizationMethod >= argList.size())) {
			
			
			System.err.println("Bad usage");
			System.err.println(getUsage());
			
		} else {
			
			String algoStr = argList.get(idAlgorithm);
			inputFile = argList.get(idSourceFile);
			percentage = Integer.parseInt(argList.get(idPercentage));
			classIndex = Integer.parseInt(argList.get(idClassIndex));
			intervalNumber =Integer.parseInt(argList.get(idIntervalNumber));
			discretizationMethod =argList.get(idDiscretizationMethod);
			if(discretizationMethod!="EWD" && discretizationMethod!="EFD")
			{
				System.err.println("Bad usage : discretizationMethod");
				System.err.println(getUsage());
				System.exit(-1);
			}
			
			//discrimination de la classe à instancier
			
			
			if (algoStr.equals("C45")) {
				
				algorithm = new C45();
				
			} else if (algoStr.equals("NaiveBayes")){
				
				algorithm = new NaiveBayes();					
				
			} else if (algoStr.equals("NaiveBayesV2")){
				
				algorithm = new NaiveBayesV2();
			
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
		
		algorithm.setIntervalNumber(intervalNumber);
		algorithm.setClassIndex(classIndex);
		algorithm.setDiscretizationMethod(discretizationMethod);
		algorithm.readData(inputFile, percentage);	
		algorithm.classify();
		algorithm.printResults();
		
		
		
		
	}

}
