/**
 * CS 241
 * Professor: Dr. Wei Sophie
 *
 * Project #3
 *
 * <create a Directed Graph data structure that allows input of citys and roads and uses Djikstra's algorithm to find shortest
 * path between two cities. Program also keeps track of the route taken on shortest path between cities. The program should be
 * able to handle ANY incorrect input and let user know how to make corrections to input. The only way the program will be able to
 * exit is if the user selects command 'e' . >
 *
 * @author Eric Schenck
 * last modified: 11/23/17
 */

import java.io.*;
import java.util.Scanner;

public class Project3 {
	public static void main(String[] args) throws IOException{
		
		final int numOfCities = 20;
		final int numOfCityDataItems = 5;
		int numOfEdges = 0;													// to hold num of edges counter
																			// 2D array to store all cityData info
		String[][] cityData = new String[numOfCities + 1][numOfCityDataItems];		// 2d array to store a copy of roadData from file		
		int[][] roadData = new int[numOfCities + 1][numOfCities + 1];
		Edge[] edges;														// to hold edges from file and load into graph
		int counter = 0;													// using for edges index 
		boolean cityIsFound = false;										// to track if city is found or not
		
		int lastOriginIndex = 0;											// used to hold last Distance search lookup
		int lastDestIndex = 0;												
		
		int originCityIndex = 0;											// used to hold index of cities entered
		int destCityIndex = 0;
		
		String temp;														// to hold temporary strings of text
						
		String cityFile = "C://Users//Eric//Desktop//city.dat";
		String roadFile = "C://Users//Eric//Desktop//road.dat";
		
		try{																// city.dat input will exit if resource file not readable
		
			File file = new File(cityFile);									// Open file
			Scanner inputFile = new Scanner(file);
			
																			// loads cities into a 2D array to be used
			for(int i = 1 ; i <= numOfCities; ++i){
				for(int j = 0; j < numOfCityDataItems; ++j){
					cityData[i][j] = inputFile.next();
				
					if(j == 2){												// used to check for extended city names
						temp = inputFile.next();
						try{
							Integer.parseInt(temp);							// if next value is a string then wont parse
							cityData[i][++j] = temp;						// if value parses then save string version to array
																			// update j
						}catch( Exception e ){ 
							String updateName = cityData[i][j];
							updateName += " " + temp;						// making the city name longer
							cityData[i][j] = updateName;
						}
					}		
				}
			}
			inputFile.close();												// closing inputFile
	
		}catch( Exception e ){												// file could not be read 
			System.out.println("city.dat resource file could not be read, Goodbye...");
			System.exit(1);													// exit program
		}
	
		try{
			
			File file = new File(roadFile);									// Open file
			Scanner inputFile = new Scanner(file);
			
			while(inputFile.hasNext()){										// loads 2d array with edge information
				int i = Integer.parseInt(inputFile.next());
				int j = Integer.parseInt(inputFile.next());
				roadData[i][j] = Integer.parseInt(inputFile.next());
				++numOfEdges;
			}
			
			inputFile.close();  											// closing inputFile stream-
			
		}catch(Exception e){												// file could not be read
			System.out.println("road.dat resource file could not be read, Goodbye...");
			System.exit(1);													// exit program
		}
		
		edges = new Edge[numOfEdges];										// setting size of array
		
		for(int i = 1; i <= numOfCities; ++i){
			for(int j = 1; j <= numOfCities; ++j){
				
				if(roadData[i][j] != 0){									// edges array to pass to graph
					edges[counter++] = new Edge(i,j,roadData[i][j]);		// fill array with edges
				}
			}
		}
		
		DirectedGraph graph = new DirectedGraph(edges);						// creating the graph from edges given
		Scanner keyboard = new Scanner(System.in);
		
		E: while(true){														// only exits when command E is entered
		Jumpto:	if(true){													// used to jump back to if wrong command is entered									
			char tempChar = 'a'; 											// used to store command character
			
			System.out.print("Command? ");
			
			String userSel = keyboard.nextLine();							// user input
			String[] splitUserSel = userSel.split("\\s+"); 					// used to handle possible spaces
		 
			splitUserSel = handleInitialSpaces(splitUserSel);				// handling initial space issue
			
			try{															//check for 2nd entry
				if(splitUserSel[1] != null){
					System.out.println("Please enter only one Command.");
					break Jumpto;											// breaks to end of while loop, therefore relooping to command prompt
				}
			}catch(Exception e){}
			
			try{															// checks if a two character command was entered
				splitUserSel[0].charAt(1);								
				System.out.println("Please enter only one character next time as a command.");
				break Jumpto;												// breaks to end of while loop, therefore relooping to command prompt
			}catch(Exception e){}
			
			try{															// gathers command character
				tempChar = splitUserSel[0].toLowerCase().charAt(0);	
			} catch( Exception e ){											// incase of wrong command entry
				System.out.println("Please enter a character next time.");
				break Jumpto;												// breaks to end of while loop, therefore relooping to command prompt
			}
			
			
																			// used for User Interface
			switch(tempChar) {
				case 'q': 													// Query city info by entering city code
					
					String[] queryInfo = new String[numOfCityDataItems];		// used to store cityInfo 
					System.out.print("City Code: "); 							// prompt for input
					userSel = keyboard.nextLine();								// user input
					splitUserSel = userSel.split("\\s+");						// user to handle possible spaces
					cityIsFound = false;										// resetting to false 
								
					splitUserSel = handleInitialSpaces(splitUserSel);			// handling initial space issue
					
					queryInfo[0] = splitUserSel[0].toLowerCase();				// gathers city code into queryInfo[0]
				
					try{														//check for 2nd entry and break if found	
						if(splitUserSel[1] != null){
							System.out.println("Please enter only one City code.");
							break;
						}
					}catch(Exception e){}

					
					for(int i = 1; i <= numOfCities ; ++i){						// checks for city code and prints data
						
						if( cityData[i][1].toLowerCase().compareTo(queryInfo[0].toLowerCase()) == 0 ){	
							for(int j = 0; j < numOfCityDataItems ; ++j){
																				// printing out City Information
								System.out.print(cityData[i][j].toUpperCase() + " ");
							}
							cityIsFound = true;
							System.out.println();								// used for print formatting
						}
						
						if( i == numOfCities  && cityIsFound != true ){			// reached last city and not found
							System.out.println("City code " + queryInfo[0] + " doesn't exist.");
						}
					}
					break;
					
				case 'd':														// find minimum distance between cities
					String[] cityInfo = new String[numOfCityDataItems];			// used to store cityInfo 
					String routeCityNames = "";									// used to store thru route string
					int[] minRouteIndices = new int[numOfCities];				// used to store city to city pathway
					int minRouteCounter = 0;
					int predIndex = 0;											// used to hold predecessor index
					
					
					
					System.out.print("City Codes: "); 							// prompt for input
					userSel = keyboard.nextLine();								// user input
					splitUserSel = userSel.split("\\s+");						// user to handle possible spaces
					
					originCityIndex = 0;
					destCityIndex = 0;											// resetting to zero
					
					
					splitUserSel = handleInitialSpaces(splitUserSel);			// handling initial space issue
					
					try{
						if(splitUserSel[0].compareTo(splitUserSel[1]) == 0){	// incase only one value is entered
							System.out.println("Please enter two city codes next time.");
							break;												// a third entry was made by user so try again
						}else if(splitUserSel[2] != null && splitUserSel[1].compareTo(splitUserSel[2]) != 0){				
							System.out.println("Please only enter two city codes next time.");
							break;
						}
					}catch(Exception e){}
					
																				// gathers city code info and neglects spaces
					cityInfo[0] = splitUserSel[0].toLowerCase();
					try{
						cityInfo[1] = splitUserSel[1].toLowerCase();
					
					}catch(Exception e){
						System.out.println("Please enter two city codes next time.");
						break;
					}
		
					originCityIndex = checkOrigin( cityData, cityInfo, numOfCities );// checking origin city, saving index
					destCityIndex = checkDest( cityData , cityInfo , numOfCities ); // checking destination city, saving index
					
					if(originCityIndex == 0 || destCityIndex == 0){					// if either is returned zero then 
						break;														// at least one of the cities doesnt exist 
					}																// so break to command prompt
					
					if(lastOriginIndex == originCityIndex && lastDestIndex == destCityIndex){	// if repeating query
						System.out.println("This query has already been made.");
						break;
					}
					
					graph.findShortestPath(originCityIndex , destCityIndex);		// finding shortest path to destination
		
					if(graph.getNode(destCityIndex).getDistFromOrigin() != Integer.MAX_VALUE){	// A road exists
						
						lastOriginIndex = originCityIndex;							// storing last indices to check for repeating querys
						lastDestIndex = destCityIndex;
						
						System.out.print("The minimum distance between " + cityData[originCityIndex][2] + " and "
								+ cityData[destCityIndex][2] + " is " + graph.getNode(destCityIndex).getDistFromOrigin()
								+ " through the \nroute: ");
						
						predIndex = graph.getNode(destCityIndex).getPreNode(); 		// get predecessor node index
						minRouteIndices[minRouteCounter++] = destCityIndex;			// place destIndex in array and increment counter
						
						A : while(true){											// used to load all pred into array until originIndex is pred
							
							minRouteIndices[minRouteCounter++] = predIndex;			// place predecessorIndex in array and increment counter
							
							if(predIndex == originCityIndex){						// if predecessor is same as origin then exit while
								break A;
							}
							predIndex = graph.getNode(predIndex).getPreNode();  	// gets next predecessor Index
						}
						
						minRouteCounter -= 1;										// adjusting counter for accurate printout
						
						while(minRouteCounter >= 0){								// while routes exist in array 
								
							routeCityNames += cityData[minRouteIndices[minRouteCounter--]][2];       // used print city name and decrement counter
							
							if(minRouteCounter >= 0){
								routeCityNames += ", ";								// formatted commas
							}else{
								System.out.println(routeCityNames + ".");	  		// formatted printout with a period
							}
						}
					}else{																		// road does not exist
						System.out.println("The path from " + cityData[originCityIndex][2] + " to "
								+ cityData[destCityIndex][2] + " doesn't exist.");
					}
					
					break;
					
				case 'i': 											// insert a road by entering two city codes and distance
					
					int tempWeight = 0;											// used to store distance entered by user
					String[] tempStr = new String[numOfCityDataItems];			// used to store cityInfo 
					System.out.print("City Codes and distance: "); 				// prompt for input
					userSel = keyboard.nextLine();								// user input
					splitUserSel = userSel.split("\\s+");						// user to handle possible spaces
				
					lastOriginIndex = 0;										// last indices ressetting since
					destCityIndex = 0;											// changes to list being made it may
																				// change the path 
					
					originCityIndex = 0;
					destCityIndex = 0;											// resetting to zero
					
					splitUserSel = handleInitialSpaces(splitUserSel);			// handling initial space issue
								
					try{
						tempStr[0] = splitUserSel[0].toLowerCase();				// grabbing first term
					}catch(Exception e){										// if only space is entered the try again
						System.out.println("Please enter two city codes and a distance.");
						break;
					}
					
					try{
						if(tempStr[0] != splitUserSel[1].toLowerCase()){		// only one  term was entered
							tempStr[1] = splitUserSel[1].toLowerCase();
						}else{
							tempStr[1] = null;
							System.out.println("Please enter two city codes and a distance.");
							break;
						}
					}catch(Exception e){									
						System.out.println("Please enter two city codes and a distance.");
						break;
					}
					
																			
					try{														//check for 4th entry and break if found
						if(splitUserSel[3] != null){
							System.out.println("Please enter only two city codes and one distance.");
							break;
						}
					}catch(Exception e){}
					
																				
					try{														// checks for a third entry 
						tempWeight = Integer.parseInt(splitUserSel[2]); 		// also checking for int based distance
						
					}catch(Exception e){
						System.out.println("Please enter the distance as a decimal value.");
						break;
					}
					
					
					originCityIndex = checkOrigin( cityData, tempStr, numOfCities );// checking origin city, saving index
					destCityIndex = checkDest( cityData , tempStr , numOfCities ); 	// checking destination city, saving index
					
					if(originCityIndex == 0 || destCityIndex == 0){					// if either is returned zero then 
						break;														// at least one of the cities doesnt exist 
					}																// so break to command prompt
					
					
					if(graph.addEdge(originCityIndex, destCityIndex, tempWeight) == -1){	// trying to add edge into graph
																							// and checking if edge was duplicate
						System.out.println("The road from " + cityData[originCityIndex][2] + " to "
								+ cityData[destCityIndex][2] + " already exists.");
					}else{																	// else add function worked so inform user
						System.out.println("You have inserted a road from " + cityData[originCityIndex][2] + " to " 
								+ cityData[destCityIndex][2] + " with a distance of " 
								+ graph.getEdgeWeight(originCityIndex, destCityIndex));
					}
	
					break;
					
				case 'r': 										// remove an existing road by entering two city codes
					
					String[] tempStrn = new String[numOfCityDataItems];			// used to store cityInfo 
					System.out.print("City Codes: "); 							// prompt for input
					userSel = keyboard.nextLine();								// user input
					splitUserSel = userSel.split("\\s+");						// user to handle possible spaces

					lastOriginIndex = 0;										// last indices ressetting since
					destCityIndex = 0;											// changes to list being made it may
																				// change the path 
					
					originCityIndex = 0;
					destCityIndex = 0;											// resetting to zero
					
					splitUserSel = handleInitialSpaces(splitUserSel);			// handling initial space issue
								
					try{
						tempStrn[0] = splitUserSel[0].toLowerCase();				// grabbing first term
					}catch(Exception e){											// if only space is entered the try again
						System.out.println("Please enter two city codes.");
						break;
					}
					
					
					try{
						if(tempStrn[0] != splitUserSel[1].toLowerCase()){		// only one  term was entered
							tempStrn[1] = splitUserSel[1].toLowerCase();
						}else{
							tempStrn[1] = null;
							System.out.println("Please only enter two city codes.");
							break;
						}														 
					}catch(Exception e){									
						System.out.println("Please only enter two city codes.");
						break;
					}
					
					try{														// three or more terms were entered
						if(splitUserSel[2] != null && splitUserSel[1].compareTo(splitUserSel[2]) != 0){				
							System.out.println("Please only enter two city codes.");
							break;
						}
					}catch(Exception e){}
									
					originCityIndex = checkOrigin( cityData, tempStrn, numOfCities );// checking origin city, saving index
					destCityIndex = checkDest( cityData , tempStrn , numOfCities ); // checking destination city, saving index
					
					if(originCityIndex == 0 || destCityIndex == 0){					// if either is returned zero then 
						break;														// at least one of the cities doesnt exist 
					}																// so break to command prompt
					
					
					if(graph.edgeExists(originCityIndex, destCityIndex)){			// if edge exists remove it
						
						if(graph.removeEdge(originCityIndex, destCityIndex)){		// removesEdge and checks for true value
							System.out.println("The road between " + cityData[originCityIndex][2] + " and "
									+ cityData[destCityIndex][2] + " has been removed.");
						}
						
					}else{															// else print appropriate msg
						System.out.println("The road between " + cityData[originCityIndex][2] + " and "
								+ cityData[destCityIndex][2] + " doesn't exist.");
					}
					
					break;
					
				case 'e':															// used to exit program
					System.out.print("Thank you for using my program!");
					break E;
					
				case 'h': 															// display commands 
					System.out.println("Q Query the city information by entering the city code.");
					System.out.println("D Find the minimum distance between two cities.");
					System.out.println("I Insert a road by entering two city codes and distance.");
					System.out.println("R Remove an existing road by entering two city codes.");
					System.out.println("H Display this message.");
					System.out.println("E Exit.");
					break;
					
				default:
					System.out.println("Incorrect Entry, try again.");
					break;
			}
		
			}					
		
		} // end of while loop
		
		
		keyboard.close();   			// closing keyboard input
		

		////////////////////////////////////////////////////////////////////////////////
		// Used to printout all graph info for testing. 
		
		//System.out.print(graph.toString());

		//////////////////////////////////////////////////////////////////////////
		
	}

