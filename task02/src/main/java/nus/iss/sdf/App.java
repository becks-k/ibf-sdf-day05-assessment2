package nus.iss.sdf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
         StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader("texts\\frost\\frost.txt"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        String content = sb.toString();
        System.out.println(content);
    }
}
