package studentDataApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.opencsv.CSVReader;


/**
 * ______________________________________________________________________________________
 * This class....
 * 
 * @author Sabiha Karim Sulaiman Abdul and Amreen Fatima S Surani
 * @since 8th March 2015
 * @version 0.1
 *
 * ______________________________________________________________________________________
 */
public class StudentDataGUI extends JFrame
{
	private Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
	private double monitorWidth=monitorSize.getWidth();
	private double monitorHeight = monitorSize.getHeight();
	private int selectedTab = 0;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private StudentList listOfStudents;
	private Assessment assObject;
	private String[] stuNameNum;
	private JList list;
	private JScrollPane scrollPane;
	private JTextField searchField;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JMenuItem loadMarks;
	private JMenuItem loadResults;
	private JMenu dataMenu;
	private JMenuItem emailstudents;
	private JMenuItem emailsettings;
	private JTabbedPane tabbedPane;
	private JMenuItem compareToAverage;
	private JMenuItem fetch;
	private JMenuItem printStudentReport;
	private ArrayList<Result> ResultList = new ArrayList<Result>();
	
	
	public StudentDataGUI()
	{
		createWindow();
	}
	
	/**
	 * Creates a window.
	 * @since 0.1
	 */
	public void createWindow()
	{
		getContentPane().removeAll();

		setSize((int)(monitorWidth/2),(int)(monitorHeight/2));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("PRA Coursework-ASM_XEO");
		setLayout(new BorderLayout());
		
		//Add a menuBar with menu items
		menuBar = new JMenuBar();
		add(menuBar,BorderLayout.NORTH);
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
	
		//adding the data menubar
		dataMenu = new JMenu("Data");
		menuBar.add(dataMenu);
		
		//Adding a menuItem to get scatterplot
		ImageIcon scatterPlotIcon = new ImageIcon("scatterPlotIcon.png");
		compareToAverage = new JMenuItem("Compare To Average",scatterPlotIcon);
		dataMenu.add(compareToAverage);
		
		//Adding an actionListener to 'Compare To Average' menuItem
		compareToAverage.addActionListener(new MyCompareToAverageListener());
				
		
		//menuBar.add(data);
		
		//Adding a menuItem to load anonymous marking codes.
		ImageIcon csvIcon = new ImageIcon("csvIcon.png");
		loadMarks = new JMenuItem("Load anonymous marking codes",csvIcon);
		fileMenu.add(loadMarks);
		
		//Adding a menuItem to Load exam results
		loadResults = new JMenuItem("Load exam results",csvIcon);
		fileMenu.add(loadResults);
		
		//Adding a menuItem to print student Report
		ImageIcon pdfIcon = new ImageIcon("pdfIcon.png");
		printStudentReport = new JMenuItem("Create Student Report",pdfIcon);
		fileMenu.add(printStudentReport);
	
		//Adding an actionListener to the load Marks menuItem.
		loadMarks.addActionListener(new myAnonymousFileListener());
		
		//Adding an actionListener to load Exam Results menuItem.
		loadResults.addActionListener(new myExamResultsFileListener());
		
		//Adding an actionListener to print student report menuItem.
		printStudentReport.addActionListener(new MyPrintReportListener());
				
		//creating an array of StudentName and Number.
		listOfStudents = new StudentList();
		stuNameNum = new String[listOfStudents.getStudentList().size()];
		for (int i=0;i<stuNameNum.length;i++)
		{
			stuNameNum[i]=listOfStudents.getStudentList().get(i).getStu_Name_Number();
		}
		
		//creating a left panel
		leftPanel = new JPanel(new BorderLayout());
		rightPanel = new JPanel(new BorderLayout());
		add(leftPanel,BorderLayout.WEST);
		add(rightPanel,BorderLayout.CENTER);
		
		    
		    tabbedPane = new JTabbedPane();

		    if (assObject != null) {
		    	for (String ass:assObject.getAssesments()) {
		    		//System.out.println("ass#=" + ass);
		    			    String columnNames[] = { "studentNumber", "candKey", "ass",  "module", "mark", "grade" };
		    			    
		    			    List<Result> results = assObject.getAssMap().get(ass);
			    			String[][] rowData =new String[results.size()][6];
			    			int i=0;
		    			    for (Result result: results) {
		    			    	rowData[i][0] = result.getStudentNumber();
		    			    	rowData[i][1] = result.getCandKey();
		    			    	rowData[i][2] = result.getAss();
		    			    	rowData[i][3] = result.getModule();
		    			    	rowData[i][4] = result.getMark();
		    			    	rowData[i][5] = result.getGrade();
		    			    	i = i+1;
		    			    }
		    			    JTable table = new JTable(rowData, columnNames);
		    			    
		    			    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

								@Override
								public void valueChanged(ListSelectionEvent e) {
									if (e.getValueIsAdjusting()==false)
									{
										//obtain the Student details using the selected index.
										int idx = e.getLastIndex();
										String selStudentNum = (String) table.getValueAt(idx, 0);
										for(Student st:listOfStudents.getStudentList())
										{
											if(selStudentNum.equals(st.getStudentNumber()))
											{
												String selName = st.getStudentName();
												String selEmail = st.getEmail();
												String selTutor = st.getTutorEmail();
												String selLastAccess = st.getLastAccess();
												popUpContents(selName,selEmail,selStudentNum,selTutor,selLastAccess,assObject);
											}
										}
									}
									
								}
								
							});
		    			    
		    			    JScrollPane scrollPane = new JScrollPane(table);
	    			    
				    		   tabbedPane.addTab(ass, null, scrollPane,
					                      "");
				    		   tabbedPane.setSelectedIndex(selectedTab);

		    	}
		    }
		 
		    
			rightPanel.add(tabbedPane);

		
		//creating a search field to filter the list of names.
		searchField = new JTextField();
		searchField.addCaretListener(new mySearchListener());
		
		leftPanel.add(searchField,BorderLayout.NORTH);
		
		//creating a scroll pane and adding a list of students
		list = new JList(stuNameNum);
		scrollPane = new JScrollPane(list);
		leftPanel.add(scrollPane,BorderLayout.CENTER);
		
		//Adding a listener to the list
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (list.getValueIsAdjusting()==false)
				{
					//obtain the Student details using the selected index.
					int idx = list.getSelectedIndex();
					String selName = listOfStudents.getStudentList().get(idx).getStudentName();
					String selEmail = listOfStudents.getStudentList().get(idx).getEmail();
					String selStudentNum = listOfStudents.getStudentList().get(idx).getStudentNumber();
					String selTutor = listOfStudents.getStudentList().get(idx).getTutorEmail();
					String selLastAccess = listOfStudents.getStudentList().get(idx).getLastAccess();
					popUpContents(selName,selEmail,selStudentNum,selTutor,selLastAccess,new Assessment(ResultList));
				
				}
			}
		});
		
		setVisible(true);
		revalidate();


	}
	
	//Adding an ActionListener for the search field.
	class mySearchListener implements CaretListener
	{
		public void caretUpdate(CaretEvent e)
		{	
			List<String> subList = new ArrayList<String>();
			for(int i=0;i<stuNameNum.length;i++)
			{
				if(stuNameNum[i].toLowerCase().contains(searchField.getText().toLowerCase()))
				{
					subList.add(stuNameNum[i]);
				}
			}
			list.setListData(subList.toArray());
		}
	}

	/**
	 * Creates an informationPanel for the selected Student.
	 * @param userName name of the selected student.
	 * @param userEmail email of the selected student
	 * @param userStudentNumber student number of the selected student
	 * @param userTutor email of the tutor of the student
	 */
	
	// Creates an informationPanel for the selected Student
	public void popUpContents(String userName,String userEmail,String userStudentNumber,String userTutor,String userLastAccess,Assessment userAssessment)
	{

		JFrame infoFrame = new JFrame();
		infoFrame.setLocationRelativeTo(null);
		infoFrame.setSize((int)(monitorWidth/3),(int)(monitorHeight/4));
		infoFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		infoFrame.setTitle("Student Information");
		Box box = Box.createVerticalBox();
		infoFrame.add(box);
		
		String popname = "<html><tag><h1><font color=#2E15D3><b>	"+userName+"</h1></b></font></tag></html>";
		JLabel nameLabel = new JLabel(popname);
		nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);;
		box.add(nameLabel);
		
		String popEmail = "<html><tag><h2><font color=#6BA2E6><i>"+userEmail+"</h2></i></font></tag></html>";
		JLabel emailLabel = new JLabel(popEmail);
		emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		box.add(emailLabel);
		
		String popStudentNumber ="<html><tag><h3><b><font color=#2E15D3>Student Number: </font>"+userStudentNumber+"</b></h3></tag></html>";
		JLabel studentNumLabel = new JLabel(popStudentNumber);
		studentNumLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(studentNumLabel);
		
		String popTutor = "<html><tag><h3><b><font color=#2E15D3>Tutor: </font>"+userTutor+"</b></h3></tag></html>";
		JLabel tutorLabel = new JLabel(popTutor);
		tutorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(tutorLabel);
		
		
		String popLastAccess = "<html><tag><h3><b><font color=#2E15D3>Student Last Access: </font>"+userLastAccess+"</b></h3></tag></html>";
		JLabel lastAccessLabel = new JLabel(popLastAccess);
		lastAccessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(lastAccessLabel);
		
		if(!userAssessment.getAssMap().isEmpty())
		{
			infoFrame.resize((int)(monitorWidth/3),(int)(monitorHeight/3));
			JTable popStudentResult = userAssessment.getResultTable(userStudentNumber);
			JScrollPane tempScrollPane = new JScrollPane(popStudentResult);
			JPanel tempPanel = new JPanel(new BorderLayout());
			tempPanel.add(tempScrollPane, BorderLayout.CENTER);
			box.add(tempPanel);
		}
		infoFrame.setVisible(true);
	}
	

	//This listener uses an external jar file to parse CSV Files.
	class myAnonymousFileListener implements ActionListener 

	{
		public void actionPerformed(ActionEvent e)
		{
			FileDialog anonymousFileChooser = new FileDialog(StudentDataGUI.this,"Choose a File",FileDialog.LOAD);
			anonymousFileChooser.setVisible(true);
			String selectedFile = anonymousFileChooser.getFile();
			if (selectedFile!=null)
			{
				String anonymousFileDirectory = anonymousFileChooser.getDirectory();
				
				String anonymousFileSelected = anonymousFileDirectory+selectedFile;
				try {
					CSVReader anonymousFileReader = new CSVReader(new FileReader(anonymousFileSelected));
					String[] inputMarks;
					int countRead = 0;
					int countAdded = 0;
					//Assigning the anonymous marking code to each student.
					while((inputMarks=anonymousFileReader.readNext())!=null)
					{
						for (int j=0;j<listOfStudents.getStudentList().size();j++)
						{
							if (inputMarks[0].equals(listOfStudents.getStudentList().get(j).getStudentNumber()))
							{
								listOfStudents.getStudentList().get(j).setAnonymousMarkingCodes(inputMarks[1]);
								countAdded++;
							}
						}
						countRead++;
					}
					int countNotAdded = countRead-countAdded;
					String displayMessage;
					if ((countNotAdded==1)&&(countAdded==1))
					{
						displayMessage = "<html><tag><h3><b><font color=#2E15D3>Anonymous marking codes imported. "+ countAdded +" code was for a known student; "+countNotAdded+" code was for an unknown student.</font></b></h3></tag></html>";
					}
					else if((countAdded==1)&&(countNotAdded!=1))
					{
						displayMessage = "<html><tag><h3><b><font color=#2E15D3>Anonymous marking codes imported. "+ countAdded +" code was for a known student; "+countNotAdded+" codes were for unknown students.</font></b></h3></tag></html>";
					}
					else if((countAdded!=1)&&(countNotAdded==1))
					{
						displayMessage = "<html><tag><h3><b><font color=#2E15D3>Anonymous marking codes imported. "+ countAdded +" codes were for known students; "+countNotAdded+" code was for an unknown student.</font></b></h3></tag></html>";
					}
					else
					{
						displayMessage = "<html><tag><h3><b><font color=#2E15D3>Anonymous marking codes imported. "+ countAdded +" codes were for known students; "+countNotAdded+" codes were for unknown students.</font></b></h3></tag></html>";
					}
					JOptionPane MessagePane = new JOptionPane();
					ImageIcon infoIcon = new ImageIcon("informationMessage.png");
					MessagePane.showMessageDialog(null,displayMessage,"Import Status",JOptionPane.INFORMATION_MESSAGE,infoIcon);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
	}
}

	class myExamResultsFileListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			FileDialog resultsFileChooser = new FileDialog(StudentDataGUI.this,"Choose a File",FileDialog.LOAD);
			resultsFileChooser.setVisible(true);
			String resultsFileName = resultsFileChooser.getFile();
			if (resultsFileName!=null)
			{
				String resultsFileDirectory = resultsFileChooser.getDirectory();
				String resultsFile = resultsFileDirectory+resultsFileName;
				try
				{
					CSVReader resultsFileReader =new CSVReader(new FileReader(resultsFile));
					
					//Reading the file header and working out its position.
					String[] resultsFileHeader = resultsFileReader.readNext();
					int moduleIndex = headerIndex(resultsFileHeader,"#Module");
					int assIndex = headerIndex(resultsFileHeader,"#Ass#");
					int candKeyIndex = headerIndex(resultsFileHeader,"#Cand Key");
					int markIndex = headerIndex(resultsFileHeader,"Mark");
					int gradeIndex = headerIndex(resultsFileHeader,"Grade");
					
					//Creating a list of Result Objects.
					String[] inputResult;
					while((inputResult=resultsFileReader.readNext())!=null)
					{
						Result res = new Result();
						res.setModule(inputResult[moduleIndex].replace('#',' ').trim());
						res.setAss(inputResult[assIndex].replace('#',' ').trim());
						res.setCandKey(inputResult[candKeyIndex].replace('#',' ').trim());
						res.setMark(inputResult[markIndex]);
						res.setGrade(inputResult[gradeIndex]);
						
						//de-anonymising the candidate key.
						boolean found = false;
						for(int i=0;i<listOfStudents.getStudentList().size() && !found;i++)
						{
							String inCandKey = res.getCandKey();
							String lookUpCandKey = listOfStudents.getStudentList().get(i).getAnonymousMarkingCodes();
							String studentNbr = listOfStudents.getStudentList().get(i).getStudentNumber();
							//System.out.println(listOfStudents.getStudentList().get(i).getStudentNumber() + "lookUpCandKey=" + lookUpCandKey + ":inCandKey=" + inCandKey);
							if ((inCandKey!=null)&&(lookUpCandKey!=null)&&(inCandKey.equals(lookUpCandKey)))
							{
								found = true;
								//System.out.println("anonymous student:inCandKey=" + inCandKey + ":found=" + found);
								res.setStudentNumber(listOfStudents.getStudentList().get(i).getStudentNumber());
							} else {
								//non anonymous student
								String tempStudentNbr = inCandKey;								
								int lastIndex = inCandKey.indexOf("/") ;
								if (lastIndex> -1) {
									tempStudentNbr = tempStudentNbr.substring(0, lastIndex);
								}
								if ((studentNbr!=null)&&(tempStudentNbr.equals(studentNbr))) {
									found = true;
								}
								res.setStudentNumber(tempStudentNbr);
								//System.out.println("NON anonymous student:inCandKey=" + inCandKey+ " :" + tempStudentNbr + ":found=" + found + ":lastIndex=" + lastIndex);
							}
							
						}
						ResultList.add(res);
					}
					 assObject = new Assessment(ResultList);
					 createWindow();
				}
				catch (IOException e2)
				{
					e2.printStackTrace();
				}
			}
		}
	}

	public int headerIndex(String[] userArray, String nameLookup)
	{
		int result = -1;
		for(int i=0;i<userArray.length;i++)
		{
			if (userArray[i].equals(nameLookup))
			{
				result=i;
			}
		}
		return result;
	}
	

	//A listener for generating scatter plot to compare to the average
	class MyCompareToAverageListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFreeChart scatterplot = ChartFactory.createScatterPlot("ScatterPlot","Average Mark","Mark",createDataset(),PlotOrientation.VERTICAL,true,true,false);
		        // create and display a frame...
		    ChartFrame frame = new ChartFrame("ScatterPlot", scatterplot);
		    frame.pack();
		    frame.setVisible(true);

		}
	}
	
	//Creates the series for the scatterplot
		private XYDataset createDataset() {
		    XYSeriesCollection result = new XYSeriesCollection();
		    XYSeries series = new XYSeries("Mark Against Average");
		    String selectedTab = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
		    
		    for(Student st:listOfStudents.getStudentList())
		    {
		    	double sum = 0;
		    	double mark = 0;
		    	boolean studentFound = false;
				
		    	ArrayList<Result> stuResList = assObject.getResultList(st.getStudentNumber());
		    	for (Result x:stuResList)
				{	
		    		if(selectedTab.equals(x.getAss()))
					{
		    			studentFound = true;
		    			mark = Double.parseDouble(x.getMark());
					}
					if(selectedTab!=x.getAss())
					{
						sum = sum + Double.parseDouble(x.getMark());
					}
				}

		    	double average = sum/(stuResList.size() -1);
		    	
		    	if(studentFound==true)
		    	{
		    		series.add(mark,average);
		    	}
		    }  	
		    result.addSeries(series);
		    return result;
		    
		}
		//A listener for printing reports per student to a single pdf
				class MyPrintReportListener implements ActionListener
				{
					public void actionPerformed(ActionEvent e)
					{
						new Print2PDF(listOfStudents.getStudentList(),new Assessment(ResultList));
					}
				}
}
