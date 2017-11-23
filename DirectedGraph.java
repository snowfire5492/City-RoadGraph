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
public class DirectedGraph{
	
	private int numberOfNodes = 20;						// setting numberOfNodes to 20 since it is known
	private Node[] nodes = new Node[numberOfNodes + 1];	// used to hold all nodes in graph
	private MinHeap priorityQ = new MinHeap();			// creating a priority Queue using MinHeap
	
														// constructor
	public DirectedGraph(Edge[] edges){
		 								
		for(int i = 1; i < nodes.length; ++i){
			this.nodes[i] = new Node();					// creating all nodes to be given edges
		}
		
														// going through all edges
		for(int currentEdge = 0; currentEdge < edges.length; ++currentEdge){						
											// this doesnt allow for duplicate roads to be stored ( -1 == DNE )
			if(nodes[edges[currentEdge].getSourceNodeIndex()].edgeIndex(edges[currentEdge]) == -1){
											// adding edges into appropriate nodes in graph
				nodes[edges[currentEdge].getSourceNodeIndex()].addEdge(edges[currentEdge]);
											// only goes into the source node since this is a directional graph		
			}
		}
	}
	
	
	/**
	 * used to return number of nodes in graph 
	 * @return
	 */
	public int getNumberOfNodes(){
		return numberOfNodes;
	}
	
	/**
	 * used to access individual nodes 
	 * @param nodeIndex
	 * @return
	 */
	public Node getNode(int nodeIndex){
		return nodes[nodeIndex];
	}
	
	/**
	 * function creates an edge object and adds the edge to an origin node using the node.addEdge() method
	 * @param originIndex node that the edge will be added too 
	 * @param destIndex index of destination city
	 * @param weight distance between cities
	 * @return -1 if edge couldnt be added, else return 0
	 */
	public int addEdge(int originIndex , int destIndex , int weight ){
		
		int testValue = - 1;															// used to store test value to return 
		
		if(!nodes[originIndex].destIndexExists(destIndex)){								// if edge doesnt exist
			Edge tempEdge = new Edge(originIndex , destIndex , weight );				// creating an edge to add to nodes
			nodes[originIndex].addEdge(tempEdge);										// adding edge to origin node 
			testValue = 0;	
		}
		return testValue;																// return -1 if edge already exists
	}																					// otherwise returns 0
	
	/**
	 * function checks if edgeExists and returns true if it infact does
	 * @param originIndex
	 * @param destIndex
	 * @return false if edge DOES NOT exist
	 */
	public boolean edgeExists(int originIndex, int destIndex){
		boolean edgeExists = false;
		
		for(int i = 0; i < nodes[originIndex].getNumOfEdges(); ++i){					// going thru array of nodes
			if(nodes[originIndex].destIndexExists(destIndex)){							// checking if edges are equal
			edgeExists = true;															// if they are they set true
			}	
		}
		return edgeExists;
	}
	
	/**
	 * calling removeEdge from the target node and returning boolean to check if removed
	 * @param origin Index of origin node
	 * @param dest index of destination node
	 * @return true if edge was removed, false if not
	 */
	public boolean removeEdge(int originIndex, int destIndex){
		boolean edgeRemoved = false;
		
		if(nodes[originIndex].removeEdge(destIndex)){									// removing edge from target node
			edgeRemoved = true;															// and set edgeRemoved == true for check
		}
		return edgeRemoved;
	}
	
	
	/**
	 * finding shortest path from originCity to destinationCity using Dijkstra's Algorithm
	 * Note that shortest path is stored in destinationCity Node as distFromOrigin 
	 * and preNode() will return the predecessor of each node making it possible to track the route through each city
	 * @param originCity
	 * @param destCity
	 */
	public void findShortestPath(int originCity, int destCity){
		
		Node currentCityNode;														// used to store node after dequeue
		
		for(int i = 1; i <= numberOfNodes; ++i){
			nodes[i].setDistFromOrigin(Integer.MAX_VALUE);   						// resetting all nodes to "infinity" - note: its just the max value possible
			nodes[i].setIsVisited(false); 											// all nodes initially set to NOT visited
			nodes[i].setPreNode(0);													// resetting all preNode values to 0
		}
		nodes[originCity].setDistFromOrigin(0);										// origin node getting set to 0 distance
		
		
		checkRoads(nodes[originCity]);												// performs Djisktra edge check and 
																					// sets node isVisited = true and
																					// minHeaps all destination city nodes
		
		while(!priorityQ.heapIsEmpty()){											// while heap contains nodes
			
			currentCityNode = priorityQ.dequeue();									// getting min value node
			checkRoads(currentCityNode);											// checking edges of current Node
		}
		// after while loop, the heap is empty and the shortest distance from origin city to dest city is found	
	}
	
	
	/**
	 * function checks roads in given city node, sets node isVisited = true, fills prioriyQ with destination cities
	 * sets each destination city node distance variable equal to predecessor node distanceFromOrigin + edge weight
	 * and sets predecessor variable to predecessor city index. 
	 * @param currentCityNode
	 */
	public void checkRoads(Node currentCityNode){
		if(!currentCityNode.getIsVisited()){										// only check roads if city isVisited = false
			
			for(int i = 0; i < currentCityNode.getNumOfEdges(); ++i){				// goes thru each edge or road 
																		
			
				int destIndex = currentCityNode.getEdge(i).getDestNodeIndex();		// getting destination index at current edge
			
				int currentEdgeWeight = currentCityNode.getEdge(i).getWeight();      	// getting edge weight at current edge
				currentEdgeWeight += currentCityNode.getDistFromOrigin();				// adding current node dist to edge weight
																	// if node dist from origin > previous dist + edge weight
				if(nodes[destIndex].getDistFromOrigin() > currentEdgeWeight){
					nodes[destIndex].setDistFromOrigin(currentEdgeWeight);				// setting distfromOrigin to new weight
					nodes[destIndex].setPreNode(currentCityNode.getEdge(i).getSourceNodeIndex());
																						// setting the preNode to city of origin 
				}
				
				if(!nodes[destIndex].getIsVisited()){				// if destination node IsVisited = false
																	// add destination node to MinHeap using DistFromOrigin as value
					priorityQ.addSequential(nodes[destIndex]);			
				}
				
			}
			currentCityNode.setIsVisited(true);  						 				// node has been checked so set isVisited true
		}
	}
	
	/**
	 * used to getEdgeWeight for printout to console
	 * @param originIndex 
	 * @param destIndex
	 * @return the weight in an edge that has just been added
	 */
	public int getEdgeWeight(int originIndex , int destIndex){
		
		return nodes[originIndex].getEdgeWeight(destIndex);		
	
	}

	////////////////	 For Testing 	//////////////////////
	/**
	 * used to test program, calls Node.toString() which calls Edge.toString()
	 */
	public String toString(){
		String str = "";
		
		// for printing out a test check of nodes and edges
		for(int i = 1; i < nodes.length; ++i){
			str += ("Node( " + (i) + " )\n" + nodes[i].toString());
		}	
		
		return str;
	}
	//////////////////////////////////////////////////////////
	
}	// end of class
