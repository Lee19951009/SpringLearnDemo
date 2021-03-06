package com.chenguangli.spring.mybatis;

import com.chenguangli.spring.db.DbConfig;
import com.chenguangli.spring.db.Spitter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Create by chenguangli on 2019/4/13 23:02
 */
@Configuration
@Import(DbConfig.class)
@MapperScan(basePackageClasses = MybatisConfig.class)
@ComponentScan
@EnableTransactionManagement
public class MybatisConfig {


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/*.xml"));
        //使得 mapper文件中type="com.chenguangli.spring.db.Spitter" 可以直接写成 type="Spitter"
        factoryBean.setTypeAliases(new Class[]{Spitter.class});
        //factoryBean.setConfiguration(configuration());
        return factoryBean.getObject();
    }

    /**
     * 事务
     *
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean
//    public SpitterDao spitterMapper(SqlSessionFactory sqlSessionFactory) throws Exception {
//        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
//        return sqlSessionTemplate.getMapper(SpitterDao.class);
//    }

    /**
     * 和注解@MapperScan效果一样
     *
     * @return
     */
//    @Bean
//    public MapperScannerConfigurer scannerConfigurer() {
//        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
//        configurer.setBasePackage("com.chenguangli.spring.mybatis");
//        return configurer;
//    }

//    @Bean
//    public SpitterDao mapperMapperFactoryBean(SqlSessionFactory sqlSessionFactory) throws Exception {
//        MapperFactoryBean<SpitterDao> mapperFactoryBean = new MapperFactoryBean();
//        mapperFactoryBean.setMapperInterface(SpitterDao.class);
//        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
//        return mapperFactoryBean.getObject();
//    }

    @Bean
    public org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //可以将下划线的字段转换为驼峰
        //configuration.setMapUnderscoreToCamelCase(true);
        //configuration.addMapper(SpitterDao.class);
        configuration.addMappers("com.chenguangli.spring.mybatis");
        return configuration;
    }
}
