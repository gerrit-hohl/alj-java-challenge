package jp.co.axa.apidemo.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;


/**
 * <p>
 * The {@link EmployeeService} implementation.
 * </p>
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	
	/** The repository for {@link Employee} entities. */
	@Autowired
	private EmployeeRepository employeeRepository;


	/**
	 * <p>
	 * Creates a service.
	 * </p>
	 */
	public EmployeeServiceImpl() {
		super();
	}
	
	
	/**
	 * <p>
	 * Sets the repository for {@link Employee} entities.
	 * </p>
	 *
	 * @param employeeRepository
	 *            The repository.
	 */
	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	
	@Override
	public List<Employee> retrieveEmployees() {
		List<Employee> employees = this.employeeRepository.findAll();
		return employees;
	}
	
	
	@Override
	public Employee getEmployee(Long employeeId) {
		Employee employee;
		Optional<Employee> optEmp;
		
		
		optEmp = this.employeeRepository.findById(employeeId);
		employee = optEmp.orElse(null);
		return employee;
	}
	
	
	@Override
	public Employee saveEmployee(Employee employee) {
		Employee persisted;
		Optional<Employee> optEmp;
		
		
		if (employee == null)
			throw new IllegalArgumentException("Argument employee is null.");
		
		// --- Does the entity already exist? ---
		if (employee.getId() != null) {
			optEmp = this.employeeRepository.findById(employee.getId());
			if (optEmp.isPresent())
				throw new EntityExistsException(
						"Can't save employee: Employee having ID " + employee.getId() + " already exists.");
		}
		// --- Create the entity ---
		persisted = this.employeeRepository.save(employee);
		return persisted;
	}
	
	
	@Override
	public boolean deleteEmployee(Long employeeId) {
		boolean deleted;
		
		
		if (employeeId == null)
			throw new IllegalArgumentException("Argument employeeId is null.");
		
		// --- Throws an EmptyResultDataAccessException if entity doesn't exist ---
		try {
			this.employeeRepository.deleteById(employeeId);
			deleted = true;
		} catch (EmptyResultDataAccessException exc) {
			deleted = false;
		}
		return deleted;
	}
	
	
	@Override
	public Employee updateEmployee(Employee employee) {
		Employee persisted;
		Optional<Employee> optEmp;


		if (employee == null)
			throw new IllegalArgumentException("Argument employee is null.");

		// --- Does the entity not exist? ---
		if (employee.getId() != null) {
			optEmp = this.employeeRepository.findById(employee.getId());
			if (!optEmp.isPresent())
				throw new EntityExistsException(
						"Can't update employee: Employee having ID " + employee.getId() + " doesn't exist.");
		}
		// --- Update the entity ---
		persisted = this.employeeRepository.save(employee);
		return persisted;
	}


	@Override
	public Employee saveOrUpdateEmployee(Employee employee) {
		Employee persisted;
		
		
		if (employee == null)
			throw new IllegalArgumentException("Argument employee is null.");
		
		// --- Creates or - if already exists - updates the entity ---
		persisted = this.employeeRepository.save(employee);
		return persisted;
	}
	
	
}