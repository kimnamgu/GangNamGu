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
	private String filePath = null;     //xls ���� ���
	private int sheetNum    = 0;        //Sheet ��ȣ
	private int startNum    = 0;		//������ġ
	
	public static void main(String[] args) {
		String file = "C:\\Users\\Administrator\\Desktop\\";
		System.out.print("�������ϸ� : ");
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
		
		HSSFWorkbook wb = null;     //xls workbench ��ü
		HSSFSheet sheet = null;     //xls sheet ��ü
		HSSFRow row 	= null;     //xls row ��ü
		HSSFCell cell   = null;     //xls cell ��ü	
		
		String cellValue = null;		
		List xlsList = null;
		FileInputStream input = null;
		
		try {
			input = new FileInputStream(filePath);
			POIFSFileSystem fs = new POIFSFileSystem(input);
			
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(sheetNum);
			int rows = sheet.getLastRowNum();   //��ü ROW ��			
			
			for (int i = 0 ; i <= rows ; i++) {
				if (i < startNum) continue;
				row = sheet.getRow(i);
				
				//Cell �� ������ Index�� ���Ѵ�.(ù��° ��)
				if (row == null) continue;
				int cellbound = row.getLastCellNum();
				
				//if (row.getCell(0)==null) continue;	//���� ù��° ���� ������� ��
				List cellList = new ArrayList();				
				for(int k = 0; k < cellbound; k++) {
					cell = row.getCell(k);
					//Cell�� ù��° ���� ������ ��ŵ
					if (k==0 && (cell==null || (cell!=null && cell.toString().replaceAll(" ", "").trim()==""))) {
						continue;
					}
					
					//���� ���� ��� ""�� �Է�
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
