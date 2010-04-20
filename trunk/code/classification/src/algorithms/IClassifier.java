package algorithms;

import java.io.IOException;

/*
 * Interface représentant un classifieur
 */
public interface IClassifier {

	/**
	 * lit les données et initialise l'algorithme
	 * @param inputFile le fichier comportant les données
	 * @throws IOException 
	 */
	void readData(String inputFile) throws IOException;

	/**
	 * classifie les instances
	 */
	void classify();

	/**
	 * affiche les résultats
	 */
	void printResults();

}
