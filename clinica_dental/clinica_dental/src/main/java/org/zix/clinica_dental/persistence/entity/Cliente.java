package org.zix.clinica_dental.persistence.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "Cliente")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String fechaDeN;

    public Integer getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaDeN() {
        return fechaDeN;
    }

    public void setFechaDeN(String correo) {
        this.fechaDeN = correo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigoCliente=" + codigoCliente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + fechaDeN + '\'' +
                '}';
    }
}
