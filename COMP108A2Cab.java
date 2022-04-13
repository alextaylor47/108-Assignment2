//
// Note: You are allowed to add additional methods if you need.
// Coded by Prudence Wong 2021-03-06
//
// Name: Alexander Taylor
// Student ID: 201539398
// MWS username: sgatayl6
//
// Time Complexity and explanation: 
// f denotes initial cabinet size
// n denotes the total number of requests 
// d denotes number of distinct requests
// You can use any of the above notations or define additional notation as you wish.
// 
// appendIfMiss():
// Time Complexity Worst case = O(f)
// Time Complexity Best Case = O(n)
// To find out if we have to add a file at the end of the filling cabinet we have to initinally
// serach trough f files, so we in the worst case we need to search f files to find out
// if the file is the filling cabinet already, or if we need to add it at the end.
// thefore we can say that the time complecity is for the worst case in O(f).

// if we have n requests the the worst case it that each request causes 
// the whole fling cabinet to be searched so the time complecity becomes O(fn)

// In the best case the file will always be in the 1st position so we dont need to serach through
// any of the other files therefor the time complexity is just the number of requests as it
// and as each requestwill always take the same amount of time to find the first file.
// O(n)


// 	
// moveToFront():
// Time Complexity Worst case = O(n)
// Time Complexity Best Case = O(1)
// If the file we are seraching for is not in the filling cabiet then we will always 
// add it to the front, but to find out 
// 	
// freqCount():
// 

import java.util.Arrays;
import java.util.ArrayList;

 
class COMP108A2Cab {

	public COMP108A2Node head, tail;
	
	public COMP108A2Cab() {
		head = null;
		tail = null;
	}

	// append to end of list when miss
	public COMP108A2Output appendIfMiss(int rArray[], int rSize) {
		COMP108A2Output output = new COMP108A2Output(rSize, 1);
		COMP108A2Node cabinetNode =new COMP108A2Node(1);
        // ****** Code hear******
		boolean hit;
		boolean end;
		int comparisons;
		
		for (int i = 0; i < rSize; i++){ // interate through all file retrival requests
			
			// *****************
			comparisons=0;
			hit = false;
			end = false;
			cabinetNode = head;  // start at the beinging of the list
			
			
			while(hit==false && end==false){
				comparisons++;
				if (rArray[i] == cabinetNode.data) {
					hit =true;
					output.hitCount++;
				}
				else {
					  
					if (cabinetNode.next==null){
					end=true;
				    }
				    else{
			        cabinetNode=cabinetNode.next;
				    }
			    }
			}
		  if (hit==false){
			COMP108A2Node newTail = new COMP108A2Node(rArray[i]);
			insertTail(newTail);
			output.missCount++; 
		  }
		  output.compare[i]=comparisons;
		}
	
		output.cabFromHead = headToTail();
		output.cabFromTail = tailToHead();

		return output;
	}

	// move the file requested to the beginning of the list
	public COMP108A2Output moveToFront(int rArray[], int rSize) {
		COMP108A2Output output = new COMP108A2Output(rSize, 1);
       // search through list for requested file
	   // if file found (i.e hit) move to front of list
	   // if file is not found (i.e miss) move to front of list
        boolean hit;
		boolean end;
		COMP108A2Node cabinetNode;
		int comparisons =0;
		for (int i = 0; i < rSize; i++){ // iterate through al the file access entries
			comparisons=0;
			cabinetNode = head;
			hit = false;
			end = false;
			while(hit==false && end==false){
				comparisons++;  // count each access of existing file
				if (rArray[i] == cabinetNode.data) { // does the file allready exist
					hit =true; // indicate we have found it
				}
				else {
				     if (cabinetNode.next==null){
				     end=true;
				     }
				     else{
			         cabinetNode=cabinetNode.next;
				     } 
			   }
			}
		 if (hit==true){
			output.hitCount++;
			if (cabinetNode.next==null){ // are we at tail if yes move tail to front 
				COMP108A2Node newtail;
				newtail=cabinetNode.prev;
				newtail.next=null;
				insertHead(cabinetNode);
				tail=newtail;
				}
			else if (cabinetNode.next !=null && cabinetNode.prev != null){ // node in middel
			COMP108A2Node leftNode;
			COMP108A2Node rightNode;
			leftNode=cabinetNode.prev;
			rightNode=cabinetNode.next;
			leftNode.next=cabinetNode.next;
			rightNode.prev=cabinetNode.prev;
			insertHead(cabinetNode);
			}
		 }
		if (hit==false){ // this is a miss
		output.missCount++;
		COMP108A2Node newHead = new COMP108A2Node(rArray[i]);
				insertHead(newHead);
			}
		output.compare[i]=comparisons;
	}
		output.cabFromHead = headToTail();
		output.cabFromTail = tailToHead();
		return output;	
	}
	
