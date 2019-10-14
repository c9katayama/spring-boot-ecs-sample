package c9katayama.springbootecssample;

import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClient;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathResult;

@Configuration
public class CommonConfig {

	public static final Log log = LogFactory.getLog(CommonConfig.class);

	public static class DBConfig {
		public String dbUrl;
		public String dbPassword;
	}

	@Bean
	public DBConfig dbConfig() {
		Map<String, String> paramMap = loadParamsFromSSM();
		DBConfig config = new DBConfig();
		config.dbUrl = paramMap.get("dbUrl");
		config.dbPassword = paramMap.get("dbPassword");
		return config;
	}

	private Map<String, String> loadParamsFromSSM() {
		AWSSimpleSystemsManagement ssmClient = AWSSimpleSystemsManagementClient.builder()
				.withRegion(Regions.AP_NORTHEAST_1).build();
		String ssmPathPrefix = "/spring-boot-ecs-sample/";
		GetParametersByPathRequest reqeust = new GetParametersByPathRequest().withPath(ssmPathPrefix)
				.withWithDecryption(true);
		GetParametersByPathResult parametersByPath = ssmClient.getParametersByPath(reqeust);
		Map<String, String> source = parametersByPath.getParameters().stream()
				.collect(Collectors.toMap(r -> r.getName().replace(ssmPathPrefix, ""), r -> r.getValue().toString()));
		log.info("load parama names[" + source.keySet().stream().collect(Collectors.joining(",")));
		return source;
	}

}
