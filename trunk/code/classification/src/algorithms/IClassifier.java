package algorithms;

/*
 * Interface représentant un classifieur
 */
public interface IClassifier {

	/**
	 * lit les données et initialise l'algorithme
	 * @param inputFile le fichier comportant les données
	 */
	void readData(String inputFile);

	/**
	 * classifie les instances
	 */
	void classify();

	/**
	 * affiche les résultats
	 */
	void printResults();

}
