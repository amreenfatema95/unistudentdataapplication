/**
 * 
 */
package studentDataApp;

/**
 * _________________________________________
 * This class creates a Result Object.
 * 
 * @author Amreen Fatima S Surani
 * @since 13th March 2015
 * @version 0.1
 *
 * _________________________________________
 */
public class Result 
{
	private String module;
	private String ass;
	private String candKey;
	private String mark;
	private String grade;
	private String studentNumber;
	
	public Result()
	{
		
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @return the ass
	 */
	public String getAss() {
		return ass;
	}

	/**
	 * @return the candKey
	 */
	public String getCandKey() {
		return candKey;
	}

	/**
	 * @return the mark
	 */
	public String getMark() {
		return mark;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	
	
	/**
	 * @return the studentNumber
	 */
	public String getStudentNumber() {
		return studentNumber;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @param ass the ass to set
	 */
	public void setAss(String ass) {
		this.ass = ass;
	}

	/**
	 * @param candKey the candKey to set
	 */
	public void setCandKey(String candKey) {
		this.candKey = candKey;
	}

	/**
	 * @param mark the mark to set
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @param studentNumber the studentNumber to set
	 */
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	
	
}
