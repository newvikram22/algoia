import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import common.Result;
import common.Util;

import algorithms.C45;
import algorithms.NaiveBayesV2;



public class Test {
	
	
	private List<String> listDatasets;	

	private String dataFolder;
	
	private Double percentage;
	
	private List<Result> results;
	
	private Hashtable<String, Integer> classIndexes;
	
	private Hashtable<String, String> natureOfAttributes;
	
	private double meanC45;
	private double meanNaiveBayes;
	private double meanNaiveBayes2;
	
	private int numWinsC45;
	private int numWinsNaiveBayes;
	private int numWinsNaiveBayes2;
	
	private void init() {	
		
		initClassIndexes();
		results = new ArrayList<Result>();
		
		dataFolder = "data/";
		percentage = 66.;
		
		File folder = new File(dataFolder);
		
		listDatasets = new ArrayList<String>();
		for (File file : folder.listFiles()) {
			if (file.isFile() && (file.getName().contains(".data"))) {
				listDatasets.add(file.getPath());
			}
		}
	}	
	
	

	private void initClassIndexes() {

		classIndexes = new Hashtable<String, Integer>();
		natureOfAttributes = new Hashtable<String, String>();
		
		classIndexes.put("data/cancer.data", 10);
		classIndexes.put("data/car.data", 6);
		classIndexes.put("data/glass.data", 10);
		classIndexes.put("data/iris.data", 4);
		classIndexes.put("data/wine.data", 0);
		classIndexes.put("data/balance-scale.data", 0);
		classIndexes.put("data/tic-tac-toe.data", 9);
		classIndexes.put("data/kr-vs-kp.data", 36);
		classIndexes.put("data/hayes-roth.data", 5);
		classIndexes.put("data/promoters.data", 0);
		classIndexes.put("data/splice.data", 0);
		classIndexes.put("data/nursery.data", 7);
		classIndexes.put("data/krkopt.data", 6);
		classIndexes.put("data/cmc.data", 9);
		classIndexes.put("data/tae.data", 5);
		classIndexes.put("data/zoo.data", 17);
		classIndexes.put("data/transfusion.data", 4);
		
		natureOfAttributes.put("data/cancer.data", "num");
		natureOfAttributes.put("data/car.data", "mix");
		natureOfAttributes.put("data/glass.data", "num");
		natureOfAttributes.put("data/iris.data", "num");
		natureOfAttributes.put("data/wine.data", "num");
		natureOfAttributes.put("data/balance-scale.data", "num");
		natureOfAttributes.put("data/tic-tac-toe.data", "nom");
		natureOfAttributes.put("data/kr-vs-kp.data", "nom");
		natureOfAttributes.put("data/hayes-roth.data", "num");
		natureOfAttributes.put("data/nursery.data", "mix");
		natureOfAttributes.put("data/krkopt.data", "mix");
		natureOfAttributes.put("data/cmc.data", "num");
		natureOfAttributes.put("data/zoo.data", "mix");
		natureOfAttributes.put("data/transfusion.data", "num");

	
	}



