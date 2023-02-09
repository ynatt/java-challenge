package jp.co.axa.apidemo.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    @JsonManagedReference
    private List<Employee> employees = new ArrayList<>();
}
