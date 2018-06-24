package com.sts.pjtry.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sts.pjtry.util.PropUtil;

/**
 * DataSource Configuration
 * @author ()
 *
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages="com.sts.pjtry",
			sqlSessionFactoryRef="sqlSessionFactory")
public class DataSourceConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);
	
	
    @Bean
    @Primary
    //@ConfigurationProperties(prefix="spring.datasource.main")
    public DataSource dataSource() {
    	
    
    	DataSource ds = null;

    	String jndiName = PropUtil.getInstance().getValues("application.properties", "spring.datasource.main.jndi-name");
		
		JndiDataSourceLookup lookup = new JndiDataSourceLookup();
		ds = lookup.getDataSource(jndiName);	
		
		
    	
    	logger.debug("> dataSource " + ds);
        return ds;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
    	logger.debug("> transactionManager");

    	return new DataSourceTransactionManager(dataSource());
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
    	logger.debug("> sqlSessionFactory");

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
        bean.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis.xml"));
        return bean.getObject();
    }


//    @Bean
//    public SqlSessionTemplate sqlSession() throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory());
//    }


}
