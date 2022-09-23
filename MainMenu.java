import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;
import javax.swing.plaf.basic.*;

public class MainMenu extends JFrame {

	// Menu Panel
	private JPanel MenuPane;
	public static Panel Panel;

	// Transformation
	private static JButton jbTranslate;
	private static JButton jbRotate;
	private static JButton jbScale;
	private static JButton jbShear;
	private static JButton jbReflection;
	private static JButton jbFill;
	private static JButton jbClear;

	// Clipping
	public static JCheckBox jClipping;
	public static JLabel jlClipping, jlCxmin, jlCxmax, jlCymin, jlCymax;
	public static JSpinner SpinCxmin, SpinCxmax, SpinCymin, SpinCymax;
	public static double Cxmin, Cymin, Cxmax, Cymax;
	public static boolean clear = false;
	public static JSlider jsAngle;
	public static JSpinner jsTx;

	public static JSpinner tScale;
	public static JSlider Sides;

	public static JSpinner trSpinX;
	public static JSpinner trSpinY;
	public static JSpinner ScSpinX;
	public static JSpinner ScSpinY;
	public static JComboBox Axis, FillAlgo, PolyShapesChoice;
	public static JSpinner Rotx;
	public static JSpinner Roty;
	public static JSpinner ptScX;
	public static JSpinner ptScY;
	public static JSpinner SpinAngle;
	public static JSpinner tSides;
	public static JSpinner ShSpinX;
	public static JSpinner ShSpinY;

	private JLabel jlTranslate;
	private JLabel jlScale_Ins;
	private JLabel jlShear, jlReflection;

	// Colour
	public static Color BackgroundColour = new Color(204, 153, 102);// pale brown
	public static Color Label1 = new Color(235, 244, 250);
	public static Color PanelColour = new Color(231, 208, 176);// light brown
	public static Color ButtonColour = new Color(155, 118, 84);// brown
	public static Color ButtonBorder1 = new Color(170, 183, 184);
	public static Color ButtonBorder2 = new Color(170, 183, 184);
	public static Color ClearButton = new Color(191, 201, 202);// grey
	public static Color SpinnerBackground = new Color(0, 0, 0);// black
	public static Color DrawingCoordinate = new Color(0, 100, 0);// Dark Green
	public static Color InitialCoordinate = new Color(40, 180, 99);// Green
	public static Color ConfirmCoordinate = new Color(3, 37, 126);// Dark blue

	public static Border Border1 = BorderFactory.createLineBorder(ButtonBorder1, 2);// Button Border
	public static Border Border2 = BorderFactory.createLineBorder(ButtonBorder2, 2);

	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	public static int w = gd.getDisplayMode().getWidth();
	public static int h = gd.getDisplayMode().getHeight() - 40;
	JSplitPane body = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	public MainMenu() {
		Menu();
		Panel();
		this.add(body);
		this.setTitle("GRAPHICS USER INTERFACE ASSIGNME NT 2");
		this.setSize(w, h);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		this.setExtendedState(MAXIMIZED_BOTH);
		JOptionPane.showMessageDialog(null,
				"NOTE:\n1. Use your mouse(left click) to draw each point of the shape.\n2. Use Right Click to confirm the object. "
						+ "\n3. Use the menu on the left side for the different functions.");
	}

	private void Panel() {
		Panel = new Panel();
		FlowLayout flowLayout = (FlowLayout) Panel.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		Panel.setBorder(null);
		body.setBottomComponent(Panel);
	}

	public void clear2() {
		this.removeAll();
		this.dispose();

		MainMenu x = new MainMenu();
		x.setResizable(true);
	}

	public void clear() {

		this.setClear(false);
	}

	public void setClear(boolean c) {
		clear = c;// Use in the objects
	}

	public boolean getClear() {
		return clear;
	}

	private static class MySliderUI extends BasicSliderUI {

		private static float[] fracs = { 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f };
		private LinearGradientPaint p;
		Color thumbColor;

		public MySliderUI(JSlider s, Color tColor) {
			super(s);
			thumbColor = tColor;
		}

