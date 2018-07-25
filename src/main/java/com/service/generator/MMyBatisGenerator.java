package com.service.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MMyBatisGenerator {

	private String generatorConfigPath;

	public MMyBatisGenerator(String generatorConfigPath) {
		this.generatorConfigPath = generatorConfigPath;




	}

	public void generate(){
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(this.generatorConfigPath/*"./src/generatorConfig.xml"*/);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config;

		try {
			config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator;
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}


}
