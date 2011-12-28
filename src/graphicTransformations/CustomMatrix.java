package graphicTransformations;

public class CustomMatrix implements TransformationMatrix {

	public double[][] matrix = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };;

	public CustomMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public double[][] getMatrix() {
		return matrix;
	}

}
