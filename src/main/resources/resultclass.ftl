package ${package}.entity.custom;

public class ${className} {
	
	private boolean success;
	private String msg;
	private Object resut;
	
	public static ${className} succeed() {
		StorageResult ${className?uncap_first}=new StorageResult();
		${className?uncap_first}.setMsg("success");
		${className?uncap_first}.setSuccess(true);
		${className?uncap_first}.setResut(null);
		return ${className?uncap_first};
	}
	
	public static ${className} succeed(Object obj) {
		${className} ${className?uncap_first}=new ${className}();
		${className?uncap_first}.setMsg("success");
		${className?uncap_first}.setSuccess(true);
		${className?uncap_first}.setResut(obj);
		return ${className?uncap_first};
	}
	
	
	public static ${className} failed(Object obj) {
		${className} ${className?uncap_first}=new ${className}();
		${className?uncap_first}.setMsg("failed");
		${className?uncap_first}.setSuccess(false);
		${className?uncap_first}.setResut(obj);
		return ${className?uncap_first};
	}
	
	public static ${className} failed(String msg) {
		${className} ${className?uncap_first}=new ${className}();
		${className?uncap_first}.setMsg(msg);
		${className?uncap_first}.setSuccess(false);
		return ${className?uncap_first};
	}
	
	public static StorageResult failed() {
		${className} ${className?uncap_first}=new ${className}();
		${className?uncap_first}.setMsg("failed");
		${className?uncap_first}.setSuccess(false);
		return ${className?uncap_first};
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResut() {
		return resut;
	}
	public void setResut(Object resut) {
		this.resut = resut;
	}
	

}
