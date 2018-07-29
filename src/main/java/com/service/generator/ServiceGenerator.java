
package com.service.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ServiceGenerator {

	private String base_package;
	private String resultClass;
	private String templateDirectory;

	public ServiceGenerator(String base_package, String resultClass, String templateDirectory) {
		this.base_package = base_package;
		this.resultClass = resultClass;
		this.templateDirectory = templateDirectory;

	}

	public void generate(List<String> classNames) {
		String path = this.getProjectPath();
		this.generate(classNames, path);

	}

	public String getProjectPath() {
		File file = new File("");
		try {
			String canonicalPath = file.getCanonicalPath();
			String path = canonicalPath + "//src//main//java";
			return path;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getProjectTestPath() {
		File file = new File("");
		try {
			String canonicalPath = file.getCanonicalPath();
			String path = canonicalPath + "//src//test//java";
			return path;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void generate(List<String>  classNames,String baseDirectory) {



		//1、创建一个模板文件
		//2、创建一个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//3、设置模板文件保存的目录
		try {
			configuration.setDirectoryForTemplateLoading(new File(this.templateDirectory));

			//4、模板文件的编码格式，一般就是utf-8
			configuration.setDefaultEncoding("utf-8");
			//5、加载一个模板文件，创建一个模板对象。
			//				Template template = configuration.getTemplate("hello.ftl");
			Template serviceTemplate = configuration.getTemplate("service.ftl");
			Template serviceimpTemplate = configuration.getTemplate("serviceimp.ftl");
			Template controllerTemplate = configuration.getTemplate("controller.ftl");
			Template testTemplate = configuration.getTemplate("test.ftl");
			Template applicationTemplate = configuration.getTemplate("application.ftl");


			Template resultclassTemplate = configuration.getTemplate("resultclass.ftl");
			Template intercepterTemplate = configuration.getTemplate("intercepter.ftl");
			Template formTemplate = configuration.getTemplate("html_form.ftl");

			Map data = new HashMap<>();
			data.put("package", this.base_package);
			data.put("result", this.resultClass);
			data.put("title", "storage");
			String directory = this.getPackageDirectory(baseDirectory);


			for (String className:classNames) {
				data.put("className", className);
				File serviceFile = new File(directory+"service//"+className+"Service.java");
				boolean success = createParentDirectoryAndDeleteFile(serviceFile);

				File serviceImpFile = new File(directory+"service//imp//"+className+"ServiceImp.java");
				boolean success2 = createParentDirectoryAndDeleteFile(serviceImpFile);

				File controllerFile = new File(directory+"controller//"+className+"Controller.java");
				boolean success3 = createParentDirectoryAndDeleteFile(controllerFile);

				String testDirectory = this.getPackageDirectory(this.getProjectTestPath());

				File testImpFile = new File(testDirectory+"//test//"+className+"Test.java");
				boolean success4 = createParentDirectoryAndDeleteFile(testImpFile);

				if(!success || !success2||!success3||!success4 ) {
					return ;
				}
				generate(serviceTemplate,data,serviceFile);


				List<ReflectEntity> fields = this.getField(className);


				data.put("fields", fields);
				generate(serviceimpTemplate,data,serviceImpFile);

				File file=new File("");
				String canonicalPath = file.getCanonicalPath();
				String path=canonicalPath+"//src//main//resources//static//";

				String lowerCaseClassName=Character.toString(Character.toLowerCase(className.charAt(0)))+className.substring(1, className.length());

				File formFile = new File(path+lowerCaseClassName+".html");
				createParentDirectoryAndDeleteFile(formFile);

				generate(formTemplate,data,formFile);

				data.remove("fields");
				generate(controllerTemplate,data,controllerFile);
				generate(testTemplate,data,testImpFile);


			}

			File intercepterFile = new File(directory+"intercepter//"+"Login"+"Intercepter.java");
			boolean success5 = createParentDirectoryAndDeleteFile(intercepterFile);
			generate(intercepterTemplate,data,intercepterFile);
			data.put("className", this.resultClass);
			File resultClass = new File(directory+"entity//custom//"+this.resultClass+".java");

			boolean success6 = createParentDirectoryAndDeleteFile(resultClass);
			generate(resultclassTemplate,data,resultClass);

			File applicationFile = new File(directory+"config//Application.java");
			boolean success7 = createParentDirectoryAndDeleteFile(applicationFile);
			generate(applicationTemplate,data,applicationFile);
			File utilsFile = new File(directory+"utils");
			boolean success9 = createParentDirectoryAndDeleteFile(utilsFile);




		}catch(Exception e) {

			e.printStackTrace();
		}
	}

	public List<ReflectEntity> getField(String className) {

		File file = new File(this.getPackageDirectory(this.getProjectPath()));

		File packagePath = this.getPackagePath("entity", file);
		File[] filesUnderPackage = packagePath.listFiles();
		for (File javaClass : filesUnderPackage) {
			if (!javaClass.getName().endsWith("Example.java") && javaClass.isFile()
					&& javaClass.getName().contains(className)) {
				String absolutePath = javaClass.getAbsolutePath();

				String substring = absolutePath.substring(absolutePath.indexOf("java") + 5);
				String fullClassName = substring.replace('\\', '.').replaceAll(".java", "");

				Class<?> clazz;
				try {

					JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();
					StandardJavaFileManager standardFileManager = systemJavaCompiler.getStandardFileManager(null, null,
							null);
					Iterable<? extends JavaFileObject> javaFileObjects = standardFileManager
							.getJavaFileObjects(javaClass.getAbsolutePath());
					List<String> optionsList = new ArrayList<String>();
					optionsList.add("-Xlint:unchecked");
					// 设置classpath
					optionsList.add("-d");
					String projectPath = new File("").getCanonicalPath();
					optionsList.add(projectPath + "//target//classes");
					CompilationTask run = systemJavaCompiler.getTask(null, standardFileManager, null, optionsList, null,
							javaFileObjects);
					if (run.call()) {
						clazz = Class.forName(fullClassName);
						Field[] fields = clazz.getDeclaredFields();

						List<ReflectEntity> reflectEntities = new ArrayList<>();
						for (Field field : fields) {
							String fieldName = field.getName();
							Class<?> parameterTypes = field.getDeclaringClass();
							String paramTypeName = parameterTypes.getSimpleName();
							ReflectEntity entity = new ReflectEntity(fieldName, paramTypeName);
							reflectEntities.add(entity);
						}
						return reflectEntities;
					}

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
		return null;

	}

	public String getPackageDirectory(String classDirectory) {
		String[] split = this.base_package.split("\\.");
		StringBuilder builder = new StringBuilder();
		builder.append(classDirectory + "\\");
		for (String string : split) {
			builder.append(string + "\\");
		}

		String directory = builder.toString();
		System.out.println(directory);
		return directory;
	}

	private static void generate(Template template, Map data, File serviceFile) {
		Writer out;
		try {
			System.out.println(serviceFile);
			out = new FileWriter(serviceFile);
			template.process(data, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	private static boolean createParentDirectoryAndDeleteFile(File serviceFile) {
		File parentFile = serviceFile.getParentFile();
		boolean exists = parentFile.exists();
		boolean mkdirs;
		if (!exists) {
			mkdirs = parentFile.mkdirs();
		} else {
			mkdirs = true;
		}

		if (mkdirs) {
			if (serviceFile.exists()) {
				serviceFile.delete();
			}
			return true;

		} else {
			System.out.println("creating directory error");
			return false;
		}

	}

	public File getPackagePath(String name, File file) {

		if (file.isDirectory()) {
			System.out.println(file.getName());
			if (file.getName().contains(name)) {
				return file;
			}
			File[] listFiles = file.listFiles();
			for (File file2 : listFiles) {
				File mapperPath = this.getPackagePath(name, file2);
				if (mapperPath == null) {
					continue;
				} else {
					return mapperPath;
				}
			}
		}

		return null;
	}
}
