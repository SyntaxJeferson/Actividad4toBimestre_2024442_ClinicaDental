package org.zix.clinica_dental.persistence.crud;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zix.clinica_dental.persistence.entity.Cliente;

public interface ClienteCrud extends JpaRepository<Cliente, Integer> {

}
