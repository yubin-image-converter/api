package dev.yubin.imageconverter.api;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
@ConfigurationPropertiesScan("dev.yubin.imageconverter")
public class ImageConverterApiApplication {

  @Autowired
  DataSource dataSource;

  public static void main(String[] args) {
    SpringApplication.run(ImageConverterApiApplication.class, args);
  }

  @PostConstruct
  public void logJdbcUrl() throws SQLException {
    System.out.println("🔍 JDBC URL = " + dataSource.getConnection().getMetaData().getURL());
  }
}

