package jp.co.axa.apidemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.axa.apidemo.entities.Employee;


/**
 * <p>
 * The Spring Data repository for {@link Employee} entities.
 * </p>
 *
 * @author Gerrit Hohl (gerrit.hohl@freenet.de)
 * @version <b>1.0</b>, 26.02.2023
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	
	// --- Doesn't define any own methods yet. ---
	
	
}
