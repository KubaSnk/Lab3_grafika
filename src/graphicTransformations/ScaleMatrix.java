package graphicTransformations;

public class ScaleMatrix implements TransformationMatrix {

	public double[][] matrix = {
									{1,0,0},
									{0,1,0},
									{0,0,1}
								};;
	
	public ScaleMatrix(double d, double e){
		matrix[0][0] = d;
		matrix[1][1] = e;
	}
	public double[][] getMatrix() {
		return matrix;
	}

}
