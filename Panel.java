

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;

public class Panel extends JPanel {
	
	
	public static LineClippingPanel lcp = new LineClippingPanel(0,0,0,0);
	public static boolean clear = false;
	public static boolean enable=false;
	public static Polygon poly;
	public static Polygon oPoly;
	public static boolean origin=false;
	public static Polygon FPoly;
	public static ArrayList<Polygon> undoPoly = new ArrayList<Polygon>();
	public static ArrayList<Polygon> redoPoly = new ArrayList<Polygon>();
	public static Polygon currentPolygon;
	public static Color currentColor;
	public static boolean centerExist=false;
	public static int sid = 3;
	public static boolean draw = true;
	public int[] C = new int[2];
	public boolean irreg=false;
	public static float frame=50;
	public static float scale=frame/10;
	
	public ArrayList<Integer> X = new ArrayList<Integer>();
	public ArrayList<Integer> Y = new ArrayList<Integer>();
	public static float currentX=0,currentY=0;
	public static DecimalFormat decimalformat = new DecimalFormat("#.00");
	public static boolean Translate=false,Rotate=false,Scale=false,Shear=false,Reflect=false,Fill=false,Fractal=false,SHScale=true,MF=false,Clipping=false;
	public static int Xenter;
	public static int Yenter;
	public static double[][] R=null;
	public static int x1,x2,y1,y2;
	public static int h, w;
	public static BufferedImage img=new BufferedImage(MainMenu.w,MainMenu.h, BufferedImage.TYPE_INT_ARGB);
	public static int[] pix= ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	Color color=new Color(106,90,205);
	int rgb=color.getRGB();
	
	//Panel
	public Panel(){
		currentPolygon = null;
		setBackground(MainMenu.PanelColour);
		HandlerClass handler = new HandlerClass();
		addMouseListener(handler);
		addMouseMotionListener(handler);
		addMouseWheelListener(handler);
	}
	
	
	//Clear Button
	public void setClear(boolean c) {
		clear=c;
	}
	public boolean getClear() {
		return clear;
	}
	
	public void clear() {	
		
		 lcp = new LineClippingPanel(0,0,0,0);
		 clear = false;
		 enable=false;
		 poly = null;
		 oPoly = null;
		 origin=false;
		 FPoly = null;
		 undoPoly.clear();
		 redoPoly.clear();
		 currentPolygon = null;
		 currentColor = new Color(0,0,0,0);
		 centerExist=false;
		 sid = 3;
		 draw = true;
		 C = new int[2];
		 irreg=false;
		 frame=50;
		 scale=frame/10;
		 X.clear();
		 Y.clear();
		 currentX=currentY=0;
		 decimalformat = new DecimalFormat("#.00");
		 Translate=false;Rotate=false;Scale=false;Shear=false;Reflect=false;Fill=false;Fractal=false;SHScale=true;MF=false;Clipping=false;
		 Xenter=0;
		 Yenter=0;
		 R=null;
		 x1=x2=y1=y2=0;
		 h=0;w=0;
		 img= null;
		color=new Color(106,90,205);
		rgb=color.getRGB();
			
	}
	 
