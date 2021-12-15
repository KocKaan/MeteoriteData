package project5;

import java.util.*;
/**
 * The BST class is responsible for the creation of the Binary Search Tree which is 
 * the main data structure used for storing the Meteorite objects. The class extends 
 * to Comparable which will be used to sort the Binary search tree according to the 
 * need of the tree. This class comes with methods that will help the creation of the 
 * Binary Search Tree and tools to analyze the tree. 
 * 
 * @author Kaan Karakas
 *
 */
public class BST<T extends Comparable<T>> {
	

	private BSTNode root; // reference to the root node of the tree
	private int size; // number of values stored in this tree
	private Comparator<T> comparator; // comparator object to overwrite the
	// natural ordering of the elements

	private boolean found; // helper variable used by the remove methods
	private boolean added; // helper variable used by the add method

	/**
	 * Constructs a new, empty tree, sorted according to the natural ordering of its
	 * elements.
	 */
	public BST() {
		root = null;
		size = 0;
		comparator = null;
	}

	/**
	 * Constructs a new, empty tree, sorted according to the specified comparator.
	 */
	public BST(Comparator<T> comparator) {
		this.comparator = comparator;
	}
/////////////////////////////////////////////////
	/**
	*Returns true if this tree contains the specified element. More formally, returns true if and only
	*if this tree contains an element e such that Objects.equals(o, e).
	*This operation should be O(H).
	*
	* @param  o - object to be checked for containment in this set
	*
	* @return true if this tree contains the specified element
	*
	* @throws ClassCastException - if the specified object cannot be compared with the
	* elements currently in the set
	* NullPointerException - if the specified element is null and this tree uses
	* natural ordering, or its comparator does not permit null elements
	*/

	public boolean contains(Object o) {

		T obj;
		//try catch is used to catch and manually throw the class cast exception 
	      try {
			  obj = (T) o;
	      }catch(ClassCastException ex) {
				throw new ClassCastException("contains object doesnt match");
			}
 
		/// checks for null object if the comparator is null
		if ((comparator == null && o == null)) {
			throw new NullPointerException("the given object is null");
		}
		
         //return the recursive function
		return containsRec(obj, root);
	}
	/**
	* The recursive part of the contains method. 
	*
	* @param T obj and the root of the BST tree
	*
	* @return true if the obj is inside the tree false if the obj is not found
	*
	*/

	private boolean containsRec(T obj, BSTNode current) {
		//this is the base case and if the node is null returns false
		//as it means the current comes to the leaf not founding the obj
		if (current == null) {
			return false;
		}
		
		//of the obj smaller than the current a recursive call comes 
		//to the left current
		if (obj.compareTo(current.data) < 0) {

			return containsRec(obj, current.left);
		}
		
		//if the obj is same with the current data returns true;
		if (obj.compareTo(current.data) == 0) {

			return true;
		}
		//of the obj bigger than the current a recursive call comes 
        //to the left current
		if (obj.compareTo(current.data) > 0) {

			return containsRec(obj, current.right);
		}

		return true;

	}

/////////////////////////////////////////////////
	/**
	* Returns true if this tree contains no elements.
	* This operation should be O(1).
	*
	* @return true if this tree contains no elements
	*/
	public boolean isEmpty() {
		//if the root is null the bst is empty
		if (root == null) {
			return true;
		}
		return false;
	}

/////////////////////////////////////////////////
	/**
	* Returns an iterator over the elements in this tree in ascending order.
	* This operation should be O(N).
	*
	* @param double double latitude and longitude
	*
	* @return returns nothing just initializes the latitude and longitude
	*
	* @throws IllegalArgumentException latitude not in range [-90.0, 90.0] or longitude not in range [-180.0, 180.0]
	*/
	private class Itr implements Iterator<T> {
		//array list will hold the nodes of the BST inorder
		ArrayList<T> list;
		
		//counter will be used as pointer on the arraylist
	    int counter;
	    
        //this private constructor creates a new list array holding the
	    //meteorites. The arrays are added by calling the inorder traversal method.
	    //THe time compelxity it O(n)
	    private Itr (BSTNode root) {
	    	 this.counter = -1;

	       
	        this.list = new ArrayList<T>();

	       
	        this.traverse(root);
	    }
	    
	    /**
		* This traverse methos is used for inorder traversal and 
		* adding the nodes to the array list.
		*
		* parameter: takes the root of the tree.
		*/
	    private void traverse(BSTNode current) {
	    	//this is the base case for the inorder traversal
	    	//if it reaches to null it will return.

	        if (current == null) {
	            return;
	        }
	        this.traverse(current.left);
	        //add the current data to list.
	        this.list.add(current.data);
	        this.traverse(current.right);
	    }

