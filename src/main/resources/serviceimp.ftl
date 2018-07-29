package ${package}.service.imp;
import java.util.List;
import ${package}.entity.${className};
import ${package}.entity.custom.${result};
import ${package}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import ${package}.mapper.${className}Mapper;
import ${package}.entity.${className}Example;
import ${package}.entity.${className}Example.Criteria;
@Service
public class ${className}ServiceImp implements  ${className}Service{
	@Autowired
	${className}Mapper ${className?uncap_first}Mapper;
	
	public ${result} add${className}(${className} ${className?uncap_first}){
		if(${className?uncap_first}==null) {
			return StorageResult.failed("invalid parameter");
		}
	
		this.${className?uncap_first}Mapper.insert(${className?uncap_first});
		return ${result}.succeed();
	}
	public ${result} update${className}(${className} ${className?uncap_first} ){
		if((${className?uncap_first}==null) ||(${className?uncap_first}.getId()==null) ||(${className?uncap_first}.getId()<0)) {
			return ${result}.failed("invalid parameter ");
		}
	
		this.${className?uncap_first}Mapper.updateByPrimaryKey(${className?uncap_first});
		
		return ${result}.succeed();
	}
	public ${result} delete${className}ById(Integer id){
		if(id==null || id<0){
		  	return ${result}.failed("invalid id");
		}
		this.${className?uncap_first}Mapper.deleteByPrimaryKey(id);
		return ${result}.succeed();
	}
	public ${result} get${className}ByExample(${className}  ${className?uncap_first} ){
		
		
		${className}Example example=new ${className}Example();
		Criteria criteria = example.createCriteria();
		
		<#list fields as field>
			if(${className?uncap_first}.get${field.name?cap_first}()!=null){
				criteria.and${field.name?cap_first}EqualTo(${className?uncap_first}.get${field.name?cap_first}());
			}
		
		</#list>
		

		List<${className}> results = this.${className?uncap_first}Mapper.selectByExample(example);
		
		
		return ${result}.succeed(results);
	}
	public ${result} get${className}ById(Integer id){
		if(id==null || id<0){
		  	return ${result}.failed("invalid id");
		}
		
		${className} ${className?uncap_first} = this.${className?uncap_first}Mapper.selectByPrimaryKey(id);
		return ${result}.succeed(${className?uncap_first});
	}
	public ${result} update${className}Selective(${className} ${className?uncap_first}){
		if((${className?uncap_first}==null) ||(${className?uncap_first}.getId()==null) ||(${className?uncap_first}.getId()<0)) {
			return ${result}.failed("invalid parameter ");
		}
		
		
		this.${className?uncap_first}Mapper.updateByPrimaryKeySelective(${className?uncap_first});
		return  ${result}.succeed();
	
	}
	
	public ${result} count(){
		${className}Example example=new ${className}Example();
		long count = this.${className?uncap_first}Mapper.countByExample(example);
		return  ${result}.succeed(count);
	}
	


	


}




