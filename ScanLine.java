

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class ScanLine {
	private static final int X = 0;
	private static final int Y = 1;

	public static Color shapeColor = new Color(0, 51, 153);
	public static Color newColor = new Color(255,0,0);
	
	
	public void paintComponent(Graphics g){
	
		// TODO Auto-generated method stub
		
		Graphics2D ga = (Graphics2D)g;
		ga.setPaint(Color.BLACK);
	
	
	}
		

	public static void scanLineFill(int[][] points){
		newColor = JColorChooser.showDialog(null, "Choose a shape color", shapeColor);
		shapeColor = newColor;
		
		Color FColor = shapeColor;
		double[] p;
		int i, y, adjacent1, adjacent2;
		

		// Find minimum and maximum Y position
		int minY, maxY;
		
		minY = points[0][Y];
		maxY = points[0][Y];
		
		for (i = 1; i < points.length; i++) {
			if (points[i][Y] < minY)
				minY = points[i][Y];
			else if (points[i][Y] > maxY)
				maxY = points[i][Y];
		}
		
		// Calculate height of the polygon
		int height = maxY - minY;
		
		// Initialize and populate edge table
		Edge[] edgeTable;
		Edge edge1, edge2;
		
		edgeTable = new Edge[height + 1];
		 
		for (y = minY; y <= maxY; y++) {
			// Find vertices on the current y value and add them to the edge table
			for (i = 0; i < points.length; i++) {
				if (y == points[i][Y]) {
					edge1 = new Edge();
					edge2 = new Edge();
					
					// Compute position of adjacent vertices
					adjacent1 = i + 1;
					adjacent2 = i - 1;

					if (adjacent1 >= points.length)
						adjacent1 = 0;
					
					if (adjacent2 < 0)
						adjacent2 = points.length - 1;
					
					// Use the vertex with the higher Y value for edge1
					edge1.v1 = i;
					edge2.v1 = i;
					
					if (points[adjacent1][Y] > points[adjacent2][Y]) {
						edge1.v2 = adjacent1;
						edge2.v2 = adjacent2;
					}
					else {
						edge1.v2 = adjacent2;
						edge2.v2 = adjacent1;
					}
					
					edge1.cX = points[edge1.v1][X];
					
					if (points[edge1.v1][Y] > points[edge1.v2][Y])
						edge1.maxY = points[edge1.v1][Y];
					else
						edge1.maxY = points[edge1.v2][Y];
					
					if (points[edge1.v1][X] == points[edge1.v2][X])
						edge1.xIncrement = 0;
					else if (points[edge1.v1][Y] == points[edge1.v2][Y])
						edge1.xIncrement = 1;
					else
						edge1.xIncrement = (double)
							(points[edge1.v2][X] - points[edge1.v1][X]) /
							(points[edge1.v2][Y] - points[edge1.v1][Y]);
					
					// Add edge to edge table
					if (points[edge1.v1][Y] != points[edge1.v2][Y])
						insertInOrder(edgeTable, (y - minY), edge1);
					
					// Add edge2 if the two edges are not increasing monotonically.
					if ((points[edge1.v2][Y] >= points[i][Y] &&
							points[edge2.v2][Y] >= points[i][Y]) ||
							(points[edge1.v2][Y] < points[i][Y] &&
							points[edge2.v2][Y] < points[i][Y])) {
						
						edge2.cX = points[i][X];
						
						if (points[edge2.v1][Y] > points[edge2.v2][Y])
							edge2.maxY = points[edge2.v1][Y];
						else
							edge2.maxY = points[edge2.v2][Y];
						
						if (points[edge2.v1][X] == points[edge2.v2][X])
							edge2.xIncrement = 0;
						else if (points[edge2.v1][Y] == points[edge2.v2][Y])
							edge2.xIncrement = 1;
						else
							edge2.xIncrement = (double)
								(points[edge2.v2][X] - points[edge2.v1][X]) /
								(points[edge2.v2][Y] - points[edge2.v1][Y]);
						
						// Add edge to edge table
						if (points[edge2.v1][Y] != points[edge2.v2][Y])
							insertInOrder(edgeTable, (y - minY), edge2);
						
					}
				}
					
			}
		}
		
		// Get binary value of the specified fill color
		int fillColor = FColor.getRGB();
		
		// Create active edge list and paint
		Edge[] activeEdgeList;
		Edge edge, temp;
		int x;
		
		activeEdgeList = new Edge[1];
		
		for (y = minY; y <= maxY; y++) {
			edge = edgeTable[y - minY];
			
			// Put edges in the active edge list
			while (edge != null) {
				temp = edge.clone();
				
				insertInOrder(activeEdgeList, 0, temp);
				
				edge = edge.next;
			}

			// Remove edge whose maximum Y value has been reached
			removeMaxNodes(activeEdgeList, 0, y);
			
			// Paint alternate pairs of edges
			edge = activeEdgeList[0];
			
			while (edge != null) {
				edge2 = edge.next;
				x = (int) Math.round(edge.cX);
				
				if (edge2 == null) {
					System.out.println("note: last edge could not find a pair");
					
					break;
				}
				
				if (y >= 0 && y < Panel.img.getHeight()) {
					while (x <= edge2.cX) {
						if (x >= 0 && x < Panel.img.getWidth())
							Panel.img.setRGB(x, y, fillColor);						
						x++;
					}
				}
				
				// Proceed with the next pair
				edge = edge2.next;
			}
			
			// Increment X values of all edges
			temp = activeEdgeList[0];
			
			while (temp != null) {
				temp.cX += temp.xIncrement;
				temp = temp.next;
			}
		}
		
		return;
	}
	
	private static void insertInOrder(Edge[] edgeTable, int index, Edge node) {
		// Insert at the beginning
		if (edgeTable[index] == null) {
			edgeTable[index] = node;
			
			return;
		}
		
		if (node.cX <= edgeTable[index].cX) {
			if (node.cX == edgeTable[index].cX) {
				if (node.xIncrement < edgeTable[index].xIncrement) {
					node.next = edgeTable[index];
					edgeTable[index] = node;
					
					return;
				}
			}
			else {
				node.next = edgeTable[index];
				edgeTable[index] = node;
				
				return;
			}
		}
			
		// Insert in the middle
		Edge current, next;
		
		current = edgeTable[index];
		next = current.next;
		
		while (next != null) {
			if (node.cX <= next.cX) {
				if (node.cX == next.cX) {
					if (node.xIncrement > next.xIncrement) {
						current = next;
						next = current.next;
						
						continue;
					}
				}
				
				current.next = node;
				node.next = next;

				return;
			}
			
			current = next;
			next = current.next;
		}
		
		// Insert at the end
		current.next = node;
	}
	
	private static void removeMaxNodes(Edge[] edgeTable, int index, int yValue) {
		Edge parent, current;
		
		// Remove middle elements
		parent = edgeTable[0];
		
		if (parent == null)
			return;
		
		current = parent.next;
		
		while (current != null) {
			if (current.maxY <= yValue)
				parent.next = current.next;
			else
				parent = current;

			current = current.next;
		}
		
		// Remove first element
		if (edgeTable[0].maxY == yValue)
			edgeTable[0] = edgeTable[0].next; 
	}
	
	private static class Edge {
		public int maxY;
		public double cX;
		public double xIncrement;
		
		public int v1, v2;
		
		public Edge next;
		
		public Edge clone() {
			Edge c = new Edge();
			
			c.maxY = maxY;
			c.cX = cX;
			c.xIncrement = xIncrement;
			c.v1 = v1;
			c.v2 = v2;
			c.next = null;
			
			return c;
		}
	}

}



