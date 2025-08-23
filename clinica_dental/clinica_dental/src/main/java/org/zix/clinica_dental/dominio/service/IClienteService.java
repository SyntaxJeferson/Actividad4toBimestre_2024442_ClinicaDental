package org.zix.clinica_dental.dominio.service;

import org.zix.clinica_dental.persitence.entity.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> listarCliente();

    //firmas de metodo
    public List<Cliente> listarClientes();
    public Cliente buscarClientePorId(Integer codigo);
    public void guardarCliente(Cliente cliente);
    public void eliminarCliente(Cliente cliente);
}