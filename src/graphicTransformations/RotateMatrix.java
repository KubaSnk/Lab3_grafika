package graphicTransformations;

public class RotateMatrix implements TransformationMatrix {

	public double[][] matrix = {
									{1,0,0},
									{0,1,0},
									{0,0,1}
								};;
	
	public RotateMatrix(double angle){
		matrix[0][0] = Math.cos(angle*Math.PI/180);
		matrix[1][1] = Math.cos(angle*Math.PI/180);
		matrix[0][1] = -Math.sin(angle*Math.PI/180);
		matrix[1][0] = Math.sin(angle*Math.PI/180);
		
	}
	public double[][] getMatrix() {
		return matrix;
	}

}
