package com.vlad.config;

import com.vlad.init.CustomMigration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Created by Fedyunkin_Vladislav on 2/9/2017.
 */
@Configuration
@ComponentScan(basePackages = {"com.vlad"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        })
public class RootConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/taskschema");
        dataSource.setUsername("Vlad");
        dataSource.setPassword("djljktq123");
        return dataSource;
    }

    @Bean(value = "flyway")
    public CustomMigration repairAndMigrate(){
        CustomMigration customMigration = new CustomMigration();
        customMigration.setDataSource(dataSource());
        customMigration.setBaselineOnMigrate(true);
        customMigration.repairAndMigrate();
        return customMigration;
    }

}
