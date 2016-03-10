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
	
	// use a circular array
	private E[] array;
	private int front;
	private int rear;
	private int numitems;

	public Queue()
		{
		// TODO
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
		if (numitems == array.length){
			if (array[0] != null){
				expandCapacity();
			} else {
				rear = 0;
			}
		}
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
		if (numitems == 0){
			throw new EmptyQueueException();
		}
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

				for (int i = 0; i < numitems; i++) {
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
		//expanding should be done using the naive copy-all-elements approach
		E[] larger = (E[]) new Object [array.length * 2];
		for (int i = 0; i < numitems; i++){
			larger[i] = array[front];
			front++;
		}
		front = 0;
		rear = numitems;
		array = larger;
		}
	}
