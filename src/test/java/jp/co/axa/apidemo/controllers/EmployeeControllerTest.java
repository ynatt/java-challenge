package jp.co.axa.apidemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.config.SecurityConfig;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *  without importing EmployeeController with @ContextConfiguration here,
 *  spring doesn't scan controller, and we will have 404 when trying to send request
 */
@WebMvcTest(controllers = EmployeeController.class)
@ContextConfiguration(classes = {SecurityConfig.class, EmployeeController.class})
class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    public static Employee testEmployee() {
        Employee employee = new Employee();
        employee.setName("Name");
        employee.setDepartment("Department");
        employee.setSalary(121241);
        return employee;
    }


    @Test
    @WithMockUser
    public void testGetEmployeesReturnsEmptyEmployees() throws Exception {
        when(employeeService.retrieveEmployees()).thenReturn(new ArrayList<>());

        mvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    @WithMockUser
    public void testGetEmployeeByIdWhenEmployeeDoesntExists() throws Exception {
        when(employeeService.getEmployee(1L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testPostValidEmployeeWithAdminRoleAndReturnIsOk() throws Exception {
        Employee employee = testEmployee();
        when(employeeService.saveEmployee(employee)).thenReturn(employee);
        mvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testPostValidEmployeeWithUserRoleAndReturnIsForbidden() throws Exception {
        mvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void testDeleteEmployeeWithUserRoleAndReturnIsForbidden() throws Exception {
        mvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteEmployeeWithAdminRoleAndReturnIsOk() throws Exception {
        Long employeeId = 1L;

        doNothing().when(employeeService).deleteEmployee(employeeId);

        mvc.perform(delete("/api/v1/employees/" + employeeId))
                .andExpect(status().isAccepted());
    }

}