	private void doTests() {
		int counter = 0;
		meanC45 = 0.;
		meanNaiveBayes = 0.;
		meanNaiveBayes2 = 0.;
		
		numWinsC45 = 0;
		numWinsNaiveBayes = 0;
		numWinsNaiveBayes2 = 0;
		
		for (String path : listDatasets) {
			
			boolean succed = true;
			
			C45 c45 = new C45();
			try{
				c45.setClassIndex(classIndexes.get(path));
				c45.readData(path, percentage);
				c45.classify();
			} catch (Exception e) {
				System.err.println("Error with C4.5 on file : "+path);
				e.printStackTrace();
				succed = false;
			}
			
			NaiveBayesV2 naiveBayes = new NaiveBayesV2();
			try{
				naiveBayes.setClassIndex(classIndexes.get(path));
				naiveBayes.setIntervalNumber(4);
				naiveBayes.setDiscretizationMethod("EWD");
				naiveBayes.readData(path, percentage);
				naiveBayes.classify();
			} catch (Exception e) {
				System.err.println("Error with NaiveBayes on file : "+path);
				e.printStackTrace();
				succed = false;
			}
			
			NaiveBayesV2 naiveBayes2 = new NaiveBayesV2();
			try{
				naiveBayes2.setClassIndex(classIndexes.get(path));
				naiveBayes2.readData(path, percentage);
				naiveBayes2.classify();
			} catch (Exception e) {
				System.err.println("Error with NaiveBayes on file : "+path);
				e.printStackTrace();
				succed = false;
			}
			
			if (succed) {
											
				counter++;
				
				Result result = new Result();
				result.dataset = (new File(path)).getName().replace(".data", "");
				result.errorC45 = c45.getPercentageIncorrect();
				result.errorNaiveBayes = naiveBayes.getPercentageIncorrect();
				result.errorNaiveBayes2 = naiveBayes2.getPercentageIncorrect();
				result.numAttributes = naiveBayes2.getNumAttributes();
				result.numClasses = naiveBayes2.getClasses().size();
				result.numInstances = naiveBayes2.getInstances().size();
				
				results.add(result);
				
				if (result.errorC45 < result.errorNaiveBayes) {
					if (result.errorC45 < result.errorNaiveBayes2) {
						numWinsC45++;
					}
					else{
						numWinsNaiveBayes2++;
					}
				} else {
					if (result.errorNaiveBayes < result.errorNaiveBayes2) {
						numWinsNaiveBayes++;
					}
					else{
						numWinsNaiveBayes2++;
					}
				}
				
				meanC45 += result.errorC45;
				meanNaiveBayes += result.errorNaiveBayes;
				meanNaiveBayes2 += result.errorNaiveBayes2;
			}	
		}
		
		meanC45 /= counter;
		meanNaiveBayes /= counter;
		meanNaiveBayes2 /= counter;
	}
	private void processTests() {
		init();
		
		doTests();
		
	}

	private String toLatexArray() {
		String str = "";

		str += "\\begin{tabular}{|c|c|c|c|c|c|c|c|}\n";
		str += "\\hline\n";
		str += "Dataset & num. instances & nature & num. attributes & num. classes & \\multicolumn{3}{c|}{ Classification error } \\\\\n";
		str += "\\cline{5-6}\n";
		str += "& & & & & C4.5 & Naive Bayes k=4 & Naive Bayes \\\\\n";
		str += "\\hline\n";
		
		for (Result result : results) {
			str += 	result.dataset+"&"+
					result.numInstances+"&"+
					natureOfAttributes.get(dataFolder+result.dataset+".data")+"&"+
					result.numAttributes+"&"+
					result.numClasses+"&"+
					Util.printRounded(result.errorC45, 3)+"\\%&"+
					Util.printRounded(result.errorNaiveBayes, 3)+"\\%&"+
					Util.printRounded(result.errorNaiveBayes2, 3)+"\\% \\\\\n";
			str += "\\hline\n";
		}
		
		str += "\\multicolumn{5}{|r|}{ Mean error } & "+Util.printRounded(meanC45, 3)+" & "+Util.printRounded(meanNaiveBayes, 3)+" & "+Util.printRounded(meanNaiveBayes2, 3)+"\\\\\n" ;
		str += "\\hline\n"	;
		
		str += "\\multicolumn{5}{|r|}{ num. wins } & "+numWinsC45+" & "+numWinsNaiveBayes+" & "+numWinsNaiveBayes2+"\\\\\n" ;
		str += "\\hline\n"	;
		
		
		str += "\\end{tabular}\n";

					
		
		return str;
	}

	
	public static void main(String[] args) {
		
		Test test = new Test();
		
		test.processTests();
		
		System.out.println(test.toLatexArray());
		
		
	}




}
