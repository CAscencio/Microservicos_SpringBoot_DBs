package com.microservicios.curso.model;


import com.commons.alumno.model.Alumno;
import com.commons.examenes.model.Examen;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @JsonIgnoreProperties(value = {"curso"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CursoAlumno> cursoAlumnos;

    @Transient
    private List<Alumno> alumnoList;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Examen> examenList;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public Curso() {
    }

    public Curso(List<Examen> examenList) {
        this.alumnoList = new ArrayList<>();
        this.examenList = new ArrayList<>();
        this.cursoAlumnos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<Alumno> getAlumnoList() {
        return alumnoList;
    }

    public void setAlumnoList(List<Alumno> alumnoList) {
        this.alumnoList = alumnoList;
    }

    public List<Examen> getExamenList() {
        return examenList;
    }

    public void setExamenList(List<Examen> examenList) {
        this.examenList = examenList;
    }

    public void addExamen(Examen examen) {
        this.examenList.add(examen);
    }

    public void removeExamen(Examen examen) {
        this.examenList.remove(examen);
    }

    public void addAlumno(Alumno alumno) {
        this.alumnoList.add(alumno);
    }

    public void removeAlumno(Alumno alumno) {
        this.alumnoList.remove(alumno);
    }

    public List<CursoAlumno> getCursoAlumnos() {
        return cursoAlumnos;
    }

    public void setCursoAlumnos(List<CursoAlumno> cursoAlumnos) {
        this.cursoAlumnos = cursoAlumnos;
    }

    public void addCursoAlumnos(CursoAlumno cursoAlumnos) {
        this.cursoAlumnos.add(cursoAlumnos);
    }

    public void removeCursoAlumnos(CursoAlumno cursoAlumnos) {
        this.cursoAlumnos.remove(cursoAlumnos);
    }

}
