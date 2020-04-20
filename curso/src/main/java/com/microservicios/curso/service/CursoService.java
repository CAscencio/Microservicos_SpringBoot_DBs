package com.microservicios.curso.service;

import com.commons.alumno.model.Alumno;
import com.microservicio.commons.services.CommonService;
import com.microservicios.curso.model.Curso;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CursoService extends CommonService<Curso> {

    public Curso findCursoByAlumnoId(Long id);

    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);

    public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids);

    public void eliminarCursoAlumnoPorId(Long id);
}
