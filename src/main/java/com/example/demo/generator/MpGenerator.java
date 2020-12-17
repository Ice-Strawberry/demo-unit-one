package com.example.demo.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class MpGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("wyy");
        gc.setOutputDir("D:/0-hmp/demo/hlw-com/main/java");
        gc.setFileOverride(true);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
         gc.setMapperName("%sDao");
         gc.setXmlName("%sDao");
         gc.setServiceName("%sService");
         gc.setServiceImplName("%sServiceImpl");
         gc.setControllerName("%sController");
         gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        //驱动名称
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/a_yy_t?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 包配置
        PackageConfig pc = new PackageConfig();

        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(new String[]{"user_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.no_change);// 表名生成策略
//        strategy.setInclude(new String[]{"V_QUERY_STAFF_INFO"}); // 需要生成的表
        strategy.setInclude(new String[]{
                "user"
        }); // 需要生成的表
//        pc.setParent("com.cloud.hlw.reg");
        pc.setParent("com.example.demo");
//        pc.setParent("com.cloud.otr");
        //生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        //自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time",FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time",FieldFill.INSERT_UPDATE));
//        tableFillList.add(new TableFill("modified_time",FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("is_delete",FieldFill.INSERT));
        strategy.setTableFillList(tableFillList);
        // 类名：Tb_userController -> TbUserController
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 属性名：start_time -> startTime
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 代替 setter/getter等方法
        strategy.setEntityLombokModel(true);
        // 设置Controller为RestController
        strategy.setRestControllerStyle(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
//         strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);


        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setXml("dao.mapper");
//        pc.setXml("dao.xml");
//        pc.setModuleName("test");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//            }
//        };
//
//        // 自定义 xxList.jsp 生成
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/template/list.jsp.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return "D://my_" + tableInfo.getEntityName() + ".jsp";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 调整 mapper 生成目录演示
//        focList.add(new FileOutConfig("/templates/dao.mapper.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return "/develop/code/mapper/" + tableInfo.getEntityName() + ".mapper";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 关闭默认 mapper 生成，调整生成 至 根目录
//        TemplateConfig tc = new TemplateConfig();
//        tc.setXml(null);
//        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
//        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
