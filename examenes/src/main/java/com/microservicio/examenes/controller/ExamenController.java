package com.microservicio.examenes.controller;

import com.commons.examenes.model.Examen;
import com.commons.examenes.model.Pregunta;
import com.microservicio.commons.controllers.CommonController;
import com.microservicio.examenes.service.ExamenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Optional<Examen> e = service.findById(id);

        if (!e.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Examen examendb = e.get();
        examendb.setNombre(examen.getNombre());

        List<Pregunta> eliminadas = new ArrayList<>();

        examendb.getPreguntas().forEach(pdb -> {
            if (!examen.getPreguntas().contains(pdb)) {
                eliminadas.add(pdb);
            }
        });

        eliminadas.forEach(p -> {
            examendb.removePregunta(p);
        });

//        CODIGO MAS OPTIMIZADO :D
//        examendb.getPreguntas()
//                .stream()
//                .filter(pdb -> !examen.getPreguntas().contains(pdb))
//                .forEach(examendb::removePregunta);

        examendb.setPreguntas(examen.getPreguntas());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examendb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term) {
        return ResponseEntity.ok(service.findByNombre(term));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.findAllAsignaturas());
    }

}
