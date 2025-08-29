package org.zix.clinica_dental;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zix.clinica_dental.Cita;
import org.zix.clinica_dental.CitaService;
import org.zix.clinica_dental.Paciente;
import org.zix.clinica_dental.ResourceNotFoundException;
import org.zix.clinica_dental.PacienteService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ClinicaDentalApplication implements CommandLineRunner {
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;
    private final CitaService citaService;

    public ClinicaDentalApplication(PacienteService pacienteService,
                                    OdontologoService odontologoService,
                                    CitaService citaService) {
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.citaService = citaService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClinicaDentalApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Clínica Dental ===");
            System.out.println("1. Registrar Paciente");
            System.out.println("2. Listar Pacientes");
            System.out.println("3. Registrar Odontólogo");
            System.out.println("4. Listar Odontólogos");
            System.out.println("5. Agendar Cita");
            System.out.println("6. Listar Citas");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> registrarPaciente(sc);
                case 2 -> listarPacientes();
                case 3 -> registrarOdontologo(sc);
                case 4 -> listarOdontologos();
                case 5 -> agendarCita(sc);
                case 6 -> listarCitas();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void registrarPaciente(Scanner sc) {
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.print("DNI: ");
        String dni = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();

        Paciente p = new Paciente();
        p.setNombreCompleto(nombre);
        p.setIdentificacion(dni);
        p.setTelefono(telefono);

        pacienteService.crear(p);
        System.out.println("✅ Paciente registrado.");
    }

    private void listarPacientes() {
        System.out.println("=== Lista de Pacientes ===");
        pacienteService.listar()
                .forEach(p -> System.out.println(p.getId() + " - " + p.getNombreCompleto()));
    }

    private void registrarOdontologo(Scanner sc) {
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();

        Odontologo o = new Odontologo();
        o.setNombreCompleto(nombre);
        o.setEspecialidad(especialidad);
        o.setTelefono(telefono);
        o.setCorreo(correo);

        odontologoService.guardarOdontologo(o);
        System.out.println("✅ Odontólogo registrado.");
    }

    private void listarOdontologos() {
        System.out.println("=== Lista de Odontólogos ===");
        odontologoService.listarOdontologos()
                .forEach(o -> System.out.println(o.getId() + " - " + o.getNombreCompleto()));
    }

    private void agendarCita(Scanner sc) {
        System.out.println("=== Agendar Cita ===");
        listarPacientes();
        System.out.print("ID Paciente: ");
        String identificacion = sc.nextLine();
        sc.nextLine();

        listarOdontologos();
        System.out.print("ID Odontólogo: ");
        Long idOdontologo = sc.nextLong();
        sc.nextLine();

        System.out.print("Motivo: ");
        String motivo = sc.nextLine();

        Cita c = new Cita();
        c.setPaciente(pacienteService.buscarPorIdentificacion(identificacion));
        c.setOdontologo(odontologoService.buscarPorId(idOdontologo).orElseThrow());
        c.setMotivo(motivo);
        c.setFechaHora(LocalDateTime.now());

        citaService.agendarCita(c);
        System.out.println("✅ Cita agendada.");
    }

    private void listarCitas() {
        System.out.println("=== Lista de Citas ===");
        citaService.listarCitas()
                .forEach(c -> System.out.println(
                        "Cita " + c.getId() +
                                " | Paciente: " + c.getPaciente().getNombreCompleto() +
                                " | Odontólogo: " + c.getOdontologo().getNombreCompleto() +
                                " | Motivo: " + c.getMotivo() +
                                " | Fecha: " + c.getFechaHora()
                ));
    }
}
