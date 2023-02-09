package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.Project;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.exceptions.ProjectNotFoundException;
import jp.co.axa.apidemo.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final EmployeeService employeeService;

    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project assignEmployee(Long projectId, Long employeeId) throws ProjectNotFoundException, EmployeeNotFoundException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        Employee employee = employeeService.getEmployee(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        project.getEmployees().add(employee);
        employee.setProject(project);
        projectRepository.save(project);
        return project;
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
