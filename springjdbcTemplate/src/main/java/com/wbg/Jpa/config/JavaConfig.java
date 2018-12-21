package com.wbg.Jpa.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan("com.wbg.Jpa")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.wbg.Jpa.dao"})
public class JavaConfig  {

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("org.mariadb.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl("jdbc:mariadb://localhost:3306/wbg_logistics");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setMaxPoolSize(30);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDataSource());
        return jdbcTemplate;
    }
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("com.wbg.Jpa.entity");
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties = new Properties();
        /**
         * validate 加载hibernate时，验证创建数据库表结构 
         * create 每次加载hibernate，重新创建数据库表结构，这就是导致数据库表数据丢失的原因。。
         * create-drop        加载hibernate时创建，退出是删除表结构 
         * update             加载hibernate自动更新数据库结构
         */
        properties.setProperty("hibernate.hbm2ddl.auto","update");
        //格式化输出语句
        /**列如
         * Hibernate: select role0_.id as id1_0_, role0_.note as note2_0_, role0_.role_name as role_nam3_0_ from Role role0_
         *格式化韦
         * select
         *         role0_.id as id1_0_,
         *         role0_.note as note2_0_,
         *         role0_.role_name as role_nam3_0_
         *     from
         *         Role role0_
         */
        properties.setProperty("hibernate.format_sql","true");
        //显示执行sql语句
        properties.setProperty("hibernate.show_sql","true");

        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        bean.setJpaProperties(properties);

        return bean;
    }

}
