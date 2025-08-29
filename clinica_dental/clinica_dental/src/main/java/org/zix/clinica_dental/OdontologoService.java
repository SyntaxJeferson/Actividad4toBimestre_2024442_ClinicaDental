package org.zix.clinica_dental;

import org.springframework.transaction.annotation.Transactional;
import org.zix.clinica_dental.Odontologo;
import org.zix.clinica_dental.OdontologoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private final OdontologoRepository odontologoRepository;

    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Transactional
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    @Transactional(readOnly = true)
    public List<Odontologo> listarOdontologos() {
        return odontologoRepository.findAll();
    }

    public Optional<Odontologo> buscarPorId(Long id) {
        return odontologoRepository.findById(id);
    }
}
