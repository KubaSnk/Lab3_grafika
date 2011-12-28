package windows;

import graphicTransformations.CustomMatrix;
import graphicTransformations.MoveMatrix;
import graphicTransformations.RotateMatrix;
import graphicTransformations.ScaleMatrix;
import graphicTransformations.TransformationMatrix;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class MainWindowOld extends JFrame {
	private static final long serialVersionUID = -2437884369165607125L;
	
	boolean vectorMode = false;
	
	PanelImage panelImage = new PanelImage();
	JPanel panelInterface = new JPanel();

	JTextField fieldPath = new JTextField();
	JButton rastrRead = new JButton("Wczytaj");
	JButton rastrSave = new JButton("Zapisz");
	JButton rastrClear = new JButton("Wyczyœæ");
	JButton btnRastrMode = new JButton("Rastrowa");
	JButton btnVectorMode = new JButton("Wektorowa");
	JButton btnFlipX = new JButton("Przerzuæ OX");
	JButton btnFlipY = new JButton("Przerzuæ OY");
	JButton btnScale = new JButton("Skaluj");
	JButton readMatrix= new JButton("Wczytaj macierz");
	JButton btnZlozenie= new JButton("Z³o¿enie");
	JLabel cbLabel  = new JLabel("Interpoluj");
	JButton btnReadFile = new JButton("Przegl¹daj");
	JTextField fieldScaleX = new JTextField("1.5");
	JTextField fieldScaleY = new JTextField("1.5");
	JTextField fieldRotateAngle = new JTextField("45");
	JButton btnRotate = new JButton("Obróæ");
	JCheckBox cbInterpolate = new JCheckBox();
	
	int tPositionX = 350;
	int tPositionY = 30;

	private String rastrPath = "E:/3.jpg";
	private String vectPath = "E:/vect.txt";
	private String matrixPath = "E:/matrix.txt";
	

	public MainWindowOld() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		setLayout(null);


		setSize(800, 740);
		setResizable(false);
		setTitle("Przekszta³canie grafika rastrowa i wektorowa - Zad2b - Jakub Wr¹bel");

		panelImage.setBounds(5, 5, 785, 500);
		panelInterface.setBounds(5, 510, 785, 200);
		
		panelImage.setBackground(Color.WHITE);
		panelInterface.setBorder(new TitledBorder("Panel interfejsu:"));
	
		
		

		rastrRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(vectorMode){
					Scanner sc = null;
					
					try {
						LinkedList<Line2D> tempList = new LinkedList<Line2D>();
						sc = new Scanner(new FileReader( new File(fieldPath.getText())));
						
						Double[] tempArray = new Double[4];
						int counter = 0;
						
						while(sc.hasNextInt()){
							tempArray[counter] = sc.nextDouble();
							
							if(counter == 3){
								counter = 0;
								tempList.add(new Line2D.Double(tempArray[0], tempArray[1], tempArray[2], tempArray[3]));
								tempArray = new Double[4];
							}
							else counter++;
						}
						System.out.println(tempList.size());
						panelImage.setPointsList(tempList);
						
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(rootPane,	e.getMessage());
						System.err.println("Wyst¹pi³ b³¹d podczas wczytywania pliku!");
					}
					
					repaint();	
				}
				else panelImage.readRastr(fieldPath.getText());
			}
		});
		rastrSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelImage.save(fieldPath.getText());
			}
		});
		rastrClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelImage.clear();
			}
		});
		btnFlipX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(vectorMode){
					panelImage.transformVector(new ScaleMatrix(1, -1));
				}
				else panelImage.transform(new ScaleMatrix(1, -1));
			}
		});
		btnFlipY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(vectorMode){
					panelImage.transformVector(new ScaleMatrix(-1,1));
				}
				else panelImage.transform(new ScaleMatrix(-1, 1));
			}
		});
		btnScale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(vectorMode){
					panelImage.transformVector(new ScaleMatrix(Double.parseDouble(fieldScaleX.getText()), Double.parseDouble(fieldScaleY.getText())));
				}
				else panelImage.transform(new ScaleMatrix(Double.parseDouble(fieldScaleX.getText()), Double.parseDouble(fieldScaleY.getText())));
			}
		});
		btnRotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(vectorMode){
					panelImage.transformVector(new RotateMatrix(Double.parseDouble(fieldRotateAngle.getText())));
				}
				else panelImage.transform(new RotateMatrix(Double.parseDouble(fieldRotateAngle.getText())));
			}
		});
		btnRastrMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vectorMode = false;
				fieldPath.setText(rastrPath);
				repaint();
			}
		});
		btnVectorMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vectorMode = true;
				fieldPath.setText(vectPath);
				repaint();
			}
		});
		readMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(vectorMode){
					panelImage.transformVector(panelImage.readMatrix());
				}
				else panelImage.transform(panelImage.readMatrix());;
				repaint();
			}
		});
		
					
		btnReadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(vectorMode?vectPath:rastrPath));
				fc.showOpenDialog(null);
				if(vectorMode) vectPath = fc.getSelectedFile().getPath();
				else rastrPath = fc.getSelectedFile().getPath();
				fieldPath.setText(fc.getSelectedFile().getPath());

			}
		});
		btnZlozenie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelImage.compound();
			}
		});

		panelInterface.setLayout(null);
		panelInterface.add(fieldPath);
		panelInterface.add(rastrRead);
		panelInterface.add(rastrSave);
		panelInterface.add(rastrClear);
		panelInterface.add(btnFlipX);
		panelInterface.add(btnFlipY);
		panelInterface.add(btnVectorMode);
		panelInterface.add(btnRastrMode);
		panelInterface.add(btnScale);
		panelInterface.add(fieldScaleX);
		panelInterface.add(fieldScaleY);
		panelInterface.add(fieldRotateAngle);
		panelInterface.add(btnRotate);
		panelInterface.add(cbInterpolate);
		panelInterface.add(btnReadFile);
		panelInterface.add(btnZlozenie);
		panelInterface.add(cbLabel);
		panelInterface.add(readMatrix);
		
		fieldPath.setBounds(150, 30, 150, 20);
		fieldPath.setText(rastrPath );
		btnReadFile.setBounds(150, 60, 150, 20);
		rastrRead.setBounds(150, 90, 150, 20);
		rastrSave.setBounds(150, 120, 150, 20);
		rastrClear.setBounds(150, 150, 150, 20);
		
		btnRastrMode.setBounds(20, 30, 100, 20);
		btnVectorMode.setBounds(20, 60, 100, 20);
		
		btnFlipX.setBounds(tPositionX,tPositionY, 150,20);
		btnFlipY.setBounds(tPositionX,tPositionY+30, 150,20);
		fieldScaleX.setBounds(tPositionX + 160,tPositionY+60, 30,20);
		fieldScaleY.setBounds(tPositionX + 200,tPositionY+60, 30,20);
		btnScale.setBounds(tPositionX,tPositionY+60, 150,20);
		fieldRotateAngle.setBounds(tPositionX + 160,tPositionY+90, 30,20);
		btnRotate.setBounds(tPositionX,tPositionY+90, 150,20);
		btnZlozenie.setBounds(tPositionX,tPositionY+120, 150,20);
		
		cbInterpolate.setBounds(600, 30, 20, 20);
		cbInterpolate.setSelected(true);
		cbLabel.setBounds(650, 30, 60, 20);
		readMatrix.setBounds(510, 150, 150, 20);
		
		getContentPane().add(panelImage);
		getContentPane().add(panelInterface);

	}

	class PanelImage extends JPanel {

		public class CompoundWindow {

			private JFrame frame;
			private JTextField arg1;
			private JTextField arg2;
			CountDownLatch loginSignal = new CountDownLatch(1);
			MainWindowOld window;
			JTextPane textPane;
			private LinkedList<TransformationMatrix> transformationsList;

			public CompoundWindow()  {
				
				initialize();
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
				}

				setTransformationsList(new LinkedList<TransformationMatrix>());
			}

			public void compound(boolean vectMode) {
				
			}

			/**
			 * Initialize the contents of the frame.
			 */
			private void initialize() {
				frame = new JFrame();
				frame.setBounds(100, 100, 465, 391);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(null);

				JButton btnNewButton = new JButton("Odbij OX");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						getTransformationsList().add(new ScaleMatrix(1, -1));
						textPane.setText(textPane.getText() + "Odbij OX\n");
					}
				});
				btnNewButton.setBounds(10, 11, 150, 32);
				frame.getContentPane().add(btnNewButton);

				JButton btnOdbijOy = new JButton("Odbij OY");
				btnOdbijOy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						getTransformationsList().add(new ScaleMatrix(-1, 1));
						textPane.setText(textPane.getText() + "Odbij OY\n");
					}
				});
				btnOdbijOy.setBounds(10, 54, 150, 32);
				frame.getContentPane().add(btnOdbijOy);

				JButton btnPrzesu = new JButton("Przesu\u0144");
				btnPrzesu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						getTransformationsList().add(
								new MoveMatrix(Double.parseDouble(arg1.getText()),
										Double.parseDouble(arg2.getText())));
						textPane.setText(textPane.getText() + "Przesuñ ["
								+ arg1.getText() + ", " + arg2.getText() + "]\n");
					}
				});
				btnPrzesu.setBounds(10, 97, 150, 32);
				frame.getContentPane().add(btnPrzesu);

				JButton btnSkaluj = new JButton("Skaluj");
				btnSkaluj.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						getTransformationsList().add(
								new ScaleMatrix(Double.parseDouble(arg1.getText()),
										Double.parseDouble(arg2.getText())));
						textPane.setText(textPane.getText() + "Skaluj ["
								+ arg1.getText() + ", " + arg2.getText() + "]\n");
					}
				});

				btnSkaluj.setBounds(10, 140, 150, 32);
				frame.getContentPane().add(btnSkaluj);

				JButton btnObr = new JButton("Obr\u00F3\u0107");
				btnObr.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						getTransformationsList().add(
								new RotateMatrix(Double.parseDouble(arg1.getText())));
						textPane.setText(textPane.getText() + "Obróæ o "
								+ arg1.getText() + " stopni \n");
					}
				});
				btnObr.setBounds(10, 183, 150, 32);
				frame.getContentPane().add(btnObr);

				arg1 = new JTextField("1");
				arg1.setBounds(20, 226, 53, 20);
				frame.getContentPane().add(arg1);
				arg1.setColumns(10);

				arg2 = new JTextField("1");
				arg2.setColumns(10);
				arg2.setBounds(83, 226, 53, 20);
				frame.getContentPane().add(arg2);

				JPanel panel = new JPanel();
				panel.setBorder(new TitledBorder(null, "Kolejka przekszta³ceñ",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.setBounds(174, 11, 256, 279);
				frame.getContentPane().add(panel);
				panel.setLayout(null);

				textPane = new JTextPane();
				textPane.setBounds(6, 16, 244, 274);
				panel.add(textPane);

				JButton btnNewButton_1 = new JButton("Wyczy\u015B\u0107");
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setTransformationsList(new LinkedList<TransformationMatrix>());
						textPane.setText("");
					}
				});
				btnNewButton_1.setBounds(10, 267, 89, 23);
				frame.getContentPane().add(btnNewButton_1);

				JButton btnNewButton_2 = new JButton("Wykonaj");
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(vectorMode)transformVector(transformationsList);
						else transform(transformationsList);
					}
				});
				btnNewButton_2.setBounds(10, 299, 420, 43);
				frame.getContentPane().add(btnNewButton_2);
			}

			public LinkedList<TransformationMatrix> getTransformationsList() {
				return transformationsList;
			}

			public void setTransformationsList(
					LinkedList<TransformationMatrix> transformationsList) {
				this.transformationsList = transformationsList;
			}
		}
		
			
		private static final long serialVersionUID = 1L;
		
		//////////////////////////////////////////////// RASTER PARAMETERS ///////////
		
		private Image imgRastr;
		private int scale = 10;
		private LinkedList<Line2D> pointsList = new LinkedList<Line2D>();
		
		public void transform(LinkedList<TransformationMatrix> ll){
			imgRastr = graphicTransformations.Main.transformate(imgRastr, ll, cbInterpolate.isSelected());
            repaint();
		}
		

		public void compound() {
			CompoundWindow cw = new CompoundWindow();
			cw.frame.setVisible(true);
			cw.frame.setUndecorated(true);
		}


		private TransformationMatrix readMatrix() {
			Scanner sc = null;
			
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(matrixPath));
			fc.showOpenDialog(null);
			
			try {
				sc = new Scanner(new FileReader( fc.getSelectedFile().getPath()));
				
				double[][] retMatrix = new double[3][3];

				
				retMatrix[0][0] = sc.nextDouble();
				retMatrix[0][1] = sc.nextDouble();
				retMatrix[0][2] = sc.nextDouble();
				retMatrix[1][0] = sc.nextDouble();
				retMatrix[1][1] = sc.nextDouble();
				retMatrix[1][2] = sc.nextDouble();
				retMatrix[2][0] = sc.nextDouble();
				retMatrix[2][1] = sc.nextDouble();
				retMatrix[2][2] = sc.nextDouble();
//				
				return new CustomMatrix(retMatrix);
				
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(rootPane,	e.getMessage());
				System.err.println("Wyst¹pi³ b³¹d podczas wczytywania pliku!");
			}
			return null;
		}

		public void transformVector(LinkedList<TransformationMatrix> ll){
			pointsList = graphicTransformations.Main.transformate(pointsList, ll);
			repaint();
		}
		
		public void transform(TransformationMatrix matrix){
			if(imgRastr!=null){
				LinkedList<TransformationMatrix> ll = new LinkedList<TransformationMatrix>();
				ll.add(matrix);
				transform(ll);
			}
		}
		
		
		public void transformVector(TransformationMatrix matrix){
			if(pointsList!=null){
				LinkedList<TransformationMatrix> ll = new LinkedList<TransformationMatrix>();
				ll.add(matrix);
				transformVector(ll);
			}
		}

		public PanelImage(){

        }

		public void readRastr(String path) {
			try {
				imgRastr = ImageIO.read(new File(path));
				repaint();
//				JOptionPane.showMessageDialog(rootPane, "Wczytano obraz (rastrowy):\n\t" + path);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(rootPane,	ex.getMessage());
				System.err.println("Wyst¹pi³ b³¹d podczas wczytywania pliku!");
			}
		}

		public void save(String path) {
			Image temp = new BufferedImage(imgRastr.getWidth(getRootPane()),
					imgRastr.getHeight(getRootPane()),
					BufferedImage.TYPE_INT_BGR);
			
			Graphics g = temp.getGraphics();
			paint(g);
			
			try {
				ImageIO.write((RenderedImage) temp, "JPG", new File(path));
				JOptionPane.showMessageDialog(rootPane, "Zapisano obraz (rastrowy) w:\n\t" + path);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(rootPane,	ex.getMessage());
				System.err.println("Wyst¹pi³ b³¹d podczas zapisywaniu pliku!");
			}
		}

		public void clear() {
			imgRastr = null;
			repaint();
		}

		public void paint(Graphics g) {
			super.paint(g);
			if(vectorMode){
				cbInterpolate.setVisible(false);
				cbLabel.setVisible(false);
				g.setColor(Color.BLACK);
				g.drawLine(393, 0, 393, 500);
				g.drawLine(0, 250, 785, 250);
				for(int i = 0; i < 500; i += scale){
					g.drawLine(390, i, 397, i);
				}
				for(int i = 0; i < 785; i += scale){
					g.drawLine(i+3, 247,i+3, 253);
				}
				
				Iterator<Line2D> it = pointsList.iterator();
				
				while(it.hasNext()){
					Line2D line = (Line2D) it.next().clone();
					line.setLine(393+line.getX1()*scale, 250-line.getY1()*scale , 393+line.getX2()*scale, 250- line.getY2()*scale);
					((Graphics2D)g).draw(line);
				}
			}
			else{
				cbInterpolate.setVisible(true);
				cbLabel.setVisible(true);
				if (imgRastr == null) {
					g.drawString("Nie wczytano obrazu", getWidth() / 2 - 30,
							getHeight() / 2);
				} else	g.drawImage(imgRastr, 0, 0, this);
			}
		}

		public void setPointsList(LinkedList<Line2D> pointsList) {
			this.pointsList = pointsList;
		}

	}
	

	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindowOld window = new MainWindowOld();

					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
