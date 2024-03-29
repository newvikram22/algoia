package algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

	private void methodEWD() {
		for(int i=0;i<numAttributes;i++)
		{
			/*
			System.out.println("AVANT:");
			if (i==0) {
				for (List<String> inst : instances) {
					System.out.println(inst.get(i));
				}
			}
			*/
			
			boolean b=true;
			double d,max=-Double.MAX_VALUE,min=Double.MAX_VALUE;
			
			if(i!=classIndex)
			{
				for(int j=0;j<instances.size();j++)
				{
					List<String> e =  instances.get(j);
					try{
						d=Double.parseDouble(e.get(i));
					}
					catch(Exception e1)
					{
						b=false;
						break;
					}
					
					if(d>max)
						max=d;
					if(d<min)
						min=d;
				}
				
				if(b==true)
				{
					double w=(max-min)/intervalNumber;
					for(int j=0;j<instances.size();j++)
					{
						List<String> e =  instances.get(j);
						d=min+w;
						int k=0;
						while(k<intervalNumber-1)
						{
							if(Double.parseDouble(e.get(i))<=d)
								break;
							d=d+w;
							k++;
						}
						e.set(i,String.valueOf(k));
					}
				}
			}
			/*
			System.out.println("APRES :");
			if (i==0) {
				for (List<String> inst : instances) {
					System.out.println(inst.get(i));
				}
			}
			
			*/
		}
	}

	private void methodEFD() {
		for(int i=0;i<numAttributes;i++)
		{
			boolean b=true;
			double d;
			ArrayList<Double> tri= new ArrayList<Double>();
			ArrayList<Integer> triIndex= new ArrayList<Integer>();
			if(i!=classIndex)
			{
				for(int j=0;j<instances.size();j++)
				{
					List<String> e =  instances.get(j);
					try{
						d=Double.parseDouble(e.get(i));
					}
					catch(Exception e1)
					{
						b=false;
						break;
					}
					tri.add(d);
					triIndex.add(j);
				}
				
				if(b==true)
				{
					int j=0;
			        boolean inversion;
			        do
			            {
			            inversion=false;

			            for(int k=0;k<tri.size()-1;k++)
			                {
			                if(tri.get(k)>tri.get(k+1))
			                    {
			                    Collections.swap(tri, k, k+1);
			                    Collections.swap(triIndex, k, k+1);
			                    inversion=true;
			                    }
			                }
			             }
			        while(inversion);
			        int l=0;
					while(j<instances.size())
					{
						int k=0;
						while((k<instances.size()/intervalNumber || k==0) && j<instances.size())
						{
							List<String> e =  instances.get(triIndex.get(j));
							e.set(i, String.valueOf(l));
							k++;j++;
						}
						if(l<intervalNumber)
							l++;
					}
				}
			}
		}
	}	
	
	private void discretizeData() {
		if(discretizationMethod.equals("EWD")) {
			methodEWD();

                } else if (discretizationMethod.equals("EFD")) {
			methodEFD();
                } 
	}
	
	@Override
	protected void doClassify() {
		
		//Discrétisation
		if(intervalNumber!=-1)
			discretizeData();
		
		//construction ensemble d'apprentissage/ensemble de test
		int trainSize = (int)Math.round(percentage*instances.size()/100.);
		
		train = new ArrayList<List<String>>();
		test = new ArrayList<List<String>>();
		
		for (int i = 0 ; i < trainSize ; i++) {
			train.add(instances.get(i));
			classes.add(instances.get(i).get(classIndex));
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
			String instClasse = lst.get(classIndex);
			Util.add(probClasses, instClasse, 1.);
									
			
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
			
			theResults.add(new Couple<Integer,Integer>(prediction, realClass));			
			
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
					//classIndex = instance.size()-1;
				}
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
