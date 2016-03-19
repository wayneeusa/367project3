///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  StudentCenter.java
// File:             Course.java
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class to represent every Course in our Course Registration environment
 * 
 * @author CS367
 *
 */

public class Course
	{

	private String courseCode;
	private String name;

	// Number of students allowed in the course
	private int maxCapacity;
	// Number of students enrolled in the course
	private int classCount;

	// The PriorityQueue structure
	private PriorityQueue<Student> registrationQueue;

	// List of students who are finally enrolled in the course
	private List<Student> courseRoster;

	public Course(String classCode, String name, int maxCapacity)
		{
		// TODO initialize all parameters
		this.courseCode = classCode;
		this.name = name;
		this.maxCapacity = maxCapacity;
		registrationQueue = new PriorityQueue <Student> ();
		courseRoster = new ArrayList <Student> ();
		}

	/**
	 * Creates a new PriorityqueueItem - with appropriate priority(coins) and
	 * this student in the item's queue. Add this item to the registrationQueue.
	 * 
	 * @param student
	 *            the student
	 * @param coins
	 *            the number of coins the student has
	 */
	public void addStudent(Student student, int coins)
		{
		// This method is called from Studentcenter.java
		// TODO : see function header
		// Enqueue a newly created PQItem.
		// Checking if a PriorityQueueItem with the same priority already exists
		// is done in the enqueue method.
		PriorityQueueItem <Student> newItem = new PriorityQueueItem <Student> (coins);
		newItem.add(student);
		// if student has enough coins, add it to registrationQueue
		if (student.deductCoins(coins) == true){
			registrationQueue.enqueue(newItem);
		}
		}

	/**
	 * Populates the courseRoster from the registration list.
	 * Use the PriorityQueueIterator for this task.
	 */
	public void processRegistrationList()
		{
		// TODO : populate courseRoster from registrationQueue
		// Use the PriorityQueueIterator for this task.
		// Initializes iterator
		Iterator <PriorityQueueItem<Student>> itr = registrationQueue.iterator();
		while (itr.hasNext()){
			// gets a PriorityQueueItem
			PriorityQueueItem <Student> temp = itr.next();
			// if class is not full
			if (classCount < maxCapacity){
				// dequeue the queue in the PriorityQueue Item
				// add the student to courseRoster and increment classCount
				while (temp.getList().isEmpty() == false){
					if (classCount < maxCapacity){
						courseRoster.add(temp.getList().dequeue());
						this.classCount++;
					}
				}	
			} 
		}
		}

	public String getCourseName()
		{
		// TODO
		return this.name;
		}

	public String getCourseCode()
		{
		// TODO
		return this.courseCode;
		}

	public List<Student> getCourseRegister()
		{
		// TODO
		return this.courseRoster;
		}
	}
