///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             Queue.java
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
/**
 * An ordered collection of items, where items are added to the rear and removed
 * from the front.
 */
public class Queue<E> implements QueueADT<E>
	{
	// TODO
	// You may use a naive expandable circular array or a chain of listnodes.
	// You may NOT use Java's predefined classes such as ArrayList or
	// LinkedList.
	
	// We chose to use a circular array

	// array is where all the items will be stored
	// front is the front of the queue which is at array[0] initially
	// rear is at the end of the queue 
	// numitems is counter for all items
	private E[] array;
	private int front;
	private int rear;
	private int numitems;

	public Queue()
		{
		// TODO
		// initializes all the variables
		array = (E[]) new Object [100];
		front = 0;
		rear = 0;
		numitems = 0;
		}

	/**
	 * Adds an item to the rear of the queue.
	 * 
	 * @param item
	 *            the item to add to the queue.
	 * @throws IllegalArgumentException
	 *             if item is null.
	 */
	public void enqueue(E item)
		{
		// TODO
		if (item == null){
			throw new IllegalArgumentException();
		}
		// If numitems reached at the end index, 
		// If array is full at all indexes, expand array
		// If not full at all indexes, insert element at front because of circular structure
		if (numitems == array.length){
			if (array[0] != null){
				expandCapacity();
			} else {
				rear = 0;
			}
		}
		// add item to rear if array is not full and increment numitems
		array[rear] = item;
		rear ++;
		numitems ++;
		}

	/**
	 * Removes an item from the front of the Queue and returns it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E dequeue()
		{
		// TODO
		if (numitems == 0){
			throw new EmptyQueueException();
		}
		// remove and returns the element at front and increment front index
		E dequeued = array[front];
		array[front] = null;
		front ++;
		numitems --;
		return dequeued;
		}

	/**
	 * Returns the item at front of the Queue without removing it.
	 * 
	 * @return the front item in the queue.
	 * @throws EmptyQueueException
	 *             if the queue is empty.
	 */
	public E peek()
		{
		// TODO
		// peeks at the front of the queue
		if (numitems == 0){
			throw new EmptyQueueException();
		}
		return array[front];
		}

	/**
	 * Returns true iff the Queue is empty.
	 * 
	 * @return true if queue is empty; otherwise false.
	 */
	public boolean isEmpty()
		{
		// TODO
		// checks if numitems is 0
		if (numitems == 0){
			return true;
		} else {
			return false;
		}
		}

	/**
	 * Removes all items in the queue leaving an empty queue.
	 */
	public void clear()
		{
		// TODO
		// clears the array
		for (int i = 0; i < numitems; i++){
			dequeue();
		}
		}

	/**
	 * Returns the number of items in the Queue.
	 * 
	 * @return the size of the queue.
	 */
	public int size()
		{
		return numitems;
		}

	private void expandCapacity()
		{
		// TODO
		//expanding should be done using the naive copy-all-elements approach
		E[] larger = (E[]) new Object [array.length * 2];
		// copies all elements into a new larger array
		for (int i = 0; i < numitems; i++){
			larger[i] = array[front];
			front++;
		}
		front = 0;
		rear = numitems;
		array = larger;
		}
	}
