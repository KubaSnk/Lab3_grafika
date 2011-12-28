package windows;

import graphic3d.Point3D;
import graphic3d.Triangle3D;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.JDesktopPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenu mnPlik;
	JMenuItem mntmWczytaj;
	private JPanel panelOX;
	private JPanel panelOY;
	private JPanel panelOZ;
	private JPanel panelInterface;
	private Point oXCenter;
	private Point oYCenter;
	private Point oZCenter;
	private LinkedList<Point3D> verticesList;
	private LinkedList<Triangle3D> trianglesList;
	private Point panelXActualCursorPosition;
	private Point panelYActualCursorPosition;
	private Point panelZActualCursorPosition;

	// COLORS
	private Color panelsBgrnd = Color.BLACK;
	private Color verticesColor = Color.WHITE;
	private Color linesColor = Color.WHITE;
	private Color axisXColor = Color.BLUE;
	private Color axisYColor = Color.GREEN;
	private Color axisZColor = Color.RED;

	// SETUPS - LAYOUT
	private final int panelWidth = 600;
	private final int panelHeight = 350;
	private final int panelSpace = 5;
	private final int panelInterfaceHeight = 150;
	private double oXUnitSize = 40;
	private double oYUnitSize = 40;
	private double oZUnitSize = 40;
	private int verticeSize = 1;

	// SETUPS - OTHER
	private String defaultFilePath = "E:/Dropbox/Studia/Grafika/Sceny_opis_geometrii";

	// private int amountOfVertices;

	public MainWindow() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		getContentPane().setLayout(null);

		initializeVariables();
		initializeListeners();

	}

	private void initializeVariables() {
		
		setBounds(0, 0, panelWidth * 2 + panelSpace, panelHeight * 2
				+ panelSpace * 2 + panelInterfaceHeight + 40);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		panelOX = new JPanel();

		panelOX.setBackground(panelsBgrnd);
		panelOX.setBounds(0, 0, panelWidth, panelHeight);
		getContentPane().add(panelOX);
		panelOX.setLayout(null);

		panelOY = new JPanel();
		panelOY.setBackground(panelsBgrnd);
		panelOY.setBounds(panelWidth + panelSpace, 0, panelWidth, panelHeight);
		getContentPane().add(panelOY);

		panelOZ = new JPanel();
		panelOZ.setBackground(panelsBgrnd);
		panelOZ.setLayout(null);
		panelOZ.setBounds(0, panelHeight + panelSpace, panelWidth, panelHeight);
		getContentPane().add(panelOZ);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(panelsBgrnd);
		panel_3.setLayout(null);
		panel_3.setBounds(panelWidth + panelSpace, panelHeight + panelSpace,
				panelWidth, panelHeight);
		getContentPane().add(panel_3);

		panelInterface = new JPanel();
		panelInterface.setBorder(new TitledBorder(null, "Interface",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelInterface.setBounds(0, panelHeight * 2 + panelSpace,
				panelWidth * 2, panelInterfaceHeight);
		getContentPane().add(panelInterface);
		panelInterface.setLayout(null);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnPlik = new JMenu("Plik");
		menuBar.add(mnPlik);

		mntmWczytaj = new JMenuItem("Wczytaj (BRS)");
		mnPlik.add(mntmWczytaj);

		oXCenter = new Point(panelWidth / 2, panelHeight / 2);
		oYCenter = new Point(panelWidth / 2, panelHeight / 2);
		oZCenter = new Point(panelWidth / 2, panelHeight / 2);
	}

	private void initializeListeners() {

		mntmWczytaj.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				readFile();
			}
		});

		panelOX.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent arg0) {
				panelXActualCursorPosition = arg0.getPoint();
			}
		});
		panelOX.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				oXCenter.x = oXCenter.x + arg0.getX()
						- panelXActualCursorPosition.x;
				panelXActualCursorPosition.x = arg0.getX();
				oXCenter.y = oXCenter.y + arg0.getY()
						- panelXActualCursorPosition.y;
				panelXActualCursorPosition.y = arg0.getY();

				repaint();
			}
		});

		panelOX.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				oXUnitSize -= 3.5 * arg0.getWheelRotation();
				if (oXUnitSize < 0)
					oXUnitSize = 0;

				repaint();
			}
		});

		panelOY.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent arg0) {
				panelYActualCursorPosition = arg0.getPoint();
			}
		});
		panelOY.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				oYCenter.x = oYCenter.x + arg0.getX()
						- panelYActualCursorPosition.x;
				panelYActualCursorPosition.x = arg0.getX();
				oYCenter.y = oYCenter.y + arg0.getY()
						- panelYActualCursorPosition.y;
				panelYActualCursorPosition.y = arg0.getY();

				repaint();
			}
		});

		panelOY.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				oYUnitSize -= 3.5 * arg0.getWheelRotation();
				if (oYUnitSize < 0)
					oYUnitSize = 0;

				repaint();
			}
		});

		panelOZ.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent arg0) {
				panelZActualCursorPosition = arg0.getPoint();
			}
		});
		panelOZ.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				oZCenter.x = oZCenter.x + arg0.getX()
						- panelZActualCursorPosition.x;
				panelZActualCursorPosition.x = arg0.getX();
				oZCenter.y = oZCenter.y + arg0.getY()
						- panelZActualCursorPosition.y;
				panelZActualCursorPosition.y = arg0.getY();

				repaint();
			}
		});

		panelOZ.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				oZUnitSize -= 3.5 * arg0.getWheelRotation();
				if (oZUnitSize < 0)
					oZUnitSize = 0;

				repaint();
			}
		});

	}

	protected void readFile() {

		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(defaultFilePath));
		fc.showOpenDialog(null);
		if (fc.getSelectedFile() != null) {
			try {

				defaultFilePath = fc.getSelectedFile().getPath();
				// fieldPath.setText(fc.getSelectedFile().getPath());

				JOptionPane.showMessageDialog(rootPane, "Wczytano: "
						+ defaultFilePath);

				Scanner sc = new Scanner(new FileReader(new File(
						defaultFilePath)));
				// sc.skip("\\/*.\n");
				// sc.nextInt();
				// amountOfVertices = new
				// Scanner(sc.nextLine()).nextInt();

				verticesList = new LinkedList<Point3D>();
				trianglesList = new LinkedList<Triangle3D>();

				LinkedList<Double> tempList = new LinkedList<Double>();
				while (sc.hasNext()) {
					tempList.add(Double.parseDouble(sc.next()));
				}

				Iterator<Double> it = tempList.iterator();

				for (Double i = it.next(); i > 0; i--) {
					verticesList.add(new Point3D(it.next(), it.next(), it
							.next()));
				}

				for (Double i = it.next(); i > 0; i--) {
					trianglesList.add(new Triangle3D(verticesList
							.get((int) (Math.floor(it.next()))), verticesList
							.get((int) (Math.floor(it.next()))), verticesList
							.get((int) (Math.floor(it.next())))));
				}

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(rootPane,
						"Wyst¹pi³ b³¹d podczas wczytywania pliku!");
			}
			repaint();
		}
	}

	public void paint(Graphics g) {

		super.paint(g);

		// OX PANEL DRAWING
		drawProjection(panelOX.getGraphics(), axisZColor, axisYColor,
				oXUnitSize, oXCenter, 0);

		// OY PANEL DRAWING
		drawProjection(panelOY.getGraphics(), axisXColor, axisZColor,
				oYUnitSize, oYCenter, 1);

		// OZ PANEL DRAWING
		drawProjection(panelOZ.getGraphics(), axisXColor, axisYColor,
				oZUnitSize, oZCenter, 2);

	}

	private void drawProjection(Graphics g, Color horizontalAxisColor,
			Color verticalAxisColor, double unitSize, Point centerPoint,
			int option) {

		drawAxis(g, horizontalAxisColor, verticalAxisColor, centerPoint);

		if (trianglesList != null) {
			drawTriangles(g, option, unitSize, centerPoint);
		}

		if (verticesList != null) {
			drawVertices(g, option, unitSize, centerPoint);
		}

	}

	private void drawAxis(Graphics g, Color horizontalAxisColor,
			Color verticalAxisColor, Point centerPoint) {

		g.setColor(horizontalAxisColor);
		g.drawLine(0, (int) centerPoint.y, panelWidth, (int) centerPoint.y);
		g.setColor(verticalAxisColor);
		g.drawLine((int) centerPoint.getX(), 0, (int) centerPoint.getX(),
				panelHeight);

	}

	private void drawTriangles(Graphics g, int option, double unitSize,
			Point centerPoint) {

		g.setColor(linesColor);

		Iterator<Triangle3D> it = trianglesList.iterator();
		while (it.hasNext()) {
			Triangle3D triangle = it.next();

			Point2D.Double p1 = Point3D.transform3DPointTo2DDouble(
					triangle.getP1(), option);
			Point2D.Double p2 = Point3D.transform3DPointTo2DDouble(
					triangle.getP2(), option);
			Point2D.Double p3 = Point3D.transform3DPointTo2DDouble(
					triangle.getP3(), option);

			g.drawLine((int) (p1.x * unitSize + centerPoint.x),
					(int) (centerPoint.y - p1.y * unitSize), (int) (p2.x
							* unitSize + centerPoint.x),
					(int) (centerPoint.y - p2.y * unitSize));
			g.drawLine((int) (p1.x * unitSize + centerPoint.x),
					(int) (centerPoint.y - p1.y * unitSize), (int) (p3.x
							* unitSize + centerPoint.x),
					(int) (centerPoint.y - p3.y * unitSize));
			g.drawLine((int) (p3.x * unitSize + centerPoint.x),
					(int) (centerPoint.y - p3.y * unitSize), (int) (p2.x
							* unitSize + centerPoint.x),
					(int) (centerPoint.y - p2.y * unitSize));
		}

	}

	private void drawVertices(Graphics g, int param, double unitSize,
			Point centerPoint) {
		Iterator<Point3D> it = verticesList.iterator();
		g.setColor(verticesColor);
		while (it.hasNext()) {
			Point2D.Double p = Point3D.transform3DPointTo2DDouble(it.next(),
					param);
			g.fillRect((int) (p.x * unitSize + centerPoint.x),
					(int) (centerPoint.y - p.y * unitSize), verticeSize,
					verticeSize);
		}
	}

	private Point calculatePosition(double x, double y, Point centerPoint,
			double scale) {
		return new Point((int) (x * scale + centerPoint.x),
				(int) (centerPoint.y - y * scale));
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
