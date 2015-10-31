package studentDataApp;

import java.awt.Color;
import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

/**
 * ______________________________________________________________________________________
 * This class prints a pdf for the given java frame
 * 
 * @author Amreen Fatima S Surani
 * @since 16th March 2015
 * @version 0.1
 *
 * ______________________________________________________________________________________
 */

public class Print2PDF 
{

	private ArrayList<Student> studentList;
	private String studentName;
	private String studentEmail;
	private String studentTutorEmail;
	private String studentNumber;
	private String studentLastAccess;
	private Paragraph nameHeader;
	private Paragraph emailSubHeader;
	private Assessment studentAssessment;
	private ArrayList<Result> studentResult;
	
	private static Font header1Font = new Font(Font.FontFamily.TIMES_ROMAN,24,Font.BOLD,BaseColor.BLUE);
	private static Font header2Font = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.ITALIC,BaseColor.BLUE);
	private static Font header3Font = new Font(Font.FontFamily.TIMES_ROMAN,10,Font.ITALIC,BaseColor.BLUE);
	private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN,10,Font.NORMAL);

	/**
	 * @param userList-ArrayList of Student Objects
	 * @param userAssessment-Assessment Object holding result list of students
	 */
	public Print2PDF(ArrayList<Student> userList,Assessment userAssessment)  
	{
		studentList = (ArrayList<Student>)userList.clone();
		studentAssessment = userAssessment;
		createPDF();
	}
	
	/**
	 * This method creates a single PDF containing details of a single student per page.
	 */
	
	public void createPDF()
	{
		try
		{
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Student Report.pdf"));		
		document.open();

		// Page generation per student
		for(Student st:studentList)
		{
			studentName = st.getStudentName();
			studentEmail = st.getEmail();
			studentTutorEmail =st.getTutorEmail();
			studentNumber = "Student Number: "+st.getStudentNumber();
			studentLastAccess = "Student Last Access: "+ st.getLastAccess();
			
			// Prints the student name
			nameHeader = new Paragraph(studentName,header1Font);
			nameHeader.setAlignment(nameHeader.ALIGN_CENTER);
			document.add(nameHeader);
			
			// Prints the student email
			emailSubHeader = new Paragraph(studentEmail,header2Font);
			emailSubHeader.setAlignment(emailSubHeader.ALIGN_CENTER);
			document.add(emailSubHeader);
			
			// Prints a horizontal line
			document.add(Chunk.NEWLINE);
			LineSeparator line1 = new LineSeparator();              
			document.add(line1);
			
			//Prints tutor information and student number information
			document.add(new Phrase("Tutor: ",normalFont));
			document.add(new Phrase(studentTutorEmail,header3Font));
			document.add(new Paragraph(studentNumber,normalFont));
			document.add(new Paragraph(studentLastAccess,normalFont));
			document.add(Chunk.NEWLINE);
			
			
			
			// Prints a horizontal line
			LineSeparator line2 = new LineSeparator();              
			document.add(line2);
			
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			
			// Prints a table to print marks and graded for each module by assessment
			studentResult = studentAssessment.getResultList(st.getStudentNumber());
			
			if(studentResult.size()!=0)
			{
				PdfPTable table = new PdfPTable(4);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell("Assessment");
				table.addCell("Module");
				table.addCell("Mark");
				table.addCell("Grade");
				
			for(Result x:studentResult)
			{
				table.addCell(x.getAss());
				table.addCell(x.getModule());
				table.addCell(x.getMark());
				table.addCell(x.getGrade());
			}
			document.add(table);
			}
			else
			{
				document.add(new Paragraph("No data available for any assessments",normalFont));
			}
			document.newPage();
			document.newPage();
		}
	    document.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}	
	}
	

}