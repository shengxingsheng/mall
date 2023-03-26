package org.mall.generator.properties;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.google.common.base.CaseFormat;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.mall.generator.mapper.GeneratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sxs
 * @since 2023/1/10
 */
@Component
@ConfigurationProperties(prefix = "my.generator",ignoreInvalidFields = true)
@Data
public class GeneratorUtil {
    private String basePath;
    private String db;
    private String url;
    private String username;
    private String password;
    private String moduleName;
    private String moduleParent;
    private String entityPath ;
    private String tablePrefix ;
    private String packageParent ;
    private String author ;

    @Autowired
    private GeneratorMapper generatorMapper;


    /**
     * 生成sql
     * @throws IOException
     */
    public void generateSql() throws IOException {
        List<String> tables = generatorMapper.listTableNames(db);
        if (StringUtils.isBlank(db) || CollectionUtils.isEmpty(tables) || StringUtils.isBlank(basePath)) {
            throw new RuntimeException("参数不能为null");
        }
        StringBuilder delSqls = new StringBuilder();
        StringBuilder addSqls = new StringBuilder();
        for (String name : tables) {
            delSqls.append("/*==============================================================*/\n" +
                    "/* Table: "+name+"                                             */\n" +
                    "/*==============================================================*/\n");
            String delSql =
                    "alter table "+name+" DROP is_deleted;\n" +
                            "alter table "+name+" DROP create_by;\n" +
                            "alter table "+name+" DROP create_time;\n" +
                            "alter table "+name+" DROP update_by;\n" +
                            "alter table "+name+" DROP update_time;\n\n";
            delSqls.append(delSql);
            addSqls.append("/*==============================================================*/\n" +
                    "/* Table: "+name+"                                             */\n" +
                    "/*==============================================================*/\n");
            String addSql = "ALTER TABLE "+name+" ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';\n" +
                    "ALTER TABLE "+name+" ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';\n" +
                    "ALTER TABLE "+name+" ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';\n" +
                    "ALTER TABLE "+name+" ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';\n" +
                    "ALTER TABLE "+name+" ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';\n\n";
            addSqls.append(addSql);
        }
        FileWriter fileWriter = new FileWriter(basePath+"db\\modify\\"+db+"_del.sql");
        fileWriter.write(delSqls.toString());
        fileWriter.flush();
        fileWriter.close();
        FileWriter fileWriter1 = new FileWriter(basePath+"db\\modify\\"+db+"_add.sql");
        fileWriter1.write(addSqls.toString());
        fileWriter1.flush();
        fileWriter1.close();
        System.out.println(basePath+"db\\modify\\"+db+"_del.sql");
        System.out.println(basePath+"db\\modify\\"+db+"_add.sql");
        System.out.println("sql生成完毕");
    }

    public void generateCode() {
        System.out.println("---代码生成开始:"+moduleName+"---");
        List<String> tables = generatorMapper.listTableNames(db);
        String outputDir = basePath+moduleName+"\\src\\main\\java";
        Map<OutputFile, String> pathInfo = new HashMap<>(2);
        pathInfo.put(OutputFile.entity, basePath+entityPath+"\\src\\main\\java\\"+packageParent.replace(".","/")+"\\entity");
        pathInfo.put(OutputFile.xml, basePath+moduleName+"\\src\\main\\resources\\mapper");
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .disableOpenDir()
                            .outputDir(outputDir)
//                            .enableSwagger()
                            .enableSpringdoc()
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd");
                })
                .packageConfig(builder -> {
                    builder.parent(packageParent) // 设置父包名
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .controller("controller")
                            .pathInfo(pathInfo);// 设置mapperXml生成路径;
                })
                .strategyConfig(builder -> {
                    builder
//                            .likeTable(new LikeTable("ums_")) // 设置需要生成的表名 模糊匹配
                            .addInclude(tables)
                            .addTablePrefix(tablePrefix) // 设置过滤表前缀

                            .entityBuilder() //实体类配置
                            .enableFileOverride()
//                            .superClass(BaseEntity.class)
//                            .enableActiveRecord()
//                            .enableChainModel()
                            .enableLombok()
                            .enableRemoveIsPrefix()
//                            .enableTableFieldAnnotation()
                            .logicDeleteColumnName("is_deleted")
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
//                            .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                            .idType(IdType.AUTO)
                            .formatFileName("%s")

                            .controllerBuilder() //控制层配置
                            .enableFileOverride()
                            .enableHyphenStyle()
                            .enableRestStyle()
                            .enableFileOverride()
                            .formatFileName("%sController")

                            .serviceBuilder()//service
                            .enableFileOverride()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")

                            .mapperBuilder()
                            .enableFileOverride()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .mapperAnnotation(Mapper.class)
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper")
                    ;
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
        System.out.println("---代码生成完毕:"+moduleName+" "+tables.size()+"---");
    }

    /**
     * 修改entity
     *  1.type = IdType.AUTO
     */
    public void modifyEntity() throws IOException {
        System.out.println("---修改实体类开始---");
        List<String> tableList = generatorMapper.listTableNames(db);
        for (String table : tableList) {
            String entity = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table).substring(3);
            String path = basePath+entityPath+"\\src\\main\\java\\" + packageParent.replace(".","\\") + "\\entity\\";
            removeIdType(entity, path);
            System.out.println(path + entity + ".java");
        }
        System.out.println("---修改实体类完毕---");
    }

    /**
     *
     * @param entity
     * @param path
     * @throws IOException
     */
    private void removeIdType(String entity, String path) throws IOException {
        FileReader fileReader = new FileReader(path + entity + ".java");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine())!=null) {
            if (line.contains("@TableId(value")) {
                line = line.replace(", type = IdType.AUTO", "");
            }
            if (line.contains("@Setter")){
                line = line + "\n@ToString";
            }
            if (line.contains("@TableName")){
                line = line + "\n@JsonIgnoreProperties({\"deleted\", \"createBy\", \"createTime\", \"updateBy\", \"updateTime\"})";
            }
//            if (line.contains("com.baomidou.mybatisplus.annotation.TableName")){
//                line = line + "\nimport com.fasterxml.jackson.databind.annotation.JsonSerialize;\n" +
//                        "import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;";
//            }
            if (line.contains("lombok.Setter")){
                line =line+"\nimport lombok.ToString;";
            }
            if (line.contains("java.io.Serializable")){
                line =  "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n"+line;
            }
            if (!line.contains("com.baomidou.mybatisplus.annotation.IdType")) {
                stringBuilder.append(line+"\n");
            }
        }
        bufferedReader.close();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path + entity + ".java"));
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
