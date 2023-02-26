package jp.co.axa.apidemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.axa.apidemo.entities.Employee;


/**
 * <p>
 * Performs some tests on the {@link ApiDemoApplication}.
 * </p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiDemoApplicationTests {
	
	
	/** The Spring mock MVC. */
	@Autowired
	private MockMvc mvc;
	
	
	/**
	 * <p>
	 * Performs some tests on the {@link ApiDemoApplication}.
	 * </p>
	 *
	 * @throws Exception
	 *             If an error occurs.
	 */
	@Test
	public void test() throws Exception {
		MvcResult result;
		List<Employee> employees;
		Employee persisted, employee;


		// --- Get all employees from empty database ---
		System.out.println("--- Get all employees from empty database ---");
		result = this.mvc.perform(get("/api/v1/employees")).andDo(print()).andExpect(status().isOk()).andReturn();
		employees = getEntity(new TypeReference<List<Employee>>() {
			// --- Doesn't define any methods. ---
		}, result);
		Assert.assertNotNull(employees);
		Assert.assertEquals(0, employees.size());


		// --- Get not existing employee ---
		System.out.println("--- Get not existing employee ---");
		result = this.mvc.perform(get("/api/v1/employees/1")).andDo(print()).andExpect(status().isNotFound()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNull(persisted);


		// --- Delete not existing employee ---
		System.out.println("--- Delete not existing employee ---");
		result = this.mvc.perform(delete("/api/v1/employees/1")).andDo(print()).andExpect(status().isNotFound()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNull(persisted);


		// --- Update not existing employee ---
		System.out.println("--- Update not existing employee ---");
		employee = new Employee();
		employee.setId(Long.valueOf(1));
		employee.setName("John Doe");
		employee.setSalary(Integer.valueOf(8000000));
		employee.setDepartment("Research And Development");
		result = this.mvc.perform(setEntity(put("/api/v1/employees/1"), employee)).andDo(print())
				.andExpect(status().isNotFound()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNull(persisted);


		// --- Save an employee ---
		System.out.println("--- Save an employee ---");
		employee = new Employee();
		employee.setName("John Doe");
		employee.setSalary(Integer.valueOf(8000000));
		employee.setDepartment("Research And Development");
		result = this.mvc.perform(setEntity(post("/api/v1/employees"), employee)).andDo(print()).andExpect(status().isOk())
				.andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNotNull(persisted);
		System.out.println("Employee ID : " + persisted.getId());
		Assert.assertEquals(Long.valueOf(1), persisted.getId());
		Assert.assertEquals("John Doe", persisted.getName());
		Assert.assertEquals(Integer.valueOf(8000000), persisted.getSalary());
		Assert.assertEquals("Research And Development", persisted.getDepartment());
		// --- Check the employee ---
		result = this.mvc.perform(get("/api/v1/employees/1")).andDo(print()).andExpect(status().isOk()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNotNull(persisted);
		Assert.assertEquals(Long.valueOf(1), persisted.getId());
		Assert.assertEquals("John Doe", persisted.getName());
		Assert.assertEquals(Integer.valueOf(8000000), persisted.getSalary());
		Assert.assertEquals("Research And Development", persisted.getDepartment());


		// --- Get existing employee ---
		System.out.println("--- Get existing employee ---");
		result = this.mvc.perform(get("/api/v1/employees/1")).andDo(print()).andExpect(status().isOk()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNotNull(persisted);
		Assert.assertEquals(Long.valueOf(1), persisted.getId());
		Assert.assertEquals("John Doe", persisted.getName());
		Assert.assertEquals(Integer.valueOf(8000000), persisted.getSalary());
		Assert.assertEquals("Research And Development", persisted.getDepartment());


		// --- Save an already existing employee ---
		System.out.println("--- Save an already existing employee ---");
		employee = new Employee();
		employee.setId(Long.valueOf(1));
		employee.setName("John Doe");
		employee.setSalary(Integer.valueOf(9000000));
		employee.setDepartment("Research And Development");
		result = this.mvc.perform(setEntity(post("/api/v1/employees"), employee)).andDo(print())
				.andExpect(status().isConflict()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNull(persisted);
		// --- Check the employee ---
		result = this.mvc.perform(get("/api/v1/employees/1")).andDo(print()).andExpect(status().isOk()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNotNull(persisted);
		Assert.assertEquals(Long.valueOf(1), persisted.getId());
		Assert.assertEquals("John Doe", persisted.getName());
		Assert.assertEquals(Integer.valueOf(8000000), persisted.getSalary());
		Assert.assertEquals("Research And Development", persisted.getDepartment());


		// --- Update existing employee, but wrong ID ---
		System.out.println("--- Update existing employee, but wrong ID ---");
		employee = new Employee();
		employee.setId(Long.valueOf(1));
		employee.setName("John Doe");
		employee.setSalary(Integer.valueOf(10000000));
		employee.setDepartment("Research And Development");
		result = this.mvc.perform(setEntity(put("/api/v1/employees/2"), employee)).andDo(print())
				.andExpect(status().isBadRequest()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNull(persisted);
		// --- Check the employee ---
		result = this.mvc.perform(get("/api/v1/employees/1")).andDo(print()).andExpect(status().isOk()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNotNull(persisted);
		Assert.assertEquals(Long.valueOf(1), persisted.getId());
		Assert.assertEquals("John Doe", persisted.getName());
		Assert.assertEquals(Integer.valueOf(8000000), persisted.getSalary());
		Assert.assertEquals("Research And Development", persisted.getDepartment());
		
		
		// --- Update existing employee ---
		System.out.println("--- Update existing employee ---");
		employee = new Employee();
		employee.setId(Long.valueOf(1));
		employee.setName("John Doe");
		employee.setSalary(Integer.valueOf(11000000));
		employee.setDepartment("Research And Development");
		result = this.mvc.perform(setEntity(put("/api/v1/employees/1"), employee)).andDo(print()).andExpect(status().isOk())
				.andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNotNull(persisted);
		Assert.assertEquals(Long.valueOf(1), persisted.getId());
		Assert.assertEquals("John Doe", persisted.getName());
		Assert.assertEquals(Integer.valueOf(11000000), persisted.getSalary());
		Assert.assertEquals("Research And Development", persisted.getDepartment());
		// --- Check the employee ---
		result = this.mvc.perform(get("/api/v1/employees/1")).andDo(print()).andExpect(status().isOk()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNotNull(persisted);
		Assert.assertEquals(Long.valueOf(1), persisted.getId());
		Assert.assertEquals("John Doe", persisted.getName());
		Assert.assertEquals(Integer.valueOf(11000000), persisted.getSalary());
		Assert.assertEquals("Research And Development", persisted.getDepartment());
		
		
		// --- Get all employees from filled database ---
		System.out.println("--- Get all employees from filled database ---");
		result = this.mvc.perform(get("/api/v1/employees")).andDo(print()).andExpect(status().isOk()).andReturn();
		employees = getEntity(new TypeReference<List<Employee>>() {
			// --- Doesn't define any methods. ---
		}, result);
		Assert.assertNotNull(employees);
		Assert.assertEquals(1, employees.size());
		persisted = employees.get(0);
		Assert.assertNotNull(persisted);
		Assert.assertEquals(Long.valueOf(1), persisted.getId());
		Assert.assertEquals("John Doe", persisted.getName());
		Assert.assertEquals(Integer.valueOf(11000000), persisted.getSalary());
		Assert.assertEquals("Research And Development", persisted.getDepartment());
		
		
		// --- Delete existing employee ---
		System.out.println("--- Delete existing employee ---");
		result = this.mvc.perform(delete("/api/v1/employees/1")).andDo(print()).andExpect(status().isNoContent()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNull(persisted);
		// --- Check the employee ---
		result = this.mvc.perform(get("/api/v1/employees/1")).andDo(print()).andExpect(status().isNotFound()).andReturn();
		persisted = getEntity(Employee.class, result);
		Assert.assertNull(persisted);
	}


	/**
	 * <p>
	 * Returns the entity of the specified type from the response.
	 * </p>
	 *
	 * @param <T>
	 *            The type of entity.
	 * @param type
	 *            The type of the entity.
	 * @param result
	 *            The result.
	 * @return The entity.
	 */
	private static <T> T getEntity(Class<T> type, MvcResult result) {
		T entity;
		String contentAsString;
		ObjectMapper objectMapper;


		Assert.assertNotNull(type);
		Assert.assertNotNull(result);
		try {
			if (isContentEmpty(result)) {
				entity = null;
			} else {
				contentAsString = result.getResponse().getContentAsString();
				objectMapper = new ObjectMapper();
				entity = objectMapper.readValue(contentAsString, type);
			}
		} catch (IOException exc) {
			exc.printStackTrace();
			Assert.fail(exc.getMessage());
			return null;
		}
		return entity;
	}


	/**
	 * <p>
	 * Returns the entity of the specified type from the response.
	 * </p>
	 *
	 * @param <T>
	 *            The type of entity.
	 * @param typeReference
	 *            The type reference.
	 * @param result
	 *            The result.
	 * @return The entity.
	 */
	private static <T> T getEntity(TypeReference<T> typeReference, MvcResult result) {
		T entity;
		String contentAsString;
		ObjectMapper objectMapper;


		Assert.assertNotNull(typeReference);
		Assert.assertNotNull(result);
		try {
			if (isContentEmpty(result)) {
				entity = null;
			} else {
				contentAsString = result.getResponse().getContentAsString();
				objectMapper = new ObjectMapper();
				entity = objectMapper.readValue(contentAsString, typeReference);
			}
		} catch (IOException exc) {
			exc.printStackTrace();
			Assert.fail(exc.getMessage());
			return null;
		}
		return entity;
	}
	
	
	/**
	 * <p>
	 * Returns the flag if the content of the response is empty.
	 * </p>
	 *
	 * @param result
	 *            The result.
	 * @return The flag.
	 */
	private static boolean isContentEmpty(MvcResult result) {
		boolean empty;
		byte[] contentAsByteArray;
		
		
		Assert.assertNotNull(result);
		contentAsByteArray = result.getResponse().getContentAsByteArray();
		empty = (contentAsByteArray.length == 0);
		return empty;
	}


	/**
	 * <p>
	 * Sets the specified entity as content.
	 * </p>
	 *
	 * @param <T>
	 *            The type of the entity.
	 * @param builder
	 *            The builder.
	 * @param entity
	 *            The entity.
	 * @return The builder for method chaining.
	 */
	private static <T> MockHttpServletRequestBuilder setEntity(MockHttpServletRequestBuilder builder, T entity) {
		Assert.assertNotNull(builder);
		Assert.assertNotNull(entity);
		return builder.content(toJsonString(entity)).contentType(MediaType.APPLICATION_JSON);
	}
	
	
	/**
	 * <p>
	 * Returns the entity as JSON string.
	 * </p>
	 *
	 * @param <T>
	 *            The type of the entity.
	 * @param entity
	 *            The entity.
	 * @return The JSON string.
	 */
	private static <T> String toJsonString(T entity) {
		String jsonString;
		ObjectMapper objectMapper;


		Assert.assertNotNull(entity);
		objectMapper = new ObjectMapper();
		try {
			jsonString = objectMapper.writeValueAsString(entity);
		} catch (JsonProcessingException exc) {
			exc.printStackTrace();
			Assert.fail(exc.getMessage());
			return null;
		}
		return jsonString;
	}


}
