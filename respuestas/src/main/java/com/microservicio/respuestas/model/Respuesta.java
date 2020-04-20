package com.microservicio.respuestas.model;

import com.commons.alumno.model.Alumno;
import com.commons.examenes.model.Pregunta;

import javax.persistence.*;

@Entity
@Table(name = "respuestas")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @Transient
    private Alumno alumno;

    @Column(name = "alumno_id")
    private Long AlumnoId;

    @OneToOne(fetch = FetchType.LAZY)
    private Pregunta pregunta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Long getAlumnoId() {
        return AlumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        AlumnoId = alumnoId;
    }
}
