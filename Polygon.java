

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;



public class Polygon {
	protected double[] X;
	protected double[] Y;
	protected int N;
	
	
	public Polygon(){
		this(null,null,0);
	}
	public Polygon(double[] xpts,double[]ypts,int npts){
		setX(xpts);
		setY(ypts);
		setN(npts);
	}
	public void setX(double[] xpts){
		if(xpts!=null)
		this.X = new double[xpts.length];
		for(int i=0;i<xpts.length;i++)
			this.X[i]=xpts[i];
	}
	public void setY(double[] ypts){
		if(ypts!=null)
		this.Y = new double[ypts.length];
		for(int i=0;i<ypts.length;i++)
			this.Y[i]=ypts[i];
	}
	public void setN(int npts){
		this.N=npts;
	}

	public double[] getX(){
		return X;
	}
	public double[] getY(){
		return Y;
	}
	public int getN(){
		return N;
	}
	public LineSegment[] getLineSegments(){
		LineSegment lines[] = new LineSegment[N];
		for(int i=0;i<N;i++){
			int j = (i+1) % N;
			lines[i] = new LineSegment(X[i],Y[i],X[j],Y[j]);
		}
		return lines;
	}
	
	
	public void draw(Graphics g) {	
		Graphics2D ga = (Graphics2D)g;
		
		ga.setStroke(new BasicStroke(2));
		
		for(int i=0;i<N;i++){
			
			double x =X[i];
			double y =Y[i];
			x=(X[i]-(Panel.w/2))/10;
			y=(Y[i]-(Panel.h/2))/-10;	
	        String coordinate = "( "+x+" , "+y+" )";
	        ga.drawString(coordinate, (int)X[i], (int)Y[i]);

		}
		
		float dashes[] = { 10.0f };
for(int i=0;i<X.length-1;i++){
			
			double x1,x2,y1,y2,dx,dy,steps,incrX,incrY, a,b;
			x1 = X[i];
			y1 = Y[i];
			x2 = X[i+1];
			y2 = Y[i+1];
			
			dx=Math.abs(x2-x1);
			dy=Math.abs(y2-y1);
			
			double diff = dx-dy;

			
			if (x1 < x2){
				incrX = 1;
		    }
		    else {
		    	incrX = -1;
		    }
		    if (y1 < y2) {
		    	incrY = 1;
		    }
		    else{
		    	incrY = -1;
		    }
			
		    while ((x1 != x2) || (y1 != y2)) {  

		        double p0 = 2 * diff;

		        if (p0 > -dy) {
		        	diff = diff - dy;
		        	x1 += incrX;
		        }
		        if (p0 < dx) {
		        	diff = diff + dx;
		        	y1 += incrY;
		        }
			    ga.drawLine((int)x1, (int)y1, (int)x1, (int)y1);
		      
			}
		}		
		
		double x1,x2,y1,y2,dx,dy,steps,incrX,incrY, a,b;
		x1 =X[X.length-1];
		y1 =Y[Y.length-1];
		x2 =  X[0];
		y2 = Y[0];
		
		dx=Math.abs(x2-x1);
		dy=Math.abs(y2-y1);
		
		double diff = dx-dy;

		
		if (x1 < x2){
			incrX = 1;
	    }
	    else {
	    	incrX = -1;
	    }
	    if (y1 < y2) {
	    	incrY = 1;
	    }
	    else{
	    	incrY = -1;
	    }
		
	    while ((x1 != x2) || (y1 != y2)) {  

	        double p0 = 2 * diff;

	        if (p0 > -dy) {
	        	diff = diff - dy;
	        	x1 += incrX;
	        }
	        if (p0 < dx) {
	        	diff = diff + dx;
	        	y1 += incrY;
	        }
		    ga.drawLine((int)x1, (int)y1, (int)x1, (int)y1);			  
		}
	}	
}


