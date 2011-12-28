package graphicTransformations;

public class MoveMatrix implements TransformationMatrix {

	public double[][] matrix = {
									{1,0,0},
									{0,1,0},
									{0,0,1}
								};;
	
	public MoveMatrix(int x, int y){
		matrix[2][0] = x;
		matrix[2][1] = y;
	}
	public MoveMatrix(double x, double y){
		matrix[2][0] = x;
		matrix[2][1] = y;
	}
	public double[][] getMatrix() {
		return matrix;
	}

}
