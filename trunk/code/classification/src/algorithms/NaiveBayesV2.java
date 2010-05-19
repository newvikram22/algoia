package algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import weka.core.Instances;

import common.Couple;
import common.Util;

public class NaiveBayesV2 extends AbstractClassifier {

	
	/**
	 * pourcentage d'instance à prendre dans l'ensemble d'apprentissage
	 */
	private Double percentage;
	
	/**
	 * la liste des instances
	 * une instance est une liste d'attributs
	 */
	private List<List<String>> instances;
	
	/**
	 * ensemble d'apprentissage
	 */
	private List<List<String>> train;
	
	/**
	 * ensemble de test
	 */
	private List<List<String>> test;

	/**
	 * le nombre d'attributs
	 */
	private int numAttributes;
	
	/**
	 * ensemble des valeurs des classes
	 */
	private Set<String> classes;
	
	/**
	 * probClasses.get(c) est la probabilité de la classe c
	 */
	private Hashtable<String, Double> probClasses;
	
	/**
	 * probAttributeByClass.get(c).get(a).get(v)
	 * probabilité que l'attribut a soit égal à v dans la classe c
	 */
	private Hashtable<String, List<Hashtable<String, Double>>> probAttributeByClass;
	
	
	/**
	 *  liste comportant les couples (prédiction, classe) pour chaque instance
	 */
	List<Couple<Integer, Integer>> theResults;
	
	
	
	
	public List<List<String>> getInstances() {
		return instances;
	}

	public void setInstances(List<List<String>> instances) {
		this.instances = instances;
	}

	public int getNumAttributes() {
		return numAttributes;
	}

	public void setNumAttributes(int numAttributes) {
		this.numAttributes = numAttributes;
	}

	public Set<String> getClasses() {
		return classes;
	}

	public void setClasses(Set<String> classes) {
		this.classes = classes;
	}

	@Override
	protected void doClassify() {
		
		//construction ensemble d'apprentissage/ensemble de test
		int trainSize = (int)Math.round(percentage*instances.size()/100.);
		
		train = new ArrayList<List<String>>();
		test = new ArrayList<List<String>>();
		
		for (int i = 0 ; i < trainSize ; i++) {
			train.add(instances.get(i));
		}
		for (int i = trainSize ; i < instances.size(); i++) {
			test.add(instances.get(i));
		}
		
		//initialisation
		probAttributeByClass = new Hashtable<String, List<Hashtable<String, Double>>>();
		for (String classe : classes) {
			probAttributeByClass.put(classe, new ArrayList<Hashtable<String, Double>>());
			for (int i = 0 ; i < numAttributes ; i++) {
				probAttributeByClass.get(classe).add(new Hashtable<String, Double>());
			}
		}
		probClasses = new Hashtable<String, Double>();
		
		/*
		 * première passe : comptage
		 */
		for (List<String> lst : train) {
						
			//ajout de la classe
			Util.add(probClasses, lst.get(classIndex), 1.);
						
			
			//ajout des attributs
			for (int i = 0 ; i < numAttributes ; i++) {
				
				Util.add(probAttributeByClass.get(lst.get(classIndex)).get(i), lst.get(i), 1.);
			}
		}
		
		
		/*
		 * seconde passe : normalisation des moyennes
		 */
		//probabilité de chaque classe
		for (String classe : classes) {
			//System.out.println(classe+" : "+probClasses.get(classe));
			probClasses.put(classe, probClasses.get(classe)/train.size());
		}
		
		//les attributs
		//nombre d'instances de classe c qui ont l'attribut a égal à v 
		//divisé par le nombre d'instances de classe c
		for (String classe : classes) {
			int count = 0;
			for (List<String> inst : train) {
				if (inst.get(classIndex).equals(classe)) {
					count++;
				}
			}
			for (int att = 0 ; att < numAttributes ; att++) {				
				for (String key : probAttributeByClass.get(classe).get(att).keySet()) {
					probAttributeByClass.get(classe).get(att).put(
							key,
							probAttributeByClass.get(classe).get(att).get(key)/count);
				}
			}
		}
		
		/*
		 * classification de l'ensemble de test 
		 */
		theResults = new ArrayList<Couple<Integer, Integer>>();
		
		for (List<String> inst : test) {
			Double results[] = new Double[classes.size()];
			int ii = 0;
			for (String classe : classes) {
				results[ii] = getProbForThisClass(inst, classe);
				ii++;
			}			
					
			int prediction = 0;
			Double max = results[prediction];
			for (int i = 0 ; i < results.length ; i++) {
				if (results[i] > max) {
					max = results[i];
					prediction = i;
				}
			}
			
			int realClass = 0;
			ii = 0;
			for (String classe : classes) {
				if ( classe.equals(inst.get(classIndex))) {
					realClass = ii;
				}
				ii++;
			}
			
			theResults.add(new Couple(prediction, realClass));			
			
		}	
	}

	private Double getProbForThisClass(List<String> inst, String classe) {
	
		Double Pc = probClasses.get(classe);
		Double product = 1.;
		for (int i = 0 ; i < numAttributes ; i++) {
			if (i != classIndex) {
				String val = inst.get(i);
				if (probAttributeByClass.get(classe).get(i).get(val) != null) {
					product *= probAttributeByClass.get(classe).get(i).get(val);
				} else {
					product *= 0.00001;
				}
			}
		}
		
		return Pc*product;
		
	}

	@Override
	protected List<Couple<Integer, Integer>> getResults() {
		return theResults;
	}

	@Override
	public void readData(String inputFile, double percentage) {
		instances = new ArrayList<List<String>>();
		classes = new TreeSet<String>();
		
		this.percentage = percentage;
		
		try {
			BufferedReader ff = new BufferedReader(new FileReader(inputFile));
			String line;
			
			while (ff.ready()) {
				
				line = ff.readLine(); 
				String arrLine[] = line.split(",");
				List<String> instance = new ArrayList<String>();
				
				for (int i = 0 ; i < arrLine.length ; i++) {
					
					String val = "";
					
					try {
						val = arrLine[i];
				
					} catch(Exception e){
						e.printStackTrace();
						System.exit(-1);
					}
					
					instance.add(val);			
					
				}
				instances.add(instance);
				if (instances.size() == 1) {
					numAttributes = instance.size();
					classIndex = instance.size()-1;
				}
				classes.add(instance.get(classIndex));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void shakeInstances(int seed) {
		List<List<String>> shakedInstances = new ArrayList<List<String>>();

		int shakedIndices[] = Util.buildBijection(seed, instances.size());

		for (int i = 0; i < instances.size(); i++) {
			shakedInstances.add(instances.get(shakedIndices[i]));
		}

		instances = shakedInstances;

	}

	@Override
	public String getName() {
		return "NaiveBayesV2";
	}

}
