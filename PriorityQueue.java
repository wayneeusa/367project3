///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             PriorityQueue.java
// Semester:         CS 367 Spring 2016
//
// Author:           Jonathan Santoso, jsantoso2@wisc.edu
// CS Login:         santoso
// Lecturer's Name:  Jim Skrentny
// Lab Section:      (your lab section number)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Wayne Eternicka
// Email:            wayne@badgers.me
// CS Login:         eternicka
// Lecturer's Name:  Deb Deppeler
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER ///////
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * PriorityQueue implemented as a Binary Heap backed by an array. Implements
 * QueueADT. Each entry in the PriorityQueue is of type PriorityQueueNode<E>.
 * First element is array[1]
 * 
 * @author CS367
 *
 * @param <E>
 */
public class PriorityQueue<E> implements QueueADT<PriorityQueueItem<E>>
	{
	private final int DEFAULT_CAPACITY = 100;

	// Number of elements in heap
	private int currentSize;

	/**
	 * The heap array. First element is array[1]
	 */
	private PriorityQueueItem<E>[] array;

	/**
	 * Construct an empty PriorityQueue.
	 */
	public PriorityQueue()
		{
		currentSize = 0;
		// the below code initializes the array.. similar code used for
		// expanding.
		array = (PriorityQueueItem<E>[]) Array.newInstance(PriorityQueueItem.class, DEFAULT_CAPACITY + 1);
		}

	/**
	 * Copy constructor
	 * 
	 * @param pq
	 *            PriorityQueue object to be copied
	 */
	public PriorityQueue(PriorityQueue<E> pq)
		{
		this.currentSize = pq.currentSize;
		this.array = Arrays.copyOf(pq.array, currentSize + 1);
		}

	/**
	 * Adds an item to this PriorityQueue. If array is full, double the array
	 * size.
	 * 
	 * @param item
	 *            object of type PriorityQueueItem<E>.
	 */
	@Override
	public void enqueue(PriorityQueueItem<E> item)
		{
		// TODO write appropriate code
		// Check if array is full - double if necessary
		if (this.size() == currentSize){
			doubleArray();
		}
		// if array size is 1, add to first element
		boolean match = false;
		if (currentSize == 0){
			array[1] = item;
			match = true;
			currentSize++;
		}
		// Check all nodes and find if one with equal priority exists.
		// Add to the existing node's list if it does
		for (int i = 1; i <= currentSize; i++){
			if (item.compareTo(array[i]) == 0){
				match = true;
				for (int j = 0;  j < item.getList().size(); j++){	
					array[i].getList().enqueue(item.getList().dequeue()); 
				}
				if (match == true){
					break;
				}
			}
		}
		// Else create new node with value added to list and percolate it up
		if (match == false){
			currentSize++;
			array[currentSize] = item;
			
			// Percolate up code block
			PriorityQueueItem <E> temp = null;
			boolean done = false;
			int child = currentSize;
			int parent = 0;
			int temporaryindex = 0;
			int percolatecount = 0;
			while (!done){
				if (percolatecount >= 1){
					child = temporaryindex;
				}
				parent = child/2;
				if (parent == 0){
					done = true;
				// if child is smaller than parent we are done
				} else if (array[child].compareTo(array[parent]) <= 0){
					done = true;
				} else {
					// swap parent with child and increase percolatecount
					temp = array[parent];
					array[parent] = array[child];
					array[child] = temp;
					temporaryindex = parent;
					percolatecount++;
				}
			}
		}
		}

	/**
	 * Returns the number of items in this PriorityQueue.
	 * 
	 * @return the number of items in this PriorityQueue.
	 */
	public int size()
		{
		// TODO write appropriate code
		return this.currentSize;
		}

	/**
	 * Returns a PriorityQueueIterator. The iterator should return the
	 * PriorityQueueItems in order of decreasing priority.
	 * 
	 * @return iterator over the elements in this PriorityQueue
	 */
	public Iterator<PriorityQueueItem<E>> iterator()
		{
		// TODO write appropriate code - see PriortyQueueIterator constructor
		return new PriorityQueueIterator <E> (this);
		}

	/**
	 * Returns the largest item in the priority queue.
	 * 
	 * @return the largest item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> peek()
		{
		// TODO fill in appropriate code, replace default return statement
		if (array[1] == null){
			throw new NoSuchElementException();
		}
		return array[1];
		}

	/**
	 * Removes and returns the largest item in the priority queue. Switch last
	 * element with removed element, and percolate down.
	 * 
	 * @return the largest item.
	 * @throws NoSuchElementException
	 *             if empty.
	 */
	@Override
	public PriorityQueueItem<E> dequeue()
		{
		// TODO
		// Remove first element
		if (currentSize == 0){
			throw new NoSuchElementException();
		}
		PriorityQueueItem <E> largest = array[1];
		// Replace with last element, percolate down
		array[1] = array [currentSize];
		array[currentSize] = null; 
		currentSize--;
		percolateDown(1);
		return largest;
		}

	/**
	 * Heapify Establish heap order property from an arbitrary arrangement of
	 * items. ie, initial array that does not satisfy heap property. Runs in
	 * linear time.
	 */
	private void buildHeap()
		{
		for (int i = currentSize / 2;i > 0;i--)
			percolateDown(i);
		}

	/**
	 * Make this PriorityQueue empty.
	 */
	public void clear()
		{
		// TODO write appropriate code
		currentSize = 0;
		// the below code reinitializes the array
		array = (PriorityQueueItem<E>[]) Array.newInstance(PriorityQueueItem.class, DEFAULT_CAPACITY + 1);
		}

	/**
	 * Internal method to percolate down in the heap. <a
	 * href="https://en.wikipedia.org/wiki/Binary_heap#Extract">Wiki</a>}
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	public void percolateDown(int hole)
		{
		// TODO
		PriorityQueueItem <E> temp = null;
		// Left is the left child index while right is the right child index
		int Left = 2*hole;
		int Right = (2*hole + 1);
		int largest = hole;
		// if left child is greater than largest replace largest with left child index
		if(Left <= currentSize && array[Left].getPriority() > array[largest].getPriority()){
			largest = Left;
		}
		// if left child is greater than largest replace largest with right child index
		if(Right <= currentSize && array[Right].getPriority() > array[largest].getPriority()){
			largest = Right;
		}
		// if largest index does not equal hole, replace hole with item at largest index
		// recursive call on percolate down until it finds the largest one
		if(largest != hole) {
			temp = array[hole];
			array[hole] = array[largest];
			array[largest] = temp;
			percolateDown(largest);
		}
		}

	/**
	 * Internal method to expand array.
	 */
	private void doubleArray()
		{
		PriorityQueueItem<E>[] newArray;

		newArray = (PriorityQueueItem<E>[]) Array.newInstance(PriorityQueueItem.class, array.length * 2);

		for (int i = 0;i < array.length;i++)
			newArray[i] = array[i];
		array = newArray;
		}

	@Override
	public boolean isEmpty()
		{
		if(currentSize == 0)
			return true;
		return false;
		}
	}
