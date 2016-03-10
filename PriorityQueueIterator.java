import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PriorityQueueIterator<T> implements Iterator<PriorityQueueItem<T>>
	{

	private PriorityQueue<T> priorityQueue;
	private int currentSize;
	private int currPos;

	/**
	 * Constructs a PriorityQueueIterator by utilizing a copy of the
	 * PriorityQueue. Hint : The local priorityQueue object need not be
	 * preserved, and hence you can use the dequeue() operation.
	 * 
	 * @param pq
	 */
	public PriorityQueueIterator(PriorityQueue<T> pq)
		{
		// TODO
		// This copies the contents of the passed parameter to the local object.
		// Hint : see copy constructor in PriorityQueue
		this.currentSize = pq.size();
		this.priorityQueue = new PriorityQueue(pq);
		currPos = 1;
		}

	/**
	 * Returns true if the iteration has more elements.
	 * 
	 * @return true/false
	 */
	@Override
	public boolean hasNext()
		{
		// TODO
		if (currPos <= currentSize){
			return true;
		} else {
			return false;
		}
		}

	/**
	 * Returns the next element in the iteration. The iterator should return the
	 * PriorityQueueItems in order of decreasing priority.
	 * 
	 * @return the next element in the iteration
	 * @throws NoSuchElementException
	 *             if the iteration has no more elements
	 */
	@Override
	public PriorityQueueItem<T> next()
		{
		// TODO
		if (currPos > currentSize){
			throw new NoSuchElementException();
		}
		return priorityQueue.dequeue();
		}

	/**
	 * Unsupported in this iterator for this assignment
	 */
	@Override
	public void remove()
		{
		// Do not implement
		throw new UnsupportedOperationException();
		}

	}
