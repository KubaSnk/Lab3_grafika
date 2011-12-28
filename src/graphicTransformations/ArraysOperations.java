package graphicTransformations;

public class ArraysOperations {
	public static double[][] multArrays(double[][] array1, double[][] array2) throws ArraysMultException{
		if(array1[0].length != array2.length) throw new ArraysMultException();
		
		double[][] result = new double[array1.length][array2[0].length];
		for(int y=0; y < array1.length; y++){
			for(int x = 0; x < array2[0].length; x++){
				result[y][x] = 0;
				for(int i = 0; i < array1[0].length; i++){
					result[y][x] += array1[y][i] * array2[i][x];
				}
			}
		}
		
		return result;
		
	}
//	
//	public static void main(String[] args){
//		double[][] a1 = {{1,2,3},{3,4,1}};
//		double[][] a2 = {{1,2},{1,2},{2,1}};
//		
//		for(int x = 0; x<2;x++){
//			for(int y = 0; y <3; y++){
//					System.out.print("[" + a1[x][y] +"]");
//			}
//			System.out.print("\n");
//		}
//		System.out.print("----------------------------\n");
//		for(int x = 0; x<3;x++){
//			for(int y = 0; y <2; y++){
//				System.out.print("[" + a2[x][y] +"]");
//			}
//			System.out.print("\n");
//		}
//		System.out.print("----------------------------\n");
//		
//		for(int x = 0; x<2;x++){
//			for(int y = 0; y <2; y++){
//				try {
//					System.out.print("[" + multArrays(a1, a2)[x][y] +"]");
//				} catch (ArraysMultException e) {
//					e.printStackTrace();
//				} 
//			}
//			System.out.print("\n");
//		}
//		
//		}
}