		public void paint(Graphics g, JComponent c) {
			recalculateIfInsetsChanged();
			recalculateIfOrientationChanged();
			Rectangle clip = g.getClipBounds();

			if (slider.getPaintTrack() && clip.intersects(trackRect)) {
				paintTrack(g);
			}
			if (slider.getPaintTicks() && clip.intersects(tickRect)) {
				paintTicks(g);
			}
			if (slider.getPaintLabels() && clip.intersects(labelRect)) {
				paintLabels(g);
			}
			if (slider.hasFocus() && clip.intersects(focusRect)) {
				paintFocus(g);
			}
			if (clip.intersects(thumbRect)) {
				Color savedColor = slider.getBackground();
				slider.setBackground(thumbColor);
				paintThumb(g);
				slider.setBackground(savedColor);
			}
		}

		@Override
		public void paintTrack(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Rectangle t = trackRect;
			Point2D start = new Point2D.Float(t.x, t.y);
			Point2D end = new Point2D.Float(t.width, t.height);
			Color[] colors = { Color.magenta, Color.blue, Color.cyan,
					Color.green, Color.yellow, Color.red };

			// ... TrashGod's code ...

			// calculate how much of the progress bar to fill
			double percentage = (double) slider.getValue() / (double) slider.getMaximum();

			// PAINT THE BACKGROUND
			// create a gradient paint for the background
			p = new LinearGradientPaint(start, end, new float[] { 0.4f, 0.8f },
					new Color[] { Color.gray.brighter(), Color.gray.brighter().brighter() });
			g2d.setPaint(p);
			g2d.fillRoundRect((int) (t.width * percentage), t.y, t.width - (int) (t.width * percentage), t.height / 2,
					4, 4);

			// PAINT THE FOREGROUND
			// create the gradient paint
			p = new LinearGradientPaint(start, end, fracs, colors);
			g2d.setPaint(p);

			// fill the progress bar with a rectange of that size, (with curved corners of
			// 4px diameter)
			g2d.fillRoundRect(t.x, t.y, (int) (t.width * percentage), t.height / 2, 4, 4);
		}

	}

