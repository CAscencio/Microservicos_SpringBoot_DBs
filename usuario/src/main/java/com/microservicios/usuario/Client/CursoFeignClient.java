package com.microservicios.usuario.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("microservicio-cursos")
public interface CursoFeignClient {

    @DeleteMapping("/eliminar-alumno/{id}")
    public void eliminarCursoAlumnoPorId(@PathVariable Long id);
}
