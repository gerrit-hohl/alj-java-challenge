package jp.co.axa.apidemo.controllers;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;


/**
 * <p>
 * The REST controller for handling the {@link Employee} entity.
 * </p>
 */
@RestController
@RequestMapping("/api/v1")
@Api()
public class EmployeeController {


	/** The service for handling {@link Employee} entities. */
	@Autowired
	private EmployeeService employeeService;


	/**
	 * <p>
	 * Creates a controller.
	 * </p>
	 */
	public EmployeeController() {
		super();
	}


	/**
	 * <p>
	 * Sets the service for handling {@link Employee} entities.
	 * </p>
	 *
	 * @param employeeService
	 *            The employee service.
	 */
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	/**
	 * <p>
	 * Returns a list of all existing employees.
	 * </p>
	 *
	 * @return The list; empty if no employees exist.
	 */
	@GetMapping(value = "/employees", produces = "application/json")
	@ApiOperation(value = "Returns a list of all existing employees.", notes = "Empty if no employees exist.")
	@ApiResponse(code = 200, message = "", response = Employee.class, responseContainer = "List")
	public List<Employee> getEmployees() {
		List<Employee> employees;


		employees = this.employeeService.retrieveEmployees();
		return employees;
	}


	/**
	 * <p>
	 * Returns the employee having the specified ID.
	 * </p>
	 *
	 * @param employeeId
	 *            The primary ID of the employee.
	 * @return The response.
	 */
	@GetMapping(value = "/employees/{employeeId}", produces = "application/json")
	@ApiOperation(value = "Returns the employee having the specified ID.")
	@ApiResponses(
			value = { @ApiResponse(code = 200, message = "The employee if the employee exists", response = Employee.class),
					@ApiResponse(code = 400, message = "If the ID was 'null'"),
					@ApiResponse(code = 404, message = "If the employee doesn't exist.") })
	public ResponseEntity<Employee> getEmployee(
			@ApiParam(value = "The primary ID of the employee.") @PathVariable(name = "employeeId") Long employeeId) {

		ResponseEntity<Employee> entity;
		Employee employee;
		
		
		// --- Parameter check ---
		if (employeeId == null) {
			entity = ResponseEntity.badRequest().build();
		}
		// --- Try to read the employee ---
		else {
			employee = this.employeeService.getEmployee(employeeId);
			if (employee == null) {
				entity = ResponseEntity.notFound().build();
			} else {
				entity = ResponseEntity.ok(employee);
			}
		}
		return entity;
	}


	/**
	 * <p>
	 * Saves the employee.
	 * </p>
	 * <p>
	 * This creates the employee.
	 * </p>
	 *
	 * @param employee
	 *            The employee.
	 * @return The response.
	 */
	@PostMapping(value = "/employees", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Saves the employee.", notes = "This creates the employee.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The saved employee.", response = Employee.class),
			@ApiResponse(code = 400, message = "If the employee was 'null'"), @ApiResponse(code = 409,
					message = "If the employee already exists and therefore can't be created.", response = Employee.class) })
	public ResponseEntity<Employee> saveEmployee(
			@ApiParam(value = "The employee which should be saved.", required = true) @RequestBody Employee employee) {
		
		ResponseEntity<Employee> entity;
		Employee persisted;


		// --- Parameter check ---
		if (employee == null) {
			entity = ResponseEntity.badRequest().build();
		}
		// --- Try to save the employee ---
		else {
			try {
				persisted = this.employeeService.saveEmployee(employee);
				System.out.println("Employee Saved Successfully");
				entity = ResponseEntity.ok(persisted);
			} catch (EntityExistsException exc) {
				System.out.println("Can' Save Employee Because Already Exists");
				entity = ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}
		return entity;
	}


	/**
	 * <p>
	 * Deletes the employee having the specified ID.
	 * </p>
	 *
	 * @param employeeId
	 *            The primary ID of the employee.
	 * @return The response.
	 */
	@DeleteMapping("/employees/{employeeId}")
	@ApiOperation(value = "Deletes the employee having the specified ID.")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "The employee was deleted successfully."),
			@ApiResponse(code = 404, message = "The employee wasn't deleted because it doesn't exist.") })
	public ResponseEntity<Void> deleteEmployee(
			@ApiParam(value = "The primary ID of the employee.") @PathVariable(name = "employeeId") Long employeeId) {
		
		ResponseEntity<Void> entity;


		// --- Parameter check ---
		if (employeeId == null) {
			entity = ResponseEntity.badRequest().build();
		}
		// --- Try to delete the employee ---
		else {
			if (this.employeeService.deleteEmployee(employeeId)) {
				System.out.println("Employee Deleted Successfully");
				entity = ResponseEntity.noContent().build();
			} else {
				System.out.println("Can't Delete Employee Because Doesn't Exist");
				entity = ResponseEntity.notFound().build();
			}
		}
		return entity;
	}


	/**
	 * <p>
	 * Updates the specified employee.
	 * </p>
	 *
	 * @param employee
	 *            The employee.
	 * @param employeeId
	 *            The primary ID of the employee.
	 * @return The response.
	 */
	@PutMapping(value = "/employees/{employeeId}", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Updates the specified employee.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The updated employee.", response = Employee.class),
			@ApiResponse(code = 400, message = "If the employee or the ID were 'null'"),
			@ApiResponse(code = 404, message = "If the employee doesn't exists.") })
	public ResponseEntity<Employee> updateEmployee(
			@ApiParam(value = "The employee which should be updated.", required = true) @RequestBody Employee employee,
			@ApiParam(value = "The primary ID of the employee.") @PathVariable(name = "employeeId") Long employeeId) {
		
		ResponseEntity<Employee> entity;
		Employee persisted;
		
		
		// --- Parameter check ---
		if ((employee == null) || (employeeId == null)) {
			entity = ResponseEntity.badRequest().build();
		}
		// --- If the ID is set in the employee, it must be same ID as as the path parameter ---
		else if ((employee.getId() != null) && !employeeId.equals(employee.getId())) {
			entity = ResponseEntity.badRequest().build();
		}
		// --- Try to update the employee ---
		else {
			// --- Set the ID (if it is already set, it is the same anyway) ---
			employee.setId(employeeId);
			try {
				persisted = this.employeeService.updateEmployee(employee);
				System.out.println("Employee Updated Successfully");
				entity = ResponseEntity.ok(persisted);
			} catch (EntityExistsException exc) {
				System.out.println("Can't Update Employee Because Doesn't Exists");
				entity = ResponseEntity.notFound().build();
			}
		}
		return entity;
	}
	
	
}
