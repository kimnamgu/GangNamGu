package nexti.ejms.util;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class XlsWritePOI {
	
	/**
	 * DB조회결과를 XLS로 저장
	 * @param response HttpServletResponse
	 * @param fileName 파일명
	 * @param sheetName 쉬트명
	 * @param con DB커넥션
	 * @param selectQuery 조회쿼리
	 * @param isTitle 컬럼명포함유무
	 * @return XLS저장 성공유무
	 * @throws Exception
	 */
	public static boolean writeXls(HttpServletResponse response, String fileName, String sheetName, Connection con, String selectQuery, boolean isTitle) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OutputStream out = null;
		boolean result = false;
		
	    try {
			pstmt = con.prepareStatement(selectQuery);
			rs = pstmt.executeQuery();
	
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet();
	        workbook.setSheetName(0, sheetName);

	        //제목 꾸미기
	        HSSFCellStyle style = workbook.createCellStyle();
	        style.setFillBackgroundColor(HSSFColor.BLUE.index);

	        ResultSetMetaData mdrs = rs.getMetaData();
			int colcount = mdrs.getColumnCount();
	        int rows = 0;
	        HSSFRow row = null;
			if (isTitle) {
		        row = sheet.createRow(0);
		        for(int i=0; i<colcount; i++) {
		        	HSSFCell cell = row.createCell(i);
		        	cell.setCellStyle(style);
		        	HSSFRichTextString str = new HSSFRichTextString(mdrs.getColumnName(i+1));
		        	cell.setCellValue(str);
		        }
		        rows++;
			}

	        //내용 꾸미기
	        while(rs.next()){
		        row = sheet.createRow(rows);
		        for (int i=0; i<colcount; i++) {
		            HSSFCell cell = row.createCell(i);
		            if("NUMBER".equals(rs.getMetaData().getColumnTypeName(i+1))){
		            	cell.setCellValue(rs.getInt(i+1));
		            } else {
		            	cell.setCellValue(new HSSFRichTextString(rs.getString(i+1)));
		            }
		        }
		        rows++;
	        }

	        //컬럼 사이즈 조정
	        for(short i=0; i<colcount; i++) {
	        	sheet.autoSizeColumn(i);
	        }
	         
        	response.setContentType("application/vnd.ms-excel;charset=euc-kr");
			response.setHeader("Content-disposition","attachment;filename=" + commfunction.fileNameFix(fileName.replaceAll(";", ":")));
			out = response.getOutputStream();
			
            workbook.write(out);
            out.flush();
            result = true;
	    } catch ( Exception e ) {
			e.printStackTrace();	        	
		} finally {
			try { rs.close(); } catch (Exception e) {}
			try { pstmt.close(); } catch (Exception e) {}
			try { out.close(); } catch (Exception e) {}
		}
		
		return result;
	}
	
	/**
	 * String[][] 데이터를 XLS로 저장
	 * @param response HttpServletResponse
	 * @param fileName 파일명
	 * @param sheetName 쉬트명목록(String)
	 * @param sheetData 데이터목록(String[][])
	 * @return XLS저장 성공유무
	 * @throws Exception
	 */
	public static boolean writeXls(HttpServletResponse response, String fileName, List sheetName, List sheetData) {
		OutputStream out = null;
		boolean result = false;
		
	    try {	
	        HSSFWorkbook workbook = new HSSFWorkbook();

	        for ( int i = 0; sheetName != null && i < sheetName.size(); i++ ) {
		        HSSFSheet sheet = workbook.createSheet((String)sheetName.get(i));
	        	String[][] data = (String[][])sheetData.get(i);
	        	
		        //내용 꾸미기
	        	for ( int rowIndex = 0; rowIndex < data.length; rowIndex++ ) {
	        		HSSFRow row = sheet.createRow(rowIndex);
	        		
	        		for ( int colIndex = 0; colIndex < data[rowIndex].length; colIndex++ ) {
			            HSSFCell cell = row.createCell(colIndex);
			            cell.setCellValue(new HSSFRichTextString(data[rowIndex][colIndex]));
	        		}
	        	}
	        	
		        //컬럼 사이즈 조정
		        for( short k = 0; k < data[0].length; k++ ) {
		        	sheet.autoSizeColumn(k);
		        	sheet.setColumnWidth(k, sheet.getColumnWidth(k) + 1000);
		        }
	        }
	        
        	response.setContentType("application/vnd.ms-excel;charset=euc-kr");
			response.setHeader("Content-disposition","attachment;filename=" + commfunction.fileNameFix(fileName.replaceAll(";", ":")));
			out = response.getOutputStream();   
			
            workbook.write(out);
            out.flush();
            result = true;
	    } catch ( Exception e ) {
			e.printStackTrace();	        	
		} finally {
			try { out.close(); } catch (Exception e) {}
		}
		
		return result;
	}
}