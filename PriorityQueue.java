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
		System.out.println("60");
		// TODO write appropriate code
		// Check if array is full - double if necessary
		if (this.size() == currentSize){
			doubleArray();
		}
		if (currentSize == 0){
			System.out.println("66");
			array[1] = item;
		}
		// Check all nodes and find if one with equal priority exists.
		// Add to the existing node's list if it does
		// NOT SURE ABOUT THIS???
		boolean match = false;
		for (int i = 1; i < array.length; i++){
			if (item.compareTo(array[i]) == 0){
				System.out.println("71");
				array[i].add(item.getList().dequeue());
				match = true;
				break;
			}
		}
		
		// Else create new node with value added to list and percolate it up
		// NOT SURE ABOUT THIS????
		if (match == false){
			System.out.println("80");
			PriorityQueueItem <E> newNode = new PriorityQueueItem (item.getPriority());
			array[currentSize] = newNode;
			boolean done = false;
			int child = currentSize;
			while (!done){
				int parent = child /2;
				if (parent == 0){
					done = true;
				} else if (array[child].compareTo(array[parent]) <= 0){
					done = true;
				} else {
					child = parent;
				}
			}
		}
		currentSize++;
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
		PriorityQueueItem <E> largest = array[1];
		for (int i = 1; i < array.length; i++){
			if (array[i].compareTo(largest) >= 0){
				largest = array[i];
			}
		}
		return largest;
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
		int largest_index = -1;
		for (int i = 1; i < array.length; i++){
			if (array[i].compareTo(largest) >= 0){
				largest = array[i];
				largest_index = i;
			}
		}
		// Replace with last element, percolate down
		array[largest_index] = array[currentSize];
		array[currentSize] = null; 
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
	private void percolateDown(int hole)
		{
		// TODO
		PriorityQueueItem <E> temp = array[hole];
		for (int i = hole; i < currentSize; i++){
			if (i * 2 <= currentSize && i * 2 + 1 <= currentSize){
				if (temp.compareTo(array[i*2]) >= 0 && temp.compareTo (array[i*2 + 1]) >= 0){
					break;
				} else if (temp.compareTo(array[i * 2]) < 0){
					array[i] = array[i*2];
					array[i*2] = temp;
				} else {
					array[i] = array[i*2 + 1];
					array[i*2 + 1] = temp;
				}
			}
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
