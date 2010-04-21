package algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe implémentant l'algorithme de classification NaiveBayes
 * @author remi
 *
 */
public class NaiveBayes extends AbstractClassifier {
	
	private ArrayList<ArrayList<String>> attributs;
	private ArrayList<ArrayList<Integer>> nbAttributs;
	private ArrayList<ArrayList<ArrayList<Integer>>> nbVal;

	/**
	 * lit les données et initialise l'algorithme
	 * @param inputFile le fichier comportant les données
	 * @param percentage le pourcentage de données à considérer comme ensemble d'apprentissage
	 * @throws IOException 
	 */
	@Override
	public void readData(String inputFile, double percentage) {
		attributs= new ArrayList< ArrayList<String> >();
		nbAttributs= new ArrayList< ArrayList<Integer> >();
		nbVal= new ArrayList< ArrayList<ArrayList< Integer > > >();
		
		try {

			BufferedReader ff = new BufferedReader(new FileReader(inputFile));
			String s = ff.readLine();
			String[] result = s.split(",");
			for (int i = 0; i < result.length; i++) {
				ArrayList<String> e = new ArrayList<String>();
				e.add(result[i]);
				attributs.add(e);

				ArrayList<Integer> f = new ArrayList<Integer>();
				f.add(1);
				nbAttributs.add(f);
				if (i != result.length - 1) {
					ArrayList<ArrayList<Integer>> g = new ArrayList<ArrayList<Integer>>();
					f = new ArrayList<Integer>();
					f.add(1);
					g.add(f);
					nbVal.add(g);
				}
			}
			printResults();

			// int n=Integer.parseInt(ff.readLine());
			boolean b = true;
			while ((s = ff.readLine()) != null) {
				result = s.split(",");
				for (int i = 0; i < result.length - 1; i++) {
					ArrayList<String> e = attributs.get(i);
					ArrayList<Integer> f = nbAttributs.get(i);
					int j = e.indexOf(result[i]);
					if (j < 0) {
						e.add(result[i]);
						f.add(1);

						ArrayList<ArrayList<Integer>> g = nbVal.get(i);
						ArrayList<Integer> h = new ArrayList<Integer>(g.get(0));
						ArrayList<Integer> hc = new ArrayList<Integer>(g.get(0));
						for (int k = 0; k < h.size(); k++) {
							h.set(k, 0);
							hc.set(k, 0);
						}
						g.add(h);
						j = e.size() - 1;
					} else {
						f.set(j, f.get(j) + 1);
					}

					e = attributs.get(result.length - 1);
					int m = e.indexOf(result[result.length - 1]);
					// System.out.println(result.length-1);
					if (m < 0) {
						f = nbAttributs.get(result.length - 1);
						e.add(result[result.length - 1]);
						f.add(1);
						b = false;

						for (int k = 0; k < result.length - 1; k++) {
							ArrayList<ArrayList<Integer>> g = nbVal.get(k);
							for (int l = 0; l < g.size(); l++) {
								ArrayList<Integer> h = g.get(l);
								if (k == i && l == j) {
									h.add(1);
								} else {
									h.add(0);
								}
							}
						}
					} else {
						ArrayList<Integer> z = nbAttributs
								.get(result.length - 1);
						if (b) {
							z.set(m, z.get(m) + 1);
							b = false;
						}
						ArrayList<ArrayList<Integer>> g = nbVal.get(i);
						for (int l = 0; l < g.size(); l++) {
							ArrayList<Integer> h = g.get(l);
							if (l == j) {
								h.set(m, h.get(m) + 1);
							}
						}
					}
				}
				printResults();
				b = true;

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
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * classifie les instances
	 */
	@Override
	public void doClassify() {
		// TODO Auto-generated method stub
		
	}	

	/**
	 * affiche les résultats
	 */
	@Override
	public void printResults() {
		String s="Attribut:\n\n";
		ArrayList<Integer> a =nbAttributs.get(attributs.size()-1);
		for(int i=0;i<attributs.size()-1;i++)
		{
			ArrayList<String> e =attributs.get(i);
			ArrayList<Integer> f =nbAttributs.get(i);
			ArrayList< ArrayList<Integer> > g =nbVal.get(i);
			for(int j=0;j<e.size();j++)
			{		
				s+=e.get(j)+" "+f.get(j)+" ";
				if(j!=e.size())
				{
					ArrayList<Integer> h =g.get(j);
					s+="( ";
					for(int k=0;k<h.size();k++)
					{
						s+=h.get(k)+"/"+a.get(k)+",";
					}
					s+=") - ";
				}
			}
			s+="\n";
		}
		ArrayList<String> e =attributs.get(attributs.size()-1);
		for(int j=0;j<e.size();j++)
		{		
			s+=e.get(j)+" "+a.get(j)+" ";
			s+=" - ";
		}
		s+="\n";		
		
		System.out.print(s);
		
	}

}
