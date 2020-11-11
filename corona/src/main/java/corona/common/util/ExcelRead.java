package corona.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;

public class ExcelRead {

    public static List<Map<String, String>> read(ExcelReadOption excelReadOption) {
	    	 
	        // ���� ���� ��ü
	        // ���������� �о� ���δ�.
	        // FileType.getWorkbook() <-- ������ Ȯ���ڿ� ���� �����ϰ� �����´�.
	        Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
	        //    ���� ���Ͽ��� ù��° ��Ʈ�� ������ �´�.
	        Sheet sheet = wb.getSheetAt(0);
	        // sheet���� ��ȿ��(�����Ͱ� �ִ�) ���� ������ �����´�.
	        int numOfRows = sheet.getPhysicalNumberOfRows();
	        int numOfCells = 0;
	 
	        Row row = null;
	        Cell cell = null;
	 
	        String cellName = "";
	        /**
	         * �� row������ ���� ������ �� ��ü ����Ǵ� ������ ������ ����. put("A", "�̸�"); put("B",
	         * "���Ӹ�");
	         */
	        Map<String, String> map = null;
	        /*
	         * �� Row�� ����Ʈ�� ��´�. �ϳ��� Row�� �ϳ��� Map���� ǥ���Ǹ� List���� ��� Row�� ���Ե� ���̴�.
	         */
	        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	 
	        /**
	         * �� Row��ŭ �ݺ��� �Ѵ�.
	         */
	        for (int rowIndex = excelReadOption.getStartRow() - 1; rowIndex < numOfRows; rowIndex++) {
	          /*
	           * ��ũ�Ͽ��� ������ ��Ʈ���� rowIndex�� �ش��ϴ� Row�� �����´�. �ϳ��� Row�� �������� Cell�� ������.
	           */
	          row = sheet.getRow(rowIndex);
	 
	          if (row != null) {
	            /*
	             * ������ Row�� Cell�� ������ ���Ѵ�.
	             */
	            numOfCells = row.getPhysicalNumberOfCells();
	            /*
	             * �����͸� ���� �� ��ü �ʱ�ȭ
	             */
	            map = new HashMap<String, String>();
	            /*
	             * cell�� �� ��ŭ �ݺ��Ѵ�.
	             */
	            
	            System.out.println("������ Ȯ��" + excelReadOption.getOutputColumns().size());
	            
	            for (int cellIndex = 0; cellIndex < excelReadOption.getOutputColumns().size(); cellIndex++) {
	              /*
	               * Row���� CellIndex�� �ش��ϴ� Cell�� �����´�.
	               */
	              cell = row.getCell(cellIndex);

	              /*
	               * ���� Cell�� �̸��� �����´� �̸��� �� : A,B,C,D,......
	               */
	              cellName = ExcelCellRef.getName(cell, cellIndex);
	              /*
	               * ���� ��� �÷����� Ȯ���Ѵ� ���� ��� �÷��� �ƴ϶��, for�� �ٽ� �ö󰣴�
	               */
	              if (!excelReadOption.getOutputColumns().contains(cellName)) {
	                continue;
	              }
	              /*
	               * map��ü�� Cell�� �̸��� Ű(Key)�� �����͸� ��´�.
	               */
	              //�̺κ��� �߰�����
	              if(cell != null){
		              //��¥������ ������ ǥ���Ǿ ��Ʈ������ �޵��� ��ȯ
		              if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
		            	    String str = NumberToTextConverter.toText(cell.getNumericCellValue());
		            	    map.put(cellName, str);
		              }else{
		            	  map.put(cellName, ExcelCellRef.getValue(cell));
		              }
	              }else{
	            	  map.put(cellName, "");
	              }
	            }
	            /*
	             * ������� Map��ü�� List�� �ִ´�.
	             */
	            result.add(map);
	          }
	        }
	        return result;

	    }

	private static Cell Cell(String string) {
		// TODO Auto-generated method stub
		return null;
	}
  }
