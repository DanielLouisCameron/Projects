//Daniel Cameron
//dcamer45@uwo.ca
//251239855

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Maze {

	private Graph graph;
	private int scaleFactor;
	private int width;
	private int length;
	private int numCoins;
		
	private final char entrance = 's';
	private final char exit = 'x';
	private final char room = 'o';
	private final char corridor = 'c';
	private final char wall = 'w';
	
	private int entranceIndex;
	private int exitIndex;
	int rowLen;
	int colLen;
	private int nodeCount = 0;
	
	
	//constructor for maze. takes in a formatted file and creates an undirected graph containing
	//all information for the graph
	public Maze(String inputFile) throws MazeException, GraphException {

		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			
			this.scaleFactor = Integer.parseInt(br.readLine().trim());
			this.width = Integer.parseInt(br.readLine().trim());
			this.length = Integer.parseInt(br.readLine().trim());
			this.numCoins = Integer.parseInt(br.readLine().trim());
			this.graph = new Graph(length*width);
					
			rowLen = 2*length -1;
			colLen = 2*width -1;
			
			for(int row = 0;row < rowLen;row++) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				line.trim();
				for(int col = 0;col < colLen;col++) {
					
					if(line.charAt(col) == entrance) {
						entranceIndex = nodeCount;
					}
					else if(line.charAt(col) == exit) {
						exitIndex = nodeCount;
					}
					//if statements checking if current spot and next 2 spots go node -> edge -> node horizontally
					if(line.charAt(col) == room || line.charAt(col) == entrance || line.charAt(col) ==exit) {
						if(col + 2 < colLen && 
							(line.charAt(col+1) == corridor || Character.isDigit(line.charAt(col+1)) ) ) {
							if(line.charAt(col+2) == room || 
								line.charAt(col+2) == entrance || line.charAt(col+2) == exit ) {
								horizAdd(line.charAt(col+1));
							}
						}
						//if statements checking if current spot and next 2 spots go node -> edge -> node vertically
						if(line.charAt(col) == room || line.charAt(col) == entrance || line.charAt(col) == exit) {
							if(row + 2 < rowLen) {
								br.mark(3*rowLen);
								String l1 = br.readLine();
								if(l1 != null) {
									l1.trim();
								
									if(l1.charAt(col) == corridor || Character.isDigit(l1.charAt(col))) {
										String l2 = br.readLine();
										if(l2 != null) {
											
											l2.trim();
											if(l2.charAt(col) == room || l2.charAt(col) == entrance || l2.charAt(col) == exit) {
												vertAdd(l1.charAt(col), l1, line, col);		
												
											}
										}
									}
								}
								//reset as we jumped ahead 2 lines to test vertical connections
								br.reset();
							}
						}
						
						nodeCount++;
					}
					else if(line.charAt(col) == corridor || line.charAt(col) == wall || Character.isDigit(line.charAt(col)) ) {
						continue;
					}
					else {//if input is not any of the formatted maze specifiers, throws exception
						throw new MazeException("File Input is not correct");
					}
				}
			}
		} 
		catch (FileNotFoundException e) {
			throw new MazeException("File not found");
		} 
		catch (IOException e) {
			throw new MazeException("Incorrect Input");
		}
	}
	
	//helper method for constructor, takes in current node and adds an edge
	//between current and next node
	//all boundaries have already been tested in constructor
	private void horizAdd(char c) throws GraphException {
		try {
			//check to see if there are coins associated with the edge
			int weight = Character.getNumericValue(c);
			if(weight < 0 || weight > 9) {
				weight = 0;
			}

			graph.insertEdge(graph.getNode(nodeCount), graph.getNode(nodeCount+1), weight, "abc");
		}
		
		catch (GraphException excep) {
			System.out.println("graph exceptioooon (horizAdd)");
		}

	}
	
	//helper method for constructor, same as horizAdd
	//but needs the file line below so that it can test
	//if the edge being inserted has coins associated with it
	private void vertAdd(char c, String l1, String line, int index) {
		try {

			
			int weight = Character.getNumericValue(l1.charAt(index));
			if(weight < 0 || weight > 9) {
				weight = 0;
			}
			graph.insertEdge(graph.getNode(nodeCount), graph.getNode(nodeCount+width), weight, "sui");
			
		}
		catch (GraphException excep) {
			System.out.println("graph exceptioooon (vertAdd)");
		}
	}
	
	//getter for graph
	public Graph getGraph() throws MazeException {
		if (graph == null) {
			throw new MazeException("graph is null");
		}
		return this.graph;
	}
	

	//uses a recursive dfs algorithm with stacks 
	//returns a path from maze's entrance to exit
	public Iterator solve() throws GraphException {
		
		GraphNode curr = graph.getNode(entranceIndex);
		LinkedList<GraphNode> stack = new LinkedList<>();
		LinkedList<GraphNode> stack2 = new LinkedList<>();

		stack.push(curr);
		stack2 = dfs(curr, stack, numCoins);
		
		if(stack2 == null) {
			return null;
		}
		
		return stack2.iterator();
	}
	
	//recursive dfs algorithm takes in current node, path already taken, and amount of coins left
	//recursively calls itself until path is given or it is determined that no solution exists
	private LinkedList<GraphNode> dfs(GraphNode current, LinkedList<GraphNode> path, int coins) throws GraphException {
		//checks if current node is exit
        if (current.getName() == exitIndex && coins >= 0) {
            path.push(current);
            return path;
        }

        current.mark(true);
        Iterator<GraphEdge> edgesIterator;
        try {
            edgesIterator = graph.incidentEdges(current);
        } catch (GraphException e) {
            return null;
        }
        //iterates through all edges adjacent to node
        while (edgesIterator.hasNext()) {
            GraphEdge edge = edgesIterator.next();
            GraphNode node = (current == edge.secondEndpoint()) ? edge.firstEndpoint() : edge.secondEndpoint();
            if (!node.isMarked() && coins - edge.getType() >= 0) {
                coins -= edge.getType();
                path.push(node);
                
                if (node.getName() == exitIndex && coins >= 0) {
                    return path;
                }
                
                LinkedList<GraphNode> result = dfs(node, path, coins); 
                if(result != null) {
                	return result;
                }
                coins += edge.getType();
            	path.pop();
            }
        }
		return null;
	}
	
	

}
