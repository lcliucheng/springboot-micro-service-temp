/**
 *
 */

package com.lc.generate.utils;

import com.lc.generate.entity.ColumnEntity;
import com.lc.generate.entity.TableEntity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * 代码生成器   工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
public class GenUtils {

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String> ();
        templates.add ("template/lc/biz/Entity.java.vm");
        templates.add ("template/lc/biz/Service.java.vm");
        templates.add ("template/lc/biz/ServiceImpl.java.vm");
        templates.add ("template/lc/biz/Controller.java.vm");
        templates.add ("template/lc/biz/Mapper.java.vm");

        templates.add ("template/lc/admin/list.vue.vm");
        templates.add ("template/lc/admin/edit.vue.vm");
        templates.add ("template/lc/admin/Controller.java.ad.vm");
        templates.add ("template/lc/admin/DTO.java.ad.vm");
        templates.add ("template/lc/admin/Request.java.ad.vm");
        templates.add ("template/lc/admin/Service.java.ad.vm");
        templates.add ("template/lc/admin/ServiceFallbackImpl.java.ad.vm");
        templates.add ("template/lc/admin/menu.sql.ad.vm");


        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {
        //配置信息
        Configuration config = getConfig ();
        boolean hasBigDecimal = false;
        boolean hasDateTime=false;
        boolean hasDate=false;
        //表信息
        TableEntity tableEntity = new TableEntity ();
        tableEntity.setTableName (table.get ("tableName"));
        tableEntity.setComments (table.get ("tableComment"));
        //表名转换成Java类名
        String className = tableToJava (tableEntity.getTableName (), config.getString ("tablePrefix"));
        tableEntity.setClassName (className);
        tableEntity.setClassname (StringUtils.uncapitalize (className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<> ();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity ();
            columnEntity.setColumnName (column.get ("columnName"));
            columnEntity.setDataType (column.get ("dataType"));
            columnEntity.setComments (column.get ("columnComment"));
            columnEntity.setExtra (column.get ("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava (columnEntity.getColumnName ());
            columnEntity.setAttrName (attrName);
            columnEntity.setAttrname (StringUtils.uncapitalize (attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString (columnEntity.getDataType (), "unknowType");
            columnEntity.setAttrType (attrType);
            if (!hasBigDecimal && attrType.equals ("BigDecimal")) {
                hasBigDecimal = true;
            }
            if(!hasDateTime&&attrType.equals ("LocalDateTime")){
                hasDateTime=true;
            }
            if(!hasDate&&attrType.equals ("LocalDate")){
                hasDate=true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase (column.get ("columnKey")) && tableEntity.getPk () == null) {
                tableEntity.setPk (columnEntity);
            }

            columsList.add (columnEntity);
        }
        tableEntity.setColumns (columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk () == null) {
            tableEntity.setPk (tableEntity.getColumns ().get (0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties ();
        prop.put ("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init (prop);

        String mainPath = config.getString ("mainPath");
        mainPath = StringUtils.isBlank (mainPath) ? "io.renren" : mainPath;

        //封装模板数据
        Map<String, Object> map = new HashMap<> ();
        map.put ("tableName", tableEntity.getTableName ());
        map.put ("comments", tableEntity.getComments ());
        map.put ("pk", tableEntity.getPk ());
        map.put ("className", tableEntity.getClassName ());
        map.put ("classname", tableEntity.getClassname ());
        map.put ("pathName", tableEntity.getClassname ().toLowerCase ());
        map.put ("columns", tableEntity.getColumns ());
        map.put ("hasBigDecimal", hasBigDecimal);
        map.put ("hasDateTime", hasDateTime);
        map.put ("hasDate", hasDate);
        map.put ("mainPath", mainPath);
        map.put ("package", config.getString ("package"));
        map.put ("moduleName", config.getString ("moduleName"));
        map.put ("author", config.getString ("author"));
        map.put ("email", config.getString ("email"));
        map.put ("packagePath", config.getString ("mainPath"));
        map.put ("packagePath2", config.getString ("mainPath2"));
        map.put ("packagePath3", config.getString ("mainPath2").toUpperCase ());
        map.put ("datetime", DateUtils.format (new Date (), DateUtils.DATE_TIME_PATTERN));
        map.put ("vueSymbolOne",config.getString ("vueSymbolOne"));
        VelocityContext context = new VelocityContext (map);

        //获取模板列表
        List<String> templates = getTemplates ();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter ();
            Template tpl = Velocity.getTemplate (template, "UTF-8");
            tpl.merge (context, sw);

            try {
                //添加到zip
                zip.putNextEntry (new ZipEntry (getFileName (template, tableEntity.getClassName (), config.getString ("package"), config.getString ("mainPath2"), config.getString ("moduleName"))));
                IOUtils.write (sw.toString (), zip, "UTF-8");
                IOUtils.closeQuietly (sw);
                zip.closeEntry ();
            } catch (IOException e) {
                throw new RRException ("渲染模板失败，表名：" + tableEntity.getTableName (), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully (columnName, new char[]{'_'}).replace ("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank (tablePrefix)) {
            tableName = tableName.replace (tablePrefix, "");
        }
        return columnToJava (tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration ("generator.properties");
        } catch (ConfigurationException e) {
            throw new RRException ("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String mainPath2, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank (packageName)) {
            packagePath += packageName.replace (".", File.separator) + File.separator;

        }

        if (template.contains ("Entity.java.vm")) {
            return "biz"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "biz" + File.separator + "domain" +File.separator+"entity"+ File.separator + "db" + File.separator + moduleName + File.separator +  className + ".java";
            //com.zzjr.service.tourism.biz.domain.entity.db.ld.Activity
        }

        if (template.contains ("Mapper.java.vm")) {
            return "biz"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "biz" + File.separator + "repository" + File.separator + "db" + File.separator + moduleName + File.separator +  className + "Mapper.java";
            //com.zzjr.service.tourism.biz.repository.db.ld.DictionariesMapper
        }

        if (template.contains ("Service.java.vm")) {
            return "biz"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "biz" + File.separator + "service" + File.separator + "db" + File.separator + moduleName + File.separator + className + "Service.java";
            //com.zzjr.service.tourism.biz.service.db.ld
        }
        if (template.contains ("Service.java.ad.vm")) {
            return "admin"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "admin" + File.separator + "service" + File.separator + "feign" + File.separator + moduleName + File.separator + className + "Service.java";
            //com.zzjr.service.tourism.admin.service.feign
        }

        if (template.contains ("ServiceImpl.java.vm")) {
            return "biz"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "biz" + File.separator + "service" + File.separator + "db" + File.separator + moduleName + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
            //com.zzjr.service.tourism.biz.service.db.ld.impl
        }

        if (template.contains ("Controller.java.vm")) {
            return "biz"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "biz" + File.separator + "web" + File.separator + moduleName + File.separator + className + "Controller.java";
            //com.zzjr.service.tourism.biz.web.ld
        }

        if (template.contains ("ServiceFallbackImpl.java.ad.vm")) {
            return "admin"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "admin" + File.separator + "service" + File.separator + "feign" + File.separator +  "fallback" + File.separator + moduleName + File.separator +className + "ServiceFallbackImpl.java";
            //com.zzjr.service.tourism.admin.service.feign.fallback
        }


        if (template.contains ("Controller.java.ad.vm")) {

            return "admin"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "admin" + File.separator + "web" + File.separator + moduleName + File.separator + className + "Controller.java";
            //com.zzjr.service.tourism.admin.web.ld
        }
        if (template.contains ("DTO.java.ad.vm")) {
            return "admin"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "admin" + File.separator + "domain" +File.separator+"dto"+  File.separator + moduleName + File.separator +  className + "DTO.java";
            //com.zzjr.service.tourism.admin.domain.dto.ld.LdChannelDTO
        }
        if (template.contains ("Request.java.ad.vm")) {
            return "admin"+File.separator+packagePath + "service" + File.separator + mainPath2 + File.separator + "admin" + File.separator + "domain" +File.separator+"request"+  File.separator + moduleName + File.separator +  className + "Request.java";
            //com.zzjr.service.tourism.admin.domain.request.ld.LdActivityRequest
        }



        if (template.contains("list.vue.vm" )) {
            return "vue" + File.separator + moduleName + File.separator + className.toLowerCase()+File.separator + "list.vue";
        }
        if (template.contains("edit.vue.vm" )) {
            return "vue" + File.separator + moduleName + File.separator + className.toLowerCase() +File.separator+ "edit.vue";
        }

		if (template.contains("menu.sql.ad.vm" )) {
			return className.toLowerCase() + "_menu.sql";
		}

        return null;
    }
}
