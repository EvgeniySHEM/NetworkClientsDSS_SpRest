package ru.sanctio;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import ru.sanctio.dao.DBManagerAddress;
import ru.sanctio.dao.DBManagerClient;
import ru.sanctio.dao.impl.DBManagerAddressImpl;
import ru.sanctio.dao.impl.DBManagerClientImpl;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class TestConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:14-alpine")
                .withInitScript("create.sql")
                .waitingFor(Wait.forListeningPort());// ожидание доступности порта (2)
    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
        hikariConfig.setUsername(postgreSQLContainer.getUsername());
        hikariConfig.setPassword(postgreSQLContainer.getPassword());

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource(postgreSQLContainer()));
        sessionFactory.setPackagesToScan("ru.sanctio.models.entity");

        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect",
                "org.hibernate.dialect.PostgresPlusDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
        return hibernateTransactionManager;
    }

    @Bean
    public DBManagerClient dbManagerClient() {
        return new DBManagerClientImpl(sessionFactory().getObject());
    }

    @Bean
    public DBManagerAddress dbManagerAddress() {
        return new DBManagerAddressImpl(sessionFactory().getObject());
    }
}

