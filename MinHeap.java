public class MinHeap{

	private final int HEAP_SIZE = 30000001;			// index 1 - 100000. neglect index 0
	private int[] heap;
	private int lastIndex = 0;					// last index initialized at 0
	
	public MinHeap(){
		heap = new int[HEAP_SIZE];				// making a MaxHeap array size 101 [0,100]
	}
	
	/**
	 * Adds values to Heap array, comparing values systematically to
	 * form correct Heap structure when completed ( uses simple Sequential method)
	 * @param entry new value
	 * @return number of swaps to complete add 
	 */
	public int addSequential(int entry) {
		
		int currentIndex = lastIndex + 1;		// keeps track of currentIndex 
		int parentIndex = currentIndex / 2;		// keeps track of parent node index
		int numOfSwaps = 0;						// to record number of swaps required  
		
		while ( (parentIndex != 0) && (heap[parentIndex] > entry)){
			heap[currentIndex] = heap[parentIndex];	// swaps parent into child node
			currentIndex = parentIndex; 			// sets currentIndex to index parent was at
			parentIndex = currentIndex / 2; 		// re-adjust parentIndex for new currentIndex
			++numOfSwaps; 							// counts swap that took place
		}
		heap[currentIndex] = entry;					// now add the new entry to the heap
		++lastIndex;								// increase to keep track of last index
	
		return numOfSwaps;							// returns number of swaps needed 
	}
	

	/**
	 * removes root of heap, rootIndex, and performs reheap
	 * @return
	 */
	public Node dequeue() {
		
		Node root = new Node(heap[1]);						// grabbing the Min value (root of tree)
		heap[1] = heap[lastIndex];					// swapping min value to last leaf node					
		--lastIndex;
		
		adjustHeap();								// performs reheap									
		return root;
	}

	/**
	 * Performs adjustment to heap 
	 * @param rootIndex
	 * @return number of swaps to complete reheap()
	 */
	public int reheap(int rootIndex) {
		int numOfSwaps = 0;							// to keep track of number of swaps
		
		int parent = heap[rootIndex];	// saving parent value from heap[rootIndex]
		int leftChildIndex = 2 * rootIndex;			// saving leftChildIndex of rootIndex
		
		A: while (leftChildIndex <= lastIndex){		// will not go past lastIndex of array
			
			int smallerChildIndex = leftChildIndex;	// assumption, will be checked for validity
			
			int rightChildIndex = leftChildIndex + 1; // getting location of left child
			
			if ((rightChildIndex <= lastIndex) && (heap[rightChildIndex] < heap[leftChildIndex])){
													// if rightChildIndex exists and if 
				smallerChildIndex = rightChildIndex;	// right Child is smaller than left Child
			} 										// set largerChildIndex = rightChildIndex;
			
			if( (smallerChildIndex <= lastIndex) && (heap[smallerChildIndex]) < parent){  	
													// if the parent is larger than the largest child, and child exists on array
				heap[rootIndex] = heap[smallerChildIndex];	// smaller Child becomes parent 
				rootIndex = smallerChildIndex;				// adjusting rootIndex location
				leftChildIndex = 2 * rootIndex;				// adjusting leftChildIndex
				++numOfSwaps;								// counting swap
			}else
				break A;									// exits while loop
		}
		
		heap[rootIndex] = parent;							// parent is saved to rootIndex location which may be updated 
		
		return numOfSwaps;									// returns number of swaps
	}
	
	public void adjustHeap(){
		int swaps = 0;				
		do{
			for(int rootIndex = lastIndex / 2; rootIndex > 0; --rootIndex){
				swaps = reheap(rootIndex);	
			}
		}while(swaps != 0);					
	}
	
	public boolean heapIsEmpty(){
		if(lastIndex == 0){
			return true;									// return true if heap is empty
		}else
			return false;									// false if heap has value in it
	}
	
}
