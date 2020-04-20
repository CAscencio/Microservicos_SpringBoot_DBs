package com.microservicio.examenes.repository;

import com.commons.examenes.model.Asignatura;
import org.springframework.data.repository.CrudRepository;

public interface AsignaturaRepository extends CrudRepository<Asignatura, Long> {
}