	    /**
	     * this method is used to return the next elemnt on the 
	     * BST usinf the stored nodes on the array list.
	     * @return the next smallest number
	     */
	    public T next() {
	    	//counter is a indicator of where the pointer is on the list
	    	this.counter+=1;
	    	//gets the next element usign the get() method
	        return this.list.get(this.counter);
	    }

	    /**
	     *hasNext checks whether there is a next element on the bst by
	     *usign the counter pointer.
	     * @return whether we have a next smallest number
	     */
	    public boolean hasNext() {
	    	//if the current pointer is below the last index of the array list
	    	//that means pointer has not reached to the last element.
	        return 1+this.counter < this.list.size();
	    }
	}	
		
	/**
	 * This is used for calling the iterator on the BST tree. 
	 * 
	 * returns the Iterator with the given root.
	 */
	public Iterator<T> iterator() {
		return new Itr(root);
	}
	
	
/////////////////////////////////////////////////
	/**
	* Returns a collection whose elements range from fromElement, inclusive, to toElement, inclusive.
	* The returned collection/list is backed by this tree, so changes in the returned list are
	* reflected in this tree, and vice-versa (i.e., the two structures share elements. The returned
	* collection should be organized according to the natural ordering of the elements (i.e., it
	* should be sorted).
	* This operation should be O(M) where M is the number of elements in the returned list.
	*
	* @param fromElement - low endpoint (inclusive) of the returned collection
	* toElement - high endpoint (inclusive) of the returned collection
	*
	* @return a collection containing a portion of this tree whose elements range from fromElement,
	* inclusive, to toElement, inclusive
	*
	* @throws NullPointerException - if fromElement or toElement is null
	* IllegalArgumentException - if fromElement is greater than toElement
	*/
	public ArrayList<T> getRange​(T fromElement, T toElement) {
		//checks if the parameters are null 
		if (fromElement == null || toElement == null) {
			throw new NullPointerException("fromElement or toElement is null");
		}
		
		//checks if the from elemetn is bigger than the to element.
		if (fromElement.compareTo(toElement) > 0) {
			throw new IllegalArgumentException("from element cant be bigger than to element ");
		}
		
		//creates an new arraylist 
		ArrayList<T> list = new ArrayList<T>();

		//this calls the recursice function 
		getRangeRec(fromElement, toElement, list, root);
		return list;

	}
	/*
	 * Actual recursive implementation of getRange method: returns the wanted elements on theBST.
	 *
	 * This function recursively finds the wanted data and eventually stores 
	 * the data at the array list 
	 * 
	 * @param fromElement - low endpoint (inclusive) of the returned collection
	 * toElement - high endpoint (inclusive) of the returned collection
	 * list- the array list that will hold the meteroites that are in the range
	 * current- The node that will be traversed
	 * 
	 */
	private void getRangeRec(T fromElement, T toElement, ArrayList<T> list, BSTNode current) {
		
		//Two int will be used to store the result of the comprason
		int comp1=0;
		int comp2=0;
		//this is the base case
		if (current == null) {
			return;
		}
		//if teh comprator is null uses the natural comprateTO method whoch compares alphanumerical
		if (comparator == null) {
			comp1 = current.data.compareTo(fromElement);
			comp2= current.data.compareTo(toElement);
		}
		else {
			//if the comparator is used compras according to the comparator class.
			comp1 = comparator.compare(current.data, fromElement);	
			comp2= comparator.compare(current.data,toElement);
			
		}

         //if the first var is bigger than 0  goes left of the BST.
		if (comp1 > 0) {
			getRangeRec(fromElement, toElement, list, current.left);
		}
		//if the first var is bigger tahtn 0 and second is lower than 0 adds to the list.
		if (comp1 >= 0 && comp2 <= 0) {
			list.add(current.data);
		}
		//If the second var is smaller than 0 goest to right of the BST.
		if (comp2 < 0) {
			getRangeRec(fromElement, toElement, list, current.right);
		}
	}

/////////////////////////////////////////////////  
	/**
	* Returns the first (lowest) element currently in this tree.
	* This operation is O(H).
	*
	* @return the first (lowest) element currently in this tree
	*
	* @throws NoSuchElementException - if this tree is empty
	*/
	public T first() {
		if (root == null) {
			throw new NoSuchElementException("The tree is empty");
		}
		//current is assigne as root.
		BSTNode current = root;
		//as the smallest term is leftmost traverse to the left of the tree.
		while (current.left != null) {
			current = current.left;
		}
		return current.data;

	}

/////////////////////////////////////////////////  
	/**
	* Returns the last (highest) element currently in this tree.
	* This operation is O(H).
	*
	*
	* @return the last (highest) element currently in this tree
	*
	* @throws  NoSuchElementException - if this tree is empty
	*/
	public T last() {
		//throws exception if the bst is empty
		if (root == null) {
			throw new NoSuchElementException("The tree is empty");
		}
		
		BSTNode current = root;
		//as the largest term is rightmost traverse to the right of the tree.
		while (current.right != null) {
			current = current.right;
		}
		return current.data;
	}

/////////////////////////////////////////////////  
	/**
	* Compares the specified object with this tree for equality. Returns true if the give
	* object is also a tree, the two trees have the same size, and the inorder traversal of the
	* two trees returns the same nodes in the same order.
	* This operation is O(N).
	* 
	* Overrides: equals in class Object
	*
	* @param obj - object to be compared for equality with this tree
	*
	* @return true if the specified object is equal to this tree
	*
	*/
	public boolean equals(Object obj) {

		// first check whether the stack is the same with the obj
		if (this == obj)
			return true;
		// if the obj is null return false
		if (obj == null) {
			
			return false;
		}
		// checks whethet obj is an instance of BST
		if (!(obj instanceof BST)) {
	

			return false;
		}
		// Castign the obj to the BST object with a variable of other
		BST<T> other = (BST<T>) obj;

		// Checks if both BST sizes are same
		if (other.size() != this.size()) {
			
			return false;
		}
	   //calls the recursive function.
		return equalsRec(other.root, this.root);
	}
	/*
	 * Actual recursive implementation of equals: checks if the two BST are same.
	 *
	 * This function recursively checks both BST and compare them for equality.
	 * 
	 * @param current1- node of the first bst
	 * current2- node of the second bst.
	 * 
	 */
	private boolean equalsRec(BSTNode current1, BSTNode current2) {
		//this is the base case
		if (current1 == null && current2 == null) {
			return true;
		}

		//if the current 1 and current2 are not null first checks the for equality 
		//then left recursiom lastly right recursion
		if (current1 != null && current2 != null) {
			return (current1.data.equals(current2.data) && equalsRec(current1.left, current2.left)
					&& equalsRec(current1.right, current2.right));
		}
		return false;
	}
	
///////////////////////////////////////////////// 
	/**
	* Returns a string representation of this tree. The string representation consists of a list
	* of the tree's elements in the order they are returned by its iterator (inorder traversal),
	* enclosed in square brackets ("[]"). Adjacent elements are separated by the characters ", "
	* (comma and space). Elements are converted to strings as by String.valueOf(Object).
	*This operation should be O(N)
	*
	* Overrides:
	* toString in class Object
	*
	* @return  a string representation of this collection
	*/
	public String toString() {
         //adds [ to the eend end beginning.
		String str= "["+ toStringRec(root);
		//to not have index bound exception if lenght is belov 2 returns empty array.
		if(str.length()>2) {
		return str.substring(0,str.length()-2)+"]";
		}else {
			return "[]";
		}
		
	}
	/*
	 * Actual recursive implementation of toString: returns string
	 *
	 * 
	 * @param current- BSTnode as a root
	 * 
	 *returns String representatipn of BST
	 */
	private String toStringRec(BSTNode current) {
		
	    if (current == null) {
	    	return "";
	    }
	    return toStringRec(current.left) + String.valueOf(current.data)+", "+
	    toStringRec (current.right) ; 
		
	}

/////////////////////////////////////////////////  
	/**
	* This function returns an array containing all the elements returned by this tree's iterator,
	* in the same order, stored in consecutive elements of the array, starting with index 0. The
	* length of the returned array is equal to the number of elements returned by the iterator.
	* This operation should be O(N).
	*
	*
	* @return an array, whose runtime component type is Object, containing all of the elements in this tree
	* 
	*/
	
