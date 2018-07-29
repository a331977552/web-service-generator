package com.service.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StartUp {

	public static final String BASE_PACKAGE = "com.storage";
	public static final String RESULT_CLASS = "StorageResult";

	public static void main(String[] args) throws Exception {

		URL resource = StartUp.class.getClassLoader().getResource("generatorConfig.xml");
		String path = resource.getPath().substring(1);
		System.out.println(path);

		MMyBatisGenerator batisGenerator = new MMyBatisGenerator(path);
		batisGenerator.generate();
		int lastIndexOf = path.lastIndexOf('/');
		String templateDir = path.substring(0, lastIndexOf);
		System.out.println(templateDir);

		ServiceGenerator generator = new ServiceGenerator(BASE_PACKAGE, RESULT_CLASS, templateDir);
		String classPath = generator.getProjectPath();
		String directory = generator.getPackageDirectory(classPath);
		File file = new File(directory);

		File mapperPath = generator.getPackagePath("mapper", file);

		File[] listFiles = mapperPath.listFiles();
		List<String> classNames = new ArrayList<>();

		List<File> mappers = new ArrayList<>();

		for (File name : listFiles) {
			if (name.getName().endsWith("Mapper.java")) {
				classNames.add(name.getName().substring(0, name.getName().lastIndexOf("Mapper")));
				mappers.add(name);
			}
		}
		generator.generate(classNames);

		for (File mapper : mappers) {
			StringBuilder builder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(mapper));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {

				if (line.startsWith("public interface")) {
					builder.append("import org.apache.ibatis.annotations.Mapper;");
					builder.append(System.getProperty("line.separator"));
					builder.append("@Mapper");
					builder.append(System.getProperty("line.separator"));
				}
				builder.append(line);
				builder.append(System.getProperty("line.separator"));

			}
			bufferedReader.close();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mapper));
			bufferedWriter.write(builder.toString());
			bufferedWriter.close();

		}



	}



}