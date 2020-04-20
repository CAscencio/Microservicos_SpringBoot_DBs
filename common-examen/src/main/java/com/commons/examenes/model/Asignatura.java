package com.commons.examenes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asgnaturas")
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @JsonIgnoreProperties(value = {"asigHijos"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura asigPadre;

    @JsonIgnoreProperties(value = {"asigPadre"},allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asigPadre", cascade = CascadeType.ALL)
    private List<Asignatura> asigHijos;

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

    public Asignatura getAsigPadre() {
        return asigPadre;
    }

    public Asignatura() {
        this.asigHijos = new ArrayList<>();
    }

    public void setAsigPadre(Asignatura asigPadre) {
        this.asigPadre = asigPadre;
    }

    public List<Asignatura> getAsigHijos() {
        return asigHijos;
    }

    public void setAsigHijos(List<Asignatura> asigHijos) {
        this.asigHijos = asigHijos;
    }
}
