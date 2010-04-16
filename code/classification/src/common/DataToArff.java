package common;

import java.io.File;
import java.io.Reader;

/**
 * Classe permettant de convertir un fichier de données en un fichier arff
 * @author remi
 *
 */
public class DataToArff {

	/**
	 * le fichier de données
	 */
	File file;
	
	/**
	 * constructeur par paramètres
	 * @param file le fichier de données
	 */
	public DataToArff(File file) {
		this.file = file;
	}

	/**
	 * construit un fichier arff à partir du fichier de données
	 * @return un Reader sur le fichier arff
	 */
	public Reader buildArffFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