	public Object[] toArray() {
		//creates an array with the size of the tree
		Object[] container= new Object[this.size()];
		
		//Calls the iterator to iterate throught the array.
		Iterator<T> itr= this.iterator();
		for(int i=0; i<this.size(); i++) {
			container[i]= itr.next();
			
		}
		return container;
	
	}
/////////////////////////////////////////////////  
	/**
	 * Adds the specified element to this tree if it is not already present. If this
	 * tree already contains the element, the call leaves the tree unchanged and
	 * returns false.
	 * 
	 * @param data element to be added to this tree
	 * @return true if this tree did not already contain the specified element
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean add(T data) {
		added = false;
		if (data == null)
			return added;
		// replace root with the reference to the tree after the new
		// value is added
		root = add(data, root);
		// update the size and return the status accordingly
		if (added)
			size++;
		return added;
	}

	/*
	 * Actual recursive implementation of add.
	 *
	 * This function returns a reference to the subtree in which the new value was
	 * added.
	 *
	 * @param data element to be added to this tree
	 * 
	 * @param node node at which the recursive call is made
	 */
	private BSTNode add(T data, BSTNode node) {
		if (node == null) {
			added = true;
			return new BSTNode(data);
		}
		// decide how comparisons should be done
		int comp = 0;
		if (comparator == null) // use natural ordering of the elements
			comp = node.data.compareTo(data);
		else // use the comparator
			comp = comparator.compare(node.data, data);

		// find the location to add the new value
		if (comp > 0) { // add to the left subtree
			node.left = add(data, node.left);
		} else if (comp < 0) { // add to the right subtree
			node.right = add(data, node.right);
		} else { // duplicate found, do not add
			added = false;
			return node;
		}
		return node;
	}

