package io.github.stayfool;

import io.github.stayfool.config.*;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by stay fool on 2017/8/17.
 */
public class PluginTest {

    @Test
    public void test() throws Exception {
        Class<CodeGenMojo> cls = CodeGenMojo.class;
        Field dataBase = cls.getDeclaredField("database");
        dataBase.setAccessible(true);

        Field generate = cls.getDeclaredField("generate");
        generate.setAccessible(true);

        GenerateConfig generateConfig = new GenerateConfig();
        generateConfig.setBaseDir("src/test/java");
        generateConfig.setBasePkg("io.github.stayfool");

        generateConfig.setRepository(new SpringRepositoryConfig());

        EntityConfig entityConfig = new EntityConfig();
        entityConfig.setPkg("po");
        entityConfig.setUseLombok(true);
        entityConfig.setNeedColumnAnnotation(false);
        generateConfig.setEntity(entityConfig);

//        SpringRepositoryConfig repositoryConfig = new SpringRepositoryConfig();
//        repositoryConfig.setPkg("repo");
//
//        generateConfig.setRepository(repositoryConfig);

        CodeGenMojo mojo = new CodeGenMojo();
        dataBase.set(mojo, getDatabaseConfig());
        generate.set(mojo, generateConfig);

        mojo.execute();
    }

    @Test
    public void testWithSuperClass() throws Exception {
        Class<CodeGenMojo> cls = CodeGenMojo.class;
        Field dataBase = cls.getDeclaredField("database");
        dataBase.setAccessible(true);

        Field generate = cls.getDeclaredField("generate");
        generate.setAccessible(true);

        GenerateConfig generateConfig = new GenerateConfig();
        generateConfig.setBaseDir("src/test/java");
        generateConfig.setBasePkg("io.github.stayfool");
        generateConfig.setOverride(true);

        generateConfig.setRepository(new SpringRepositoryConfig());

        EntityConfig entityConfig = new EntityConfig();
        entityConfig.setUseLombok(true);
        entityConfig.setPkg("po");
        entityConfig.setNeedColumnAnnotation(false);
        entityConfig.setIdType("Long");

        entityConfig.setSuperClass("test.BasePO");
        entityConfig.setExcludeFields(Arrays.asList("id", "active", "createdTime", "createdBy", "updatedTime", "updatedBy"));

        generateConfig.setEntity(entityConfig);

        CodeGenMojo mojo = new CodeGenMojo();
        dataBase.set(mojo, getDatabaseConfig());
        generate.set(mojo, generateConfig);

        mojo.execute();
    }

    private DatabaseConfig getDatabaseConfig() {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setType("mysql");
//        databaseConfig.setType("oracle");
//        databaseConfig.setTablePrefix("prefix_");
//        databaseConfig.setIncludes(Arrays.asList("sys_dept_relation"));

        DatasourceConfig datasourceConfig = new DatasourceConfig();
        datasourceConfig.setDriverClass("com.mysql.jdbc.Driver");
        datasourceConfig.setUsername("root");
        datasourceConfig.setPassword("root");
        datasourceConfig.setUrl("jdbc:mysql://localhost:3306/pig?useSSL=false");

        databaseConfig.setDatasource(datasourceConfig);
        return databaseConfig;
    }
}
