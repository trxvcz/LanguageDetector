import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextProcesor {

    public static float[] extractFeatures(String text){
        float[] features = new float[26];
        text = text.toLowerCase();
        int totalLetters = 0;
        for(char c : text.toCharArray()){
            if(c >= 'a' && c <= 'z'){
                features[c - 'a']++;
                totalLetters++;
            }
        }

        if(totalLetters > 0){
            for(int i = 0; i < features.length; i++){
                features[i] /= totalLetters;
            }
        }

        return features;
    }

    public static String readFileToString(File file) {
        try (Scanner sc = new Scanner(file)) {
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) sb.append(sc.nextLine());
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        }
    }


    public static Map<File, String> getFiles(File dir) {
        File[] files = dir.listFiles();
        Map<File, String> map = new LinkedHashMap<>();

        if (files == null) return map;

        for (File file : files) {
            if (file.isFile()) {
                map.put(file, dir.getName());
            } else if (file.isDirectory()) {
                map.putAll(getFiles(file));
            }
        }
        return map;
    }


}
