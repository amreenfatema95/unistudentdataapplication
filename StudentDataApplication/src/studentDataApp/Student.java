package studentDataApp;

/**
 * _________________________________________
 * This class creates a Student Object.
 * 
 * @author Amreen Fatima S Surani
 * @since 8th March 2015
 * @version 0.1
 *
 * _________________________________________
 */
public class Student 
{
	private String email;
	private String tutorEmail;
	private String studentNumber;
	private String studentName;
	private String candKey;
	private String lastAccess;
	
	public Student()
	{

	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the tutorEmail
	 */
	public String getTutorEmail() {
		return tutorEmail;
	}

	/**
	 * @return the studentNumber
	 */
	public String getStudentNumber() {
		return studentNumber;
	}

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @return the anonymousMarkingCodes
	 */
	public String getAnonymousMarkingCodes() {
		return candKey;
	}

	/**
	 * @return the last access time
	 */
	
	public String getLastAccess() {
		return lastAccess;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param tutorEmail the tutorEmail to set
	 */
	public void setTutorEmail(String tutorEmail) {
		this.tutorEmail = tutorEmail;
	}

	/**
	 * @param studentNumber the studentNumber to set
	 */
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	/**
	 * @param anonymousMarkingCodes the anonymousMarkingCodes to set
	 */
	public void setAnonymousMarkingCodes(String anonymousMarkingCodes) {
		this.candKey = anonymousMarkingCodes;
	}

	/**
	 * @param lastAccess the lastAccess time to set
	 */
	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}
	
	/**
	 * @return StudentName (StudentNumber)
	 */
	public String getStu_Name_Number()
	{
		return studentName+" ("+studentNumber+")";
	}
}

