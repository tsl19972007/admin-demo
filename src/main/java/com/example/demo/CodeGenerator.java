package com.example.demo;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {
    /**
     * 代码生成器的配置常量
     */
    private static final String outPutDir = "/src/main/java";
    private static final String dataName = "root";
    private static final String dataPwd = "root";
    private static final String dataUrl = "jdbc:mysql://localhost:3306/admin_demo?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8";
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    //生成以下表对应的代码
    private static final String[] tables = {"sys_user","sys_role","sys_permission","sys_user_role","sys_role_permission"};
    private static final String parentPackage = "com.example.demo";
    private static final String mapperName = "mapper";
    private static final String serviceName = "service";
    private static final String implName = "service.impl";
    private static final String pojoName = "mode.entity";
    private static final String controllerName = "controller";
    // 当前工程路径   配合outPutDir使用，例如多模块开发 Demo/test1，Demo/test2
    // projectPath拿到的是Demo路径，把outPutDir设置成/test1即可
    private static final String projectPath = System.getProperty("user.dir");

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = getGlobalConfig();
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig();
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = getPackageConfig();
        mpg.setPackageInfo(pc);

        InjectionConfig cfg = getInjectionConfig();
        mpg.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = getStrategyConfig();
        mpg.setStrategy(strategy);
        mpg.execute();
    }


    /**
     * 全局配置
     *
     * @return
     */
    public static GlobalConfig getGlobalConfig() {
        return new GlobalConfig()
                .setOutputDir(projectPath + outPutDir)
                .setDateType(DateType.ONLY_DATE)
                .setAuthor("tsl")
                .setOpen(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                // 覆盖生成的文件
                .setFileOverride(false)
                .setServiceName("%sService");
    }

    /**
     * 数据源配置
     *
     * @return
     */
    public static DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig()
                .setUrl(dataUrl)
                .setDriverName(driverName)
                .setUsername(dataName)
                .setPassword(dataPwd);
    }

    /**
     * 包配置
     *
     * @return
     */
    public static PackageConfig getPackageConfig() {
        // 次处注释掉路径配置，会将所有类按默认路径生成在src/main/java下另一个文件夹下；
        // 生成后再按需要覆盖或修改；
        return new PackageConfig()
                //.setParent(parentPackage)
                //.setMapper(mapperName)
                .setEntity(pojoName);
                //.setService(serviceName)
                //.setController(controllerName)
                //.setServiceImpl(implName);
    }

    /**
     * 策略配置
     *
     * @return
     */
    public static StrategyConfig getStrategyConfig() {
        return new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //生成lombok类,可删去生成类的@EqualsAndHashCode和@Accessors注解
                .setEntityTableFieldAnnotationEnable(true)
                .setEntityLombokModel(true)
                .setInclude(tables)
                .setTablePrefix("sys_")
                .setControllerMappingHyphenStyle(true);
    }

    /**
     * 自定义xml文件生成路径
     * 这里注意会生成两个xml，一个是在你指定的下面，一个是在mapper包下的xml
     * 暂时无法解决，因为源码中的判断，判断的是tableInfo和pathInfo的xml属性是否为null，这两个类都是默认生成属性的
     * 且对if (null != injectionConfig)自定义生成的判断在默认的前面，所以会生成两遍。
     * 具体可见AbstractTemplateEngine batchOutput()的方法
     *
     * @return
     */
    public static InjectionConfig getInjectionConfig() {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }
}