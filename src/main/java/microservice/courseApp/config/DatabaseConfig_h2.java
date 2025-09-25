package microservice.courseApp.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "microservice.courseApp",
        entityManagerFactoryRef = "entityManagerFactory_for_h2",
        transactionManagerRef = "transactionManager_for_h2"
)
// ===========
// H2 Database
// ===========
public class DatabaseConfig_h2
{
    @Autowired    private org.springframework.core.env.Environment env;

    // ==== ▶️ DataSource ========

    @Bean(name = "dataSource_for_h2")
    public DataSource dataSource()
    {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("jdbc.driverClassName"))
                .url(env.getProperty("jdbc.url"))
                .username(env.getProperty("jdbc.user"))
                .password(env.getProperty("jdbc.pass"))
                .build();
    }

    // ==== ▶️  SessionFactory / entityManagerFactory ========

    @Bean(name = "entityManagerFactory_for_h2")
    @Primary
    public LocalSessionFactoryBean sessionFactory(@Qualifier("dataSource_for_h2") DataSource dataSource)
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show-sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setPackagesToScan(env.getProperty("packagesToScan"));
            sessionFactory.setHibernateProperties(properties);

        return sessionFactory;
    }


    // ==== ▶️  TransactionManager, TransactionTemplate ========

    @Bean(name = "transactionManager_for_h2")
    public PlatformTransactionManager hibernateTransactionManager(
            @Qualifier("entityManagerFactory_for_h2") SessionFactory sessionFactory)
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
    @Bean(name = "PlatformTransactionManager_for_h2")
    public TransactionTemplate transactionTemplate(
            @Qualifier("transactionManager_for_h2") PlatformTransactionManager transactionManager)
    {
        return new TransactionTemplate(transactionManager);
    }

}

