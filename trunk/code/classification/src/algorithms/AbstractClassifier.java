package algorithms;

import java.io.IOException;

/**
 * Classe abstraite représentant un classifier
 * @author remi
 *
 */
public abstract class AbstractClassifier {
	
	public static int SEED = 1337;
	
	/**
	 * lit les données et initialise l'algorithme
	 * @param inputFile le fichier comportant les données
	 * @param percentage le pourcentage de données à considérer comme ensemble d'apprentissage
	 * @throws IOException 
	 */
	abstract public void readData(String inputFile, double percentage);
	
	
	
	/**
	 * classifie les instances
	 */
	public void classify() {
		
		//seed constant pour pouvoir comparer les algorithmes
		shakeInstances(SEED);
		
		doClassify();
	}

	/**
	 * mélange aléatoirement l'ensemble des instances
	 * @param seed le seed pour initialiser la marche aléatoire
	 */
	abstract protected void shakeInstances(int seed) ;

	/**
	 * classifie les instances
	 */
	abstract protected void doClassify() ;


	/**
	 * affiche les résultats
	 */
	abstract public void printResults();

}