	//Grid
	public void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
        g.drawLine((int)x1, (int)(getHeight() - y1), (int)x2, (int)(getHeight() - y2));
    }
	
	public void drawLine2(Graphics g, double x1, double y1, double x2, double y2) {
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

	//Graphic And Clipping
	public void paintComponent(Graphics g){
		
		 w = getWidth();
		 h = getHeight();
		super.paintComponent(g);
		Graphics2D ga = (Graphics2D)g;
		ga.setPaint(Color.BLACK);
		ga.setStroke(new BasicStroke(2));
		if(SHScale){
			
			ga.drawLine(0, h/2, w, h/2);
			ga.drawLine(w/2, 0, w/2, h);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 			
			ga.setStroke(new BasicStroke(1));
			//ga.setPaint(Color.darkGray);
			ga.setPaint(MainMenu.BackgroundColour);
			
			float x=scale;
			//x +ve
			g.drawString("0", w/2, (h/2)+15);
			for(int i=w/2+(int)frame;i<w;i+=frame){
				ga.drawLine(i, 0, i, h);
				String X = Float.toString(x);
				if (X.substring(X.length()-1).equals("0")){
					X = X.substring(0, X.length()-2);
				}
				if(X.length()>=5){
					g.drawString(X.substring(0, 5), i, (h/2)+15);
				}
				else if(X.length()>=4){
					g.drawString(X.substring(0, 4), i, (h/2)+15);
				}
				else if(X.length()>=3){
					g.drawString(X.substring(0, 3), i, (h/2)+15);
				}
				else if(X.length()>=2){
					g.drawString(X.substring(0, 2), i, (h/2)+15);
				}
				else if(X.length()>=1){
					g.drawString(X.substring(0, 1), i, (h/2)+15);
				}
				x=x+scale;
			}
			//x -ve
			x=0;
			for(int i=w/2;i>=0;i-=frame){
				ga.drawLine(i, 0, i, h);
				String X = Float.toString(x);
				if (X.substring(X.length()-1).equals("0")){
					X = X.substring(0, X.length()-2);
				}
				if(X.length()>=5){
					g.drawString(X.substring(0, 5), i, (h/2)+15);
				}
				else if(X.length()>=4){
					g.drawString(X.substring(0, 4), i, (h/2)+15);
				}
				else if(X.length()>=3){
					g.drawString(X.substring(0, 3), i, (h/2)+15);
				}
				else if(X.length()>=2){
					g.drawString(X.substring(0, 2), i, (h/2)+15);
				}
				
				x=x-scale;
			}
			//y +ve
			x=0;
			for(int i=h/2;i>=0;i-=frame){
				ga.drawLine(0, i, w, i);
				String X = Float.toString(x);
				if (X.substring(X.length()-1).equals("0")){
					X = X.substring(0, X.length()-2);
				}
				if(X.length()>=5){
					g.drawString(X.substring(0, 5), (w/2)+5, i-2);
				}
				else if(X.length()>=4){
					g.drawString(X.substring(0, 4), (w/2)+5, i-2);
				}
				else if(X.length()>=3){
					g.drawString(X.substring(0, 3), (w/2)+5, i-2);
				}
				else if(X.length()>=2){
					g.drawString(X.substring(0, 2), (w/2)+5, i-2);
				}
				else if(X.length()>=1){
					g.drawString(X.substring(0, 1), (w/2)+5, i-2);
				}
				x=(x+scale);
			}
			//y -ve
			x=-scale;
			for(int i=h/2+(int)frame;i<h;i+=frame){
				ga.drawLine(0, i, w, i);
				String X = Float.toString(x);
				if (X.substring(X.length()-1).equals("0")){
					X = X.substring(0, X.length()-2);
				}
				if(X.length()>=5){
					g.drawString(X.substring(0, 5), (w/2)+5, i-2);
				}
				else if(X.length()>=4){
					g.drawString(X.substring(0, 4), (w/2)+5, i-2);
				}
				else if(X.length()>=3){
					g.drawString(X.substring(0, 3), (w/2)+5, i-2);
				}
				else if(X.length()>=2){
					g.drawString(X.substring(0, 2), (w/2)+5, i-2);
				}
				x=x-scale;
			}
		}
		
		
		
		if(!Fill){
			img = new BufferedImage(MainMenu.w,MainMenu.h, BufferedImage.TYPE_INT_ARGB);
		}
		
		
		
		Graphics2D g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setColor(MainMenu.ConfirmCoordinate);
		if (poly!=null){
			poly.draw( g2d );
		}

		g2d.setColor(MainMenu.InitialCoordinate);
		if(origin){
			oPoly.draw(g2d);
		}
		
		if (currentPolygon!=null){
			g2d.setColor(MainMenu.DrawingCoordinate);
			currentPolygon.draw(g2d);			
		}
		
		if(Clipping) {;
			double x0, x1, y0, y1;
			x0 = lcp.getxMin();
			y0 = lcp.getyMin();
			x1 = lcp.getxMax();
			y1 = lcp.getyMax();

	        System.out.println("pt("+ MainMenu.Cxmin + "," + MainMenu.Cymin + ")    pt(" + MainMenu.Cxmax + "," + MainMenu.Cymax + ")");
	        System.out.println("Graphical pt("+ lcp.getxMin() + "," + lcp.getyMin() + ")    pt(" + lcp.getxMax() + "," + lcp.getyMax() + ")");

	        g2d.setStroke(new BasicStroke(3));
	        LineSegment line[] = Panel.oPoly.getLineSegments();
	        LineSegment clippedLine[] = new LineSegment[line.length];
	         
	         
	         for (int i = 0; i < line.length; i++) {

	             clippedLine[i] =  lcp.clip(line[i]) ;

	             System.out.println("Original: " + line[i]);
	             System.out.println("Clipped: " + clippedLine[i]);

	             if (clippedLine[i] == null) {
	                 g2d.setColor(Color.red);
	                 drawLine2(g2d, line[i].x0, line[i].y0, line[i].x1, line[i].y1);
	             } else {
	                 g2d.setColor(Color.red);
	                 drawLine2(g2d, line[i].x0, line[i].y0, clippedLine[i].x0, clippedLine[i].y0);
	                 drawLine2(g2d, clippedLine[i].x1, clippedLine[i].y1, line[i].x1, line[i].y1);
	                 g2d.setColor(Color.green);
	                 drawLine2(g2d, clippedLine[i].x0, clippedLine[i].y0, clippedLine[i].x1, clippedLine[i].y1);
	             }
	         }
	         g2d.setColor(MainMenu.ButtonBorder2);
			 int width = 5;
			 g2d.setStroke(new BasicStroke(width));
	        drawLine(g2d, x0, 0, x0, getHeight());
	        drawLine(g2d, x1, 0, x1, getHeight());
	        drawLine(g2d, 0, getHeight()-y0, getWidth(), getHeight()-y0);
	        drawLine(g2d, 0, getHeight()-y1, getWidth(), getHeight()-y1);

			
		}
		
		
		ga.drawImage(img, 0, 0, null);
		g2d.dispose();
		
	
	}
	
	//Handler	
	private class HandlerClass implements MouseListener, MouseMotionListener,MouseWheelListener {
		
		public void mouseReleased(MouseEvent event) {
			
			int x = event.getX();
			int y = event.getY();
			
			//Translation
			if(Translate){
				
				Polygon temp = poly;

				double[] Matrix = new double[2];
				Matrix[0]=x-Xenter;
				Matrix[1]=y-Yenter;
				
				
				double[][] pts = new double[temp.N][2];
				for(int i=0;i<temp.N;i++){
					pts[i][0]=temp.X[i];
					pts[i][1]=temp.Y[i];
				}
				
				
				pts = Transformation.translation(pts, Matrix);
				
				double [] xpts = new double[temp.N];
				double [] ypts = new double[temp.N];
				for(int i=0;i<temp.N;i++){
					xpts[i]=(int) pts[i][0];
					ypts[i]=(int) pts[i][1];	
				}
				currentPolygon = new Polygon(xpts,ypts,temp.N);

				Panel.undoPoly.add(Panel.poly);
				
				poly=currentPolygon;
				
				currentPolygon=null;
				enable=true;
				MainMenu.enabled();
				
				repaint();				
			}
			
			//Scaling
			if(Scale){
				
				Polygon temp = poly;

				double[] Matrix = new double[2];
				float fx = (x-Xenter)/frame;
				float fy = (y-Yenter)/-frame;
				
				Matrix[0]=(fx+1);
				Matrix[1]=(fy+1);
				
				double[][] pts = new double[temp.N][2];
				for(int i=0;i<temp.N;i++){
					pts[i][0]=temp.X[i];
					pts[i][1]=temp.Y[i];
				}
				
				
				
				pts = Transformation.scaling(pts, Matrix);
				
				double [] xpts = new double[temp.N];
				double [] ypts = new double[temp.N];
				for(int i=0;i<temp.N;i++){
					xpts[i]=(int) pts[i][0];
					ypts[i]=(int) pts[i][1];	
				}
				
				currentPolygon = new Polygon(xpts,ypts,temp.N);

				Panel.undoPoly.add(Panel.poly);
								
				poly=currentPolygon;
				currentPolygon=null;
				enable=true;
				MainMenu.enabled();
				repaint();				
			}
			
			//Shear
			if(Shear){
				
				Polygon temp = poly;

				double[] Matrix = new double[2];
				float fx = (x-Xenter)/-frame;
				float fy = (y-Yenter)/frame;
				
				Matrix[0]=fx;
				Matrix[1]=fy;
					
				double[][] pts = new double[temp.N][2];
				for(int i=0;i<temp.N;i++){
					pts[i][0]=temp.X[i];
					pts[i][1]=temp.Y[i];
				}
				
				
				pts = Transformation.shearing(pts, Matrix);
				
				double [] xpts = new double[temp.N];
				double [] ypts = new double[temp.N];
				for(int i=0;i<temp.N;i++){
					xpts[i]=(int) pts[i][0];
					ypts[i]=(int) pts[i][1];	
				}
				currentPolygon = new Polygon(xpts,ypts,temp.N);

				Panel.undoPoly.add(Panel.poly);
		
				poly=currentPolygon;
				currentPolygon=null;
				enable=true;
				MainMenu.enabled();				
				repaint();				
			}
			
			
			//Clipping
			if(Clipping) {
				double x0, x1, y0, y1;
				x0 = (MainMenu.Cxmin * 10) + (getWidth()/2); 
				y0 = (MainMenu.Cymin * -10) + (getHeight()/2);
				x1 = (MainMenu.Cxmax * 10) + (getWidth()/2); 
				y1 = (MainMenu.Cymax * -10) + (getHeight()/2);
				lcp.setxMin(x0);
				lcp.setyMin(y1);
				lcp.setxMax(x1);
				lcp.setyMax(y0);
				
			}
		}

		
		public void mouseDragged(MouseEvent event) {

			int x = event.getX();
			int y = event.getY();
			currentX=(x-(getWidth()/2))/10;
			currentY=-(y-(getHeight()/2))/10;
			
			//Translation
			if(Translate){
				
				Polygon temp = poly;

				double[] Matrix = new double[2];
				
				Matrix[0]=x-Xenter;
				Matrix[1]=y-Yenter;
				
			
				double[][] pts = new double[temp.N][2];
				for(int i=0;i<temp.N;i++){
					pts[i][0]=temp.X[i];
					pts[i][1]=temp.Y[i];
				}
				
				
				pts = Transformation.translation(pts, Matrix);
				
				double [] xpts = new double[temp.N];
				double [] ypts = new double[temp.N];
				for(int i=0;i<temp.N;i++){
					xpts[i]=(int) pts[i][0];
					ypts[i]=(int) pts[i][1];	
				}
								
				currentPolygon= new Polygon(xpts,ypts,temp.N);	
								
				repaint();				
			}
			
			//Scaling
			if(Scale){
				
				Polygon temp = poly;

				double[] Matrix = new double[2];
				float fx = (x-Xenter)/frame;
				float fy = (y-Yenter)/-frame;
				
				Matrix[0]=(fx+1);
				Matrix[1]=(fy+1);
			
				double[][] pts = new double[temp.N][2];
				for(int i=0;i<temp.N;i++){
					pts[i][0]=temp.X[i];
					pts[i][1]=temp.Y[i];
				}
				
				
				pts = Transformation.scaling(pts, Matrix);
				
				double [] xpts = new double[temp.N];
				double [] ypts = new double[temp.N];
				for(int i=0;i<temp.N;i++){
					xpts[i]=(int) pts[i][0];
					ypts[i]=(int) pts[i][1];	
				}
				
				currentPolygon = new Polygon(xpts,ypts,temp.N);

				repaint();				
			}
			
			//Shear
			if(Shear){
				
				Polygon temp = poly;

				double[] Matrix = new double[2];
				float fx = (x-Xenter)/-frame;
				float fy = (y-Yenter)/frame;
				
				Matrix[0]=fx;
				Matrix[1]=fy;
				
				
				double[][] pts = new double[temp.N][2];
				for(int i=0;i<temp.N;i++){
					pts[i][0]=temp.X[i];
					pts[i][1]=temp.Y[i];
				}
				
				
				pts = Transformation.shearing(pts, Matrix);
				
				double [] xpts = new double[temp.N];
				double [] ypts = new double[temp.N];
				for(int i=0;i<temp.N;i++){
					xpts[i]=(int) pts[i][0];
					ypts[i]=(int) pts[i][1];	
				}
				
				currentPolygon = new Polygon(xpts,ypts,temp.N);

				repaint();				
			}
			
			//Polygon drawing
			if(draw&&!enable){
				if(MainMenu.PolyShapesChoice.getSelectedIndex()==0){
					if(X.size()<=10){
						if (X.size()>0){
							X.set(X.size()-1, x);
							Y.set(Y.size()-1, y);
						}
					double [] xpts = new double[X.size()];
					for(int i=0;i<X.size();i++){
						xpts[i]=X.get(i).intValue();
					}
						
					double [] ypts = new double[Y.size()];
					for(int i=0;i<Y.size();i++){
						ypts[i]=Y.get(i).intValue();
					}						
						
					currentPolygon = new Polygon(xpts,ypts,xpts.length);
					repaint();
					}
				
					if(X.size()>10){
						poly=currentPolygon;
						if(!origin){
							oPoly=poly;
							origin=true;
						}
						currentPolygon=null;
						enable=true;
						MainMenu.enabled();
						X.clear();
						Y.clear();
						repaint();
						JOptionPane.showMessageDialog(null, "Max sides of a  polygon is 9");
					}
				}else{
					if(centerExist){
						int sides= Integer.parseInt(MainMenu.tSides.getValue().toString());
						x2=x;
						y2=y;
						if(x2!=x1||y2!=y1){
							double length = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
							for (int i=0; i <sides; i++){
								X.add((int) (x1 - length * Math.sin(i * 2 * Math.PI / sides)));
								Y.add((int) (y1 - length * Math.cos(i * 2 * Math.PI / sides)));
							}

							double [] xpts = new double[X.size()];
							for(int i=0;i<X.size();i++){
								xpts[i]=X.get(i).intValue();
							}
							
							double [] ypts = new double[Y.size()];
							for(int i=0;i<Y.size();i++){
								ypts[i]=Y.get(i).intValue();
							}	
							currentPolygon = new Polygon(xpts,ypts,xpts.length);
							repaint();							
							X.clear();
							Y.clear();
							
						}
					}
				}
				
			}
			
		}
		
	
		public void mousePressed(MouseEvent event) {
			
			int x = Xenter = event.getX();
			int y = Yenter = event.getY();
			
			if(draw&&!enable){
				if(MainMenu.PolyShapesChoice.getSelectedIndex()==0){
					if(event.getButton() == MouseEvent.BUTTON1 && X.size()<=17){
					X.add(x);
					Y.add(y);
					}
					if(X.size()>17){
						poly=currentPolygon;
						if(!origin){
							oPoly=poly;
							origin=true;
						}
						currentPolygon=null;
						enable=true;
						MainMenu.enabled();
						X.clear();
						Y.clear();
						repaint();
						JOptionPane.showMessageDialog(null, "Max sides of a  polygon is 9");
					}
				}else{
					if(centerExist){
						int sides= Integer.parseInt(MainMenu.tSides.getValue().toString());
						x2=x;
						y2=y;
						if(x2!=x1||y2!=y1){
							double length = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
							for (int i=0; i <sides; i++){
								X.add((int) (x1 - length * Math.sin(i * 2 * Math.PI / sides)));
								Y.add((int) (y1 - length * Math.cos(i * 2 * Math.PI / sides)));
							}

							double [] xpts = new double[X.size()];
							for(int i=0;i<X.size();i++){
								xpts[i]=X.get(i).intValue();
							}
							
							double [] ypts = new double[Y.size()];
							for(int i=0;i<Y.size();i++){
								ypts[i]=Y.get(i).intValue();
							}	
							currentPolygon = new Polygon(xpts,ypts,xpts.length);
							repaint();
							
							X.clear();
							Y.clear();
							
						}
					}
				}
					
			}
						
			
		}

		public void mouseClicked(MouseEvent event) {

			int x = event.getX();
			int y = event.getY();
			
			if (draw&&!enable){
				
				switch(MainMenu.PolyShapesChoice.getSelectedIndex()){
				case 0:
					
					if(event.getButton() == MouseEvent.BUTTON1 && X.size()<=17) {
						X.add(x);
						Y.add(y);
						
						double [] xpts = new double[X.size()];
						for(int i=0;i<X.size();i++){
							xpts[i]=X.get(i).intValue();
						}
						
						double [] ypts = new double[Y.size()];
						for(int i=0;i<Y.size();i++){
							ypts[i]=Y.get(i).intValue();
						}	
						
						currentPolygon = new Polygon(xpts,ypts,xpts.length);
						repaint();
									
					}
					
					if(X.size()>17){
						poly=currentPolygon;
						if(!origin){
							oPoly=poly;
							origin=true;
						}
						currentPolygon=null;
						enable=true;
						MainMenu.enabled();
						X.clear();
						Y.clear();
						repaint();
						JOptionPane.showMessageDialog(null, "Max sides of a  polygon is 9");
					}
						
					if(event.getButton() == MouseEvent.BUTTON3) {
						if(X.size()>0){
							poly=currentPolygon;
							if(!origin){
								oPoly=poly;
								origin=true;
							}
							currentPolygon=null;
							enable=true;
							
							X.clear();
							Y.clear();
							repaint();
						}					
					}
					
					break;
				case 1:
					
					if(event.getButton() == MouseEvent.BUTTON3&&!centerExist) {
						x1 = x;
						y1 = y;
						centerExist=true;
					}

					if(event.getButton() == MouseEvent.BUTTON1&&centerExist){
						int sides= Integer.parseInt(MainMenu.tSides.getValue().toString());
						x2=x;
						y2=y;
						if(x2!=x1||y2!=y1){
							double length = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
							for (int i=0; i <sides; i++){
								X.add((int) (x1 - length * Math.sin(i * 2 * Math.PI / sides)));
								Y.add((int) (y1 - length * Math.cos(i * 2 * Math.PI / sides)));
							}

							double [] xpts = new double[X.size()];
							for(int i=0;i<X.size();i++){
								xpts[i]=X.get(i).intValue();
							}
							
							double [] ypts = new double[Y.size()];
							for(int i=0;i<Y.size();i++){
								ypts[i]=Y.get(i).intValue();
							}	
							currentPolygon=null;
							poly = new Polygon(xpts,ypts,xpts.length);
							repaint();
							enable=true;
							MainMenu.enabled();
							X.clear();
							Y.clear();
							centerExist=false;
						}
					}
					
					break;
				}
			}
			
			if(event.getButton() == MouseEvent.BUTTON1 && Fill==true) {

						int[][] fillPoints = new int[Panel.poly.X.length][2];
							
							for(int i=0;i<Panel.poly.X.length;i++){
								fillPoints[i][0]=(int)Panel.poly.X[i];
								fillPoints[i][1]=(int)Panel.poly.Y[i];						
							}		

							ScanLine.scanLineFill(fillPoints);
							repaint();		
												
			}
			
		}

		public void mouseMoved(MouseEvent event) {
			float x = event.getX();
			float y = event.getY();
			currentX=(x-(getWidth()/2))/10;
			currentY=-(y-(getHeight()/2))/10;
			repaint();			
		}

		public void mouseEntered(MouseEvent event) {
			
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			
		}


		public void mouseExited(MouseEvent event) {
			
			setCursor(Cursor.getDefaultCursor());	
			
		}
		public void mouseWheelMoved(MouseWheelEvent e) {
			double angle = 5.0 * e.getWheelRotation();
			if(Rotate){
				
				int ptsNum = poly.X.length;
				
				if(R==null){
					R = new double[ptsNum][2];
					for(int i=0;i<ptsNum;i++){
						R[i][0]=poly.X[i];
						R[i][1]=poly.Y[i];
					}
				}
				
				R = Transformation.rotate(R, angle);
				
				double [] xpts = new double[ptsNum];
				double [] ypts = new double[ptsNum];
				for(int i=0;i<ptsNum;i++){
					xpts[i]=(int)R[i][0];
					ypts[i]=(int)R[i][1];	
				}
				
				poly= new Polygon(xpts,ypts,xpts.length);		
				Panel.undoPoly.add(Panel.poly);
				
				currentPolygon=null;
				enable=true;
				MainMenu.enabled();
				
				repaint();
			}
		}

	}

}

	


