package com.microservicios.usuario.services;

import com.commons.alumno.model.Alumno;
import com.microservicio.commons.services.CommonServiceImpl;
import com.microservicios.usuario.Client.CursoFeignClient;
import com.microservicios.usuario.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

    @Autowired
    private CursoFeignClient cursoFeignClient;

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String term) {
        return repository.findByNombreOrApellido(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAllById(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public void eliminarCursoAlumnoPorId(Long id) {
        cursoFeignClient.eliminarCursoAlumnoPorId(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        super.deleteById(id);
        this.eliminarCursoAlumnoPorId(id);
    }
}