	// move the file requested so that order is by non-increasing frequency
	public COMP108A2Output freqCount(int rArray[], int rSize) {
		COMP108A2Output output = new COMP108A2Output(rSize, 1);
		
		
        
		//******************Search for the File if not found add to end of list.
		boolean hit;   
		boolean end;
		int comparisons;
		    
		for (int i = 0; i < rSize; i++){
			COMP108A2Node searchNode = new COMP108A2Node(rArray[i]);
			COMP108A2Node tempNode = new COMP108A2Node(1); // create temp node object
			comparisons=0;  // set number of compraiosons for this node search
			hit = false;
			end = false;
			
			searchNode=head; // create a copy of the head node
			
			while(hit==false && end==false){  // end if we have a match or reach the tail
				comparisons++;
				if (searchNode.data==rArray[i]) {  // check if we have a match
					tempNode.next=searchNode.next;
   		            tempNode.prev=searchNode.prev;
		            tempNode.data=searchNode.data;
		            tempNode.freq=searchNode.freq;
					hit =true;	
				    }
				else if (searchNode.next==null){ // create a new node
					     tempNode.next=null;
   		                 tempNode.prev=null;
		                 tempNode.data=rArray[i];
		                 tempNode.freq=1;
						 end=true;
				         }
				else{
			        searchNode=searchNode.next;
				    } 
			   }
		 
		 //************Add the search node to tail if not found in the list******
		 if (hit==false){ // add the search node if its found
			 insertTail(tempNode);
			 output.missCount++; // increment miss count by 1
		    }
		//********Delete the existing search node if found in the lsit****
		
		 else if (hit == true){
			if (searchNode.prev ==null){  // logically delete a head node
			 deleteHead();
		    }
			else if (searchNode.next==null){// logicall delete a tail node
			 deleteTail();
			}
			else {         // logicall delete any node that is not a tail or head         
			 deleteNode(searchNode);
			}
			searchNode=null; // delete the search node object from memory as well
			System.gc();            // run the grabage collector to free up used memory.
			
			// ********increment the frequency count for the file
			// and add back to list in the correct postion based on frequency count
			output.hitCount++; // increment hit count by 1
			tempNode.freq++; // increment the frecyency by 1
			searchNode=head;// start frequency comparison at the beginning of list
			end = false;
			
			while (end==false){
				if  (tempNode.freq>searchNode.freq){ 
					if (searchNode.prev==null){  // inssrt new head if freq > head >freq
						insertHead(tempNode);
						end=true;
					}
					else { // and node in middel of chain
					COMP108A2Node prev;
	                COMP108A2Node next;
					
					prev=searchNode.prev;
					next=searchNode;		
					tempNode.next=next;
					prev.next=tempNode;	
					tempNode.prev=prev;
					next.prev=tempNode;
					
					end = true;
					}  
				}					
				if (searchNode.next==null && end != true){
					insertTail(tempNode);
					end = true;
				    }
				if (end==false){
			      searchNode=searchNode.next;
				}
			}
	     
		 }
		output.compare[i]=comparisons;
		
	}
	output.cabFromHead = headToTail();
	output.cabFromTail = tailToHead();
	output.cabFromHeadFreq = headToTailFreq();
	return output;	
}
					
					   
	// DO NOT change this method
	// insert newNode to head of list
	public void insertHead(COMP108A2Node newNode) {		

		newNode.next = head;
		newNode.prev = null;
		if (head == null)
			tail = newNode;
		else
			head.prev = newNode;
		head = newNode;
	}

	// DO NOT change this method
	// insert newNode to tail of list
	public void insertTail(COMP108A2Node newNode) {

		newNode.next = null;
		newNode.prev = tail;
		if (tail != null)
			tail.next = newNode;
		else head = newNode;
		tail = newNode;
	}

	// DO NOT change this method
	// delete the node at the head of the linked list
	public COMP108A2Node deleteHead() {
		COMP108A2Node curr;

		curr = head;
		if (curr != null) {
			head = head.next;
			if (head == null)
				tail = null;
			else
				head.prev = null;
		}
		return curr;
	}
	
	// DO NOT change this method
	// empty the cabinet by repeatedly removing head from the list
	public void emptyCab() {
		while (head != null)
			deleteHead();
	}
    

	// DO NOT change this method
	// this will turn the list into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTail() {
		COMP108A2Node curr;
		String outString="";
		
		curr = head;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the list into a String from tail to head
	// Only to be used for output, do not use it to manipulate the list
	public String tailToHead() {
		COMP108A2Node curr;
		String outString="";
		
		curr = tail;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.prev;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the frequency of the list nodes into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTailFreq() {
		COMP108A2Node curr;
		String outString="";
		curr = head;
		while (curr != null) {
			outString += curr.freq;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}
	// new method to delete a tail
	public COMP108A2Node deleteTail(){
		COMP108A2Node curr;

		curr = tail;
		if (curr != null) {
			tail = tail.prev;
			if (tail == null)
				head = null;
			else
				tail.next = null;
		}
		return curr;
	}
	
	
	// new method to delect a node that is not a tail or head node
	public void deleteNode(COMP108A2Node deleteNode){
	  COMP108A2Node prev;
	  COMP108A2Node next;
	  prev =deleteNode.prev;
	  prev.next=deleteNode.next;
	  next =deleteNode.next;
	  next.prev=deleteNode.prev;	
	}	
 
}
