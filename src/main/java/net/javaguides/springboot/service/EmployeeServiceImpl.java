package net.javaguides.springboot.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.expression.Objects;

import jakarta.validation.Valid;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    

    public List < Employee > getAllEmployees() 
    {
    
        return (List<Employee>) employeeRepository.findAll();
    }
    
    
    public void saveEmployee(Employee employee) throws RecordAlreadyExistsException
    {
    	
    	if(employeeRepository.findByEmail(employee.getEmail())!=null)
    	{
    		throw new RecordAlreadyExistsException("Email already exists,Please change email...");
    		
    	}
    	else
    	{
    		this.employeeRepository.save(employee);
    	}
        
    }

    public void saveupEmployee(Employee employee) throws RecordAlreadyExistsException
    {
    	if (isEmailExistsForOtherEmployee(employee.getId(), employee.getEmail()))
    	{
            throw new RecordAlreadyExistsException("Email already exists!");
        }
    	else
    	{
    		this.employeeRepository.save(employee);
    	}
    	
    }
    
    private boolean isEmailExistsForOtherEmployee(Long id, String email)
    {
        Employee employee = employeeRepository.findByEmail(email);
        return employee != null && employee.getId() != id;
    }
	
    
    public Employee getEmployeeById(long id)
    {
        Optional < Employee > optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent())
        {
            employee = optional.get();
        } 
        else 
        {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return employee;
    }

    public boolean isEmailExists(String email) 
    {
        return employeeRepository.existsByEmail(email);
    }
    
    public void deleteEmployeeById(long id) 
    {
        this.employeeRepository.deleteById(id);
    }


	@Override
	public boolean isAnyFieldUpdated(@Valid Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}
}
