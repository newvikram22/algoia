import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Result;
import common.Util;

import algorithms.AbstractClassifier;
import algorithms.C45;
import algorithms.NaiveBayesV2;


public class Test {
	
	
	private List<String> listDatasets;	

	private String dataFolder;
	
	private Double percentage;
	
	private List<Result> results;
	
	
	private void init() {		
		results = new ArrayList<Result>();
		
		dataFolder = "data/";
		percentage = 66.;
		
		File folder = new File(dataFolder);
		
		listDatasets = new ArrayList<String>();
		for (File file : folder.listFiles()) {
			System.out.println(file.getPath());
			if (file.isFile() && (file.getName().contains(".data"))) {
				listDatasets.add(file.getPath());
			}
		}
	}	
	
	

	private void doTests() {
		for (String path : listDatasets) {
			C45 c45 = new C45();
			c45.readData(path, percentage);
			c45.classify();
			
			NaiveBayesV2 naiveBayes = new NaiveBayesV2();
			naiveBayes.readData(path, percentage);
			naiveBayes.classify();
			
			Result result = new Result();
			result.dataset = (new File(path)).getName().replace(".data", "");
			result.errorC45 = c45.getPercentageIncorrect();
			result.errorNaiveBayes = naiveBayes.getPercentageIncorrect();
			result.numAttributes = naiveBayes.getNumAttributes();
			result.numClasses = naiveBayes.getClasses().size();
			result.numInstances = naiveBayes.getInstances().size();
			
			results.add(result);
			
		}
		
	}
	
	private void processTests() {
		init();
		
		doTests();
		
	}

	private String toLatexArray() {
		String str = "";

		str += "\\begin{tabular}{|c|c|c|c|c|c|}\n";
		str += "\\hline\n";
		str += "Dataset & numInstances & numAttributes & numClasses & \\multicolumn{2}{c|}{ Classification error } \\\\\n";
		str += "\\cline{5-6}\n";
		str += "& & & & C4.5 & Naive Bayes \\\\\n";
		str += "\\hline\n";
		
		for (Result result : results) {
			str += 	result.dataset+"&"+
					result.numInstances+"&"+
					result.numAttributes+"&"+
					result.numClasses+"&"+
					Util.printRounded(result.errorC45, 3)+"\\%&"+
					Util.printRounded(result.errorNaiveBayes, 3)+"\\% \\\\\n";
			str += "\\hline\n";
		}
		str += "\\end{tabular}\n";
		
					
		
		return str;
	}

	
	public static void main(String[] args) {
		
		Test test = new Test();
		
		test.processTests();
		
		System.out.println(test.toLatexArray());
		
		
	}




}
