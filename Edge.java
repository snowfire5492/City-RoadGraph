/**
 * CS 241
 * Professor: Dr. Wei Sophie
 *
 * Project #3
 *
 * 
 * <create a Directed Graph data structure that allows input of citys and roads and uses Djikstra's algorithm to find shortest
 * path between two cities. Program also keeps track of the route taken on shortest path between cities. The program should be
 * able to handle ANY incorrect input and let user know how to make corrections to input. The only way the program will be able to
 * exit is if the user selects command 'e' . >
 *
 * @author Eric Schenck
 * last modified: 11/23/17
 */
public class Edge {
	
	private int sourceNodeIndex;	// hold Index of node A
	private int destNodeIndex;		// hold Index of node B 
	private int weight;				// hold value from A to B 
												
									// constructor
	public Edge( int sourceNodeIndex, int destNodeIndex, int weight){
		this.sourceNodeIndex = sourceNodeIndex;
		this.destNodeIndex = destNodeIndex;
		this.weight = weight;
	}
	
	
	public int getSourceNodeIndex(){
		return sourceNodeIndex;
	}
	
	public int getDestNodeIndex(){
		return destNodeIndex;
	}
	
	public int getWeight(){
		return weight;
	}

	
	//////////// for testing ///////////////
	/**
	 * used to test program, called by Node and DirectedGraph
	 */
	public String toString(){
		String str = " ";
		
		str = sourceNodeIndex + " " + destNodeIndex +  "  " + weight;
		
		return str;
	}
	////////////////////////////////////////
}
