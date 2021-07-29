package com.bayee.util;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Author: 灵枢
 * Date: 2018/12/05
 * Time: 17:21
 * Description:读取Excel数据
 */
public class ExcelData {
	private XSSFSheet sheet;
	/**
	 * 构造函数，初始化excel数据
	 * @param filePath  excel路径
	 * @param sheetName sheet表名
	 */
	ExcelData(String filePath,String sheetName){
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(filePath);
			XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
			//获取sheet
			sheet = sheets.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据行和列的索引获取单元格的数据
	 * @param row
	 * @param column
	 * @return
	 */
	public String getExcelDateByIndex(int row,int column){
		XSSFRow row1 = sheet.getRow(row);
		String cell = row1.getCell(column).toString();
		return cell;
	}

	/**
	 * 根据某一列值为“******”的这一行，来获取该行第x列的值
	 * @param caseName
	 * @param currentColumn 当前单元格列的索引
	 * @param targetColumn 目标单元格列的索引
	 * @return
	 */
	public String getCellByCaseName(String caseName,int currentColumn,int targetColumn){
		String operateSteps="";
			//获取行数
			int rows = sheet.getPhysicalNumberOfRows();
			for(int i=0;i<rows;i++){
				XSSFRow row = sheet.getRow(i);
				String cell = row.getCell(currentColumn).toString();
				if(cell.equals(caseName)){
					operateSteps = row.getCell(targetColumn).toString();
					break;
				}
			}
		return operateSteps;
	}

	//打印excel数据
	public List<String>  readExcelData(){
		List<String> dataList=new ArrayList<String>();
		//获取行数
		int rows = sheet.getPhysicalNumberOfRows();
		//System.out.println(rows);
		for(int i=1;i<rows;i++){
			//获取列数
			XSSFRow row = sheet.getRow(i);
			if(null!=row) {
				int columns = row.getPhysicalNumberOfCells();
				//System.out.println(i);
				for(int j=0;j<columns;j++){
					if(null!=row.getCell(j)) {
						String cellData = row.getCell(j).toString();
						if(!StringUtils.isEmpty(cellData)) {
							dataList.add(cellData);
						}
					}
					//System.out.println(cell);
				}
			}
			
		}
		return dataList;
	}
	
	public static List<String> showExcel(String filePath,int columnNo){
		FileInputStream fileInputStream = null;
		List<String> dataList=new ArrayList<String>();
		try {
			fileInputStream = new FileInputStream(filePath);
			XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
			for(int i=0;i<sheets.getNumberOfSheets();i++) {
				//获取sheet
				XSSFSheet sheetxf = sheets.getSheetAt(i);
				for(int j=1;j<sheetxf.getPhysicalNumberOfRows();j++) {
					XSSFRow row = sheetxf.getRow(j);
					int columns = row.getPhysicalNumberOfCells(); 
					/*
					 * for(int k=0;k<columns;k++){ String cellData = row.getCell(k).toString();
					 * if(!StringUtils.isEmpty(cellData)) { System.out.println(cellData);
					 * //dataList.add(cellData); } }
					 */
					if(columns>columnNo) {
						String cellData=row.getCell(columnNo).toString();
						if(null!=cellData&&cellData.indexOf("(")>0) {
							cellData=cellData.replaceAll(" ", "").substring(0,cellData.indexOf("(")-1);
							dataList.add(cellData);
						}
					}
					
				}
				 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	//测试方法
	public static void main(String[] args){//Sheet1
		ExcelData sheet1 = new ExcelData("C:\\Users\\lw\\Desktop\\黑名单车辆.xlsx", "Sheet2");
		List<String> dataList=sheet1.readExcelData();
		//List<String> dataList=showExcel("C:\\Users\\lw\\Desktop\\业务提供数据\\绿农及货车明细\\嗨格式\\浙江长安站查验信息汇总表21.4.1-5.31.xlsx",1);
		String data="";
		for(String num:dataList) {
			if(!num.equals("出口车牌")){
				if(!data.contains(num)) {
					String sql="insert into sys_car_info(car_num,car_type)values('";
					//sql+=num.substring(1,num.length())+"',0,'货车');";
					sql+=num+"',1);";
					System.out.println(sql);
					data+=num+",";
				}
				
			}
			
			
		}
	}
}

