package ${package}.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import  ${package}.entity.${className};
import  ${package}.config.Application;
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class ${className}Test {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void get${className}() {
		try {
			ResultActions andExpect = this.mockMvc.perform(get("/${className?uncap_first}/get/1")).andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void add${className}() {
		try {
			${className} ${className?uncap_first}=new ${className}();
			
			MockHttpServletRequestBuilder request = post("/${className?uncap_first}/add",${className?uncap_first});
			request.param("", "");
			request.param("", "");
			request.param("",  "");
			request.param("",  "");
			request.param("",  "");
			request.param("",  "");
			request.param("",  "");
			ResultActions andExpect = this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void update${className}() {
		${className} ${className?uncap_first}=new ${className}();
	
		try {
			MockHttpServletRequestBuilder request = post("/${className?uncap_first}/update",${className?uncap_first});
			request.param("", "");
			request.param("", "");
			request.param("",  "");
			request.param("",  "");
			request.param("",  "");
			request.param("",  "");
			request.param("",  "");
			
			ResultActions andExpect = this.mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void delete${className}() {
		try {
			ResultActions andExpect = this.mockMvc.perform(delete("/${className?uncap_first}/delete/2")).andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
