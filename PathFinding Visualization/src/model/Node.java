package model;
import java.util.ArrayList;

// Node class used in the algorithm

public class Node implements Comparable<Node>{

	int x; //X position on the grid
	int y; //Y position on the grid
	int gScore; //Distance from start node
	int hScore; //Distance from end node
	int fScore; //gCost + hCost
	Node previous; //Previous node that this node came from
	ArrayList<Node> neighbors;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		neighbors = new ArrayList<Node>();
	}
	
	//Find surrounding neighbors of this node
	public void findNeighbors(int windowWidth, int windowHeight, int cellSize) {
		if (y > 0) {
			neighbors.add(new Node(x, y - 1));
		}
		if (y < (windowHeight / cellSize) - 1) {
			neighbors.add(new Node(x, y + 1));
		}
		if (x > 0) {
			neighbors.add(new Node(x - 1, y));
		}
		if (x < (windowWidth / cellSize) - 1) {
			neighbors.add(new Node(x + 1, y));
		}
	}
	
	//Method to compare f costs of nodes
	@Override
	public int compareTo(Node otherNode) {
		if (this.fScore < otherNode.getF()) {
			return -1;
		}
		else if (this.fScore > otherNode.getF()) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	@Override
	public boolean equals(Object other) {
		//Make sure other object is a node
		if (other == null) { 
			return false;
		}
		if (!(other instanceof Node)) {
			return false;
		}
		//If it passes the first 2 checks, safe to compare this node to the other one
		Node otherNode = (Node) other;
		if (this.x == otherNode.getX() && this.y == otherNode.getY()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Getter methods
	public ArrayList<Node> getNeighbors() {
		return neighbors;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getG() {
		return this.gScore;
	}
	
	public int getH() {
		return this.hScore;
	}
	
	public int getF() {
		return this.fScore;
	}
	
	public Node getPrevious() {
		return this.previous;
	}
	
	//Setters for x and y coordinates, as well as g, h, and f cost, and previous node
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setG(int gCost) {
		this.gScore = gCost;
	}
	
	public void setH(int hCost) {
		this.hScore = hCost;
	}
	
	public void setF(int fCost) {
		this.fScore = fCost;
	}
	
	public void setPrevious(Node previousNode) {
		this.previous = previousNode;
	}
	
}
