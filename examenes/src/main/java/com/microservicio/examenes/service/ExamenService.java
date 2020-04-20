package com.microservicio.examenes.service;

import com.commons.examenes.model.Asignatura;
import com.commons.examenes.model.Examen;
import com.microservicio.commons.services.CommonService;

import java.util.List;

public interface ExamenService extends CommonService<Examen> {

    public List<Examen> findByNombre(String term);

    public Iterable<Asignatura> findAllAsignaturas();
}
