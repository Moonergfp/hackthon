package com.hack.controller;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 导出excel测试
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {



    @RequestMapping(value = "/r/export", method = RequestMethod.GET)
    @ResponseBody
    public void testExcel( HttpServletResponse response){
        OutputStream os = null;
        WritableWorkbook wwb = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            String filename = "询价单模板.xls";
            String sheetName = "询价单";
            int sheetNum = 0;
//            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));   //设置响应头
            response.setHeader("content-disposition", "attachment");   //设置响应头
            os = response.getOutputStream();
            wwb = Workbook.createWorkbook(os);      //创建excel
            WritableSheet sheet = wwb.createSheet(sheetName, sheetNum);
            WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 14, WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE);
            WritableCellFormat numFormat = new WritableCellFormat(font, NumberFormats.FLOAT);
            WritableCellFormat cellFormat = new WritableCellFormat(font);
            cellFormat.setAlignment(Alignment.CENTRE);
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            numFormat.setAlignment(Alignment.CENTRE);
            numFormat.setVerticalAlignment(VerticalAlignment.CENTRE);


            //excel头设置
            String[] headers = {"第一列","第二列"};
            int[] widths = {13, 13};  //列宽

            for (int i = 0; i < headers.length; i++) {
                Label labelHead = new Label(i, 0, headers[i], cellFormat);  //第0行为excel头
                sheet.addCell(labelHead);
            }

            int rowNum = 1;
            for (int j = 0 ; j < headers.length ;j++) {
                sheet.addCell(new Label(0, rowNum, "cotent", cellFormat));
                rowNum++;
            }

            //设置列宽
            for (int i = 0; i < widths.length; i++) {
                sheet.setColumnView(i, widths[i]);
            }
            // 创建日志
            wwb.write();
            os.flush();
        } catch (Exception ex) {
            return;
        } finally {
//          ExcelUtil.closeQuietly(wwb);
//            IOUtils.closeQuietly(os);
        }

        return ;
    }
}
