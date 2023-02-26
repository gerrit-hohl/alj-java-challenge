package jp.co.axa.apidemo.services;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import jp.co.axa.apidemo.entities.Employee;


/**
 * <p>
 * The service for handling {@link Employee} entities.
 * </p>
 * <p>
 * Contains the business logic.
 * </p>
 */
public interface EmployeeService {


	/**
	 * <p>
	 * Returns a list of all employees.
	 * </p>
	 *
	 * @return The list; empty if no employees exist.
	 */
	List<Employee> retrieveEmployees();


	/**
	 * <p>
	 * Returns the employee having the specified ID.
	 * </p>
	 *
	 * @param employeeId
	 *            The primary ID of the employee.
	 * @return The employee or {@code null}, if no such employee exists.
	 * @throws IllegalArgumentException
	 *             If the primary ID is {@code null}.
	 */
	Employee getEmployee(Long employeeId);


	/**
	 * <p>
	 * Persists the specified employee by creating it in the database.
	 * </p>
	 * <p>
	 * If the entity already exists, it is updated.
	 * </p>
	 *
	 * @param employee
	 *            The employee.
	 * @return The persisted entity.
	 * @throws IllegalArgumentException
	 *             If the employee is {@code null}.
	 * @throws EntityExistsException
	 *             If the employee already exists.
	 */
	Employee saveEmployee(Employee employee);


	/**
	 * <p>
	 * Deletes the specified existing employee.
	 * </p>
	 *
	 * @param employeeId
	 *            The primary ID of the employee.
	 * @return The result: {@code true}, if the employee is deleted,
	 *         {@code false}, if the employee doesn't exist.
	 * @throws IllegalArgumentException
	 *             If the primary ID is {@code null}.
	 */
	boolean deleteEmployee(Long employeeId);


	/**
	 * <p>
	 * Persists the specified employee by updating it in the database.
	 * </p>
	 *
	 * @param employee
	 *            The employee.
	 * @return The persisted entity.
	 * @throws IllegalArgumentException
	 *             If the employee is {@code null}.
	 * @throws EntityNotFoundException
	 *             If the entity doesn't exist.
	 */
	Employee updateEmployee(Employee employee);
	
	
	/**
	 * <p>
	 * Persists the specified employee.
	 * </p>
	 * <p>
	 * If the employee doesn't exist, it is created. If it exists, it is
	 * updated.
	 * </p>
	 *
	 * @param employee
	 *            The employee.
	 * @return The persisted entity.
	 * @throws IllegalArgumentException
	 *             If the employee is {@code null}.
	 */
	Employee saveOrUpdateEmployee(Employee employee);


}