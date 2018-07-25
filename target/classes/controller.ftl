package ${package}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storage.entity.${className};
import com.storage.service.${className}Service;

@RestController()
@RequestMapping("/${className?uncap_first}")
public class ${className}Controller {

	@Autowired
	${className}Service service;

	@PostMapping("/add")
	public Object add${className}(${className} ${className?uncap_first}) {

		return this.service.add${className}(${className?uncap_first});
	}

	@GetMapping("/get/{id}")
	public Object get${className}(@PathVariable(name = "id") Integer id) {

		return this.service.get${className}ById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Object delete${className}ById(@PathVariable(name = "id") Integer id) {
		return this.service.delete${className}ById(id);
	}



	@PostMapping("/update")
	public Object update${className}(${className} ${className?uncap_first}) {
		return this.service.update${className}(${className?uncap_first});
	}
	@GetMapping("/count")
	public Object count() {
		return this.service.count();
	}


}
