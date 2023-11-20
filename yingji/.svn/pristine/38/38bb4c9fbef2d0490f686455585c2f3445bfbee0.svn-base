package com.fh.util;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;


public class objectExcelReadPic {
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

        try {
            File target = new File(filepath, filename);
            FileInputStream fi = new FileInputStream(target);
            InputStream inputStream = new FileInputStream(filepath+filename);


            HSSFWorkbook wb = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = wb.getSheetAt(sheetnum); 					//sheet 从0开始
            int rowNum = sheet.getLastRowNum() + 1; 					//取得最后一行的行号

            Row firstRow = sheet.getRow(startrow);

            Cell firstCell = firstRow.getCell(0);
            //如果有表头或第一行为空
            if(firstCell.getStringCellValue().equals("隐患排查表") || firstCell.getStringCellValue().equals("") || firstCell.getStringCellValue().equals("安全隐患导入表")){
                firstRow = sheet.getRow(1);
                startrow += 2;
            }
            else{
                startrow++;
            }

            //判断是不是导出的隐患表，是就把startCol往后挪一位
            Row titleRow = sheet.getRow(startrow-1);
            int colIndex = 0;
            Cell titleCell = titleRow.getCell(0);
            if(!"安全隐患编码".equals(titleCell.getStringCellValue())){
                colIndex ++;
            }

            HSSFPatriarch testList = sheet.getDrawingPatriarch();
            Map<Integer, List<HSSFPictureData>> map = new LinkedHashMap<Integer, List<HSSFPictureData>>();
            //map2是整改完的附件
            Map<Integer, List<HSSFPictureData>> map2 = new LinkedHashMap<Integer, List<HSSFPictureData>>();

            List<HSSFShape> list = new ArrayList<>();
            if(sheet.getDrawingPatriarch() != null){
                list =  sheet.getDrawingPatriarch().getChildren();
            }

            for (HSSFShape shape : list) {
                if (shape instanceof HSSFPicture) {
                    HSSFPicture picture = (HSSFPicture) shape;
                    HSSFClientAnchor cAnchor = picture.getClientAnchor();
                    HSSFPictureData pdata = picture.getPictureData();
                    int key = cAnchor.getRow1(); // 行号-列号
                    int key1 = cAnchor.getCol1();
                    //图片起始位置
                    int key2 = cAnchor.getDx1();
                    int key3 = cAnchor.getDy1();
                    //如果不为空就取出对应行的list然后添加图片
                    if(map.get(key) != null && key1 == 12-colIndex){
                        List<HSSFPictureData> mapList = map.get(key);
                        mapList.add(pdata);
                        map.put(key,mapList);
                    }
                    else if(map.get(key) == null && key1 == 12-colIndex){
                        List<HSSFPictureData> mapList = new LinkedList<>();
                        mapList.add(pdata);
                        map.put(key, mapList);
                    }
                    else if(map2.get(key) != null && key1 == 20-colIndex){
                        List<HSSFPictureData> mapList = map2.get(key);
                        mapList.add(pdata);
                        map2.put(key,mapList);
                    }
                    else if(map2.get(key) == null && key1 == 20-colIndex){
                        List<HSSFPictureData> mapList = new LinkedList<>();
                        mapList.add(pdata);
                        map2.put(key, mapList);
                    }

                }
            }

            //存储图片，根据锚点位置
            Map<Integer, String> picturePath = new LinkedHashMap<Integer, String>();
            /*String[] picturePath = new String[rowNum];*/
            picturePath = saveImg(picturePath,map);
            //整改图片
            Map<Integer, String> picturePath2 = new LinkedHashMap<Integer, String>();
            picturePath2 = saveImg(picturePath2,map2);


            for (int i = startrow; i < rowNum; i++) {					//行循环开始

                PageData varpd = new PageData();
                Row row = sheet.getRow(i); 							//行
                int cellNum = row.getLastCellNum(); 					//每行的最后一个单元格位置

                for (int j = startcol; j < cellNum; j++) {				//列循环开始

                    Cell cell = row.getCell(Short.parseShort(j + ""));
                    String cellValue = null;
                    if (null != cell) {
                        switch (cell.getCellType()) { 					// 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
                            case NUMERIC:
                                DecimalFormat df=new DecimalFormat("0");
                                cellValue=df.format(cell.getNumericCellValue());
                                Calendar calendar = new GregorianCalendar(1900,0,-1);
                                if(cellValue.length() <= 5 && (j == (8-colIndex) || j == (16-colIndex))){
                                    Date date = DateUtil.addDay(calendar.getTime(),Integer.parseInt(cellValue));
                                    cellValue = Tools.date2Str(date,"yyyy-MM-dd");
                                }

                                break;
                            case FORMULA:
                                cellValue = String.valueOf(cell.getNumericCellValue());
                                //cellValue =cell.getCellFormula() + "";
                                break;
                            case STRING:
                                cellValue = cell.getStringCellValue();
                                break;
                            case BOOLEAN:
                                cellValue = cell.getBooleanCellValue() + "";
                                break;
                            case BLANK: //空值
                                cellValue = "";
                            case ERROR:
                                cellValue = "";
                                break;
                        }
                    } else {
                        cellValue = "";
                    }

                    varpd.put("var"+(j+colIndex), cellValue);

                }
                varpd.put("var"+(12), picturePath.get(i));
                varpd.put("var"+(20), picturePath2.get(i));
                if(colIndex == 0){
                    varpd.put("edit",colIndex+"");
                }

                varList.add(varpd);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return varList;
    }

    //传入picturedata的map上传然后弄成路径
    public static Map<Integer, String> saveImg(Map<Integer, String> picturePath, Map<Integer, List<HSSFPictureData>> map) throws IOException {
        for(Map.Entry<Integer, List<HSSFPictureData>> entry : map.entrySet()){
            List<HSSFPictureData> mapList = entry.getValue();
            //每一行的图片群
            for(HSSFPictureData hssfPictureData : mapList){
                String UUID = UuidUtil.get32UUID();
                String suggestFileExtension = hssfPictureData.suggestFileExtension();// 图片格式
                String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
                String filep = Const.FILEPATHIMG+UUID+".png";
                //加上文件夹路径和文件格式
                filePath = (filePath.trim() + Const.FILEPATHIMG+UUID+".png".trim()).substring(6).trim();
                //判断该行是否已经插入字符串，如果有的话就在后面加上分隔符
                if(picturePath.get(entry.getKey()) != null){
                    String tempPath = picturePath.get(entry.getKey()) + "," + filep;
                    picturePath.put(entry.getKey(),tempPath);
                }
                else{
                    picturePath.put(entry.getKey(),filep);
                }
                //将证件照存储路径存放到数组中
                Tools.mkdirsmy(PathUtil.getClasspath()+Const.FILEPATHIMG,UUID+".png");		//验证文件路径是否存在该文件
                FileOutputStream out = new FileOutputStream(filePath);// 流写入
                out.write(hssfPictureData.getData());
                out.close();
            }

        }
        return picturePath;
    }

}
