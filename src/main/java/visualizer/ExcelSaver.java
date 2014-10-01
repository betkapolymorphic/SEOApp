package visualizer;


import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.List;

/**
 * Created by Beta on 9/25/14.
 */
public class ExcelSaver {
    public HSSFColor setColor(HSSFWorkbook workbook, byte r,byte g, byte b){
        HSSFPalette palette = workbook.getCustomPalette();
        HSSFColor hssfColor = null;
        try {
            hssfColor= palette.findColor(r, g, b);
            if (hssfColor == null ){
                palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g,b);
                hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
            }
        } catch (Exception e) {
            //logger.error(e);
        }

        return hssfColor;
    }
    static public void save(List<String> requests,int[][] matrix) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook( new FileInputStream("2.xls"));



        Sheet sheet = wb.getSheetAt(0);


        for(int i = 0;i<matrix.length;i++){
            String s = requests.get(i);
            sheet.getRow(0).createCell(i+1).setCellValue(s);
            sheet.getRow(i + 1).createCell(0).setCellValue(s);
        }
        for(int i =0 ;i<matrix.length;i++) {
            for(int j=0;j<matrix.length;j++){
                sheet.getRow(i + 1).createCell(j+1).setCellValue(matrix[i][j]);
            }
        }

        wb.write(new FileOutputStream("2.xls"));
        //wb.createSheet();
    }
}
