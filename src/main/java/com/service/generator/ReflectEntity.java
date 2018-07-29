package com.service.generator;

public class ReflectEntity {

	private String name;
	private String typeName;
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeName() {
		return this.typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public ReflectEntity(String name, String typeName) {
		super();
		this.name = name;
		this.typeName = typeName;
	}
	public ReflectEntity() {
		super();
	}

}
