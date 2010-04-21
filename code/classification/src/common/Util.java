package common;

import java.util.ArrayList;
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

}
