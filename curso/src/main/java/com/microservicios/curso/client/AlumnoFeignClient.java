package com.microservicios.curso.client;

import com.commons.alumno.model.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-usuarios")
public interface AlumnoFeignClient {

    @GetMapping("/alumnos-por-curso")
    public Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);

}
