import java.io.File;
import java.util.*;

public class Main {
    static void main() {
        File trainDir = new File("data/train");

        // reading files from directories
        Map<File, String> trainFiles = TextProcesor.getFiles(trainDir);

        List<String> languages = new ArrayList<>(new HashSet<>(trainFiles.values()));
        int numLanguages = languages.size();
        System.out.println("Wykryte języki: " + languages);

        //getting vector of letter distribution
        float[][] trainInputs = new float[trainFiles.size()][26];
        int[][] trainLabels = new int[trainFiles.size()][numLanguages];

        int index = 0;
        for (Map.Entry<File,String> entry : trainFiles.entrySet()) {
            String content = TextProcesor.readFileToString(entry.getKey());
            trainInputs[index] = TextProcesor.extractFeatures(content);

            int langIndex = languages.indexOf(entry.getValue());
            trainLabels[index][langIndex] = 1;

            index++;
        }


        Layer layer = new Layer(numLanguages,0.1f,0.1f,26);

        for (int i=0 ; i<50000; i++){
            layer.train(trainInputs,trainLabels);
        }

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("Wpisz zdanie: ");
            String input = scanner.nextLine();
            if (input.equals("exit")){
                break;
            }
            float[] features = TextProcesor.extractFeatures(input);
            int[] classification = layer.classify(features);

            for (int i = 0; i < classification.length; i++) {
                if (classification[i] == 1) {
                    System.out.println("Rozpoznany język: " + languages.get(i));
                }
            }

        }
        scanner.close();
    }


}
