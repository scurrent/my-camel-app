package com.example.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyContainer {

	@Value("${service.env}")
	private String env;

	@Value("${service.interfaceId}")
	private String interfaceId;

	@Value("${service.sourceSystem}")
	private String sourceSystem;

	@Value("${service.kafkaUrl}")
	private String kafkaUrl;

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getKafkaUrl() {
		return kafkaUrl;
	}

	public void setKafkaUrl(String kafkaUrl) {
		this.kafkaUrl = kafkaUrl;
	}

}
