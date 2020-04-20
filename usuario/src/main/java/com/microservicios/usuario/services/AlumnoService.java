package com.microservicios.usuario.services;

import com.commons.alumno.model.Alumno;
import com.microservicio.commons.services.CommonService;

import java.util.List;

public interface AlumnoService extends CommonService<Alumno> {

    public List<Alumno> findByNombreOrApellido(String term);

    public Iterable<Alumno> findAllById(Iterable<Long> ids);

    public void eliminarCursoAlumnoPorId(Long id);
}