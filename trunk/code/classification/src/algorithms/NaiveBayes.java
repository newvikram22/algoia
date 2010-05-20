package algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.Couple;
import common.Util;

/**
 * Classe implémentant l'algorithme de classification NaiveBayes
 * @author remi
 *
 */
public class NaiveBayes extends AbstractClassifier {
	
	/**
	 * TODO:Temporaire
	 */
	private ArrayList<ArrayList<String>> attributs;
	private ArrayList<ArrayList<Integer>> nbAttributs;
	private ArrayList<ArrayList<ArrayList<Integer>>> nbVal;
	
	/**
	 * l'ensemble des instances
	 */
	private ArrayList<String> instances;
	
	/**
	 * le pourcentage de données à considérer comme ensemble d'apprentissage
	 */
	private double percentage;
	
	/**
	 *  liste comportant les couples (prédiction, classe) pour chaque instance
	 */
	private List<Couple<Integer, Integer>> theResults;

	/**
	 * lit les données et initialise l'algorithme
	 * @param inputFile le fichier comportant les données
	 * @param percentage le pourcentage de données à considérer comme ensemble d'apprentissage
	 * @throws IOException 
	 */
	@Override
	public void readData(String inputFile, double percentage) {
		
		this.percentage = percentage;
		attributs= new ArrayList< ArrayList<String> >();
		nbAttributs= new ArrayList< ArrayList<Integer> >();
		nbVal= new ArrayList< ArrayList<ArrayList< Integer > > >();
		instances = new ArrayList<String>();
		
		try {
			BufferedReader ff = new BufferedReader(new FileReader(inputFile));
			String s;
			s = ff.readLine();
			instances.add(s);
			String[] result = s.split(",");
			
			for (int i = 0; i < result.length; i++) {
				ArrayList<String> e = new ArrayList<String>();
				e.add(result[i]);
				attributs.add(e);

				ArrayList<Integer> f = new ArrayList<Integer>();
				f.add(0);
				nbAttributs.add(f);
			}
			
			while ((s = ff.readLine()) != null) {
				result = s.split(",");
				for (int i = 0; i < result.length; i++) {
					ArrayList<String> e = attributs.get(i);
					ArrayList<Integer> f = nbAttributs.get(i);
					int j = e.indexOf(result[i]);
					if (j < 0) {
						e.add(result[i]);
						f.add(0);
					}
				}
				instances.add(s);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Discrétisation des Données
		if(intervalNumber!=-1)
		{
			for(int i=0;i<attributs.size();i++)
			{
				if(i!=classIndex)
				{
				ArrayList<String> e = attributs.get(i);
				ArrayList<Double> a=new ArrayList<Double>();
				boolean b=true;
				double d,max=-Double.MAX_VALUE,min=Double.MAX_VALUE;
				
				for(int j=0;j<e.size();j++)
				{
					//System.out.print(e.get(j)+" ");
					try{
						d=Double.parseDouble(e.get(j));
					}
					catch(Exception e1)
					{
						b=false;
						break;
					}
					
					a.add(d);
					if(d>max)
						max=d;
					if(d<min)
						min=d;				
					
				}
				if(b==true)
				{
					ArrayList<Integer> f = nbAttributs.get(i);
					e.clear();
					e.add("Discret");
					e.add(String.valueOf(min));
					e.add(String.valueOf(max));
					e.add(String.valueOf((max-min)/intervalNumber));
					f.clear();
					for(int j=0;j<intervalNumber;j++)
					{
						f.add(0);
					}
				}
				}
			}
		}
		ArrayList<Integer> f;
		ArrayList<ArrayList<Integer>> g;
		for (int i = 0; i < nbAttributs.size(); i++) {
			if(i!=classIndex){
				ArrayList<Integer> e = nbAttributs.get(i);
				g=new ArrayList<ArrayList<Integer>>();
				for (int j = 0; j < e.size(); j++) {
					f=new ArrayList<Integer>(nbAttributs.get(classIndex));
					g.add(f);
				}
				nbVal.add(g);
			}
			
		}
//		for (int i = 0; i < nbAttributs.size(); i++) {
//			ArrayList<Integer> e = nbAttributs.get(i);
//			for (int j = 0; j < e.size(); j++) {
//				System.out.print(e.get(j)+" ");
//			}
//			System.out.println();
//			ArrayList<String> dd = attributs.get(i);
//			for (int j = 0; j < dd.size(); j++) {
//				System.out.print(dd.get(j)+" - ");
//			}
//			System.out.println();
//		}
	}
	
	/**
	 * mélange aléatoirement l'ensemble des instances
	 * @param seed le seed pour initialiser la marche aléatoire
	 */
	@Override
	protected void shakeInstances(int seed) {
		
		ArrayList<String> shakedInstances = new ArrayList<String>();
		
		int shakedIndices[] = Util.buildBijection(seed, instances.size());
		
		for (int i = 0 ; i < instances.size() ; i++) {
			shakedInstances.add(instances.get(shakedIndices[i]));
		}
		
		instances = shakedInstances;
	}
	
	/**
	 * classifie les instances
	 */
	@Override
	public void doClassify() {
		
		//construction des ensembles d'apprentissage et de test
		int numInstances = instances.size();
		int trainSize = (int)Math.round(percentage*numInstances/100.);
		
		try {
			//on calcule les propabilités avec l'ensenble d'apprentissage
			int ind=0;
			String s;
			String[] result;
			boolean b = true;
			while (ind<trainSize) {
				s = instances.get(ind);
				ind++;
				result = s.split(",");
				for (int i = 0; i < result.length; i++) {
					if(i!=classIndex)
					{
						ArrayList<String> e = attributs.get(i);
						ArrayList<Integer> f = nbAttributs.get(i);
						int j=0;
						if(e.get(0)!="Discret")
						{
							j = e.indexOf(result[i]);
						}
						else
						{
							Double d=Double.parseDouble(e.get(1))+Double.parseDouble(e.get(3));
							while(j<intervalNumber-1)
							{
							if(Double.parseDouble(result[i])<=d)
								break;
							d=d+Double.parseDouble(e.get(3));
							j++;
							}
						}
						//System.out.println("j  "+j);
						f.set(j, f.get(j) + 1);
							
						e = attributs.get(classIndex);
						int m = e.indexOf(result[classIndex]);
							
						ArrayList<Integer> z = nbAttributs.get(classIndex);
						if (b) {
							z.set(m, z.get(m) + 1);
							b = false;
						}
						ArrayList<ArrayList<Integer>> g = nbVal.get(i);
						ArrayList<Integer> h = g.get(j);
						h.set(m, h.get(m) + 1);

					}
					else 
						i--;
				}
				b = true;
			}
			
			//on prédit sur l'ensemble de test
			ArrayList<String> a=attributs.get(classIndex);
			ArrayList<Integer> aa=nbAttributs.get(classIndex);
			theResults=new ArrayList<Couple<Integer, Integer>>();
			String v="";
			while (ind<numInstances) {
				s = instances.get(ind);
				v+="Pour linstance "+s+"\n";
				result = s.split(",");
				
				double max=0;
				int prediction=0;
				int classValue = a.indexOf(result[classIndex]);
				for(int i=0;i<aa.size();i++)
				{
					double p= aa.get(i)/(double)trainSize;
					for (int j = 0; j < result.length; j++) 
					{
						if(j!=classIndex)
						{
						ArrayList<String> bb=attributs.get(j);
						ArrayList<Integer> bbb=nbAttributs.get(j);
						ArrayList<ArrayList<Integer>> cc=nbVal.get(j);
						int k=0;
						ArrayList<Integer> ccc=cc.get(k);						
						
						if(bb.get(0)!="Discret")
						{
							k = bb.indexOf(result[j]);
						}
						else
						{
							Double d=Double.parseDouble(bb.get(1))+Double.parseDouble(bb.get(3));
							while(k<intervalNumber-1)
							{
							if(Double.parseDouble(result[i])<=d)
								break;
							d=d+Double.parseDouble(bb.get(3));
							k++;
							}
						}
						
						p=p*((double)(ccc.get(i)/(double)aa.get(i)))/(double)(bbb.get(k)/(double)trainSize);
						}
					}
					//v+="\nla probabalité de "+a.get(i)+"est de "+p+"\n";
					if(p>max)
					{
						max=p;
						prediction=i;
					}
				}
				theResults.add(new Couple<Integer, Integer>(prediction, classValue));
				ind++;
			}
			//System.out.print(v);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//printProbabilities();
	}	

//	/**
//	 * affiche les probabilités
//	 */
//	private void printProbabilities() {
//		String s="Attribut:\n\n";
//		ArrayList<Integer> a =nbAttributs.get(classIndex);
//		for(int i=0;i<attributs.size();i++)
//		{
//			if(i!=classIndex)
//			{
//			ArrayList<String> e =attributs.get(i);
//			ArrayList<Integer> f =nbAttributs.get(i);
//			ArrayList< ArrayList<Integer> > g =nbVal.get(i);
//			for(int j=0;j<e.size();j++)
//			{		
//				s+=e.get(j)+" "+f.get(j)+" ";
//				if(j!=e.size())
//				{
//					ArrayList<Integer> h =g.get(j);
//					s+="( ";
//					for(int k=0;k<h.size();k++)
//					{
//						s+=h.get(k)+"/"+a.get(k)+",";
//					}
//					s+=") - ";
//				}
//			}
//			s+="\n";
//			}
//		}
//		ArrayList<String> e =attributs.get(classIndex);
//		for(int j=0;j<e.size();j++)
//		{		
//			s+=e.get(j)+" "+a.get(j)+" ";
//			s+=" - ";
//		}
//		s+="\n";		
//		
//		System.out.print(s);
//		
//	}

	/**
	 * retourne le tableau des résultats
	 * @return liste comportant les couples (prédiction, classe) pour chaque instance
	 */
	@Override
	protected List<Couple<Integer, Integer>> getResults() {
		
		return theResults;
	}

	@Override
	public String getName() {
		return "NaiveBayes";
	}

}
