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
			File file = new File(fileName);
			Scanner input = new Scanner (file);
			String temp = null;
			String temp2 = null;
			Integer maxCoins = 0;
			String classCode = null;
			String className = null;
			int classCapacity = 0;
			Course course1 = null;
			ArrayList <Course> courseList = new ArrayList <>();
			Student student1 = null;
			ArrayList <Student> studentList = new ArrayList <>();
			String studname = null;
			String idnum = null;
			String wantclass = null;
			int wantspend = 0;
			while (input.hasNext() == true){
				temp = input.next();
				if (temp.equals("#Points/Student")){
					maxCoins = Integer.parseInt(input.next());
				} else if (temp.equals("#Courses")){
					temp2 = input.next();
					while (!temp2.equals("#Student")){
						classCode = temp2;
						System.out.println(classCode);
						className = input.next();
						System.out.println(className);
						classCapacity = Integer.parseInt(input.next());
						System.out.println(classCapacity);
						course1 = new Course (classCode, className, classCapacity);
						courseList.add(course1);
						temp2 = input.next();
					}
					while (input.hasNext()){
						input.nextLine();
						studname = input.nextLine().trim();
						System.out.println("stud name" + studname);
						idnum = input.next();
						System.out.println("Id" + idnum);
						student1 = new Student(studname,idnum,maxCoins);
						studentList.add(student1);
						temp2 = input.next();
						while (!temp2.equals("#Student")){
							wantclass = temp2;
							System.out.println("class" + wantclass);
							wantspend = Integer.parseInt(input.next());
							System.out.println("spend" + wantspend);
							for (int i = 0; i < courseList.size(); i++){
								if (courseList.get(i).getCourseCode().equals(wantclass)){
									System.out.println("104");
									// add student or anyother mthod is wrong???
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