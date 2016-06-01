/**
 * Created by boris on 05.05.16.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LogisticRegression {

    /**
     * the learning rate
     */
    private double rate;

    /**
     * the weight to learn
     */
    private double[] weights;

    /**
     * the number of iterations
     */
    private int ITERATIONS = 3000;



    private String[] features = {
            "наук",
            "работ",
            "теор",
            "научн",
            "учен",
            "исследован",
            "институт",
            "университет",
            "творчеств",
            "литературн",
            "писател",
            "поэт",
            "произведен"
    };

    public LogisticRegression() {
        this.rate = 0.0001;
        weights = new double[features.length];
    }

    private static double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }

    public void train(List<Instance> instances) {
        for (int n = 0; n < ITERATIONS; n++) {
            double lik = 0.0;
            for (int i = 0; i < instances.size(); i++) {
                int[] x = instances.get(i).x;
                double predicted = classify(x);
                int label = instances.get(i).label;
                for (int j = 0; j < weights.length; j++) {
                    weights[j] = weights[j] + rate * (label - predicted) * x[j];
                }
                // not necessary for learning
                lik += label * Math.log(classify(x)) + (1 - label) * Math.log(1 - classify(x));
            }
            System.out.println("iteration: " + n + " " + Arrays.toString(weights) + " mle: " + lik);
        }
    }

    private double classify(int[] x) {
        double logit = .0;
        for (int i = 0; i < weights.length; i++) {
            logit += weights[i] * x[i];
        }
        return sigmoid(logit);
    }

    public static class Instance {
        public int label;
        public int[] x;

        public Instance(int label, int[] x) {
            this.label = label;
            this.x = x;
        }
    }
    public int[] findFeatures(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        DataReader dataReader = new DataReader();
        int[] data = new int[features.length];
        for (int i = 0; i <  data.length; i++ ){
            data[i] = 0;
        }
        while (scanner.hasNext()) {
            String token = dataReader.stem(dataReader.removePM(scanner.next())).toLowerCase();
            for (int i = 0; i < features.length ; i++) {
                if (token.equals(features[i])){
                    data[i] = 1;
                }
            }

        }
        return data;
    }
    public  List<Instance> readDataSet(File folder, int cl) throws FileNotFoundException {
        List<Instance> dataset = new ArrayList<Instance>();
        try {
            for (final File fileEntry : folder.listFiles()) {
                int data[] = findFeatures(fileEntry);
                int label = cl;
                Instance instance = new Instance(label, data);
                dataset.add(instance);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return dataset;
    }


    public static void main(String... args) throws FileNotFoundException {
        List<Instance> instances = null;
        LogisticRegression logistic = new LogisticRegression();
        try {
            instances = logistic.readDataSet(new File("/home/boris/task2/writers"),0);
            instances.addAll(logistic.readDataSet(new File("/home/boris/task2/scientists"), 1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logistic.train(instances);
        int[] x = logistic.findFeatures(new File("/home/boris/task2/scientists/1222.txt"));
        System.out.println("prob(учёный) = " + logistic.classify(x));
        System.out.println("prob(писатель) = " + (1-logistic.classify(x)));

    }

}