	public JFrame addTranslationMenu() {
		JFrame f = new JFrame();
		f.setSize(220, 200);
		f.setLocationRelativeTo(null);
		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(BackgroundColour);
		f.setVisible(true);
		jlTranslate = new JLabel("TRANSLATION", SwingConstants.CENTER);

		jlTranslate.setForeground(Label1);
		jlTranslate.setFont(new Font("Bodoni", Font.BOLD, 16));
		jlTranslate.setBounds(0, 30, 200, 15);
		f.add(jlTranslate);

		JLabel jlTx = new JLabel("X:");
		jlTx.setForeground(Label1);
		jlTx.setFont(new Font("VERDANA", Font.BOLD, 14));
		jlTx.setBounds(65, 60, 20, 25);
		f.add(jlTx);

		trSpinX = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));// To increase or decrease
		trSpinX.setBounds(85, 60, 50, 25);
		trSpinX.setBackground(SpinnerBackground);
		trSpinX.setForeground(Label1);
		f.add(trSpinX);

		JLabel jlTy = new JLabel("Y:");
		jlTy.setForeground(Label1);
		jlTy.setFont(new Font("VERDANA", Font.BOLD, 14));
		jlTy.setBounds(65, 90, 20, 25);
		f.add(jlTy);

		trSpinY = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		trSpinY.setBounds(85, 90, 50, 25);
		trSpinY.setBackground(SpinnerBackground);
		trSpinY.setForeground(Label1);
		f.add(trSpinY);

		jbTranslate = new JButton("TRANSLATE OBJECT");
		jbTranslate.setVerticalAlignment(SwingConstants.CENTER);
		jbTranslate.setHorizontalAlignment(SwingConstants.CENTER);
		jbTranslate.setBounds(20, 130, 170, 25);
		jbTranslate.setBackground(ButtonColour);
		jbTranslate.setForeground(Label1);
		jbTranslate.setBorder(Border1);

		jbTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel.Translate = true;

				Polygon temp = Panel.poly;

				double[] Matrix = new double[2];
				Matrix[0] = Integer.parseInt(trSpinX.getValue().toString()) * Panel.scale;
				Matrix[1] = Integer.parseInt(trSpinY.getValue().toString()) * -Panel.scale;

				double[][] pts = new double[temp.N][2];
				for (int i = 0; i < temp.N; i++) {
					pts[i][0] = temp.X[i];
					pts[i][1] = temp.Y[i];
				}

				pts = Transformation.translationObj(pts, Matrix);

				double[] xpts = new double[temp.N];
				double[] ypts = new double[temp.N];
				for (int i = 0; i < temp.N; i++) {
					xpts[i] = (int) pts[i][0];
					ypts[i] = (int) pts[i][1];
				}
				Panel.currentPolygon = new Polygon(xpts, ypts, temp.N);
				Panel.undoPoly.add(Panel.poly);
				Panel.poly = Panel.currentPolygon;
				Panel.currentPolygon = null;
				Panel.enable = true;

				repaint();
				repaint();

			}
		});
		f.add(jbTranslate);

		return f;
	}

	public JFrame addRotationMenu() {
		JFrame f = new JFrame();
		f.setSize(240, 240);
		f.setLocationRelativeTo(null);

		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(BackgroundColour);
		f.setVisible(true);

		JLabel jlAng = new JLabel("ROTATION", SwingConstants.CENTER);
		jlAng.setForeground(Label1);
		jlAng.setFont(new Font("Bodoni", Font.BOLD, 16));
		jlAng.setBounds(0, 20, 200, 15);
		f.add(jlAng);

		JLabel jlAngx = new JLabel("ANGLE:");
		jlAngx.setForeground(Label1);
		jlAngx.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlAngx.setBounds(45, 60, 70, 25);
		f.add(jlAngx);

		SpinAngle = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));// Spinner to enter angle
		SpinAngle.setBounds(105, 60, 50, 25);
		SpinAngle.setBackground(SpinnerBackground);
		SpinAngle.setForeground(Label1);
		f.add(SpinAngle);

		JLabel jlRx = new JLabel("X:");
		jlRx.setForeground(Label1);
		jlRx.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlRx.setBounds(65, 90, 20, 20);
		f.add(jlRx);

		Rotx = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));// Spinner to enter RotX
		Rotx.setBounds(85, 90, 50, 25);
		Rotx.setBackground(SpinnerBackground);
		Rotx.setForeground(Label1);
		f.add(Rotx);

		JLabel jlRy = new JLabel("Y:");
		jlRy.setForeground(Label1);
		jlRy.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlRy.setBounds(65, 120, 20, 20);
		f.add(jlRy);

		Roty = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));// Spinner to enter RotX
		Roty.setBounds(85, 120, 50, 25);
		Roty.setBackground(SpinnerBackground);
		Roty.setForeground(Label1);
		f.add(Roty);

		jbRotate = new JButton("ROTATE OBJECT");
		jbRotate.setVerticalAlignment(SwingConstants.CENTER);
		jbRotate.setHorizontalAlignment(SwingConstants.CENTER);
		jbRotate.setBounds(20, 160, 170, 25);
		jbRotate.setBackground(ButtonColour);
		jbRotate.setForeground(Label1);
		jbRotate.setBorder(Border1);
		jbRotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel.Translate = false;
				Panel.Rotate = true;
				Panel.Scale = false;
				Panel.Shear = false;
				Panel.Reflect = false;
				Panel.Fill = false;
				Panel.draw = false;

				double angle = Integer.parseInt(SpinAngle.getValue().toString()) * -1;
				int ptsNum = Panel.poly.X.length;

				if (Panel.R == null) {
					Panel.R = new double[ptsNum][2];
					for (int i = 0; i < ptsNum; i++) {
						Panel.R[i][0] = Panel.poly.X[i];
						Panel.R[i][1] = Panel.poly.Y[i];
					}
				}

				Panel.R = Transformation.rotate(Panel.R, angle);

				double[] xpts = new double[ptsNum];
				double[] ypts = new double[ptsNum];
				for (int i = 0; i < ptsNum; i++) {
					xpts[i] = (int) Panel.R[i][0];
					ypts[i] = (int) Panel.R[i][1];
				}

				Panel.poly = new Polygon(xpts, ypts, xpts.length);
				Panel.undoPoly.add(Panel.poly);

				Panel.currentPolygon = null;
				Panel.enable = true;
				MainMenu.enabled();
				repaint();
			}
		});

		f.add(jbRotate);

		return f;
	}

	public JFrame addScalingMenu() {
        JFrame f = new JFrame();
		f.setSize(230, 260);
		f.setLocationRelativeTo(null);
		
		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(BackgroundColour);
		f.setVisible(true);

		jlScale_Ins = new JLabel("SCALING", SwingConstants.CENTER);
		jlScale_Ins.setForeground(Label1);
		jlScale_Ins.setFont(new Font("BODONI", Font.BOLD, 16));
		jlScale_Ins.setBounds(0, 30, 220, 15);
		f.add(jlScale_Ins);

		JLabel jlSX = new JLabel("X-point:");
		jlSX.setForeground(Label1);
		jlSX.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlSX.setBounds(45, 60, 60, 20);
		f.add(jlSX);

		JLabel jlSY = new JLabel("Y-point:");
		jlSY.setForeground(Label1);
		jlSY.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlSY.setBounds(45, 90, 60, 20);
		f.add(jlSY);

		ptScX = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		ptScX.setBounds(105, 60, 50, 25);
		ptScX.setBackground(SpinnerBackground);
		ptScX.setForeground(Label1);
		f.add(ptScX);

		ptScY = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		ptScY.setBounds(105, 90, 50, 25);
		ptScY.setBackground(SpinnerBackground);
		ptScY.setForeground(Label1);
		f.add(ptScY);

		JLabel jlScX = new JLabel("X:");
		jlScX.setForeground(Label1);
		jlScX.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlScX.setBounds(65, 120, 20, 25);
		f.add(jlScX);

		ScSpinX = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		ScSpinX.setBounds(85, 120, 50, 25);
		ScSpinX.setBackground(SpinnerBackground);
		ScSpinX.setForeground(Label1);
		f.add(ScSpinX);

		JLabel jlScY = new JLabel("Y:");
		jlScY.setForeground(Label1);
		jlScY.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlScY.setBounds(65, 150, 20, 25);
		f.add(jlScY);

		ScSpinY = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		ScSpinY.setBounds(85, 150, 50, 25);
		ScSpinY.setBackground(SpinnerBackground);
		ScSpinY.setForeground(Label1);
		f.add(ScSpinY);

		jbScale = new JButton("SCALE OBJECT");
		jbScale.setVerticalAlignment(SwingConstants.CENTER);
		jbScale.setHorizontalAlignment(SwingConstants.CENTER);
		jbScale.setBounds(20, 180, 170, 25);
		jbScale.setBackground(ButtonColour);
		jbScale.setForeground(Label1);
		jbScale.setBorder(Border1);
		jbScale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.print('o');
				Panel.Translate = false;
				Panel.Rotate = false;
				Panel.R = null;
				Panel.Scale = true;
				Panel.Shear = false;
				Panel.Reflect = false;
				Panel.Fill = false;
				Panel.draw = false;
				Panel.Fractal = false;
				Panel.MF = false;

				Polygon temp = Panel.poly;

				double[] Matrix = new double[2];
				double fx = Double.parseDouble(ScSpinX.getValue().toString());
				double fy = Double.parseDouble(ScSpinY.getValue().toString());

				Matrix[0] = (fx + 1);
				Matrix[1] = (fy + 1);

				double[][] pts = new double[temp.N][2];
				for (int i = 0; i < temp.N; i++) {
					pts[i][0] = temp.X[i];
					pts[i][1] = temp.Y[i];
				}

				pts = Transformation.scaling(pts, Matrix);

				double[] xpts = new double[temp.N];
				double[] ypts = new double[temp.N];
				for (int i = 0; i < temp.N; i++) {
					xpts[i] = (int) pts[i][0];
					ypts[i] = (int) pts[i][1];
				}

				Panel.currentPolygon = new Polygon(xpts, ypts, temp.N);

				Panel.undoPoly.add(Panel.poly);

				Panel.poly = Panel.currentPolygon;
				Panel.currentPolygon = null;
				Panel.enable = true;
				MainMenu.enabled();
				repaint();
			}
		});
		f.add(jbScale);

		return f;
	}

	public JFrame addShearMenu() {
		JFrame f = new JFrame();
		f.setSize(260, 200);
		f.setLocationRelativeTo(null);

		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(BackgroundColour);
		f.setVisible(true);

		jlShear = new JLabel("SHEARING(Drag to perform)", SwingConstants.CENTER);///////
		jlShear.setForeground(Label1);
		jlShear.setFont(new Font("BODONI", Font.BOLD, 16));
		jlShear.setBounds(0, 30, 250, 20);
		f.add(jlShear);

		JLabel jlShx = new JLabel("X:");
		jlShx.setForeground(Label1);
		jlShx.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlShx.setBounds(85, 60, 20, 25);
		f.add(jlShx);

		ShSpinX = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		ShSpinX.setBounds(105, 60, 50, 25);
		ShSpinX.setBackground(SpinnerBackground);
		ShSpinX.setForeground(Label1);
		f.add(ShSpinX);

		JLabel jlShy = new JLabel("Y:");
		jlShy.setForeground(Label1);
		jlShy.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlShy.setBounds(85, 90, 20, 25);
		f.add(jlShy);

		ShSpinY = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		ShSpinY.setBounds(105, 90, 50, 25);
		ShSpinY.setBackground(SpinnerBackground);
		ShSpinY.setForeground(Label1);
		f.add(ShSpinY);

		jbShear = new JButton("SHEAR OBJECT");
		jbShear.setVerticalAlignment(SwingConstants.CENTER);
		jbShear.setHorizontalAlignment(SwingConstants.CENTER);
		jbShear.setBounds(60, 120, 140, 25);
		jbShear.setBackground(ButtonColour);
		jbShear.setForeground(Label1);
		jbShear.setBorder(Border1);
		jbShear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel.Translate = false;
				Panel.Rotate = false;
				Panel.R = null;
				Panel.Scale = false;
				Panel.Shear = true;
				Panel.Reflect = false;
				Panel.Fill = false;
				Panel.draw = false;
				JOptionPane.showMessageDialog(null, "Use your mouse to drag and release to draw shear shapes.");
				repaint();
			}
		});
		f.add(jbShear);

		return f;
	}

	public JFrame addClippingMenu() {
		JFrame f = new JFrame();
		f.setSize(340, 200);
		f.setLocationRelativeTo(null);

		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(BackgroundColour);
		f.setVisible(true);
		jlClipping = new JLabel("CLIPPING", SwingConstants.CENTER);
		jlClipping.setForeground(Label1);
		jlClipping.setFont(new Font("BODONI", Font.BOLD, 16));
		jlClipping.setBounds(0, 20, 260, 15);
		f.add(jlClipping);

		jClipping = new JCheckBox();
		jClipping.setVerticalAlignment(SwingConstants.CENTER);
		jClipping.setHorizontalAlignment(SwingConstants.CENTER);
		jClipping.setBounds(170, 20, 20, 15);
		jClipping.setBackground(BackgroundColour);
		jClipping.setBorder(Border1);
		f.add(jClipping);

		jlCxmin = new JLabel("X-min:");
		jlCxmin.setForeground(Label1);
		jlCxmin.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlCxmin.setBounds(45, 60, 60, 20);
		f.add(jlCxmin);

		SpinCxmin = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		SpinCxmin.setBounds(105, 60, 50, 25);
		SpinCxmin.setBackground(SpinnerBackground);
		SpinCxmin.setForeground(Label1);
		f.add(SpinCxmin);

		jlCymin = new JLabel("Y-min:");
		jlCymin.setForeground(Label1);
		jlCymin.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlCymin.setBounds(165, 60, 60, 20);
		f.add(jlCymin);

		SpinCymin = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		SpinCymin.setBounds(225, 60, 50, 25);
		SpinCymin.setBackground(SpinnerBackground);
		SpinCymin.setForeground(Label1);
		f.add(SpinCymin);

		jlCxmax = new JLabel("X-max:");
		jlCxmax.setForeground(Label1);
		jlCxmax.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlCxmax.setBounds(45, 120, 60, 20); // y=365
		f.add(jlCxmax);

		SpinCxmax = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		SpinCxmax.setBounds(105, 120, 50, 25);
		SpinCxmax.setBackground(SpinnerBackground);
		SpinCxmax.setForeground(Label1);
		f.add(SpinCxmax);

		jlCymax = new JLabel("Y-max:"); // ymax
		jlCymax.setForeground(Label1);
		jlCymax.setFont(new Font("VERDANA", Font.PLAIN, 14));
		jlCymax.setBounds(165, 120, 60, 20); // y=340
		f.add(jlCymax);

		SpinCymax = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
		SpinCymax.setBounds(225, 120, 50, 25);
		SpinCymax.setBackground(SpinnerBackground);
		SpinCymax.setForeground(Label1);
		jClipping.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // onClick
				Cxmin = Double.valueOf(SpinCxmin.getValue().toString());
				Cxmax = Double.valueOf(SpinCxmax.getValue().toString());
				Cymin = Double.valueOf(SpinCymin.getValue().toString());
				Cymax = Double.valueOf(SpinCymax.getValue().toString());
				Panel.Clipping = jClipping.isSelected();
			}
		});
		f.add(SpinCymax);

		return f;
	}

	public JFrame addReflectionMenu() {
		JFrame f = new JFrame();
		f.setSize(270, 180);
		f.setLocationRelativeTo(null);

		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(BackgroundColour);
		f.setVisible(true);

		jlReflection = new JLabel("REFLECTION", SwingConstants.CENTER);
		jlReflection.setForeground(Label1);
		jlReflection.setFont(new Font("BODONI", Font.BOLD, 16));
		jlReflection.setBounds(0, 20, 250, 15);
		f.add(jlReflection);

		Axis = new JComboBox(new Object[] {});
		Axis.setModel(new DefaultComboBoxModel(new String[] { "X-Axis", "Y-Axis", "Line Y = X", "Line Y = -X" }));
		Axis.setFont(new Font("Times New Roman", Font.BOLD, 14));
		Axis.setBorder(null);
		Axis.setBackground(Color.WHITE);
		Axis.setBounds(45, 60, 150, 35);
		Axis.setBackground(ButtonColour);
		Axis.setForeground(Label1);
		Axis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel.Translate = false;
				Panel.Rotate = false;
				Panel.Scale = false;
				Panel.Shear = false;
				Panel.Reflect = true;
				Panel.Fill = false;
				Panel.draw = false;
				Polygon temp = Panel.poly;

				double[][] pts = new double[temp.N][2];
				for (int i = 0; i < temp.N; i++) {
					pts[i][0] = temp.X[i];
					pts[i][1] = temp.Y[i];
				}
				switch (Axis.getSelectedIndex()) {

					case 0:
						pts = Transformation.ReflectionX(pts);

						break;
					case 1:
						pts = Transformation.ReflectionY(pts);

						break;
					case 2:
						pts = Transformation.ReflectionYX(pts);

						break;
					case 3:
						pts = Transformation.ReflectionYNegativeX(pts);

						break;

				}
				double[] xpts = new double[temp.N];
				double[] ypts = new double[temp.N];
				for (int i = 0; i < temp.N; i++) {
					xpts[i] = (int) pts[i][0];
					ypts[i] = (int) pts[i][1];
				}
				Panel.currentPolygon = new Polygon(xpts, ypts, temp.N);

				Panel.undoPoly.add(Panel.poly);
				Panel.poly = null;

				Panel.poly = (Panel.currentPolygon);
				Panel.currentPolygon = null;
				Panel.currentPolygon = null;
				Panel.enable = true;

				enabled();

				repaint();

			}
		});
		f.add(Axis);

		return f;
	}

	public void addScanlineFillBtn() {
		jbFill = new JButton("Scanline Fill");
		jbFill.setFont(new Font("Lato", Font.BOLD, 16));

		MenuPane.add(jbFill);

		jbFill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel.Translate = false;
				Panel.Rotate = false;
				Panel.R = null;
				Panel.Scale = false;
				Panel.Shear = false;
				Panel.Reflect = false;
				Panel.Fill = true;
				Panel.draw = false;
				JOptionPane.showMessageDialog(null,
						"Double Click on the shape to choose the colour you want to fill the object.");
			}	
		});
	}
	
	public void addClearBtn() {
		jbClear = new JButton("CLEAR");
		jbClear.setFont(new Font("Lato", Font.BOLD, 16));

		jbClear.setBackground(ClearButton);
		MenuPane.add(jbClear);

		jbClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Panel.clear();
				clear2();

			}
		});
	}
	
	public void polygonDrawHandler() {
		PolyShapesChoice = new JComboBox();
		PolyShapesChoice.setModel(new DefaultComboBoxModel(new String[] { "Irregular Polygon" }));
		PolyShapesChoice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				enabled();
			}
		});
	}
	
	public void Menu() {

		MenuPane = new JPanel();
		MenuPane.setBackground(BackgroundColour);

		ActionListener btnListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("Translation")) {
					JFrame translateFrame = addTranslationMenu();
				}
				if(e.getActionCommand().equals("Rotation")) {
					JFrame translateFrame = addRotationMenu();
				}
				if(e.getActionCommand().equals("Scaling")) {
					JFrame translateFrame = addScalingMenu();
				}
				if(e.getActionCommand().equals("Shear")) {
					JFrame translateFrame = addShearMenu();
				}
				if(e.getActionCommand().equals("Clipping")) {
					JFrame translateFrame = addClippingMenu();
				}
				if(e.getActionCommand().equals("Reflection")) {
					JFrame translateFrame = addReflectionMenu();
				}
			}
		};

		JButton translateBtn = new JButton("Translation");
		translateBtn.setFont(new Font("Lato", Font.BOLD, 16));
		translateBtn.addActionListener(btnListener);
		MenuPane.add(translateBtn);

		JButton rotationBtn = new JButton("Rotation");
		rotationBtn.setFont(new Font("Lato", Font.BOLD, 16));
		rotationBtn.addActionListener(btnListener);
		MenuPane.add(rotationBtn);

		JButton scalingBtn = new JButton("Scaling");
		scalingBtn.setFont(new Font("Lato", Font.BOLD, 16));
		scalingBtn.addActionListener(btnListener);
		MenuPane.add(scalingBtn);

		JButton shearBtn = new JButton("Shear");
		shearBtn.setFont(new Font("Lato", Font.BOLD, 16));
		shearBtn.addActionListener(btnListener);
		MenuPane.add(shearBtn);

		JButton clippingBtn = new JButton("Clipping");
		clippingBtn.setFont(new Font("Lato", Font.BOLD, 16));
		clippingBtn.addActionListener(btnListener);
		MenuPane.add(clippingBtn);

		JButton reflectionBtn = new JButton("Reflection");
		reflectionBtn.setFont(new Font("Lato", Font.BOLD, 16));
		reflectionBtn.addActionListener(btnListener);
		MenuPane.add(reflectionBtn);
		
		addScanlineFillBtn();
		addClearBtn();
		polygonDrawHandler();
		
		body.setTopComponent(MenuPane);
	}

	public static void enabled() {

		if (Panel.draw) {
			trSpinX.setEnabled(Panel.enable);
			trSpinY.setEnabled(Panel.enable);
			Axis.setEnabled(Panel.enable);
			jbFill.setEnabled(Panel.enable);
			jbTranslate.setEnabled(Panel.enable);
			jbRotate.setEnabled(Panel.enable);
			jbScale.setEnabled(Panel.enable);
			jbShear.setEnabled(Panel.enable);
			Rotx.setEnabled(Panel.enable);
			Roty.setEnabled(Panel.enable);
			PolyShapesChoice.setEnabled(!Panel.enable);
			Sides.setEnabled(!Panel.enable);
			tSides.setEnabled(!Panel.enable);
		}

		if (!Panel.enable) {

			PolyShapesChoice.setEnabled(true);
			if (PolyShapesChoice.getSelectedIndex() == 1) {
				Sides.setEnabled(true);
				tSides.setEnabled(true);
			} else {
				Sides.setEnabled(false);
				tSides.setEnabled(false);
			}
		}
	}

	public static void main(String[] args) {

		MainMenu draw = new MainMenu();
		draw.setResizable(true);
	}

}
