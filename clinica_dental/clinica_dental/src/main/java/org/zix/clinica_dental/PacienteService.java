package org.zix.clinica_dental;

import org.zix.clinica_dental.Paciente;
import org.zix.clinica_dental.ResourceNotFoundException;
import org.zix.clinica_dental.PacienteRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Service
public class PacienteService {
    private final PacienteRepository repo;
    private final Validator validator;


    public PacienteService(PacienteRepository repo, Validator validator) {
        this.repo = repo;
        this.validator = validator;
    }


    @Transactional
    public Paciente crear(Paciente p) {
        validar(p);
        if (repo.existsByIdentificacion(p.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un paciente con esa identificación");
        }
        return repo.save(p);
    }


    @Transactional(readOnly = true)
    public List<Paciente> listar() {
        return repo.findAll();
    }


    @Transactional(readOnly = true)
    public Paciente buscarPorIdentificacion(String identificacion) {
        return repo.findByIdentificacion(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }


    @Transactional
    public Paciente actualizarContacto(String identificacion, String nuevoTelefono) {
        Paciente p = buscarPorIdentificacion(identificacion);
        p.setTelefono(nuevoTelefono);
        validar(p);
        return repo.save(p);
    }


    @Transactional
    public void eliminar(String identificacion) {
        Paciente p = buscarPorIdentificacion(identificacion);
        repo.delete(p);
    }


    private void validar(Paciente p) {
        Set<ConstraintViolation<Paciente>> violations = validator.validate(p);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Errores de validación: \n");
            violations.forEach(v -> sb.append(" - ").append(v.getPropertyPath()).append(": ").append(v.getMessage()).append("\n"));
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
