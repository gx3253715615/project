//package com.github.project.test;
//
//import com.github.project.handler.listener.MyInsertListener;
//import com.github.project.handler.listener.MyUpdateListener;
//import com.github.project.model.entity.Base;
//import com.mybatisflex.codegen.Generator;
//import com.mybatisflex.codegen.config.*;
//import com.mybatisflex.codegen.dialect.IDialect;
//import com.mybatisflex.core.BaseMapper;
//import com.mybatisflex.core.service.IService;
//import com.mybatisflex.spring.service.impl.ServiceImpl;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
///**
// * 代码生成器
// *
// * @author gaoxinyu
// * @date 2025/9/2 12:52
// **/
//public class Codegen {
//
//    public static void main(String[] args) {
//        //配置数据源
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/project?useInformationSchema=true&characterEncoding=utf-8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("123456");
//
//        //创建配置内容，两种风格都可以。
//        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
//
//        //通过 datasource 和 globalConfig 创建代码生成器 和数据库方言MySQL
//        Generator generator = new Generator(dataSource, globalConfig, IDialect.MYSQL);
//
//        //生成代码
//        generator.generate();
//    }
//
//    public static GlobalConfig createGlobalConfigUseStyle1() {
//        //创建配置内容
//        GlobalConfig globalConfig = new GlobalConfig();
//
//        //设置注释
//        globalConfig.setAuthor("gaoxinyu" + "\n" + " * " + "@date " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
//        globalConfig.setSince("");
//
//
//        //设置包
//        globalConfig.setBasePackage("com.github.project");
//        globalConfig.setEntityPackage("com.github.project.model.entity");
//        globalConfig.setControllerPackage("com.github.project.controller");
//        globalConfig.setServicePackage("com.github.project.service");
//        globalConfig.setServiceImplPackage("com.github.project.service.impl");
//        globalConfig.setMapperPackage("com.github.project.mapper");
//        globalConfig.setMapperXmlPath("src/main/resources/mapper");
//
//        //设置开启
//        globalConfig.setEntityGenerateEnable(true);
//        globalConfig.setControllerGenerateEnable(true);
//        globalConfig.setServiceGenerateEnable(true);
//        globalConfig.setServiceImplGenerateEnable(true);
//        globalConfig.setMapperGenerateEnable(true);
//        globalConfig.setMapperXmlGenerateEnable(true); // 可以设置为false因为flex联表不需要写sql
//
//        //设置entity
//        globalConfig.setEntitySuperClass(Base.class);
//        globalConfig.setEntityInterfaces(new Class[]{}); // 默认不实现任何接口
//        globalConfig.setEntityWithLombok(true);
//        StrategyConfig strategyConfig = globalConfig.getStrategyConfig();
//        strategyConfig.setIgnoreColumns("create_time", "create_user_id", "update_time", "update_user_id", "deleted");
//        globalConfig.setEntityJdkVersion(17);
//
//        //设置mapper
//        globalConfig.setMapperSuperClass(BaseMapper.class);
//        globalConfig.setMapperAnnotation(false);
//
//        //设置service
//        globalConfig.setServiceSuperClass(IService.class);
//
//        //设置serviceImpl
//        globalConfig.setServiceImplSuperClass(ServiceImpl.class);
//
//        //设置controller TODO
//        globalConfig.setControllerRequestMappingPrefix("/collection");
//
//        //设置表 TODO
//        globalConfig.setGenerateTable("collection");
//        TableConfig tableConfig = new TableConfig();
//        tableConfig.setTableName("collection");
//        tableConfig.setInsertListenerClass(MyInsertListener.class);
//        tableConfig.setUpdateListenerClass(MyUpdateListener.class);
//        globalConfig.setTableConfig(tableConfig);
//
//        ColumnConfig columnConfig = new ColumnConfig();
//        globalConfig.setColumnConfig(columnConfig);
//
//        // 设置覆盖
//        globalConfig.setEntityOverwriteEnable(true);
//        globalConfig.setControllerOverwriteEnable(true);
//        globalConfig.setServiceOverwriteEnable(true);
//        globalConfig.setServiceImplOverwriteEnable(true);
//        globalConfig.setMapperOverwriteEnable(true);
//        globalConfig.setMapperXmlOverwriteEnable(true);
//
//        return globalConfig;
//    }
//}
