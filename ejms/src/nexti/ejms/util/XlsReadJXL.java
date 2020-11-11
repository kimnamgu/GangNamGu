package nexti.ejms.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import jxl.Sheet;
import jxl.Workbook;

public class XlsReadJXL {
	private static Logger logger = Logger.getLogger(XlsReadJXL.class);
	private int sheetNum    = 0;        //Sheet 번호
	private int startNum    = 0;		//시작위치
	private String filePath = null;     //xls 파일 경로
	
	public static void main(String[] args) {
		String file = "C:\\Users\\Administrator\\Desktop\\";
		System.out.print("엑셀파일명 : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {file += br.readLine() + ".xls";} catch (Exception e) {}
		
		List xls = new XlsReadJXL(file, 0, 0).readXls();
		
		for ( int i = 0; xls != null && i < xls.size(); i++ ) {
			List cell = (List)xls.get(i);
			for ( int j = 0; j < cell.size(); j++ ) {
				String szValue = (String)cell.get(j);
				System.out.print(szValue + "\t");
			}
			System.out.println();
		}
	}

	public XlsReadJXL(String filePath, int sheetNum, int startNum) {
		this.filePath = filePath;
		this.sheetNum = sheetNum;
		this.startNum = startNum;
	}

	public List readXls() {
		Workbook workbook = null;
		Sheet sheet = null;
        List xlsList = null;
		try {
			//엑셀파일을 인식
	        workbook = Workbook.getWorkbook( new File(filePath));

	        //엑셀파일에 포함된 sheet의 배열을 리턴한다.
	        //workbook.getSheets();

        	//엑셀파일에서 첫번째 Sheet를 인식
            sheet = workbook.getSheet(sheetNum);

        	//셀인식 Cell a1 = sheet.getCell( 컬럼 Index, 열 Index);
            //셀 내용 String stringa1 = a1.getContents();
            int RowCount = sheet.getRows();
            //System.out.println("Row 총 개수: " + RowCount);
            int nRowStartIndex = 0;
            //배열 처럼 0 부터 시작 되므로 마지막 Row 값은 총 Row 수에서 1를 뺀 값이다. 
            int nRowEndIndex   = RowCount - 1;  
            int ColumnCount = sheet.getColumns();
            int nColumnStartIndex = 0;
            int nColumnEndIndex = ColumnCount - 1;

            String szValue = null;
            for( int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++ ) {
				if (nRow < startNum) continue;
            	
            	List cellList = new ArrayList();
            	for( int nColumn = nColumnStartIndex; nColumn <= nColumnEndIndex ; nColumn++) {
            		szValue = sheet.getCell( nColumn, nRow).getContents();
            		cellList.add(szValue);
                }
            	
            	if ( xlsList == null ) {
					xlsList = new ArrayList();
				}
            	xlsList.add(cellList);
            }
	    } catch( Exception e) {
	    	logger.error("error", e);
	    	return null;
	    } finally {
	    	try {workbook.close();} catch (Exception e) {}
	    }
	
		return xlsList;
	}
}
