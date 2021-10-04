package org.iit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;



public class Utility {
	
	static Random rand;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	
	
	public static String selectFutureDate(int nofDays)
	{
	      Calendar cal = Calendar.getInstance();
	      System.out.println(cal.getTime());
	      cal.add(Calendar.DAY_OF_MONTH, nofDays);
	      
	      SimpleDateFormat sdf = new SimpleDateFormat("d/MMMMM/YYYY");//10/October/2021
	      String date = sdf.format(cal.getTime());
	      System.out.println(date);
	      return date;
	}
	public static String generateRandom(int n,int range)
	{
		rand = new Random();
		String str = "";
		for(int j=0; j<n; j++)
		{
				str = str+ 9;	 
		}
		System.out.println(str);
			
		return str+rand.nextInt(range);
	}
	
	/**
	 * This will choose a random state
	 * @return - state 
	 */

	public static String getRandomState(){

		String state = "";

		String[] sArray = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
				"Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine","Maryland", "Massachusetts", "Michigan",
				"Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
				"North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
				"Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" };

		int num = rand.nextInt(49);
		state = sArray[num];
		System.out.println(state);		
		return state;
	}
	
	public static int getRandomNoOfDigits(int noOfDigits){
		
		rand = new Random();
		int addend1=0, addend2=0; String zero ="";String bound = "";int result=0;
		for(int i=1;i<=(noOfDigits-1); i++){
			zero = zero+0;
		}
		zero = 1+zero;
		addend1 = Integer.parseInt(zero);
		for (int j=1; j<=(noOfDigits-1); j++){
			bound = bound+9;
		}
		bound = 8+bound;
		addend2 = Integer.parseInt(bound);
		result = addend1+ rand.nextInt(addend2);
		System.out.println("Random "+noOfDigits+ " digit number is : "+result);
		return result;
		
	}
	
	/**
	 * This is to generate a random string for input data
	 * @param -int value of total no. of characters need to be in the input data
	 * @return - a random String
	 */
	public static String getRandomString(int noOfChars){

		rand = new Random();
		String s = "";
		int bound = noOfChars;
		//char[] charArray = new char[bound];
		for(int i=0; i<bound; i++){
			char c = (char) ('a'+rand.nextInt(26));
			s = s+c;
		}
		System.out.println("getRandomString Method returning "+s);
		return s;
	}

		
	public static String[][] readXls(String filePath) throws BiffException, IOException{

		File srcFile = new File(filePath);
		Workbook wb = Workbook.getWorkbook(srcFile);
		Sheet sheet = wb.getSheet(0);
		int row = sheet.getRows();
		int col = sheet.getColumns();
		
		String[][] str = new String[row][col];
		
		for (int i=0; i<row; i++){
			for (int j=0;j<col; j++){
				
				Cell cell = sheet.getCell(j, i);
				str[i][j] = cell.getContents().toString();
			}
		}
		
		return str;		
	}
	
	public static String[][] readXlsx(String filePath) throws IOException{
		
		File srcFile = new File(filePath);
		FileInputStream fis = new FileInputStream(srcFile);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int row = sheet.getLastRowNum()+1;
		int col = 2;
		String[][] str = new String[row][col];
		for (int i=0; i<row; i++){
			str[i][0] = sheet.getRow(i).getCell(0).toString();
			str[i][1] = sheet.getRow(i).getCell(1).toString();
		}
		wb.close();
		return str;		
		
	}
	public static String getFutureDate(int days,String pattern)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		Date d = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String date = sdf.format(d);
		return date;
	}
	public  static String getFutureDate(int days)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		Date d = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
		String date = sdf.format(d);
		return date;
	}
	//READING SPREADSHEET
		public static void ExcelUtils(String excelPath, String sheetName) {
			try {
				//creating a reference variable for workbook and sheet.
				workbook = new XSSFWorkbook(excelPath);
				sheet = workbook.getSheet(sheetName);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// Row count from excel sheet
		public static int getRowCount() {
			int rowCount = 0;
			try {
				rowCount = sheet.getLastRowNum()+1;
				System.out.println("No of Rows: " + rowCount);

			} catch (Exception exp) {
				System.out.println(exp.getMessage());
				System.out.println(exp.getCause());
				exp.printStackTrace();
			}
			return rowCount;
		}

		// Total number of columns 
		public static int getcolCount() {
			int colCount = 0;
			try 
			{
				colCount = sheet.getRow(0).getPhysicalNumberOfCells();
			} 
			catch (Exception exp) 
			{
				System.out.println(exp.getMessage());
				System.out.println(exp.getCause());
				exp.printStackTrace();
			}
			return colCount;
		}
		
		public static String getCellDataString(int rowCount, int colCount) {
			String cellData = null;
			try {

				cellData = sheet.getRow(rowCount).getCell(colCount).getStringCellValue();
				//System.out.println("Using Excel util"+cellData);

			} catch (Exception exp) {
				System.out.println(exp.getMessage());
				System.out.println(exp.getCause());
				exp.printStackTrace();
			}
			return cellData;
		}
		public String[][] readXlsFile(String filepath,String SheetName) throws IOException
		{
			File f=new File(filepath);
			FileInputStream fis=new FileInputStream(f);
			
			HSSFWorkbook hWB=new HSSFWorkbook(fis);
			HSSFSheet sheet=hWB.getSheet(SheetName);
			
			//No of rows
			int rows=sheet.getPhysicalNumberOfRows();
			System.out.println("Row count:::" +rows);
			
			//no of columns
			int cols=sheet.getRow(0).getLastCellNum();
			System.out.println("Column count:::" +cols);
			
			int k=0;
			
			String[][] data=new String[rows-1][cols];
			
			//If the first row contains the column names
			for(int i=1;i<rows;i++)
			{
				for(int j=0;j<cols;j++)
				{
					data[k][j]=sheet.getRow(i).getCell(j).toString();
					System.out.println(data[k][j]);
				}
				k++;
			}
			hWB.close();		
			return data;
			
		}

}
