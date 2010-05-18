package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

/**
 * classe comportant des fonctions utiles...
 * @author remi
 *
 */
public class Util {
	
	/**
	 * buildBijection
	 * créé une bijection de [0, n-1] dans lui même
	 * @param seed le seed pour le générateur aléatoire
	 * @param n borne max de l'ensemble
	 * @return un tableau de longueur n contenant la bijection
	 *
	**/
	public static int[] buildBijection(int seed, int n) {
	
		Random gen = new Random(seed);
		
		int i, j, nbelem;
	
		int f[] = new int[n];
	
	
		ArrayList<Integer> lst_integer = new ArrayList<Integer>();
	
		for (i = 0 ; i < n ; i++)
		{
			lst_integer.add(i);
		}
	
		i = 0;
		for (nbelem = n ; nbelem > 0 ; nbelem--)
		{
			j = getRandomInt(gen, 0, nbelem-1);
	
			f[i] = lst_integer.get(j);
			
			lst_integer.set(j, lst_integer.get(nbelem-1));
			
			lst_integer.remove(lst_integer.size()-1);
				
			i++;
		}
		
		return f;
	}

	/**
	 * getRandomInt
	 * retourne un entier aléatoire entre binf et bsup, bornes comprises
	 * @param seed le seed pour le générateur aléatoire
	 * @param binf la borne inf
	 * @param bsup la borne sup
	 * @return un entier aléatoire entre binf et bsup, bornes comprises
	 */
	public static int getRandomInt(Random gen, int binf, int bsup) {
		return gen.nextInt(bsup-binf+1)+binf;
	}
	
	/**
	 * calcule l'index du max d'un tableau d'entiers
	 * @param vals le tableau d'entiers
	 * @return l'index du max du tableau
	 */
	public static int indexOfMax(double[] vals) {
		
		double maxVal = vals[0];
		int maxid = 0;
		
		for (int i = 1 ; i < vals.length ; i++) {
			if (vals[i] > maxVal) {
				maxVal = vals[i];
				maxid = i;
			}
		}
		return maxid;
	}
	
	/**
	 * pour arrondir un double en spécifiant le nombre de chiffres après la virgule
	 * @param val le double à arrondir
	 * @param numDecimales le nombre de décimale après la virgule voulu
	 * @return le double arrondi
	 */
	public static double printRounded(double val, int numDecimales) {
		val *= Math.pow(10, numDecimales);
		val = Math.floor(val+0.5);
		val /= Math.pow(10, numDecimales);
		return val;
	}
	
	/**
	 * méthode d'addition sécurisée pour les hashtable contenant des doubles
	 */
	public static void add(Hashtable<String, Double> table, String key, Double value) {
		if (table.get(key) == null) {
			table.put(key, value);
		} else {
			table.put(key, table.get(key)+value);
		}
	}

}
