

import java.util.ArrayList;

public class Transformation
{
	private static int X = 0;
	private static int Y = 1;
	
	public static double[][] translation(double[][] pts, double[] matrix) {

		for(int i=0;i<pts.length;i++){
			pts[i][X] += (matrix[X]);
			pts[i][Y] += (matrix[Y]);
		}

		return pts;
	}
	
	
	public static double[][] originTranslation(double[][] pts,int inverse){
		
		double[] matrix = new double[2];
		
		matrix[X]=(((MainMenu.Panel.getWidth()/2))*inverse);
		matrix[Y]=((MainMenu.Panel.getHeight()/2)*inverse);		
		pts=translation(pts,matrix);	
		
		return pts;
	}
	
	public static double[][] translationObj(double[][] pts, double[] matrix) {

		for(int i=0;i<pts.length;i++){
			pts[i][X] += (2*matrix[X]);
			pts[i][Y] += (2*matrix[Y]);
		}

		return pts;
	}
	
	public static  double[][] rotate(double[][] pts, double angle) {
		
		pts=originTranslation(pts,-1);
		
		double[] xyMatrix = new double[2];		
		xyMatrix[0]=Double.parseDouble(MainMenu.Rotx.getValue().toString())*-10;
		xyMatrix[1]=Double.parseDouble(MainMenu.Roty.getValue().toString())*10;
		pts=translation(pts, xyMatrix);
		
		double x, y, angleRad;
		
		angleRad = Math.toRadians(angle);
		
		for (int i = 0; i < pts.length; i++) {
			x = pts[i][X];
			y = pts[i][Y];
			
			pts[i][X] = x * Math.cos(angleRad) - y * Math.sin(angleRad);
			pts[i][Y] = x * Math.sin(angleRad) + y * Math.cos(angleRad);
		}
		
		pts=originTranslation(pts,1);
		
		xyMatrix[0]=Double.parseDouble(MainMenu.Rotx.getValue().toString())*10;
		xyMatrix[1]=Double.parseDouble(MainMenu.Roty.getValue().toString())*-10;
		pts=translation(pts, xyMatrix);
		
		return pts;
	}
	
	public static double[][] scaling(double[][] pts, double[] factor) {
		
		
		pts=originTranslation(pts,-1);	
		
		double[] xyMatrix = new double[2];		
		xyMatrix[0]=Double.parseDouble(MainMenu.ScSpinX.getValue().toString())*-10;
		xyMatrix[1]=Double.parseDouble(MainMenu.ScSpinY.getValue().toString())*10;
		pts=translation(pts, xyMatrix);
		
		for (int i = 0; i < pts.length; i++) {			
			pts[i][X] *= factor[X];
			pts[i][Y] *= factor[Y];
		}
		
		pts=originTranslation(pts,1);
		
		xyMatrix[0]=Double.parseDouble(MainMenu.ScSpinX.getValue().toString())*10;
		xyMatrix[1]=Double.parseDouble(MainMenu.ScSpinY.getValue().toString())*-10;
		pts=translation(pts, xyMatrix);
		
		return pts;
		}
	
	
	public static double[][] shearing(double[][] pts, double[] factor) {
		
		pts=originTranslation(pts,-1);	
		
		double x, y;		
		for (int i = 0; i < pts.length; i++) {
			
			x = pts[i][X];
			y = pts[i][Y];
			
			pts[i][X] += factor[X] * y;
			pts[i][Y] += factor[Y] * x;
		}
		
		pts=originTranslation(pts,1);
		
		return pts;
	}
	public static double[][] ReflectionX(double[][] pts){
 
		pts=originTranslation(pts,-1);
		
       for(int i=0;i<pts.length;i++){
            pts[i][X]=pts[i][X];
            pts[i][Y]*=-1;
       }
       
       pts=originTranslation(pts,1);
       
       return pts;
    }
	
	public static double[][] ReflectionY(double[][] pts){
		 
		pts=originTranslation(pts,-1);
		
       for(int i=0;i<pts.length;i++){
            pts[i][X]*=-1;
            pts[i][Y]=pts[i][Y];
       }
       
       pts=originTranslation(pts,1);
       
       return pts;
    }
	
	public static double[][] ReflectionYX(double[][] pts){
		 
		pts=originTranslation(pts,-1);
		
		for(int i=0;i<pts.length;i++){
    	   
			double temp=pts[i][X]*(-1);
			pts[i][X]=pts[i][Y]*(-1);
			pts[i][Y]=temp;
            
		}
       
		pts=originTranslation(pts,1);
       
		return pts;
    }
	
	public static double[][] ReflectionYNegativeX(double[][] pts){
		 
		pts=originTranslation(pts,-1);
		for(int i=0;i<pts.length;i++){
	    	   
			double temp=pts[i][X];
			pts[i][X]=pts[i][Y];
			pts[i][Y]=temp;
	            
		} 
		pts=originTranslation(pts,1);
       
       return pts;
    }
	
	
        
       
        public static void ReflectionYX(ArrayList<double[]> points){
        	
         	double[] p;
            int x = 1;
            int y = -1;
            for(int i=0;i<points.size();i++){
                p = points.get(i);
                //swap x and y position
                double temp=p[X];
                p[X]=p[Y];
                p[Y]=temp;
               
            }
            
            
        }
        public static void ReflectionYNegativeX(ArrayList<double[]> points){
        	
        	double[] p;
            int x = 1;
            int y = -1;
            for(int i=0;i<points.size();i++){
                p = points.get(i);
                //swap x and y position
                double temp=p[X]*(-1);
                p[X]=p[Y]*(-1);
                p[Y]=temp;
               
            }
            
        }
	
}



