package App;
// the goal of this class is to extend the JPanel class
// and to make it draw a graph
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

// TODO: Implement the automatic adjustment of the number of horizontal and vertical divisions when scrolling using the mouse
// TODO: The space bar should pause the drawing and hold the last state
// TODO: The first to-do should still work when it is paused, and the data should be adapted

// Add the GraphPanel like a normal JPanel to the JFrame (Or other panels)
// The difference is that you can use the public methods to draw a graph

public class GraphPanel extends JPanel{
	
		private List<Integer> yValues;	// list that will contain the data to be displayed
		
		private int[] xArray;	// used for the drawPolyline() x-coordinates
		private int[] yArray;	// used for the drawPolyline() y-coordinates
		
		// the latest panel size
		private int prevPanelWidth;		// updated when resizing
		
		// Graph's properties
		private int horizontalDivs = 10;
		private int verticalDivs = 6;
		private int msPerDiv = 1000;
		private int mvPerDiv = 1000;
		
		
		// No-arguments default constructor
		public GraphPanel() {
	        yValues = new ArrayList<>();
	        yValues.add(0);
	        prevPanelWidth = getWidth();
		}
		
		// fill the array from 0 to panelWidth - 1
		private void resizeAndFillX() {
			int W = getWidth();
			xArray = new int[W];
	        // fill the xArray
	        int i;
	        for(i=0; i<W; i++) {
	        	xArray[i] = i;
	        }
		}
		
		// Getters and setters
		public int getGraphWidth() {
			return getWidth();
		}
		public int getGraphHeight() {
			return getHeight();
		}
		public void setHorizontalDivs(int numDivs) {
			this.horizontalDivs = numDivs;
		}
		public int getHorizontalDivs() {
			return this.horizontalDivs;
		}
		public void setVerticalDivs(int numDivs) {
			this.verticalDivs = numDivs;
		}
		public int getVerticalDivs() {
			return this.verticalDivs;
		}
		public void setmsPerDiv(int msPerDiv) {
			this.msPerDiv = msPerDiv;
		}
		public int getmsPerDiv() {
			return this.msPerDiv;
		}
		public void setmvPerDiv(int mvPerDiv) {
			this.mvPerDiv = mvPerDiv;
		}
		public int getmvPerDiv() {
			return this.mvPerDiv;
		}
		
		// update the Y-axis array
		private void resizeAndFillY() {
			if(yValues.size() > 0) {
				yArray = new int[yValues.size()];
		        yArray = yValues.stream().mapToInt(i -> i).toArray(); // Convert List<Integer> to int[]
			}
		}
		
		// add a new point to the graph
		public void addPoint(int y) {
			int W = getWidth();
			
			// if the window has changed
			if(W != prevPanelWidth) {
				resizeAndFillX();
				this.prevPanelWidth = W;
			}
			
			yValues.add(y);
			while(yValues.size() > W) {
				yValues.remove(0);
			}
			resizeAndFillY();
		}
		
		// update the graph to display the new points
		public void updateGraph() {
			if(xArray.length >= 2)
				repaint();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); // Call the superclass method to properly initialize the graphics context
		    
		    // TODO: this can be made faster by computing the lines before hand
		    int HDivLength = getWidth() / this.horizontalDivs;
		    int VDivLength = getHeight() / this.verticalDivs;
		    for(int i=1; i<this.horizontalDivs; i++) {
		    	g.setColor(Color.LIGHT_GRAY);
		    	g.drawLine(i*HDivLength, 0, i*HDivLength, getHeight());
		    }
		    for(int i=1; i<verticalDivs; i++) {
		    	g.setColor(Color.LIGHT_GRAY);
		    	g.drawLine(0, i*VDivLength, getWidth(), i*VDivLength);
		    }
		    // Plot the data points
		    g.setColor(Color.RED);
		    g.drawPolyline(xArray, yArray, yArray.length);
		}
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(590, 430);
		}
}
