package com.bowie.notes.framework.util;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Bowie
 * create at:  2020-07-10
 * @description: mybatis代码生成器
 */
@Data
public class CodeGenerator {
    private String dbUrl;
    private String dbDriverName = "com.mysql.jdbc.Driver";
    private String dbUserName;
    private String dbPwd;
    private String projectPath;
    private String serviceName = "I%sService";
    private String serviceImplName = "%sServiceImpl";
    private String entityName = "%sDO";
    private String moduleName;
    private String packageName;
    private String servicePkgName = "service";
    private String serviceImplPkgName = "impl";
    private String superEntityClass = "";
    private String superServiceClass = "";
    private String moduleDirName = "";
    private boolean onlyDao = false;
    private boolean onlyService = false;
    private boolean onlyWeb = false;

    public CodeGenerator(String dbUrl, String dbUserName, String dbPwd) {
        this.dbUrl = dbUrl;
        this.dbUserName = dbUserName;
        this.dbPwd = dbPwd;
    }

    private GlobalConfig getGlobalConfig() {
        if (StringUtils.isEmpty(this.projectPath)) {
            this.projectPath = System.getProperty("user.dir") + "/" + this.moduleDirName;
        }
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)
                .setAuthor("system")
                .setOutputDir(this.projectPath + "/src/main/java")
                .setFileOverride(true)
                .setIdType(IdType.ID_WORKER_STR)
                .setEnableCache(false)
                .setBaseResultMap(true);
        config.setServiceName(this.serviceName);
        config.setServiceImplName(this.serviceImplName);
        config.setEntityName(this.entityName);
        return config;
    }

    private DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(this.dbUrl);
        dsc.setDriverName(this.dbDriverName);
        dsc.setUsername(this.dbUserName);
        dsc.setPassword(this.dbPwd);
        return dsc;
    }

    private PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        pc.setEntity("com.daidai.stdlsbackend.dao" + "." + moduleName + "." + "entity");
        pc.setService(packageName + "." + moduleName);
        pc.setServiceImpl(packageName + "." + moduleName + "." + this.serviceImplPkgName);
        pc.setMapper("com.daidai.stdlsbackend.dao" + "." + moduleName + "." + "mapper");
        pc.setController(packageName + "." + moduleName );
//        pc.setModuleName(this.moduleName);
        return pc;
    }

    private TemplateConfig getTemplateConfig() {
        TemplateConfig tc = new TemplateConfig();
        if (onlyDao) {
            tc.setService(null);
            tc.setServiceImpl(null);
            tc.setController(null);
            tc.setXml(null);
        }
        if (onlyService) {
            tc.setEntity(null);
            tc.setController(null);
            tc.setXml(null);
            tc.setMapper(null);
        }

        if (onlyWeb) {
            tc.setEntity(null);
            tc.setXml(null);
            tc.setMapper(null);
            tc.setService(null);
            tc.setServiceImpl(null);
        }
        return tc;
    }

    private InjectionConfig getInjectionConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        List<FileOutConfig> focList = new ArrayList<>();
        final StringBuilder xmlPath = new StringBuilder(projectPath + "/src/main/resources/mapper/");
        if (!StringUtils.isEmpty(this.moduleName)) {
            xmlPath.append(this.moduleName + "/");
        }
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return xmlPath.toString() + tableInfo.getMapperName() + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private StrategyConfig getStrategyConfig(String... tableNames) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(false);
        strategy.setEntityLombokModel(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //修改替换成你需要的表名，多个表名传数组
        strategy.setInclude(tableNames);
        if (null != this.getSuperEntityClass()) {
            strategy.setSuperEntityClass(this.getSuperEntityClass());
        }
        if (!StringUtils.isEmpty(this.getSuperServiceClass())) {
            strategy.setSuperServiceImplClass(this.superServiceClass);
        }
        return strategy;
    }

    public void genCode(String... tableNames) {
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(getGlobalConfig());
        mpg.setDataSource(getDataSourceConfig());
        mpg.setPackageInfo(getPackageConfig());
        if (onlyDao) {
            mpg.setCfg(getInjectionConfig());
        }
        mpg.setTemplate(getTemplateConfig());
        mpg.setStrategy(getStrategyConfig(tableNames));
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
