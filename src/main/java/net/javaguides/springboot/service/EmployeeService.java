package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;
import net.javaguides.springboot.model.Employee;

public interface EmployeeService 
{
    List < Employee > getAllEmployees();
    
    
    void saveEmployee(Employee employee) throws RecordAlreadyExistsException;
    
    boolean isEmailExists(String email);
    
    Employee getEmployeeById(long id);
    
    void saveupEmployee(Employee employee) throws RecordAlreadyExistsException;
    
    void deleteEmployeeById(long id);

	boolean isAnyFieldUpdated(@Valid Employee employee);
}
