/**
 * 
 */
package graphicTransformations;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {
	public static Image transformate(Image im,
			LinkedList<TransformationMatrix> matrixesList, boolean interpolate) {
		Iterator<TransformationMatrix> it = matrixesList.iterator();
		double[][] m = it.next().getMatrix();
		while (it.hasNext()) {
			try {
				m = ArraysOperations.multArrays(m, it.next().getMatrix());
			} catch (ArraysMultException e) {
				e.printStackTrace();
			}
		}
		BufferedImage bi = (BufferedImage) im;

		/* Wyznaczanie wielkoœci nowego obrazu */
		int xmin = new Integer(0);
		int xmax = new Integer(0);
		int ymin = new Integer(0);
		int ymax = new Integer(0);

		xmin = (int)returnTransformatedPoint(new Point(0, 0), m).getX();
		xmax = xmin;
		ymin = (int)returnTransformatedPoint(new Point(0, 0), m).getY();
		ymax = ymin;

		Point p = new Point(0, 0);
		p = (Point) returnTransformatedPoint(p, m);
		xmin = p.x;
		xmax = p.x;
		ymin = p.y;
		ymax = p.y;

		p = (Point) returnTransformatedPoint(new Point(0, im.getHeight(null) - 1), m);
		if (p.x < xmin)
			xmin = p.x;
		else if (p.x > xmax)
			xmax = p.x;
		if (p.y < ymin)
			ymin = p.y;
		else if (p.y > ymax)
			ymax = p.y;
		p =  (Point) returnTransformatedPoint(new Point(im.getWidth(null) - 1, 0), m);
		if (p.x < xmin)
			xmin = p.x;
		else if (p.x > xmax)
			xmax = p.x;
		if (p.y < ymin)
			ymin = p.y;
		else if (p.y > ymax)
			ymax = p.y;
		p = (Point) returnTransformatedPoint(
				new Point(im.getWidth(null) - 1, im.getHeight(null) - 1), m);
		if (p.x < xmin)
			xmin = p.x;
		else if (p.x > xmax)
			xmax = p.x;
		if (p.y < ymin)
			ymin = p.y;
		else if (p.y > ymax)
			ymax = p.y;

		BufferedImage newImage = new BufferedImage(xmax - xmin + 1, ymax - ymin
				+ 1, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < im.getWidth(null); x++) {
			for (int y = 0; y < im.getHeight(null); y++) {
				Point newPoint = (Point)returnTransformatedPoint(new Point(x, y), m);

				newImage.setRGB(newPoint.x - xmin, newPoint.y - ymin,
						bi.getRGB(x, y));
			}
		}

		if (interpolate) {
			double[][] reverseMatrix = MatrixOperations
					.wyznaczMacierzOdwrotna(m);

			for (int x = 0; x < newImage.getWidth(null); x++) {
				for (int y = 0; y < newImage.getHeight(null); y++) {
					if (newImage.getRGB(x, y) == -16777216) {
						Point2D point = returnDoubleTransformatedPoint(
								new Point(x + xmin, y + ymin), reverseMatrix);
						
						if(point.getX() >= 0 && point.getX() < bi.getWidth() && point.getY() >= 0 && point.getY() < bi.getHeight()){
							newImage.setRGB(x, y,
									interpolate(point.getX(), point.getY(), bi));
							
						}
					}

				}
			}
		}

		return newImage;
	}

	private static int interpolate(double x, double y, BufferedImage bi) {
		int xmin = (int) Math.floor(x);
		int ymin = (int) Math.floor(y);
		if(x<0) x=0;
		if(y<0) y=0;

		int color = 0;
		int r1 = 0;
		int g1 = 0;
		int b1 = 0;
		int r2 = 0;
		int g2 = 0;
		int b2 = 0;
		
//		System.out.println("X: " + x + " Y: " + y);
		Color x1y1 = new Color(bi.getRGB(xmin, ymin));

		if (xmin + 1 < bi.getWidth() && ymin + 1 < bi.getHeight()) {
			Color x1y2 = new Color(bi.getRGB(xmin, ymin + 1));
			Color x2y2 = new Color(bi.getRGB(xmin + 1, ymin + 1));
			Color x2y1 = new Color(bi.getRGB(xmin + 1, ymin));

			r1 = (int) (x1y1.getRed() * (xmin - x + 1) + x2y1.getRed()
					* (x - xmin));
			g1 = (int) (x1y1.getGreen() * (xmin - x + 1) + x2y1.getGreen()
					* (x - xmin));
			b1 = (int) (x1y1.getBlue() * (xmin - x + 1) + x2y1.getBlue()
					* (x - xmin));
			

			r2 = (int) (x1y2.getRed() * (xmin - x + 1) + x2y2.getRed()
					* (x - xmin));
			g2 = (int) (x1y2.getGreen() * (xmin - x + 1) + x2y2.getGreen()
					* (x - xmin));
			b2 = (int) (x1y2.getBlue() * (xmin - x + 1) + x2y2.getBlue()
					* (x - xmin));

			color = new Color((int) (r1 * (ymin - y + 1) + r2 * (y - ymin)),
					(int) (g1 * (ymin - y + 1) + g2 * (y - ymin)), (int) (b1
							* (ymin - y + 1) + b2 * (y - ymin))).getRGB();
		} else {
			if (xmin + 1 < bi.getWidth()) {
				Color x2y1 = new Color(bi.getRGB(xmin + 1, ymin));

				r1 = (int) (x1y1.getRed() * (xmin - x + 1) + x2y1.getRed()
						* (x - xmin));
				g1 = (int) (x1y1.getGreen() * (xmin - x + 1) + x2y1.getGreen()
						* (x - xmin));
				b1 = (int) (x1y1.getBlue() * (xmin - x + 1) + x2y1.getBlue()
						* (x - xmin));
				
				color = new Color(r1, g1, b1).getRGB();
			} else
				color = bi.getRGB(xmin, ymin);
		}

		return color;
	}

	public static Point2D returnTransformatedPoint(Point2D p, double[][] m) {
		double[][] tempArray = { { (double) p.getX(), (double) p.getY(), 1 } };
		try {
			tempArray = ArraysOperations.multArrays(tempArray, m);
		} catch (ArraysMultException e) {
			e.printStackTrace();
		}

		p.setLocation(tempArray[0][0] / tempArray[0][2], tempArray[0][1]
				/ tempArray[0][2]);
		return p;

	}

	public static Point2D returnDoubleTransformatedPoint(Point2D p, double[][] m) {
		double[][] tempArray = { { (double) p.getX(), (double) p.getY(), 1 } };
		try {
			tempArray = ArraysOperations.multArrays(tempArray, m);
		} catch (ArraysMultException e) {
			e.printStackTrace();
		}

		return new Point2D.Double(tempArray[0][0] / tempArray[0][2], tempArray[0][1] / tempArray[0][2]);
	}

	public static LinkedList<Line2D> transformate(LinkedList<Line2D> pointsList, LinkedList<TransformationMatrix> matrixesList) {
		Iterator<TransformationMatrix> it = matrixesList.iterator();
		double[][] m = it.next().getMatrix();
		while (it.hasNext()) {
			try {
				m = ArraysOperations.multArrays(m, it.next().getMatrix());
			} catch (ArraysMultException e) {
				e.printStackTrace();
			}
		}
		
		Iterator<Line2D> it2 = pointsList.iterator();
		LinkedList<Line2D> retList = new LinkedList<Line2D>();
		
		while(it2.hasNext()){
			Line2D line = it2.next();
			System.out.println("x: " + line.getX1() + " x2: " + line.getX2());
			System.out.println("Tx: " + returnDoubleTransformatedPoint(line.getP1(), m).getX() + " x2: " + returnDoubleTransformatedPoint(line.getP2(), m).getX());
			
			retList.add(new Line2D.Double( returnTransformatedPoint(line.getP1(), m), returnTransformatedPoint(line.getP2(), m)));
		}
		return retList;
	}

}
