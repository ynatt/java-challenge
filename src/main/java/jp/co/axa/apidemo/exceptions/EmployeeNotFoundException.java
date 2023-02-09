package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends Exception{

    public EmployeeNotFoundException(Long employeeId) {
        super(String.format("Employee with id = %s is not found", employeeId));
    }
}
