public class Layer {
    Perceptron[] perceptrons;

    public Layer(int numbersOfPerceptrons, float bias, float learningRate, int dimension) {
        perceptrons = new Perceptron[numbersOfPerceptrons];
        for (int i = 0; i < numbersOfPerceptrons; i++) {
            perceptrons[i] = new Perceptron(bias, learningRate, dimension);
        }
    }

    public int[] classify(float[] inputs) {
        int[] outputs = new int[perceptrons.length];
        float[] nets = new float[perceptrons.length];
        for (int i = 0; i < perceptrons.length; i++) {
            nets[i] = perceptrons[i].getNet(inputs);
        }

        float max = Float.MIN_VALUE;
        for (int i = 0; i < perceptrons.length; i++) {
            if (nets[i] > max) {
                max = nets[i];
            }
        }

        for (int i = 0; i < perceptrons.length; i++) {
            if (nets[i] == max) outputs[i] = 1;
            else outputs[i] = 0;
        }

        return outputs;
    }

    public float[] getNetValues(float[] inputs) {
        float[] output = new float[perceptrons.length];
        for (int i = 0; i < perceptrons.length; i++) {
            output[i] = perceptrons[i].getNet(inputs);
        }
        return output;
    }

    public void train(float[] inputs, int[] outputs) {
        for (int i = 0; i < perceptrons.length; i++) {
            perceptrons[i].learn(inputs, outputs[i]);
        }
    }

    public void train(float[][] inputs, int[][] outputs) {
        for (int i = 0; i < inputs.length; i++) {
            train(inputs[i], outputs[i]);
        }
    }
}
