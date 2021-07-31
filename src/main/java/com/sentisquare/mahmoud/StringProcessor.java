package com.sentisquare.mahmoud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.sentisquare.mahmoud.Label.Positive;

enum Label {
    Positive,
    Negative,
    Neutral
}

enum TrainingData {
    WithLabel,
    WithoutLabel
}

public class StringProcessor {
    public static ArrayList<ArrayList<String>> filterDataFromFile(String filePath, TrainingData options) {

        ArrayList<ArrayList<String>> table = new ArrayList<>();
        File file = new File(filePath);

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String fileData;

            while ((fileData = bufferedReader.readLine()) != null) {
                String[] splattedData = fileData.split("[\\p{P}\\s]+");
                ArrayList<String> splitter;
                if (options == TrainingData.WithoutLabel) {
                    splitter = new ArrayList<>(Arrays.asList(splattedData).subList(1, splattedData.length));
                } else {
                    splitter = new ArrayList<>(Arrays.asList(splattedData));
                }
                table.add(splitter);
            }
            table.remove(0);
            bufferedReader.close();
            inputStreamReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("File does not exist!");
        }
        return table;
    }

    public static String[] filterDataFromString(String text) {

        return text.split("[\\p{P}\\s]+");

    }

    public static int countLabel(ArrayList<ArrayList<String>> labelData, Label options) {
        int labelCounter = 0;
        String string = switch (options) {
            case Positive -> "Positive";
            case Negative -> "Negative";
            case Neutral -> "Neutral";
            default -> null;
        };
        if (string != null) {
            assert labelData != null;
            for (ArrayList<String> label : labelData) {
                if (label.get(0).equals(string)) {
                    labelCounter++;
                }
            }
        } else {
            System.out.println("kindly insert the right data into the function");
        }
        return labelCounter;
    }

    public static ArrayList<ArrayList<String>> getLabelData(ArrayList<ArrayList<String>> data, Label options) {
        ArrayList<ArrayList<String>> labelData = new ArrayList<>();
        String word = switch (options) {
            case Positive -> "Positive";
            case Negative -> "Negative";
            case Neutral -> "Neutral";
            default -> null;
        };
        if (word != null) {
            assert data != null;
            for (ArrayList<String> label : data) {

                if (label.get(0).equals(word)) {
                    label.remove(0);
                    labelData.add(label);
                }
            }
        } else {
            System.out.println("kindly insert the right data into the function");
        }
        return labelData;
    }

    private static int amountOfWordInLabel(ArrayList<ArrayList<String>> labelData, String word) {
        int wordCounter = 1;
        if (labelData != null) {
            for (ArrayList<String> label : labelData) {

                if (label.contains(word)) {
                    wordCounter++;
                }
            }
        } else {
            System.out.println("kindly insert the right labelData into the function");
        }
        return wordCounter;
    }

    public static HashMap<String, Integer> calculateOccurrenceInLabel(ArrayList<ArrayList<String>> dataWithoutLabel, ArrayList<ArrayList<String>> labelData) {
        HashMap<String, Integer> table = new HashMap<>();

        if (dataWithoutLabel != null) {
            for (ArrayList<String> label : dataWithoutLabel) {

                for (String word : label)
                    if (!table.containsKey(word)) {
                        table.put(word, amountOfWordInLabel(labelData, word));
                    }
            }
        } else {
            System.out.println("kindly insert the right data into the function");
        }
        return table;
    }
}
