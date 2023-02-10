package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        return ResponseEntity.of(employeeService.getEmployee(id));
    }

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee employee){
        return ResponseEntity.of(Optional.of(employeeService.saveEmployee(employee)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }

    @PutMapping
    public void update(@RequestBody Employee employee) {
        Optional<Employee> originalEmployee = employeeService.getEmployee(employee.getId());
        if(originalEmployee.isPresent()){
            employeeService.updateEmployee(employee);
        }
    }

}


