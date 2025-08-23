package persitence.crud;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zix.clinica_dental.persitence.entity.Cliente;

public interface ClienteCrud extends JpaRepository<Cliente, Integer> {

}
