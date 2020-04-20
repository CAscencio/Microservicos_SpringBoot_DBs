package com.microservicios.curso.controller;

import com.commons.alumno.model.Alumno;
import com.commons.examenes.model.Examen;
import com.microservicio.commons.controllers.CommonController;
import com.microservicios.curso.model.Curso;
import com.microservicios.curso.model.CursoAlumno;
import com.microservicios.curso.service.CursoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

    @Value("${config.balanceador.test}")
    private String BALANCEADOR_TEST;

    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id) {
        service.eliminarCursoAlumnoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Override
    public ResponseEntity<?> listar() {
        List<Curso> cursoLis = ((List<Curso>) service.findAll()).stream()
                .map(c -> {
                    c.getCursoAlumnos().forEach(ca -> {
                        Alumno alumno = new Alumno();
                        alumno.setId(ca.getAlumnoId());
                        c.addAlumno(alumno);
                    });
                    return c;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(cursoLis);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Curso> entity = service.findById(id);
        if (!(entity.isPresent())) {
            return ResponseEntity.notFound().build();
        }
        Curso curso = entity.get();
        if (curso.getCursoAlumnos().isEmpty() == false) {
            List<Long> ids = curso.getCursoAlumnos()
                    .stream()
                    .map(ca -> ca.getAlumnoId())
                    .collect(Collectors.toList());

            List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
            curso.setAlumnoList(alumnos);
        }
        return ResponseEntity.ok().body(curso);
    }

    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("balanceador", BALANCEADOR_TEST);
        response.put("cursos", service.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Optional<Curso> curso1 = this.service.findById(id);
        if (!curso1.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Curso dbCurso = curso1.get();
        dbCurso.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnoList, @PathVariable Long id) {
        Optional<Curso> curso1 = this.service.findById(id);
        if (!curso1.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso = curso1.get();
        alumnoList.forEach(a -> {
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(a.getId());
            cursoAlumno.setCurso(dbCurso);
            dbCurso.addCursoAlumnos(cursoAlumno);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
        Optional<Curso> curso1 = this.service.findById(id);
        if (!curso1.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso = curso1.get();
        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());
        dbCurso.removeCursoAlumnos(cursoAlumno);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarPorAlumno(@PathVariable Long id) {
        Curso curso = service.findCursoByAlumnoId(id);
        if (curso != null) {
            List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);

            List<Examen> examenes = curso.getExamenList().stream().map(examen -> {
                if (examenesIds.contains(examen.getId())) {
                    examen.setRespondido(true);
                }
                return examen;
            }).collect(Collectors.toList());
            curso.setExamenList(examenes);
        }
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenList, @PathVariable Long id) {
        Optional<Curso> c = this.service.findById(id);
        if (!c.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso = c.get();
        examenList.forEach(e -> {
            dbCurso.addExamen(e);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }

    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
        Optional<Curso> c = this.service.findById(id);
        if (!c.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso dbCurso = c.get();
        dbCurso.removeExamen(examen);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
    }
}
