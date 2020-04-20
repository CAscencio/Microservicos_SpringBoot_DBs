package com.microservicios.usuario.controller;

import com.commons.alumno.model.Alumno;
import com.microservicio.commons.controllers.CommonController;
import com.microservicios.usuario.services.AlumnoService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

    @GetMapping("/alumnos-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }

    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> verFoto(@PathVariable Long id) {
        Optional<Alumno> alumno = service.findById(id);
        if (!alumno.isPresent() || alumno.get().getFoto() == null) {
            return ResponseEntity.notFound().build();
        }

        Resource imagen = new ByteArrayResource(alumno.get().getFoto());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imagen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Optional<Alumno> alumno1 = service.findById(id);
        if (!(alumno1.isPresent())) {
            return ResponseEntity.notFound().build();
        }
        Alumno alumnodb = alumno1.get();
        alumnodb.setNombre(alumno.getNombre());
        alumnodb.setApellido(alumno.getApellido());
        alumnodb.setEmail(alumno.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnodb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term) {
        return ResponseEntity.ok(service.findByNombreOrApellido(term));
    }

    @PostMapping("/crear-con-foto")
    public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
        if (!archivo.isEmpty()) {
            alumno.setFoto(archivo.getBytes());
        }
        return super.crear(alumno, result);
    }

    @PutMapping("/editar-con-foto/{id}")
    public ResponseEntity<?> editarConFoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id, @RequestParam MultipartFile archivo) throws IOException {
        if (result.hasErrors()) {
            return this.validar(result);
        }
        Optional<Alumno> alumno1 = service.findById(id);
        if (!(alumno1.isPresent())) {
            return ResponseEntity.notFound().build();
        }
        Alumno alumnodb = alumno1.get();
        alumnodb.setNombre(alumno.getNombre());
        alumnodb.setApellido(alumno.getApellido());
        alumnodb.setEmail(alumno.getEmail());

        if (!archivo.isEmpty()) {
            alumnodb.setFoto(archivo.getBytes());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnodb));
    }
}