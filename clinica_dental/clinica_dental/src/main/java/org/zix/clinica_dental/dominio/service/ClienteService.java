package org.zix.clinica_dental.dominio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zix.clinica_dental.persitence.crud.ClienteCrud;
import org.zix.clinica_dental.persitence.entity.Cliente;
import java.util.List;
@Service

public class ClienteService implements IClienteService{

    //Inyección de depenica de mi Repositorio (ClienteCrud) [ClienteRespository]
    @Autowired
    private ClienteCrud crud;

    @Override
    public List<Cliente> listarCliente() {
        List<Cliente> cliente = crud.findAll();
        return cliente;
    }

    @Override
    public List<Cliente> listarClientes() {
        return List.of();
    }

    @Override
    public Cliente buscarClientePorId(Integer codigo) {
        Cliente cliente = crud.findById(codigo).orElse(null);
        return cliente;
    }

    @Override
    public void guardarCliente(Cliente cliente) {//agregar nuevo y editar
        crud.save(cliente);
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        crud.delete(cliente);
    }
}
