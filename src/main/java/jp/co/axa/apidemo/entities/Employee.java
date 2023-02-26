package jp.co.axa.apidemo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * <p>
 * The entity of an employee.
 * </p>
 */
@Entity
@Table(name = "EMPLOYEE")
@ApiModel(value = "Employee", description = "The entity of an employee.")
public class Employee {
	
	
	/**
	 * <p>
	 * The primary key of the entity.
	 * </p>
	 */
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	@ApiModelProperty(value = "The primary key of the entity.", example = "1234567890", readOnly = true)
	private Long	id;
	
	
	/**
	 * <p>
	 * The name of the employee.
	 * </p>
	 */
	@Getter
	@Setter
	@Column(name = "EMPLOYEE_NAME")
	@JsonProperty("name")
	@ApiModelProperty(value = "The name of the employee.", example = "John Doe")
	private String	name;
	
	
	/**
	 * <p>
	 * The annual salary of the employee.
	 * </p>
	 */
	@Getter
	@Setter
	@Column(name = "EMPLOYEE_SALARY")
	@JsonProperty("salary")
	@ApiModelProperty(value = "The annual salary of the employee.", example = "8000000")
	private Integer	salary;
	
	
	/**
	 * <p>
	 * The department at which the employee works.
	 * </p>
	 */
	@Getter
	@Setter
	@Column(name = "DEPARTMENT")
	@JsonProperty("department")
	@ApiModelProperty(value = "The department at which the employee works.", example = "Research And Development")
	private String	department;
	
	
}
