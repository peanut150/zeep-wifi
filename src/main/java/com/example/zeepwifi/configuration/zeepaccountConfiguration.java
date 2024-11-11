// package com.example.zeepwifi.configuration;



// import java.util.HashMap;
// import javax.sql.DataSource;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Primary;
// import org.springframework.core.env.Environment;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.jdbc.datasource.DriverManagerDataSource;
// import org.springframework.orm.jpa.JpaTransactionManager;
// import org.springframework.orm.jpa.JpaVendorAdapter;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
// import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.transaction.annotation.EnableTransactionManagement;
// import jakarta.persistence.EntityManagerFactory;

// @Configuration
// @EnableTransactionManagement
// @EnableJpaRepositories(
//     entityManagerFactoryRef = "zeepaccountEntityManagerFactory",
//     basePackages = "com.example.zeepwifi.repositories.ZeepAccountRepository",
//     transactionManagerRef = "zeepaccountTransactionManager"

// )
// public class zeepaccountConfiguration {
    
//     @Autowired
//     private Environment env;

//     @Primary
//     @Bean(name = "zeepaccountDataSource")
//     public DataSource dataSource() {
//         DriverManagerDataSource ds = new DriverManagerDataSource();
//         ds.setUrl(env.getProperty("spring.datasource.url"));
//         ds.setUsername(env.getProperty("spring.datasource.username"));
//         ds.setPassword(env.getProperty("spring.datasource.password"));
//         ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//         return ds;
//     }

//     @Primary
//     @Bean(name = "zeepaccountEntityManagerFactory")
//     public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//         LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
//         bean.setDataSource(dataSource());
//         JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//         bean.setJpaVendorAdapter(adapter);
//         HashMap<String, Object> properties = new HashMap<>();
//         properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
//         bean.setJpaPropertyMap(properties);
//         bean.setPackagesToScan("com.example.zeepwifi.entity.ZeepAccountEntity");
//         return bean;
//     }

//     @Primary
//     @Bean(name = "zeepaccountTransactionManager")
//     public PlatformTransactionManager transactionManager(
//             @Qualifier("zeepaccountEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//         return new JpaTransactionManager(entityManagerFactory);
//     }
// }