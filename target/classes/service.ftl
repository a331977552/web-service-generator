package ${package}.service;

import ${package}.entity.${className};
import ${package}.entity.custom.${result};

public interface ${className}Service {
	${result} add${className}(${className} ${className?uncap_first} );
	${result} update${className}(${className} ${className?uncap_first} );
	${result} delete${className}ById(Integer id);
	${result} get${className}ByExample(${className}  ${className?uncap_first} );
	${result} get${className}ById(Integer id);
	${result} update${className}Selective(${className} ${className?uncap_first} );
	${result} count();



}
