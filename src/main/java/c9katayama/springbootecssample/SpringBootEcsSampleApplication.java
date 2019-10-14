package c9katayama.springbootecssample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import c9katayama.springbootecssample.CommonConfig.DBConfig;

@SpringBootApplication
@Import(CommonConfig.class)
public class SpringBootEcsSampleApplication {

	private Log log = LogFactory.getLog(SpringApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEcsSampleApplication.class, args);
	}

	@RestController
	public class IndexController {

		@Autowired
		private DBConfig dbConfig;

		@GetMapping
		public String index() {
			return "OK8";
		}

		@GetMapping(path = "/hello")
		public String hello() {
			return "hello2";
		}

		@GetMapping(path = "/db")
		public String db() {
			try {
				Connection connection = DriverManager.getConnection(dbConfig.dbUrl, "admin", dbConfig.dbPassword);
				ResultSet executeQuery = connection.createStatement().executeQuery("SHOW DATABASES;");
				if (executeQuery.next()) {
					return executeQuery.getString(0);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return e.getMessage();
			}

			return "hello2";
		}
	}
}
