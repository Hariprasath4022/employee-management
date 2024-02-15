package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jakarta.validation.Valid;
import net.javaguides.springboot.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>
{
	Employee findByEmail(String email);

	boolean existsByEmail(String email);
	
}