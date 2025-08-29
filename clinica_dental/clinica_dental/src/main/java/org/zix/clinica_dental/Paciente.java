package org.zix.clinica_dental;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "pacientes", indexes = {
        @Index(name = "idx_paciente_identificacion", columnList = "identificacion", unique = true)
})
public class Paciente {
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Size(max = 150)
    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;


    @Past
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;


    @NotBlank
    @Size(max = 30)
    @Column(name = "identificacion", nullable = false, unique = true, length = 30)
    private String identificacion; // DPI, NIT u otro número


    @NotBlank
    @Size(max = 25)
    @Pattern(regexp = "[+0-9 ()-]{7,25}", message = "Teléfono inválido")
    @Column(name = "telefono", nullable = false, length = 25)
    private String telefono;


    public Paciente() {}


    public Paciente(String nombreCompleto, LocalDate fechaNacimiento, String identificacion, String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.identificacion = identificacion;
        this.telefono = telefono;
    }


    public Long getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
