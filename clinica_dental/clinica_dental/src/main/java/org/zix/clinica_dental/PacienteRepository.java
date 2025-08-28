package org.zix.clinica_dental;

import org.zix.clinica_dental.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByIdentificacion(String identificacion);
    boolean existsByIdentificacion(String identificacion);
}
