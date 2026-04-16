import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
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

    public static Map<String,String> transformFilesToStrings(Map<String, File> map) {
        Map<String,String> set = new HashMap<>();
        for (String fileName : map.keySet()) {
            File trainFile = map.get(fileName);
            try {
                Scanner sc = new Scanner(trainFile);
                StringBuilder text = new StringBuilder();
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    text.append(line);

                }
                sc.close();
                set.put(fileName, text.toString());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return set;
    }


    public static Map<String,File> getFiles(File dir){
        File[] files = dir.listFiles();
        Map<String,File> map = new HashMap<>();

        if (files == null) {
            System.err.println("Directory not found or inaccessible: " + dir.getAbsolutePath());
            return map;
        }
        for (File file :files) {
            if (file.isFile()) {
                map.put(dir.getName(), file);
            }else if (file.isDirectory()) {
                Map<String, File> temp = getFiles(file);
                for (String key : temp.keySet()) {
                    map.put(key, temp.get(key));
                }
            }
        }
        return map;
    }

}
