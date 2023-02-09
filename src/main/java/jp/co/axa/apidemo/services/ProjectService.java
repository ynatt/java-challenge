package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Project;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.exceptions.ProjectNotFoundException;

import java.util.List;

public interface ProjectService {

    Project create(Project project);

    List<Project> getAllProjects();

    Project assignEmployee(Long projectId, Long employeeId) throws ProjectNotFoundException, EmployeeNotFoundException;

    void deleteProject(Long id);
}
