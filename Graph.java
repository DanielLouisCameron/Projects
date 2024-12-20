//Daniel Cameron
//dcamer45@uwo.ca
//251239855

import java.util.ArrayList;
import java.util.Iterator;

public class Graph implements GraphADT{
	
	private ArrayList<GraphNode> nodes;
	private ArrayList<GraphEdge> edges;
	
	//constructor for graph. takes in amount of nodes
	//initializes nodes and stores them in an arraylist
	public Graph(int n) {
		
		this.nodes = new ArrayList<GraphNode>();
		for(int i=0;i<n;i++) {
			GraphNode node = new GraphNode(i);
			nodes.add(node);
		}
		this.edges = new ArrayList<GraphEdge>();
	}

	//takes in 2 nodes and insert an edge between them as long as the nodes exist in the graph and the edge 
	//does not already exist
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		if(nodes == null || !nodes.contains(nodeu) || !nodes.contains(nodev)) {
			throw new GraphException("node unfortunately does not exist");
		}
		
		if(edges != null) {
			for(GraphEdge e : edges) {
				if(nodeu == e.firstEndpoint() && nodev == e.secondEndpoint() ) {//|| nodev == e.firstEndpoint() && nodeu == e.secondEndpoint()
					throw new GraphException("edge already exists");
				}
			}
		}

		GraphEdge e = new GraphEdge(nodeu, nodev, type, label);
		edges.add(e);
	}

	//getter for node given its name
	public GraphNode getNode(int u) throws GraphException {
		if(nodes!=null) {
			for(GraphNode node : nodes) {
				if(node.getName() == u) {
					return node;
				}
			}
		}
		throw new GraphException("Node dont exist");
	}

	
	//given a node, returns an iterator over all
	//edges that include that node in it
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		if(nodes == null || !nodes.contains(u)) {
			throw new GraphException("node doesnt exist");
		}
		ArrayList<GraphEdge> incident = new ArrayList<GraphEdge>();
		if(edges != null) {
			for(GraphEdge e : edges) {
				if(e.firstEndpoint() == u || e.secondEndpoint() == u) {
					incident.add(e);
				}
			}
		}
		if(incident.isEmpty()) {
			return null;
		}
		return incident.iterator();		
	}

	
	//returns the edge between 2 nodes if it exists
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		
		if(nodes == null || !nodes.contains(u)|| !nodes.contains(v)) {
			throw new GraphException("nodes dont exist");
		}
		
		if(edges != null) {
			for(GraphEdge e : edges) {
				if(u == e.firstEndpoint() && v == e.secondEndpoint() 
					|| v == e.firstEndpoint() && u == e.secondEndpoint()) {
					return e;
				}
			}
		}
		throw new GraphException("edge dont exist");
	}
	

	//returns true if there is an edge between the given 2 nodes, otherwise returns false
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		if(nodes == null || !nodes.contains(u)|| !nodes.contains(v)) {
			throw new GraphException("nodes dont exist");
		}
		try {
			GraphEdge edge = getEdge(u, v);
		}
		catch (GraphException e) {
			return false;
		}
		
		return true;
	}
	
}
