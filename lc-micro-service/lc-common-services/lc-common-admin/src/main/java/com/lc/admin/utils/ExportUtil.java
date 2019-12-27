package com.lc.admin.utils;

import cn.hutool.core.util.ReflectUtil;
import com.lc.consts.common.CommonConsts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 导出excel工具类
 *
 * @author Administrator
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExportUtil {


    public static void exportExcelFile(HttpServletResponse response, String fileName, String[] title, String[] columnKeys, List list, List<String[]> lastRows) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        export(response.getOutputStream(), title, columnKeys, list, lastRows);
    }

    /**
     * 导出excel
     *
     * @author Administrator
     * @date 2019/5/19
     */
    public static void export(ServletOutputStream outStream, String[] title, String[] columnKeys, List list, List<String[]> lastRows) {
        // 可重入锁
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            //输出流
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("Sheet1");
            XSSFRow row = null;
            // 准备写数据了
            long beginTime = System.currentTimeMillis();
            log.info("expport 开始写数据--" + beginTime);
            // 写标题信息
            if (title != null && title.length > 0) {
                row = sheet.createRow(0);
                for (int i = 0; i < title.length; i++) {
                    row.createCell(i).setCellValue(title[i]);
                }
            }
            int count = 0;
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    // 写数据
                    // 写行
                    row = sheet.createRow(1 + i);
                    Object object = list.get(i);
                    if (columnKeys != null && columnKeys.length > 0 && object != null) {
                        for (int k = 0; k < columnKeys.length; k++) {
                            if(columnKeys[k].contains(".")){
                                String[] split = columnKeys[k].split("\\.");
                                String className = split[0];
                                String propName = split[1];
                                Object classValue = getClassValue(className, object);
                                if(!Objects.isNull(classValue)){
                                    Object value = getClassValue(propName, classValue);
                                    row.createCell(k).setCellValue(value == null ? "" : value + "");
                                }
                            }else{
                                //获取 get 方法
                                Object value = getClassValue(columnKeys[k], object);
                                row.createCell(k).setCellValue(value == null ? "" : value + "");
                            }
                        }
                    }
                    count++;
                }
            }
            //向excel中插入自定义数据
            if (lastRows != null && !lastRows.isEmpty()) {
                for (int i = 0; i < lastRows.size(); i++) {
                    String[] lastRow = lastRows.get(i);
                    row = sheet.createRow(list == null ? 0 : list.size() + i + 1);
                    for (int j = 0; j < lastRow.length; j++) {
                        row.createCell(j).setCellValue(lastRow[j]);
                    }
                }

            }

            long endTime = System.currentTimeMillis();
            log.info("expExcel 写完成--耗时：" + (endTime - beginTime) + "毫秒,行数count=" + count);

            wb.write(outStream);
        } catch (Exception e) {
            log.error("导出excel异常:", e);
        } finally {
            lock.unlock();
            try {
                outStream.flush();
                outStream.close();
            } catch (IOException e) {
                log.error("导出excel,关闭连接异常:", e);
            }
        }
    }


    public static void exportTxtFile(HttpServletResponse response, String fileName, String[] columnKeys, String separator, List list) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setContentType("text/plain");
        // 下载文件的默认名称
        fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        exportTxt(response.getOutputStream(), separator, columnKeys, list);
    }

    public static void exportTxt(ServletOutputStream outStream, String separator, String[] columnKeys, List list) {

        BufferedOutputStream buffer = null;

        try {
            buffer = new BufferedOutputStream(outStream);
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    // 写行
                    Object object = list.get(i);
                    if (columnKeys != null && columnKeys.length > 0 && object != null) {
                        for (int k = 0; k < columnKeys.length; k++) {
                            // 获取 get 方法
                            Object value = getClassValue(columnKeys[k], object);
                            buffer.write((value + "").getBytes("UTF-8"));
                            if (k != columnKeys.length - 1) {
                                buffer.write(separator.getBytes("UTF-8"));
                            } else {
                                String newLine = System.getProperty("line.separator");
                                buffer.write(newLine.getBytes());
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            log.error("导出txt异常:", e);
        } finally {
            try {
                buffer.flush();
                buffer.close();
                outStream.close();
            } catch (IOException e) {
                log.error("导出txt,关闭连接异常:", e);
            }
        }
    }

    /**
     * 功能描述:
     *
     * @author liucheng
     * @param propName 属性名称
     * @param object 对象
     * @return Object
     * @date 2019/8/5
     */
    public static Object getClassValue(String propName, Object object) throws InvocationTargetException, IllegalAccessException {
        Method getMethod = ReflectUtil.getMethod(object.getClass(), true, "get" +propName);
        if(getMethod.getReturnType().getName().equals("java.time.LocalDateTime")){
            Object invoke = getMethod.invoke(object, new Object[]{});
            if(invoke != null){
                return ((LocalDateTime)invoke).format(CommonConsts.DATE_TIME_FORMATTER);
            }else{
                return null;
            }
        }
        return getMethod.invoke(object, new Object[]{});
    }
}
