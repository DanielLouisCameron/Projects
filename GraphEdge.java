//Daniel Cameron
//dcamer45@uwo.ca
//251239855

public class GraphEdge {
	
	GraphNode end1, end2;
	int type;
	String label;

	//initializes all given variables that are needed to represent an edge in a graph
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.end1 = u;
		this.end2 = v;
		this.type = type;
		this.label = label;
	}
	
	//getter for node u of edge
	public GraphNode firstEndpoint() {
		return this.end1;
	}
	
	//getter for node v of edge
	public GraphNode secondEndpoint() {
		return this.end2;
	}
	
	//getter for edge type
	public int getType() {
		return this.type;
	}
	
	//setter for edge type
	public void setType(int newType) {
		this.type = newType;
	}
	
	//getter for edge label
	public String getLabel() {
		return this.label;
	}
	
	//setter for edge label
	public void setLabel(String newLabel) {
		this.label = newLabel;
	}
	
	
}
