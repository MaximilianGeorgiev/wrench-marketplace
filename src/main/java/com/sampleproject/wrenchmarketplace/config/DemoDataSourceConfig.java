package com.sampleproject.wrenchmarketplace.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/*
 * Main datasource for User database
 */

@Configuration
@EnableJpaRepositories(basePackages={"${spring.data.jpa.repository.packages}"})
public class DemoDataSourceConfig {
	
	/*
	 * Spring Data JPA uses a entityManagerFactory (configured here) and transactionManager (autoconfigured by boot)
	 * entityManagerFactory essentially tells Spring Data JPA where to scan for JPA entities
	 */
	
	@Primary
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource appDataSource() {
		return DataSourceBuilder.create().build(); // prefix reads from applications.properties anything that starts with app.datasource
	}

	@Bean
	@ConfigurationProperties(prefix="spring.data.jpa.entity")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource appDataSource) {
		return builder
				.dataSource(appDataSource)
				.build();
	}

	/*
	 *  By default Spring Security uses regular JDBC (no JPA)
	 * Needs to be declared as a bean, likely unused(?)
	 */
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
}
