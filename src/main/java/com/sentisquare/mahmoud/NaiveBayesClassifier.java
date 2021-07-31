package com.sentisquare.mahmoud;

import java.util.ArrayList;
import java.util.HashMap;

public class NaiveBayesClassifier {
    private final String label;

    public NaiveBayesClassifier(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    private static double calculateLikelihood(HashMap<String, Integer> labelTable, int totalLabel, String userInput) {
        double likelihoodValue = 0.0;
        String[] text = StringProcessor.filterDataFromString(userInput);
        int numberOfWords;
        for (String string : text) {
            numberOfWords = labelTable.getOrDefault(string, 1);
            likelihoodValue += ((double) numberOfWords / totalLabel);
        }
        return likelihoodValue;
    }

    private static double calculatePrior(int totalLabel, int trainingDataSize) {
        return (double) totalLabel / trainingDataSize;
    }

    private static String argMax(double positive, double negative, double neutral) {
        String maxProbability;
        if (positive >= negative && positive >= neutral) {
            maxProbability = "Positive";
        } else if (negative >= positive && negative >= neutral) {
            maxProbability = "Negative";
        } else {
            maxProbability = "Neutral";
        }
        return maxProbability;
    }

    public static String naiveBayesClassifier(String filePathOfTrainingData, String userInput) {
        ArrayList<ArrayList<String>> trainingDataWithLabel = StringProcessor.filterDataFromFile(filePathOfTrainingData, TrainingData.WithLabel);
        ArrayList<ArrayList<String>> trainingDataWithoutLabel = StringProcessor.filterDataFromFile(filePathOfTrainingData, TrainingData.WithoutLabel);
        int trainingDataSize = trainingDataWithLabel.size();
        int totalOfPositive = StringProcessor.countLabel(trainingDataWithLabel, Label.Positive);
        int totalOfNegative = StringProcessor.countLabel(trainingDataWithLabel, Label.Negative);
        int totalOfNeutral = StringProcessor.countLabel(trainingDataWithLabel, Label.Neutral);
        double positivePrior = calculatePrior(totalOfPositive, trainingDataSize);
        double negativePrior = calculatePrior(totalOfNegative, trainingDataSize);
        double neutralPrior = calculatePrior(totalOfNeutral, trainingDataSize);
        ArrayList<ArrayList<String>> positiveData = StringProcessor.getLabelData(trainingDataWithLabel, Label.Positive);
        ArrayList<ArrayList<String>> negativeData = StringProcessor.getLabelData(trainingDataWithLabel, Label.Negative);
        ArrayList<ArrayList<String>> neutralData = StringProcessor.getLabelData(trainingDataWithLabel, Label.Neutral);
        HashMap<String, Integer> occurrenceInPositive = StringProcessor.calculateOccurrenceInLabel(trainingDataWithoutLabel, positiveData);
        HashMap<String, Integer> occurrenceInNegative = StringProcessor.calculateOccurrenceInLabel(trainingDataWithoutLabel, negativeData);
        HashMap<String, Integer> occurrenceInNeutral = StringProcessor.calculateOccurrenceInLabel(trainingDataWithoutLabel, neutralData);
        double positiveLikelihood = calculateLikelihood(occurrenceInPositive, totalOfPositive, userInput);
        double negativeLikelihood = calculateLikelihood(occurrenceInNegative, totalOfNegative, userInput);
        double neutralLikelihood = calculateLikelihood(occurrenceInNeutral, totalOfNeutral, userInput);
        double valueOfPositive = positiveLikelihood * positivePrior;
        double valueOfNegative = negativeLikelihood * negativePrior;
        double valueOfNeutral = neutralLikelihood * neutralPrior;
        return argMax(valueOfPositive, valueOfNegative, valueOfNeutral);
    }
}