	/**
	 * used to check if Origin city already exists
	 * @param cityData data from file to compare with
	 * @param cityInfo data from input 
	 * @param numOfCities used for iteration bound
	 * @return the index that the origin city is referenced by
	 */
	public static int checkOrigin( String[][] cityData , String[] cityInfo , int numOfCities){
			int originCityIndex = 0;
			
		for(int i = 1; i <= numOfCities ; ++i){					// checks for cities A and B in list
				
			if( cityData[i][1].toLowerCase().compareTo(cityInfo[0]) == 0 ){
				originCityIndex = i;							// city A or OriginCity has been found
			}
			if( i == numOfCities  && originCityIndex == 0){	// reached last city and not found
				System.out.println("City code " + cityInfo[0] + " doesn't exist.");	
			}
		}
		return originCityIndex;
	}
	
	/**
	 * used to check if destination city already exists
	 * @param cityData data from file to compare with
	 * @param cityInfo data from input 
	 * @param numOfCities used for iteration bound
	 * @return the index that the destination city is referenced by
	 */
	public static int checkDest( String[][] cityData , String[] cityInfo , int numOfCities){
		int destCityIndex = 0;
		
	for(int i = 1; i <= numOfCities ; ++i){					// checks for cities A and B in list
			
		if( cityData[i][1].toLowerCase().compareTo(cityInfo[1]) == 0 ){	
			destCityIndex = i;							// city B or destCity has been found
		}
		if( i == numOfCities  && destCityIndex == 0 ){	// reached last city and not found
			System.out.println("City code " + cityInfo[1] + " doesn't exist.");
		}
	}
	return destCityIndex;
	}
	
	/**
	 * checks for an initial space entered and re-adjusts String array if initial space is found
	 * @param splitUserSel
	 * @return
	 */
	public static String[] handleInitialSpaces(String[] splitUserSel){

		try{
			splitUserSel[0].charAt(0);			// handling issue of initial entered space
		}catch(Exception e){
		
			for(int i = 0; i < splitUserSel.length - 1 ; ++i){
				splitUserSel[i] = splitUserSel[i+1];
				splitUserSel[i+1] = null;
			}
		}
		return splitUserSel;
	}
}