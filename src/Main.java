import java.io.File;
import java.util.Arrays;
import java.util.Map;

public class Main {
    static void main() {
        File testDir = new File("data/test");
        File trainDir = new File("data/train");

        // reading files from directories
        Map<String,File> testFiles = TextProcesor.getFiles(testDir);
        Map<String,File> trainFiles = TextProcesor.getFiles(trainDir);

        //transforming data to strings
        Map<String,String> testSet = TextProcesor.transformFilesToStrings(testFiles);
        Map<String, String> trainSet = TextProcesor.transformFilesToStrings(trainFiles);

        //getting vector of letter distribution
        float[][] lettersDistInLang = new float[trainFiles.size()][26];
        for (int i = 0; i < lettersDistInLang.length; i++) {
            lettersDistInLang[i] =TextProcesor.extractFeatures(trainSet.get(trainSet.keySet().toArray()[i]));
        }

        // encoding lang labels
        int[][] langLanbEncoded =  new int[trainFiles.size()][trainFiles.size()];
        for (int i = 0; i < langLanbEncoded.length; i++) {
            for (int j = 0; j < langLanbEncoded[i].length; j++) {
                if (i==j){
                    langLanbEncoded[i][j]=1;
                }else {
                    langLanbEncoded[i][j]=0;
                }
            }
        }

        //prints encoded labels for cli interface
        for(int i=0;i<langLanbEncoded.length;i++){
            System.out.println(i+"\t"+ trainSet.keySet().toArray()[i] +"\t"+ Arrays.toString(langLanbEncoded[i]));
        }

        Layer layer = new Layer(trainSet.size(),0.1f,0.1f,26);

        for (int i=0 ; i<1000; i++){
            layer.train(lettersDistInLang,langLanbEncoded);
        }

        float[] netValues = layer.getNetValues(TextProcesor.extractFeatures(trainSet.get(trainSet.keySet().toArray()[2])));
        int[] classify = layer.classify(TextProcesor.extractFeatures(trainSet.get(trainSet.keySet().toArray()[2])));

        System.out.println("Net Values:" + Arrays.toString(netValues));
        System.out.println("Classify Values:" + Arrays.toString(classify));


    }

}
