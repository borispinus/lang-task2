import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by boris on 04.05.16.
 */


public class BayesClassifier {

    public Feature[] getFeatures() {
        return features;
    }

    private Feature[] features = {
            new Feature("наук"),
            new Feature("работ"),
            new Feature("теор"),
            new Feature("научн"),
            new Feature("учен"),
            new Feature("исследован"),
            new Feature("институт"),
            new Feature("университет"),
            new Feature("творчеств"),
            new Feature("литературн"),
            new Feature("писател"),
            new Feature("поэт"),
            new Feature("произведен"),
    };


    public String classify(File file) {
        return "";
    }

    private double[] calculateProbability(final File folder) throws FileNotFoundException {
        int[] checkList = new int[features.length];
        double[] result = new double[features.length];
        boolean[] flagList = new boolean[features.length];
        for (int i = 0; i < features.length; i++) {
            checkList[i] = 0;
            flagList[i] = false;
        }
        for (final File fileEntry : folder.listFiles()) {
            Scanner scanner = new Scanner(fileEntry);
            for (int i = 0; i < flagList.length; i++) {
                flagList[i] = false;
            }
            DataReader dataReader = new DataReader();
            while (scanner.hasNext()) {
                String token = dataReader.stem(dataReader.removePM(scanner.next())).toLowerCase();
                for (int i = 0; i < features.length; i++) {
                    if (features[i].getString().equals(token)) {
                        flagList[i] = true;
                    }
                }
            }
            for (int j = 0; j < flagList.length; j++) {
                if (flagList[j]) {
                    checkList[j]++;
                }
            }
        }
        for (int j = 0; j < flagList.length; j++) {
            result[j] = (double) checkList[j] / folder.listFiles().length;
        }

        return result;

    }




    public static void main(String args[]) throws FileNotFoundException {
        File file = new File("/home/boris/task2/scientists");
        //new BayesClassifier().freqFeature(file);
        BayesClassifier bayesClassifier = new BayesClassifier();
        double pS[] = bayesClassifier.calculateProbability(new File("/home/boris/task2/scientists"));
        double pW[] = bayesClassifier.calculateProbability(new File("/home/boris/task2/writers"));
        for (int i = 0; i < bayesClassifier.features.length; i++) {
            bayesClassifier.features[i].setpScientist(pS[i]);
            bayesClassifier.features[i].setpWriter(pW[i]);
            // System.out.println(bayesClassifier.features[i].getString() + " pW = " + bayesClassifier.features[i].getpWriter() +
            // " pS = " + bayesClassifier.features[i].getpScientist());
        }

        File test = new File("/home/boris/task2/writers/18833.txt");
        Scanner scanner = new Scanner(test);
        double w = 1;
        double s = 1;
        DataReader dataReader = new DataReader();
        while (scanner.hasNext()) {
            String token = dataReader.stem(dataReader.removePM(scanner.next())).toLowerCase();
            for (int i = 0; i < bayesClassifier.features.length; i++) {
                if (token.equals(bayesClassifier.features[i].getString())) {
                    w *= bayesClassifier.features[i].getpWriter();
                    s *= bayesClassifier.features[i].getpScientist();
                }
            }
        }
        if (s > w){
            System.out.println("ученый");
        }
        else{
            System.out.println("писатель");
        }

    }
}