	/**
	 * Removes the specified element from this tree if it is present. Returns true
	 * if this tree contained the element (or equivalently, if this tree changed as
	 * a result of the call). (This tree will not contain the element once the call
	 * returns.)
	 * 
	 * @param target object to be removed from this tree, if present
	 * @return true if this set contained the specified element
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean remove(T target) {
		// replace root with a reference to the tree after target was removed
		root = recRemove(target, root);
		// update the size and return the status accordingly
		if (found)
			size--;
		return found;
	}

	/*
	 * Actual recursive implementation of remove method: find the node to remove.
	 *
	 * This function recursively finds and eventually removes the node with the
	 * target element and returns the reference to the modified tree to the caller.
	 * 
	 * @param target object to be removed from this tree, if present
	 * 
	 * @param node node at which the recursive call is made
	 */
	private BSTNode recRemove(T target, BSTNode node) {
		if (node == null) { // value not found
			found = false;
			return node;
		}

		// decide how comparisons should be done
		int comp = 0;
		if (comparator == null) // use natural ordering of the elements
			comp = target.compareTo(node.data);
		else // use the comparator
			comp = comparator.compare(target, node.data);

		if (comp < 0) // target might be in a left subtree
			node.left = recRemove(target, node.left);
		else if (comp > 0) // target might be in a right subtree
			node.right = recRemove(target, node.right);
		else { // target found, now remove it
			node = removeNode(node);
			found = true;
		}
		return node;
	}

	/*
	 * Actual recursive implementation of remove method: perform the removal.
	 *
	 * @param target the item to be removed from this tree
	 * 
	 * @return a reference to the node itself, or to the modified subtree
	 */
	private BSTNode removeNode(BSTNode node) {
		T data;
		if (node.left == null) // handle the leaf and one child node with right subtree
			return node.right;
		else if (node.right == null) // handle one child node with left subtree
			return node.left;
		else { // handle nodes with two children
			data = getPredecessor(node.left);
			node.data = data;
			node.left = recRemove(data, node.left);
			return node;
		}
	}

	/*
	 * Returns the information held in the rightmost node of subtree
	 *
	 * @param subtree root of the subtree within which to search for the rightmost
	 * node
	 * 
	 * @return returns data stored in the rightmost node of subtree
	 */
	private T getPredecessor(BSTNode subtree) {
		if (subtree == null) // this should not happen
			throw new NullPointerException("getPredecessor called with an empty subtree");
		BSTNode temp = subtree;
		while (temp.right != null)
			temp = temp.right;
		return temp.data;
	}

	/**
	 * Returns the number of elements in this tree.
	 * 
	 * @return the number of elements in this tree
	 */
	public int size() {
		return size;
	}

	public String toStringTree() {
		StringBuffer sb = new StringBuffer();
		toStringTree(sb, root, 0);
		return sb.toString();
	}

	// uses preorder traversal to display the tree
	// WARNING: will not work if the data.toString returns more than one line
	private void toStringTree(StringBuffer sb, BSTNode node, int level) {
		// display the node
		if (level > 0) {
			for (int i = 0; i < level - 1; i++) {
				sb.append("   ");
			}
			sb.append("|--");
		}
		if (node == null) {
			sb.append("->\n");
			return;
		} else {
			sb.append(node.data + "\n");
		}

		// display the left subtree
		toStringTree(sb, node.left, level + 1);
		// display the right subtree
		toStringTree(sb, node.right, level + 1);
	}

	/*
	 * Node class for this BST
	 */
	private class BSTNode implements Comparable<BSTNode> {

		T data;
		BSTNode left;
		BSTNode right;

		public BSTNode(T data) {
			this.data = data;
		}

		public BSTNode(T data, BSTNode left, BSTNode right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
		// the compareto invokes comparator if the comparartor was used when createding bst

		public int compareTo(BSTNode other) {
			if (BST.this.comparator == null) {
				
				return this.data.compareTo(other.data);
			}else {
				
				return comparator.compare(this.data, other.data);
			}
				
		}
	}

}
