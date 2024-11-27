//Daniel Cameron
//dcamer45@uwo.ca
//251239855

public class GraphNode {
	
	int name;
	boolean mark;

	//initializes a single node in the graph with a given name
	//sets mark to false
	public GraphNode(int name) {
		this.name = name;
		this.mark = false;
	}
	
	//getter for mark
	public void mark(boolean mark) {
		this.mark = mark;
	}
	
	//setter for mark
	public boolean isMarked() {
		return this.mark;
	}
	
	//getter for now
	public int getName() {
		return this.name;
	}
	
}
