package algorithms;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;

import common.Couple;
import common.Result;
import common.Util;

/**
 * Classe abstraite représentant un classifier
 * 
 * @author remi
 * 
 */
public abstract class AbstractClassifier {

	public static int SEED = 1337;

	protected String strResults;

	/**
	 * liste comportant les couples (prédiction, classe) pour chaque instance
	 */
	protected List<Couple<Integer, Integer>> results;

	/**
	 * l'index de la classe
	 */
	protected int classIndex;

	protected Double percentageCorrect;
	protected Double percentageIncorrect;

	public Double getPercentageCorrect() {
		return percentageCorrect;
	}

	public void setPercentageCorrect(Double percentageCorrect) {
		this.percentageCorrect = percentageCorrect;
	}

	public Double getPercentageIncorrect() {
		return percentageIncorrect;
	}

	public void setPercentageIncorrect(Double percentageIncorrect) {
		this.percentageIncorrect = percentageIncorrect;
	}

	public AbstractClassifier() {
		results = null;
	}

	/**
	 * lit les données et initialise l'algorithme
	 * 
	 * @param inputFile
	 *            le fichier comportant les données
	 * @param percentage
	 *            le pourcentage de données à considérer comme ensemble
	 *            d'apprentissage
	 * @throws IOException
	 */
	abstract public void readData(String inputFile, double percentage);

	abstract public String getName();

	public int getClassIndex() {
		return classIndex;
	}

	public void setClassIndex(int classIndex) {
		this.classIndex = classIndex;
	}

	/**
	 * classifie les instances
	 */
	public void classify() {

		// seed constant pour pouvoir comparer les algorithmes
		shakeInstances(SEED);

		doClassify();

		results = getResults();

		computeResults();

	}

	/**
	 * retourne le tableau des résultats
	 * 
	 * @return liste comportant les couples (prédiction, classe) pour chaque
	 *         instance
	 */
	abstract protected List<Couple<Integer, Integer>> getResults();

	/**
	 * mélange aléatoirement l'ensemble des instances
	 * 
	 * @param seed
	 *            le seed pour initialiser la marche aléatoire
	 */
	abstract protected void shakeInstances(int seed);

	/**
	 * classifie les instances
	 */
	abstract protected void doClassify();

	private void computeResults() {
		if (results == null) {
			System.err
					.println("printResults() : results have not been computed yet !");
		} else {

			TreeSet<Integer> setClasses = new TreeSet<Integer>();
			// result est la liste comportant les couples (prédiction, classe)
			// pour chaque instance
			for (Couple<Integer, Integer> c : results) {
				setClasses.add(c.first);
				setClasses.add(c.second);
			}

			Hashtable<Integer, Hashtable<String, Double>> table = new Hashtable<Integer, Hashtable<String, Double>>();
			for (Integer classe : setClasses) {
				table.put(classe, new Hashtable<String, Double>());
				table.get(classe).put("truePositive", 0.);
				table.get(classe).put("falsePositive", 0.);
				table.get(classe).put("falseNegative", 0.);

				table.get(classe).put("recall", 0.);
				table.get(classe).put("precision", 0.);
				table.get(classe).put("F-measure", 0.);
			}

			int numCorrects = 0;
			int numIncorrects = 0;

			for (Couple<Integer, Integer> c : results) {
				if (c.first != c.second) {
					numIncorrects++;
					// c'est un faux positif pour c.first
					// c'est un faux negatif pour c.second

					Double oldFP_first = table.get(c.first)
							.get("falsePositive");
					Double oldFN_second = table.get(c.second).get(
							"falseNegative");

					table.get(c.first).put("falsePositive", 1 + oldFP_first);
					table.get(c.second).put("falseNegative", 1 + oldFN_second);

				} else {
					numCorrects++;
					// c'est un vrai positif pour c.first==c.second

					Double oldTP = table.get(c.first).get("truePositive");

					table.get(c.first).put("truePositive", 1 + oldTP);
				}
			}

			for (Integer classe : setClasses) {

				Double TP = table.get(classe).get("truePositive");
				Double FP = table.get(classe).get("falsePositive");
				Double FN = table.get(classe).get("falseNegative");

				Double recall = TP / (TP + FN);
				Double precision = TP / (TP + FP);
				Double F_measure = 2 * (precision * recall)
						/ (precision + recall);

				table.get(classe).put("recall", recall);
				table.get(classe).put("precision", precision);
				table.get(classe).put("F-measure", F_measure);

			}

			// nombre de decimales apres la virgule à afficher
			int numDecimales = 3;

			percentageCorrect = (100. * numCorrects) / (double) results.size();
			percentageIncorrect = (100. * numIncorrects)
					/ (double) results.size();

			strResults = "Results : \n";
			strResults += "Correctly classified instances : " + numCorrects
					+ "\t" + Util.printRounded(percentageCorrect, numDecimales)
					+ "%\n";
			strResults += "Incorrectly classified instances : " + numIncorrects
					+ "\t"
					+ Util.printRounded(percentageIncorrect, numDecimales)
					+ "%\n";
			strResults += "\n";
			strResults += "Details by class : \n";
			strResults += "\n";
			strResults += "Class \tPrecision \tRecall \tF-Measure \n";
			for (Integer classe : setClasses) {
				strResults += classe + "\t";
				strResults += Util.printRounded(table.get(classe).get(
						"precision"), numDecimales)
						+ "\t\t";
				strResults += Util.printRounded(
						table.get(classe).get("recall"), numDecimales)
						+ "\t";
				strResults += Util.printRounded(table.get(classe).get(
						"F-measure"), numDecimales)
						+ "\n";
			}
		}
	}

	/**
	 * affiche les résultats
	 */
	public void printResults() {

		System.out.println(strResults);

	}

}
