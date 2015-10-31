/**
 * 
 */
package studentDataApp;

import java.util.ArrayList;

import studentdata.Connector;
import studentdata.DataTable;

/**
 * ___________________________________________________________________________________________________________
 * This class retrieves data from the server and create a student object for every student in the data table.
 * 
 * @author Amreen Fatima S Surani
 * @since 8th March 2015
 * @version 0.1
 *
 * ___________________________________________________________________________________________________________
 */
public class StudentList 
{
	private DataTable data;
	private ArrayList<Student> studentList;
	
	public StudentList()
	{
		Connector server = new Connector();
	    boolean success = server.connect("ASM__Xeo","32ac0bd30cca5bc4dd6e3c10f9673077");
	    
	    if (success == false) 
	    {
	        System.out.println("Fatal error: could not open connection to server");
	        System.exit(1);
	    }
	    
	    data = server.getData();
	    studentList= new ArrayList<Student>();
	    
	    for(int i=0;i<data.getRowCount();i++)
	    {
	    	Student nStudent = new Student();
	    	nStudent.setEmail(data.getCell(i,0));
	    	nStudent.setTutorEmail(data.getCell(i,1));
	    	nStudent.setStudentNumber(data.getCell(i,2));
	    	nStudent.setStudentName(data.getCell(i,3));
	    	studentList.add(nStudent);
	    }
	}

	/**
	 * @return the studentList
	 */
	public ArrayList<Student> getStudentList() 
	{
		return studentList;
	}
	
}
