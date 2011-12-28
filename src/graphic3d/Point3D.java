package graphic3d;

import java.awt.geom.Point2D;

public class Point3D {
	private double x, y, z;

	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Transforms Point3D object to Point2D.Double object by omitting one coordinate
	 * @param p			Point3D object to transform 
	 * @param option	0 - (Z,Y), 1 - (X,Z), 2 - (X,Y)
	 * @return 			Point2D
	 */
	public static Point2D.Double transform3DPointTo2DDouble(Point3D p, int option) {
		
		switch (option) {
		case 0:
			return new Point2D.Double(p.getZ(), p.getY());
		case 1:
			return new Point2D.Double(p.getZ(), p.getX());
		case 2:
			return new Point2D.Double(p.getX(), p.getY());
		}
		
		return null;
	}
}
