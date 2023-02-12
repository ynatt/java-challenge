package jp.co.axa.apidemo.controllers;


import jp.co.axa.apidemo.entities.Project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/v1/projects")
public interface ProjectController {

    @GetMapping
    List<Project> getAll();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Project save(@RequestBody Project project);

    @PostMapping("/{projectId}/assign/{employeeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Project assignEmployee(@PathVariable Long projectId, @PathVariable Long employeeId) throws Exception;
}
