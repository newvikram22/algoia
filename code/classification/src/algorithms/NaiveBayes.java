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
public class NaiveBayes implements IClassifier {
	
	private ArrayList< ArrayList<String> > attributs;
	private ArrayList< ArrayList<Integer> > nbAttributs;
	private ArrayList< ArrayList<ArrayList< Integer > > > nbVal;
	private ArrayList< ArrayList<ArrayList< Integer > > > nbClasse;
	/**
	 * lit les données et initialise l'algorithme
	 * @param inputFile le fichier comportant les données
	 * @throws IOException 
	 */
	@Override
	public void readData(String inputFile) throws IOException {
		attributs= new ArrayList< ArrayList<String> >();
		nbAttributs= new ArrayList< ArrayList<Integer> >();
		nbVal= new ArrayList< ArrayList<ArrayList< Integer > > >();
		nbClasse= new ArrayList< ArrayList<ArrayList< Integer > > >();
		
		BufferedReader ff= new BufferedReader(new FileReader(inputFile));
		String s=ff.readLine();
		String[] result = s.split(",");
		for(int i=0;i<result.length;i++)
		{
			ArrayList<String> e =new ArrayList<String>();
			e.add(result[i]);
			attributs.add(e);
			
			ArrayList<Integer> f =new ArrayList<Integer>();
			f.add(1);
			nbAttributs.add(f);
			if(i!=result.length)
			{
				ArrayList< ArrayList<Integer> > g =new ArrayList< ArrayList<Integer> >();
				g.add(f);
				nbVal.add(g);
				nbClasse.add(g);
			}
			//attributs.se//get(i);
		}
		
		
		
		//int n=Integer.parseInt(ff.readLine());
		while((s=ff.readLine()) != null)
		{
			result = s.split(",");
			for(int i=0;i<result.length-1;i++)
			{
				ArrayList<String> e =attributs.get(i);
				ArrayList<Integer> f =nbAttributs.get(i);
				int j=e.indexOf(result[i]);
				if(j<0)
				{
					e.add(result[i]);
					f.add(1);
					
					ArrayList< ArrayList<Integer> > g =nbVal.get(i);
					ArrayList<Integer> h =g.get(0);
					ArrayList< ArrayList<Integer> > gc =nbClasse.get(i);
					ArrayList<Integer> hc =g.get(0);
					for(int k=0;k<h.size();k++)
					{
						h.set(k,0);
						hc.set(k,0);
					}
					g.add(h);
					gc.add(hc);
					nbVal.set(i,g);
					nbClasse.set(i,gc);
					j=e.size();
				}
				else
				{
					f.set(j, f.get(j)+1);
				}
				attributs.set(i,e);
				nbAttributs.set(i,f);
				
				e =attributs.get(result.length-1);
				int m=e.indexOf(result.length-1);
				if(m<0)
				{
					f =nbAttributs.get(result.length-1);
					e.add(result[result.length-1]);
					f.add(1);
					attributs.set(result.length-1,e);
					nbAttributs.set(result.length-1,f);
					
					for(int k=0;k<result.length-1;k++)
					{
						ArrayList< ArrayList<Integer> > g =nbVal.get(k);
						ArrayList< ArrayList<Integer> > gc =nbClasse.get(k);
						for(int l=0;l<g.size();l++)
						{
							ArrayList<Integer> h=g.get(l);
							ArrayList<Integer> hc=gc.get(l);
							if(k==i && l==j)
							{
								h.add(1);
								hc.add(1);
							}
							else if(k==i)
							{
								h.add(0);
								hc.add(1);
							}
							else
							{
								h.add(0);
								hc.add(0);
							}
							g.set(l,h);
							gc.set(l,hc);
						}
						nbVal.set(k,g);
						nbClasse.set(k,gc);
					}
				}
				else
				{
					ArrayList< ArrayList<Integer> > g =nbVal.get(i);
					ArrayList< ArrayList<Integer> > gc =nbClasse.get(i);
					for(int l=0;l<g.size();l++)
					{
						ArrayList<Integer> h=g.get(l);
						ArrayList<Integer> hc=gc.get(l);
						if(l==j)
						{
							h.set(m,h.get(m)+1);
							hc.set(m,hc.get(m)+1);
						}
						else
						{
							hc.set(m,hc.get(m)+1);
						}
						g.set(l,h);
						gc.set(l,hc);
					}
					nbVal.set(i,g);
					nbClasse.set(i,gc);
				}
			}
		}		
	}
	
	/**
	 * classifie les instances
	 */
	@Override
	public void classify() {
		// TODO Auto-generated method stub
		
	}	

	/**
	 * affiche les résultats
	 */
	@Override
	public void printResults() {
		// TODO Auto-generated method stub
		
	}

}
