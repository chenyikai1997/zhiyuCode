package com.fh.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import static org.apache.poi.ss.usermodel.CellType.STRING;

/**
 * 从EXCEL导入到数据库
 * 创建人：FH Q313596790
 * 创建时间：2014年12月23日
 * @version
 */
public class ObjectExcelRead2 {
	protected static Logger logger = LogManager.getLogger("running-log");

	/**
	 * @param filepath //文件路径
	 * @param filename //文件名
	 * @param startrow //开始行号
	 * @param startcol //开始列号
	 * @param sheetnum //sheet
	 * @return list
	 */
	public static List<Object> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<Object> varList = new ArrayList<Object>();

		LinkedHashMap<Integer,String> titleMap = new LinkedHashMap<Integer, String>();		//用来存放标题顺序

		try {
			File target = new File(filepath, filename);
			FileInputStream fi = new FileInputStream(target);
			Workbook wb = WorkbookFactory.create(fi);
			Sheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
			int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

			Row firstRow = sheet.getRow(startrow); 							//从标题行开始


			Cell firstCell = firstRow.getCell(0);
			if(firstCell.getStringCellValue().equals("风险点登记表") || firstCell.getStringCellValue().equals("风险登记表")){
				firstRow = sheet.getRow(1);
				startrow ++;
			}
			int firstCellNum = firstRow.getLastCellNum(); 					//标题行的最后一个单元格位置
			for (int j = startcol; j < firstCellNum; j++) {                //列循环开始
				Cell titleCell = firstRow.getCell(j);
				String cellValue = fengXianJudge.judgeTitle(titleCell.getStringCellValue());	//获得该列的标题
				titleMap.put(j,cellValue);							//key是序号，value英文
			}
			startrow++;

			logger.info("导入风险列表列长度"+rowNum);


			for (int i = startrow; i < rowNum; i++) {					//行循环开始
				
				PageData varpd = new PageData();
				Row row = sheet.getRow(i); 							//行
				int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

				logger.info("导入风险列表行长度"+cellNum);

				for (int j = startcol; j < cellNum; j++) {				//列循环开始
					
					Cell cell = row.getCell(Short.parseShort(j + ""));
					//部署环境的地方会读到excel表格的后面导致只能导入一条，需要先做长度的判断
					if(j == 0 && (cell.getStringCellValue().equals("") || cell.getStringCellValue() == null)){
						continue;
					}
					String cellValue = null;
					if (null != cell) {
						CellType a = cell.getCellType();
						if(a.name().equals("NUMERIC")){
							DecimalFormat df=new DecimalFormat("0");
							cellValue=df.format(cell.getNumericCellValue());
							Calendar calendar = new GregorianCalendar(1900,0,-1);
							//超过5位数说明是联系人
							if(cellValue.length() <= 5){
								Date date = DateUtil.addDay(calendar.getTime(),Integer.parseInt(cellValue));
								cellValue = Tools.date2Str(date,"yyyy-MM-dd");
							}

						}
						else{
							cellValue = cell.getStringCellValue();
						}

					}
					else{
						cellValue = "";
					}
					
					varpd.put(titleMap.get(j), cellValue);
				}
				varList.add(varpd);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return varList;
	}
}
