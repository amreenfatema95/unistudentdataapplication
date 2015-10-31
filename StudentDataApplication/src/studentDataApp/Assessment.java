/**
 * 
 */
package studentDataApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * _______________________________________________________
 * This class creates a HashMap of Results by Assessment.
 * 
 * @author  Amreen Fatima S Surani
 * @since 14th March 2015
 * @version 0.1
 *
 * ________________________________________________________
 */
public class Assessment 
{
	private HashSet<String> assesments = new HashSet<String>();
	private ArrayList<Result> resultListArray;
	private Map<String,List<Result>> assMap =new HashMap<String,List<Result>>();
	/**
	 * @param userArrayList-ArrayList of Result Objects.
	 */
	public Assessment(ArrayList<Result> userArrayList)
	{
		resultListArray=userArrayList;
		createMap();
	}
	
	//Groups the results by Assessment.
	private void createMap()
	{
		for(Result x:resultListArray)
		{
			String assKey = x.getAss();
			assesments.add(assKey);
			if(assMap.containsKey(assKey))
			{
				assMap.get(assKey).add(x);
			}
			else
			{
				List<Result> resList = new ArrayList<Result>();
				resList.add(x);
				assMap.put(assKey, resList);
			}
			
		}
	}

	
	public Map<String, List<Result>> getAssMap() {
		return assMap;
	}

	
	public String[] getAssesments() {
		String[] rtrn = new String[assesments.size()];
        rtrn = assesments.toArray(rtrn);
        Arrays.sort(rtrn);
		return rtrn;
	}
	
	
	/**
	 * @param inStudentNumber-student number
	 * @return an array list of result objects for the given student
	 */
	public ArrayList<Result> getResultList(String inStudentNumber)
	{
		ArrayList<Result> newResultList = new ArrayList<Result>();
		for(String key : assMap.keySet())
		{
			for(int i = 0;i<assMap.get(key).size();i++)
			{
				if(inStudentNumber.equals(assMap.get(key).get(i).getStudentNumber()))
				{
					newResultList.add(assMap.get(key).get(i));
				}			
			}
		}
		
		return newResultList;
	}
	
	/**
	 * @param inStudentNumber-student number
	 * @return a Jtable containing results of all assessments for the given student
	 */
	public JTable getResultTable(String inStudentNumber)
	{
		ArrayList<Result> userResList = getResultList(inStudentNumber);
		JTable newResultTable = new JTable();
		
		if(userResList.size()!=0)
		{
		//headers for the table
        String[] columnHeader = new String[] {"Assessment", "Module", "Mark", "Grade"};
         
        //actual data for the table in a 2d array
        Object[][] tableContent = new Object[userResList.size()][4];
        
        for(int i = 0;i<userResList.size();i++)
		{
        	tableContent[i][0]  = userResList.get(i).getAss();
        	tableContent[i][1]  = userResList.get(i).getModule();
        	tableContent[i][2]  = userResList.get(i).getMark();
        	tableContent[i][3]  = userResList.get(i).getGrade();
		}
         
        final Class[] columnClass = new Class[] {String.class, String.class, String.class, String.class};
 
        //create table model with data
        DefaultTableModel defmodel = new DefaultTableModel(tableContent, columnHeader) {
 
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
 
            @Override
            public Class<?> getColumnClass(int colIndex)
            {
                return columnClass[colIndex];
            }
        };
        
        return new JTable(defmodel);
		}
		else
		{
			return null;
		}
	}
	
}

