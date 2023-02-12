package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> retrieveEmployees();

    Optional<Employee> getEmployee(Long employeeId);

    Employee saveEmployee(Employee employee);

    void deleteEmployee(Long employeeId);

    Employee updateEmployee(Employee employee) throws EmployeeNotFoundException;
}