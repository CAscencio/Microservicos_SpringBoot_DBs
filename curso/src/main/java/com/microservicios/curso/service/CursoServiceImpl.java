package com.microservicios.curso.service;

import com.commons.alumno.model.Alumno;
import com.microservicio.commons.services.CommonServiceImpl;
import com.microservicios.curso.client.AlumnoFeignClient;
import com.microservicios.curso.client.RespuestaFeingClient;
import com.microservicios.curso.model.Curso;
import com.microservicios.curso.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

    @Autowired
    private AlumnoFeignClient alumnoFeignClient;

    @Autowired
    private RespuestaFeingClient respuestaFeingClient;

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return repository.findCursoByAlumnoId(id);
    }

    //Aqui ya no se pone @Transaccional, porque no apunta a un repository en si :D
    @Override
    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
        return respuestaFeingClient.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
    }

    @Override
    public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
        return alumnoFeignClient.obtenerAlumnosPorCurso(ids);
    }


    @Override
    @Transactional
    public void eliminarCursoAlumnoPorId(Long id) {
        repository.eliminarCursoAlumnoPorId(id);
    }
}
