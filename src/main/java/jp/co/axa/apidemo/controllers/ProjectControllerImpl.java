package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Project;
import jp.co.axa.apidemo.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectControllerImpl implements ProjectController{

    private final ProjectService projectService;

    @Override
    public List<Project> getAll() {
        return projectService.getAllProjects();
    }

    @Override
    public Project save(Project project) {
        return projectService.create(project);
    }

    @Override
    public void assignEmployee(Long projectId, Long employeeId) throws Exception{
        projectService.assignEmployee(projectId, employeeId);
    }
}
