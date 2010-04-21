package algorithms;

import java.io.File;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Couple;
import common.DataToArff;
import common.Util;

import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Classe implémentant l'algorithme C4.5 en adaptant l'algorithme J48 de Weka 
 * 
 * @author remi
 *
 */
public class C45 extends AbstractClassifier {
	
	/**
	 * l'algorithme C4.5 de Weka
	 */
	private J48 adapted;
	
	/**
	 * l'ensemble des instances
	 */
	private Instances instances;
	
	/**
	 * le pourcentage de données à considérer comme ensemble d'apprentissage
	 */
	private double percentage;
	
	/**
	 *  liste comportant les couples (prédiction, classe) pour chaque instance
	 */
	private List<Couple<Integer, Integer>> theResults;

	/**
	 * constructor
	 */
	public C45() {
		theResults = new ArrayList<Couple<Integer, Integer>>();
	}
	/**
	 * lit les données et initialise l'algorithme
	 * @param inputFile le fichier comportant les données
	 * @param percentage le pourcentage de données à considérer comme ensemble d'apprentissage
	 */
	@Override
	public void readData(String inputFile, double percentage) {
		
		this.percentage = percentage;
		
		try {
			
			File file = new File(inputFile);
			if (file.isFile()) {
				DataToArff dta = new DataToArff(file);
				Reader reader = dta.buildArffFile();
				
				instances = new Instances(reader);
				
								
			} else {
				System.err.println("C45::readData : could not read data file");
				System.exit(-1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * mélange aléatoirement l'ensemble des instances
	 * @param seed le seed pour initialiser la marche aléatoire
	 */
	@Override
	protected void shakeInstances(int seed) {
		
		Instances shakedInstances = new Instances(instances);
		shakedInstances.clear();
		
		int shakedIndices[] = Util.buildBijection(seed, instances.numInstances());
		
		for (int i = 0 ; i < instances.numInstances() ; i++) {
			shakedInstances.add(instances.get(shakedIndices[i]));
		}
		
		instances = shakedInstances;
	}
	
	/**
	 * classifie les instances
	 */
	@Override
	public void doClassify() {
		adapted = new J48();
		
		//construction des ensembles d'apprentissage et de test
		int numInstances = instances.numInstances();
		int trainSize = (int)Math.round(percentage*numInstances/100.);
		int testSize = instances.numInstances()-trainSize;
		
		Instances train = new Instances(instances, 0, trainSize);
		Instances test = new Instances(instances, trainSize, testSize);
		
		try {
			
			//on fixe l'indice de la classe parmi les attribut (c'est le dernier)
			train.setClassIndex(train.numAttributes()-1);
			test.setClassIndex(train.numAttributes()-1);
			
			//construction du classifier
			adapted.buildClassifier(train);
			
		
			//on classe chaque instance de l'ensemble de tests
             for (int jj=0 ; jj < test.numInstances() ; jj++) {
            	 
            	 //on construit une instance sans l'attribut de classe
				Instance classMissing = (Instance) test.instance(jj).copy();
				classMissing.setDataset(test.instance(jj).dataset());
				classMissing.setClassMissing();

				//on calcule les proportions pour chaque classe
				double results[] = adapted.distributionForInstance(classMissing);

				int classValue = (int)test.instance(jj).classValue();
				int prediction = Util.indexOfMax(results);
				theResults.add(new Couple(prediction, classValue));
				
             }
             
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * retourne le tableau des résultats
	 * @return liste comportant les couples (prédiction, classe) pour chaque instance
	 */
	@Override
	protected List<Couple<Integer, Integer>> getResults() {

		return theResults;
			
	}	

	

	

}
