package nexti.ejms.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class XlsReadPOI {
	private static Logger logger = Logger.getLogger(XlsReadPOI.class);
	private String filePath = null;     //xls 파일 경로
	private int sheetNum    = 0;        //Sheet 번호
	private int startNum    = 0;		//시작위치
	
	public static void main(String[] args) {
		String file = "C:\\Users\\Administrator\\Desktop\\";
		System.out.print("엑셀파일명 : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {file += br.readLine() + ".xls";} catch (Exception e) {}
		
		List xls = new XlsReadPOI(file, 0, 0).readXls();
		
		for ( int i = 0; xls != null && i < xls.size(); i++ ) {
			List cell = (List)xls.get(i);
			for ( int j = 0; j < cell.size(); j++ ) {
				String szValue = (String)cell.get(j);
				System.out.print(szValue + "\t");
			}
			System.out.println();
		}
	}
	  
	public XlsReadPOI(String filePath, int sheetNum, int startNum) {
		this.filePath = filePath;
		this.sheetNum = sheetNum;
		this.startNum = startNum;
	}

	public List readXls() {
		
		HSSFWorkbook wb = null;     //xls workbench 객체
		HSSFSheet sheet = null;     //xls sheet 객체
		HSSFRow row 	= null;     //xls row 객체
		HSSFCell cell   = null;     //xls cell 객체	
		
		String cellValue = null;		
		List xlsList = null;
		FileInputStream input = null;
		
		try {
			input = new FileInputStream(filePath);
			POIFSFileSystem fs = new POIFSFileSystem(input);
			
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(sheetNum);
			int rows = sheet.getLastRowNum();   //전체 ROW 수			
			
			for (int i = 0 ; i <= rows ; i++) {
				if (i < startNum) continue;
				row = sheet.getRow(i);
				
				//Cell 의 마지막 Index를 구한다.(첫번째 열)
				if (row == null) continue;
				int cellbound = row.getLastCellNum();
				
				//if (row.getCell(0)==null) continue;	//매행 첫번째 열이 비어있을 때
				List cellList = new ArrayList();				
				for(int k = 0; k < cellbound; k++) {
					cell = row.getCell(k);
					//Cell의 첫번째 값이 없으면 스킵
					if (k==0 && (cell==null || (cell!=null && cell.toString().replaceAll(" ", "").trim()==""))) {
						continue;
					}
					
					//값이 없을 경우 ""로 입력
					if (cell == null) {
						cellValue = "";
						cellList.add(cellValue);
						continue;
					}

					switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_FORMULA :
							//cellValue = cell.getCellFormula();
							try {
								if(HSSFDateUtil.isCellDateFormatted(cell)) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									cellValue = sdf.format(cell.getDateCellValue());
								} else {
									DecimalFormat df = new DecimalFormat("#.##########");
									cellValue = String.valueOf(df.format(cell.getNumericCellValue()));
								}
							} catch (Exception e) {
								cellValue = cell.getRichStringCellValue().getString();
							}
							break;
						case HSSFCell.CELL_TYPE_NUMERIC :
							if(HSSFDateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								cellValue = sdf.format(cell.getDateCellValue());
							} else {
								DecimalFormat df = new DecimalFormat("#.##########");
								cellValue = String.valueOf(df.format(cell.getNumericCellValue()));
							}
							break;
						case HSSFCell.CELL_TYPE_STRING :
							cellValue = cell.getRichStringCellValue().getString();
							break;
						case HSSFCell.CELL_TYPE_BLANK :
							cellValue = "";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN :
							break;
						case HSSFCell.CELL_TYPE_ERROR :
							cellValue = "ERROR";
							break;
						default :
							break;
					}
					cellList.add(cellValue);					
				} //for cells
				
				if ( xlsList == null ) {
					xlsList = new ArrayList();
				}
				xlsList.add(cellList);		
			} //for rows

		} catch( Exception e) {
	    	logger.error("error", e);
	    	return null;
	    } finally {
	    	try {input.close();} catch (Exception e) {}
	    }
	    
		return xlsList;
	}
}
