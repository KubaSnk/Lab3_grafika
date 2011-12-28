package graphic3d;

public class Triangle3D {
	
	public Triangle3D(Point3D p1, Point3D p2, Point3D p3) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	private Point3D p1, p2, p3;

	public Point3D getP1() {
		return p1;
	}

	public void setP1(Point3D p1) {
		this.p1 = p1;
	}

	public Point3D getP2() {
		return p2;
	}

	public void setP2(Point3D p2) {
		this.p2 = p2;
	}

	public Point3D getP3() {
		return p3;
	}

	public void setP3(Point3D p3) {
		this.p3 = p3;
	}
	
}
