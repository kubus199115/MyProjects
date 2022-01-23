/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.web.viewresolver;

import com.mycompany.dto.woPanel;
import com.mycompany.entity.workOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author Kubus
 */
public class ExcelView extends AbstractExcelView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        List<woPanel> woList = new ArrayList<woPanel>();
        woList = (ArrayList<woPanel>) model.get("woList");    // dodajemy liste do wyświetlenia w excelu
        
        Sheet sheet = workbook.createSheet("zlecenia");
        
        Row row = null;
        Cell cell = null;
        int rowCount = 0;
        int colCount = 0;
 
        // komórki nagłówka
        row = sheet.createRow(rowCount++);
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        
 
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Numer zlecenia");
 
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Typ zlecenia");
 
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Planowany Start");
        
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Planowany Stop");
        
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Opis zlecenia");
        
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Obszar produkcyjny");
        
        // komórki danych
        if(woList != null) {
            for(woPanel wop: woList) {
                row = sheet.createRow(rowCount++);
                colCount = 0;
                row.createCell(colCount++).setCellValue(wop.getWoNumber());
                row.createCell(colCount++).setCellValue(wop.getWoType());

                // odpowiedni format daty dla komórki
                CellStyle cellStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm"));

                row.createCell(colCount++).setCellValue(wop.getPlanningStart());
                row.getCell(colCount-1).setCellStyle(cellStyle);
                row.createCell(colCount++).setCellValue(wop.getPlanningStop());
                row.getCell(colCount-1).setCellStyle(cellStyle);
                row.createCell(colCount++).setCellValue(wop.getDescription());
                row.createCell(colCount++).setCellValue(wop.getAreaName());
            }
        }
        
        // automatyczna szerokość kolumn
        for(int i=0; i<6; i++)
            sheet.autoSizeColumn(i, true);
    }
    
}
