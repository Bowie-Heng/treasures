package com.bowie.notes.framework;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//排除DataSourceConfiguratrion
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement(proxyTargetClass = true)   //开启事物管理功能
public class FrameworkApplication {

	public static void main(String[] args) {
		// 设置环境变量，解决Es的netty与Netty服务本身不兼容问题
		System.setProperty("es.set.netty.runtime.available.processors","false");
		SpringApplication.run(FrameworkApplication.class, args);
	}

}
