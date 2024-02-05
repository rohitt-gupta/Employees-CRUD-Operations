package com.employees.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.employees.model.Employee;
import com.employees.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@PostMapping("/employees")
	public String createNewEmployee(@RequestBody Employee employee) {
		System.out.println(employee);
		employeeRepository.save(employee);
		return "Employee Created in database";
	}
	@GetMapping("/")
	public String getHelloWorld() {
		return "<h1>Hello Wo0rld</h1>";
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		List<Employee> empList = new ArrayList<>();
		employeeRepository.findAll().forEach(empList::add);
		
		return new ResponseEntity<List<Employee>>(empList,HttpStatus.OK);	
	}
	
	@GetMapping("/employee/{empid}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long empid){
		Optional<Employee> emp  = employeeRepository.findById(empid);
		if(emp.isPresent()) {
			return new ResponseEntity<Employee>(emp.get(),HttpStatus.OK);
		}else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/employee/{empid}")
	public String updateEmployeeById(@PathVariable long empid,@RequestBody Employee employee) {
		
		Optional<Employee> emp = employeeRepository.findById(empid);
		if(emp.isPresent()) {
			Employee existingEmployee = emp.get();
			existingEmployee.setEmp_age(employee.getEmp_age());
			existingEmployee.setEmp_city(employee.getEmp_city());
			existingEmployee.setEmp_name(employee.getEmp_name());
			existingEmployee.setEmp_salary(employee.getEmp_salary());
			employeeRepository.save(existingEmployee);
			return "Employee Details agains id "+ empid+ "updated successfully!";
		}
		else {
			
			return "EMployee Details does not exist for empid"+empid;
		}
	}
	
	@DeleteMapping("/employee/{empid}")
	public String deleteEmployeeById(@PathVariable long empid) {
		employeeRepository.deleteById(empid);
		return "Employee Deleted Successfully";
	}
	
	@DeleteMapping("/employees")
	public String deleteAllEmployees() {
		employeeRepository.deleteAll();
		return "Employees Deleted Successfully";
	}
	
	
	
	
}
