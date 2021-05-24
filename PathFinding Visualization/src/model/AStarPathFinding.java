package model;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

// Class for the AStarPathFinding algorithm

public class AStarPathFinding {

	PriorityQueue<Node> openSet; //PriorityQueue of discovered nodes that need to be expanded
	ArrayList<Node> closedSet;//closedSet - set of discovered nodes that have already been evaluated
	ArrayList<Node> borders; //List of border nodes
	ArrayList<Node> path;  //List of nodes that make up the path
	Node currNode;
	Node startNode;
	Node endNode; 
	//windowWidth, windowHeight, and cellSize are all used so the algorithm doesn't look outside the window
	int windowWidth;
	int windowHeight; 
	int cellSize;
	
	public AStarPathFinding(int windowWidth, int windowHeight, int cellSize) {
		//Initialize variables
		openSet = new PriorityQueue<Node>();
		closedSet = new ArrayList<Node>();
		borders = new ArrayList<Node>();
		path = new ArrayList<Node>();
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.cellSize = cellSize;
		//Initialize start and end nodes, (-1, -1) are placeholder coordinates for when the
		//user hasn't placed a start/end node yet
		startNode = new Node(-1,-1); 
		endNode = new Node(-1,-1);
		setup();
	}
	
	//Sets the algorithm up to start
	public void setup() {
		//Clear any previous info from running the algorithm
		openSet.clear();
		closedSet.clear();
		path.clear();
		
		//Add the startNode to open set, and make it the current node
		openSet.add(startNode);
		currNode = startNode;
	}
	
	//Moves one step forward in the algorithm (called using timer)
	public void oneStep() {
		currNode = openSet.peek(); //Get the node with the lowest fScore
		scanNeighbors(currNode); //Evaluate the neighbors of currNode to pick next node
		openSet.remove(currNode);
		closedSet.add(currNode);
	}
	
	public void scanNeighbors(Node node) {
		//Evaluate the neighbors of the current node
		node.findNeighbors(windowWidth, windowHeight, cellSize);
		for (int i = 0; i < node.getNeighbors().size(); i++) {
			
			Node neighbor = node.getNeighbors().get(i);
			
			//Calculate the g, h, and f scores for each neighbor
			calculateScores(neighbor);
			
			neighbor.setPrevious(node);
		}
	}
	
	//Method to calculate the g, h, and f score of a node
	public void calculateScores(Node node) {
		//Only applies to nodes that aren't borders and haven't already been checked
		if (!borders.contains(node) && !closedSet.contains(node) && !closedSet.contains(currNode)) {
			//Calculate g score
			int tempGScore = currNode.getG() + 1;
			//Check if the node already has a better gScore
			if (tempGScore < node.getG()) { 
				node.setPrevious(currNode);
				node.setG(tempGScore);
			}
			
			//Calculate h score
			int hScoreX = Math.abs(endNode.getX() - node.getX());
			int hScoreY = Math.abs(endNode.getY() - node.getY());
			int hScore = hScoreX + hScoreY;
			node.setH(hScore);
			
			//Calculate f score
			int fScore = node.getG() + node.getH();
			node.setF(fScore);
			
			openSet.add(node);
		}
		
	}
	
	//Method to check if the end is found, so the timer knows to stop
	public boolean endFound() {
		//If the end is found, find the path it took and return true
		if (currNode.equals(endNode)) {
			//Find the path
			Node tempNode = currNode; //Start at the currNode (endNode)
			path.add(tempNode); //Add that to the path
			//Trace the path back until it reaches the startNode, then the path is complete
			while (tempNode.getPrevious() != startNode) {
				path.add(tempNode.getPrevious());
				tempNode = tempNode.getPrevious();
			}
			return true;
		}
		//Otherwise, return false
		return false;
	}
	
	//Method that determines that path is not found
	public boolean noPathFound() {
		if (openSet.size() <= 0) {
			return true;
		}
		return false;
	}
	
	//Methods to add and remove borders
	public void addBorder(int x, int y) {
		borders.add(new Node(x, y));
	}
	
	public void removeBorder(int x, int y) {
		for (int i = 0; i < borders.size(); i++) {
			Node currBorder = borders.get(i);
			if (currBorder.getX() == x && currBorder.getY() == y) {
				borders.remove(i);
			}
		}
	}
	
	//Setter methods
	public void setStart(int x, int y) {
		startNode.setXY(x, y);
	}
	
	public void setEnd(int x, int y) {
		endNode.setXY(x, y);
	}
	
	//Getter methods
	public PriorityQueue<Node> getOpenSet() {
		return openSet;
	}
	
	public ArrayList<Node> getClosedSet() {
		return closedSet;
	}
	
	public ArrayList<Node> getBorders() {
		return borders;
	}
	
	public ArrayList<Node> getPath() {
		return path;
	}
	
	public Node getStart() {
		return startNode;
	}
	
	public Node getEnd() {
		return endNode;
	}
	
	public Node getCurrNode() {
		return currNode;
	}
}
