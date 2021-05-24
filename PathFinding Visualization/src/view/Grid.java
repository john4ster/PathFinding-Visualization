package view;
import model.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;
import javax.swing.JPanel;

/* This class is the main GUI component on top of the Frame. The grid shows and
 * updates the visualization of the algorithm as it runs.
 */

public class Grid extends JPanel {

	AStarPathFinding aStarPathfinding;
	int cellSize = 20; //Size of each cell in the grid
	int width; //Width of the grid, same width as the frame
	int height; //Height of the grid, same height as the frame
	Timer timer;
	Menu menu; //Menu for the player to select algorithm
	
	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		aStarPathfinding = new AStarPathFinding(width, height, cellSize);
		timer = new Timer(10, updateVisual);
		menu = new Menu();
		this.add(menu);
	}
	
	public void startVisualization() {
		aStarPathfinding.setup(); //Setup the algorithm
		timer.start(); //Run the algorithm one step at a time through the timer
	}
	
	//Paint visuals to the screen (background, grid lines, nodes)
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBackground(Color.WHITE);
		
		//Draw open nodes
		g.setColor(Color.GREEN);
		if (aStarPathfinding.getOpenSet() != null) {
			for (Node node : aStarPathfinding.getOpenSet()) {
				int x = node.getX();
				int y = node.getY();
				g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
			}
		}
				
		//Draw closed nodes
		g.setColor(Color.PINK);
		if (aStarPathfinding.getClosedSet() != null) {
			for (Node node : aStarPathfinding.getClosedSet()) {
				int x = node.getX();
				int y = node.getY();
				g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
			}
		}
		
		//Draw path
		g.setColor(Color.CYAN);
		if (aStarPathfinding.getPath() != null) {
			for (Node node : aStarPathfinding.getPath()) {
				int x = node.getX();
				int y = node.getY();
				g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
			}
		}
		
		//Draw start node
		Node startNode = aStarPathfinding.getStart();
		//Make sure user has placed start node
		if (startNode.getX() != -1 && startNode.getY() != -1) {
			g.setColor(Color.BLUE);
			g.fillRect(startNode.getX() * cellSize, startNode.getY() * cellSize, cellSize, cellSize);
		}
		
		//Draw end node
		Node endNode = aStarPathfinding.getEnd();
		//Make sure user has placed end node
		if (endNode.getX() != -1 && endNode.getY() != -1) {
			g.setColor(Color.RED);
			g.fillRect(endNode.getX() * cellSize, endNode.getY() * cellSize, cellSize, cellSize);
		}
		
		//Draw the grid lines
		g.setColor(Color.BLACK);
		for (int x = 0; x < width; x+= cellSize) { //Vertical grid lines
			g.drawLine(x, 0, x, height);						
		}
		for (int y = 0; y < height; y+= cellSize) { //Horizontal grid lines
			g.drawLine(0,  y, width, y);
		}
		
		//Draw borders
		for (Node border : aStarPathfinding.getBorders()) {
			int x = border.getX();
			int y = border.getY();
			g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
		}
	}
	
	//Draws the borders on the grid and adds to path finding border list
	public void drawBorder(int x, int y) {
		//Convert mouse coordinates to grid coordinates
		int xCord = x / cellSize; 
		int yCord = (y / cellSize) - 1;
		//Add the borders to path finding and repaint
		aStarPathfinding.addBorder(xCord, yCord);
		repaint();
	}
	
	//Removes borders from grid and removes them from path finding border list
	public void removeBorder(int x, int y) {
		//Convert mouse coordinates to grid coordinates
		int xCord = x / cellSize; 
		int yCord = (y / cellSize) - 1;
		//Remove the border from path finding and repaint
		aStarPathfinding.removeBorder(xCord, yCord);
		repaint();
	}
	
	//Set start on grid and in path finding
	public void setStart(int x, int y) {
		//Convert mouse coordinates to grid coordinates
		int xCord = x / cellSize;
		int yCord = (y / cellSize) - 1;
		
		aStarPathfinding.setStart(xCord, yCord);
		repaint();
	}
	
	//Set end on grid and in path finding
	public void setEnd(int x, int y) {
		//Convert mouse coordinates to grid coordinates
		int xCord = x / cellSize;
		int yCord = (y / cellSize) - 1;
		aStarPathfinding.setEnd(xCord, yCord);
		repaint();
	}
	
	ActionListener updateVisual = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (menu.getSelectedAlgorithm() == "A* Algorithm") {
				if (!aStarPathfinding.endFound() && !aStarPathfinding.noPathFound()) {
					aStarPathfinding.oneStep();
				}
				else if (aStarPathfinding.endFound()){
					System.out.println("Path found");
					timer.stop();
				}
				else if (aStarPathfinding.noPathFound()) {
					System.out.println("No path found");
					timer.stop();
				}
			}
			repaint();
		}
	};
	
}