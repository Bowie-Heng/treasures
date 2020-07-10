package com.bowie.notes.framework.util;

/**
 * @author : Bowie
 * create at:  2020-07-10
 * 一次性生成controller service mapper xml等内容
 * @description: 代码生成器
 */
public class GenCodeUtils {
    public static void main(String[] args) throws InterruptedException {
        String dbUrl = "jdbc:mysql://localhost:3306/stdls";
        String dbUserName = "stdls";
        String dbPwd = "123456";
        // 包名
        String daoPackageName = "com.daidai.stdlsbackend.dao";
        String servicePackageName = "com.daidai.stdlsbackend.service";
        String webPackageName = "com.daidai.stdlsbackend.web";
        String daoTargetModuleName = "stdls-backend-dao";
        String serviceTargetModuleName = "stdls-backend-service";
        String webTargetModuleName = "stdls-backend-web";

        // 业务模块名
        String moduleName = "user";
        // 生成代码的module
        String[] tables = new String[]{"user"};

        CodeGenerator service = new CodeGenerator(dbUrl, dbUserName, dbPwd);
        // 包名
        service.setOnlyService(true);
        service.setPackageName(servicePackageName);
        service.setModuleName(moduleName);
        service.setModuleDirName(serviceTargetModuleName);
        service.genCode(tables);


        CodeGenerator dao = new CodeGenerator(dbUrl, dbUserName, dbPwd);
        // 包名
        dao.setOnlyDao(true);
        dao.setPackageName(daoPackageName);
        dao.setModuleName(moduleName);
        dao.setModuleDirName(daoTargetModuleName);
        dao.genCode(tables);

        CodeGenerator web = new CodeGenerator(dbUrl, dbUserName, dbPwd);
        // 包名
        web.setOnlyWeb(true);
        web.setPackageName(webPackageName);
        web.setModuleName(moduleName);
        web.setModuleDirName(webTargetModuleName);
        web.genCode(tables);
    }
}

