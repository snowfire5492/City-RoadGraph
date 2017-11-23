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

public class Node{
	
	final int MAXROADS = 100;						// setting a total number of roads from each city
													// there are 20 cities so 5 roads to each city is plenty
													// for the scope of this project
	
	private int distFromOrigin = Integer.MAX_VALUE;	// sets distance from origin to max poss value
	private boolean isVisited;						// used to keep track of visited nodes
	private Edge[] edges = new Edge[MAXROADS];		// making array of edges, large size to hold plenty of roads			
	
	private int preNode;							// used to keep track of predecessor city 
	
	private int numOfEdges = 0;						// counter to keep track of actual number of edges
	private int nextEdge = 0;						// counter to keep track of nextEdge index
	
	boolean isDuplicate = false;					// used for checking duplicates
	
	/**
	 * adds an edge to the node, but only if edge doesnt already exist. 				
	 * @param edge to be added
	 */
	public void addEdge(Edge edge){	
		if( numOfEdges == 0){						// first edge
			edges[nextEdge++] = edge;				// placing into array and updating counter afterwards
			++numOfEdges;							// updating numOfEdges
		}else{
			for(int i = 0; i < numOfEdges; ++i){
													// checking destNodeIndex for duplicates ( Origin should be same no matter what )
				if ( edges[i].getDestNodeIndex() == edge.getDestNodeIndex()){
					isDuplicate = true;
				}
			}	
			
			if (!isDuplicate){						// no duplicates were found
				edges[nextEdge++] = edge;				// placing into array and updating counter afterwards
				++numOfEdges;							// updating numOfEdges
			}else{
				System.out.print("Duplicate Road not added.");
			}
		}
	}
	
	/**
	 * removes edged based on argument input. since index of node containing edges is refrenced by the 
	 * origin index of an edge we only need the destIndex as input. 
	 * @param dest index of destination 
	 * @return true if edge was successfully removed, else false
	 */
	public boolean removeEdge(int dest){
		boolean edgeRemoved = false;
		int currentEdge = 0;
		Edge[] tempEdges = new Edge[MAXROADS];								// creating temp array to store NON-removed edges
		
		
		for(int i = 0; i < numOfEdges; ++i){
			if(edges[i].getDestNodeIndex() == dest){						// comparing edges		
				edgeRemoved = true;											// edge was removed so set true
			}else{
				tempEdges[currentEdge++] = edges[i];						// set tempEdges to NON-removed edges
				
			}		
		}
		
		if(edgeRemoved){
			numOfEdges--;													// decrimenting numOfEdges counter
			nextEdge--;														// decrimenting nextEdge counter
		}
		
		edges = tempEdges;													// resaving the NON-removed edges to edges array
		return edgeRemoved;
	}
	
	
	
	/**
	 * used to check if edges exist and if they do then it returns the index of its location
	 * @param edge	to look for	
	 * @return	-1 if edge doesnt exist and index if it does
	 */
	public int edgeIndex(Edge edge){
		int index = -1;
		A : for(int i = 0; i < numOfEdges; ++i){	// search thru edges array
			if(edges[i] == edge){
				index = i;							// set to located edges index
				break A;							// exit for loop once found
			}
		}
		return index;
	}
	
	/**
	 * used to check for duplicate edges by searching specific node, and checking all destination indexes.
	 * @param givenDestIndex	index to check for
	 * @return	true if a duplicate edge is found in node
	 */
	public boolean destIndexExists(int givenDestIndex){								
		boolean indexExists = false;	
		
		for(int i = 0; i < numOfEdges; ++i){
			if(edges[i].getDestNodeIndex() == givenDestIndex)								// comparing destIndex							
				indexExists = true;															// if found return true
		}
		return indexExists;																	// else false
	}
	
	/**
	 * returns the weight of a specific edge. 
	 * @param destNodeIndex ending index of edge 
	 * @return the weight or distance of edge
	 */
	public int getEdgeWeight(int destNodeIndex){
		
		int edgeWeight = 0;																	// value to return
		
		for( int i = 0; i < numOfEdges; ++ i){
			if(edges[i].getDestNodeIndex() == destNodeIndex)
				edgeWeight = edges[i].getWeight();											// returning edge weight when found
		}
		
		return edgeWeight;																	// if not found return 0
	}
	
	public int getDistFromOrigin(){
		return distFromOrigin;
	}
	
	public void setDistFromOrigin(int distFromOrigin){ 
		this.distFromOrigin = distFromOrigin;
	}
	
	public int getPreNode(){
		return preNode;
	}
	
	public void setPreNode(int predecessor ){
		preNode = predecessor;				
	}

	
	public boolean getIsVisited(){
		return isVisited;
	}
	
	public void setIsVisited(boolean isVisited){
		this.isVisited = isVisited;
	}
	
	public int getNumOfEdges(){
		return numOfEdges;
	}
	
	public Edge getEdge(int edgeIndex){
		return edges[edgeIndex];
	}
	
	public Edge[] getEdges(){
		return edges;
	}
	
	public void setEdges(Edge[] edges){
		this.edges = edges;
	}
	
	///////////// for testing /////////////
	/**
	 * used to test program, called by DirectedGraph, this method calls Edge.toString()
	 */
	public String toString(){
		String str = "";

	for(int i = 0; i < numOfEdges; ++i){
		str += edges[i].toString() + "\n";		// calling toString from Edge class
	} 

	return str;
	}
	//////////////////////////////////////
}
