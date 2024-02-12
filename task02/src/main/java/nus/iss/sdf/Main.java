package nus.iss.sdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Main 
{
    public static void main( String[] args )
    {   
        String directory = args[0];

        List<String> texts = new ArrayList<>();

        File dir = new File(directory);
        ArrayList<String> filePaths = getFilePathsRecursive(dir);

        for (String file : filePaths) {
            //System.out.println(file);
            texts.add(getText(file));
        }

        HashMap<List<String>,Integer> uniquePairs = new HashMap<>();
        HashMap<String, Integer> uniqueFirstWords = new HashMap<>();
        for (String content : texts) {
            uniquePairs.clear();
            uniqueFirstWords.clear();
            uniquePairs = getUniquePairs(content);
            uniqueFirstWords = getUniqueFirstWords(uniquePairs);

            for (String s: uniqueFirstWords.keySet()) {
                System.out.println(s);
                for(List<String> unique : uniquePairs.keySet()) {
                    if (s.equals(unique.get(0))) {
                        float distribution = (float)(uniquePairs.get(unique))/(float)(uniqueFirstWords.get(s));
                        System.out.println("\t" + unique.get(1) + " " + distribution);
                    }
                }
            }
            System.out.println("\n------------------------LINE BREAK-------------------------");
        }
    }

    // public static List<Path> getFilePaths(String directory) {
    //     List<Path> filePaths = new ArrayList<>();
    //     try {
    //         Path dirPath = Paths.get(directory);
    //         Files.walk(dirPath)
    //             .filter(Files::isRegularFile)
    //             .forEach(filePaths::add);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return filePaths;
    // }

    //File dir = new File(directory);
    public static ArrayList<String> getFilePathsRecursive(File dir) {
        ArrayList<String> filePaths = new ArrayList<>();
        File[] files = dir.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    filePaths.addAll(getFilePathsRecursive(f));
                } else {
                    filePaths.add(f.getPath());
                    //System.out.println("File path: " + f.getPath());
                }
            }
        }
        return filePaths;
    }
    

    public static String getText(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static HashMap<List<String>, Integer> getUniquePairs(String content) {
        String newContent = content.replaceAll("[^a-zA-Z ]", "").toLowerCase();
        String[] newContentArr = newContent.split(" ");
        //System.out.println(Arrays.toString(newContentArr));

        HashMap<List<String>, Integer> uniquePairs = new HashMap<>();
        for (int k = 0; k < newContentArr.length - 1; k++) {
            List<String> pairs = new ArrayList<>();
            pairs.add(newContentArr[k]);
            pairs.add(newContentArr[k+1]);

            if (uniquePairs.containsKey(pairs)) {
                uniquePairs.put(pairs, (uniquePairs.get(pairs) + 1));
            } else {
                uniquePairs.put(pairs, 1);
            }
        }
        return uniquePairs;
    }

    public static HashMap<String, Integer> getUniqueFirstWords(HashMap<List<String>,Integer> uniquePairs) {
        HashMap<String, Integer> uniqueFirstWords = new HashMap<>();
        for (List<String> unique : uniquePairs.keySet()) {
            //System.out.println(uniquePairs.get(unique) + "\t" + unique);
            //System.out.println(i.get(0));
            String firstWord = unique.get(0);
            if(uniqueFirstWords.containsKey(firstWord)) {
                uniqueFirstWords.put(firstWord, (uniqueFirstWords.get(firstWord) + uniquePairs.get(unique)));
            } else {
                uniqueFirstWords.put(firstWord, uniquePairs.get(unique));
            }
        }
        return uniqueFirstWords;
    }



}
