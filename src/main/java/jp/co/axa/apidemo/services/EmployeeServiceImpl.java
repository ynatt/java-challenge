package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "employee-cache")
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    @Cacheable(key = "'Employee' + #employeeId", unless = "#result == null")
    public Optional<Employee> getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @CachePut(key = "'Employee' + #result.id")
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @CacheEvict(key = "'Employee' + #employeeId")
    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    @CachePut(key = "'Employee' + #result.id")
    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
        employeeRepository.findById(employee.getId()).orElseThrow(() -> new EmployeeNotFoundException(employee.getId()));
        return employeeRepository.save(employee);
    }
}