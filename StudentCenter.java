///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 3
// Files:            StudentCenter.java, Queue.java, PriorityQueueItem.java, 
//					 PriorityQueue.java, PriorityQueueIterator.java, Course.java
// Semester:         CS 367 Spring 2016
//
// Author:           Jonathan Santoso
// Email:            jsantoso2@wisc.edu
// CS Login:         santoso
// Lecturer's Name:  Jim Skrentny
// Lab Section:      (your lab section number)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     Wayne Eternicka
// Email:            wayne@badgers.me
// CS Login:         eternicka
// Lecturer's Name:  Deb Deppeler
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Student Center abstraction for our simulation. Execution starts here.
 * 
 * @author CS367
 *
 */
public class StudentCenter
	{

	private static int DEFAULT_POINTS = 100;
	// Global lists of all courses and students
	private static List<Course> courseList = new ArrayList<Course>();
	private static List<Student> studentList = new ArrayList<Student>();

	public static void main(String[] args)
		{
		if(args.length != 3)
			{
			System.err.println("Bad invocation! Correct usage: " + "java StudentCentre <StudentCourseData file>" + "<CourseRosters File> + <StudentCourseAssignments File>");
			System.exit(1);
			}

		boolean didInitialize = readData(args[0]);

		if(!didInitialize)
			{
			System.err.println("Failed to initialize the application!");
			System.exit(1);
			}

		generateAndWriteResults(args[1], args[2]);
		}

	/**
	 * 
	 * @param fileName
	 *            - input file. Has 3 sections - #Points/Student, #Courses, and
	 *            multiple #Student sections. See P3 page for more details.
	 * @return true on success, false on failure.
	 * 
	 */
	public static boolean readData(String fileName)
		{
		try {
			// TODO parse the input file as described in the P3 specification.
			// make sure to call course.addStudent() appropriately.
			// create a new file object and initializes new scanner
			File file = new File(fileName);
			Scanner input = new Scanner (file);
			// temp and temp2 are just for temporary storage
			String temp = null;
			String temp2 = null;
			// maxCoins is the max number of coins a person can have
			Integer maxCoins = 0;
			String classCode = null;
			String className = null;
			int classCapacity = 0;
			// course1 is temporary storage of course object
			Course course1 = null;
			// studentq is temporary storage of student object
			Student student1 = null;
			// studname is the student name, idnum is the student id
			String studname = null;
			String idnum = null;
			// wantclass is what class the student wants
			// want spend is how much the student is willing to spend
			String wantclass = null;
			int wantspend = 0;
			while (input.hasNext() == true){
				temp = input.next();
				// if input equals first header, next is the maxCoins
				if (temp.equals("#Points/Student")){
					maxCoins = Integer.parseInt(input.next());
				// if input equals #Courses, it is the list of all classes
				} else if (temp.equals("#Courses")){
					temp2 = input.next();
					// keeps creating a new course object until it is finished
					while (!temp2.equals("#Student")){
						classCode = temp2;
						className = input.next();
						classCapacity = Integer.parseInt(input.next());
						course1 = new Course (classCode, className, classCapacity);
						courseList.add(course1);
						temp2 = input.next();
					}
					// reads the student information and create a student object
					while (input.hasNext()){
						input.nextLine();
						studname = input.nextLine().trim();
						idnum = input.next();
						student1 = new Student(studname,idnum,maxCoins);
						studentList.add(student1);
						temp2 = input.next();
						// reads what class student wants and how much they are 
						// willing to spend on it
						// then call addStudent to that particular course
						while (!temp2.equals("#Student")){
							wantclass = temp2;
							wantspend = Integer.parseInt(input.next());
							for (int i = 0; i < courseList.size(); i++){
								if (courseList.get(i).getCourseCode().equals(wantclass)){
									courseList.get(i).addStudent(student1, wantspend);
								}
							}
							if (input.hasNext()){	
								temp2 = input.next();
							} else {
								break;
							}
						}
					}
				}
			}
			// closes scanner
			input.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("File Parse Error");
			return false;
		}
		return true;
		}

	/**
	 * 
	 * @param fileName1
	 *            - output file with a line for each course
	 * @param fileName2
	 *            - output file with a line for each student
	 */
	public static void generateAndWriteResults(String fileName1, String fileName2)
		{
		try
			{
			// List Students enrolled in each course
			PrintWriter writer = new PrintWriter(new File(fileName1));
			for (Course c : courseList)
				{
				writer.println("-----" + c.getCourseCode() + " " + c.getCourseName() + "-----");

				// Core functionality
				c.processRegistrationList();

				// List students enrolled in each course
				int count = 1;
				for (Student s : c.getCourseRegister())
					{
					writer.println(count + ". " + s.getid() + "\t" + s.getName());
					s.enrollCourse(c);
					count++;
					}
				writer.println();
				}
			writer.close();

			// List courses each student gets
			writer = new PrintWriter(new File(fileName2));
			for (Student s : studentList)
				{
				writer.println("-----" + s.getid() + " " + s.getName() + "-----");
				int count = 1;
				for (Course c : s.getEnrolledCourses())
					{
					writer.println(count + ". " + c.getCourseCode() + "\t" + c.getCourseName());
					count++;
					}
				writer.println();
				}
			writer.close();
			}
		catch(FileNotFoundException e)
			{
			e.printStackTrace();
			}
		}

	/**
	 * Look up Course from classCode
	 * 
	 * @param courseCode
	 * @return Course object
	 */
	private static Course getCourseFromCourseList(String courseCode)
		{
		for (Course c : courseList)
			{
			if(c.getCourseCode().equals(courseCode))
				{
				return c;
				}
			}
		return null;
		}
	}