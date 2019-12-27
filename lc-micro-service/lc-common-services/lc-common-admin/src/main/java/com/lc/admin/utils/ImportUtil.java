package com.lc.admin.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 导入excel
 *
 * @author liucheng
 * @date 2019/7/22
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImportUtil {

    /**
     * 导入Excel
     *
     * @author liucheng
     * @date 2019/7/22
     */
    public static List importExcel(MultipartFile file, String[] target, String[] protName, Class clazz) {
        Workbook workbook = null;
        try {
            String fileName= file.getOriginalFilename();
            //判断Excel文件的版本
            boolean isExcel2003=true;

            if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                isExcel2003 = false;
            }

            InputStream is=file.getInputStream();
            if (isExcel2003) {
                workbook = new HSSFWorkbook(is);
            } else {
                workbook = new XSSFWorkbook(is);
            }
        } catch (Exception e) {
            log.error("未找到上传excel");
            log.error(e.getMessage(), e);
        }
        if(workbook == null){
            return null;
        }
        Sheet sheet;
        List<List> sheetList = Lists.newArrayList();
        try {
            // 获取每个Sheet表
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheet = workbook.getSheetAt(i);
                List rowList = new ArrayList();
                Map<String, Integer> xy = Maps.newHashMap();
                // getLastRowNum，获取最后一行的行标
                for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {
                    Row row = sheet.getRow(j);
                    if(row ==null){
                        break;
                    }
                    if (j == 0) {
                        for (int b = 0; b < target.length; b++) {
                            for (int a = 0; a < row.getLastCellNum(); a++) {
                                if (StringUtils.contains(row.getCell(a) == null ? "" : row.getCell(a).toString(), target[b])) {
                                    xy.put(protName[b], a);
                                    break;
                                } else {
                                    if (a == row.getLastCellNum() - 1) {
                                        xy.put(protName[b], -1);
                                    }
                                }
                            }
                        }
                    }

                    if (row != null && j != 0) {
                        Object object = null;
                        try {
                            object = clazz.newInstance();
                            for (int f = 0; f < protName.length; f++) {
                                Field field = clazz.getDeclaredField(protName[f]);
                                field.setAccessible(true);
                                //判断是否为日期类型
                                if (xy.get(protName[f]) != -1 && 0 == row.getCell(xy.get(protName[f])).getCellType()) {
                                    Cell cell = row.getCell(xy.get(protName[f]));
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                        //用于转化为日期格式
                                        Date d = cell.getDateCellValue();
                                        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        String date = simpleDateFormat.format(d == null ? new Date() : d);
                                        field.set(object, xy.get(protName[f]) != -1 ? date : "");
                                    }else{
                                        //1为字符串类型，强制转换为字符串
                                        cell.setCellType(1);
                                        String stringCellValue = cell.getStringCellValue();
                                        field.set(object, xy.get(protName[f]) != -1 ? stringCellValue: "");
                                    }
                                }else{
                                    Cell cell = row.getCell(xy.get(protName[f]));
                                    String stringCellValue = cell.getStringCellValue();
                                    field.set(object, xy.get(protName[f]) != -1 ? stringCellValue: "");
                                }

                            }
                        } catch (Exception e) {
                            log.error("excel解析映射PO失败");
                            log.error(e.getMessage(), e);
                        }
                        rowList.add(object);
                    }

                }
                if(!rowList.isEmpty()){
                    sheetList.add(rowList);
                }
                log.info("读取sheet表：" + workbook.getSheetName(i) + " 完成");
            }

            workbook.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        if (sheetList.isEmpty()) {
            return null;
        } else if (sheetList.size() == 1) {
            return sheetList.get(0);
        } else {
            List result = new ArrayList();
            for (List item : sheetList) {
                result.addAll(item);
            }
            return result;
        }
    }
}
