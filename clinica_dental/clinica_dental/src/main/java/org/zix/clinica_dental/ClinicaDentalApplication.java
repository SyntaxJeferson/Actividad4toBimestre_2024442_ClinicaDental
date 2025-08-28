package org.zix.clinica_dental;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zix.clinica_dental.Paciente;
import org.zix.clinica_dental.ResourceNotFoundException;
import org.zix.clinica_dental.PacienteService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ClinicaDentalApplication implements CommandLineRunner {

    private final PacienteService service;

    public ClinicaDentalApplication(PacienteService service) {
        this.service = service;
    }

	public static void main(String[] args) {
		SpringApplication.run(ClinicaDentalApplication.class, args);
	}

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\n=== Clínica Dental – Gestión de Pacientes ===\n");

        boolean seguir = true;
        while (seguir) {
            try {
                System.out.println("Seleccione una opción:");
                System.out.println("1) Registrar nuevo paciente");
                System.out.println("2) Listar pacientes");
                System.out.println("3) Buscar por identificación");
                System.out.println("4) Actualizar teléfono");
                System.out.println("5) Eliminar paciente");
                System.out.println("0) Salir");
                System.out.print("Opción: ");
                String opcion = sc.nextLine().trim();

                switch (opcion) {
                    case "1" -> registrar(sc, fmt);
                    case "2" -> listar();
                    case "3" -> buscar(sc);
                    case "4" -> actualizar(sc);
                    case "5" -> eliminar(sc);
                    case "0" -> {
                        seguir = false;
                        System.out.println("Saliendo… ¡Gracias!");
                    }
                    default -> System.out.println("Opción inválida\n");
                }
            } catch (IllegalArgumentException | ResourceNotFoundException ex) {
                System.out.println("\n[ERROR] " + ex.getMessage() + "\n");
            } catch (Exception ex) {
                System.out.println("\n[ERROR INESPERADO] " + ex.getMessage() + "\n");
            }
        }
    }

    private void registrar(Scanner sc, DateTimeFormatter fmt) {
        System.out.println("\n— Registro de nuevo paciente —");
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine().trim();

        LocalDate fechaNacimiento = null;
        while (fechaNacimiento == null) {
            System.out.print("Fecha de nacimiento (yyyy-MM-dd): ");
            String fechaStr = sc.nextLine().trim();
            try {
                fechaNacimiento = LocalDate.parse(fechaStr, fmt);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Intenta nuevamente.");
            }
        }

        System.out.print("Número de identificación (DPI/NIT): ");
        String identificacion = sc.nextLine().trim();

        System.out.print("Teléfono: ");
        String telefono = sc.nextLine().trim();

        Paciente p = new Paciente(nombre, fechaNacimiento, identificacion, telefono);
        Paciente guardado = service.crear(p);
        System.out.println("\nPaciente registrado con ID: " + guardado.getId() + "\n");
    }

    private void listar() {
        System.out.println("\n— Listado de pacientes —");
        List<Paciente> pacientes = service.listar();
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.\n");
            return;
        }
        pacientes.forEach(p -> System.out.printf(
                "ID:%d | %s | Nac.: %s | ID:%s | Tel:%s%n",
                p.getId(), p.getNombreCompleto(),
                p.getFechaNacimiento(), p.getIdentificacion(), p.getTelefono()
        ));
        System.out.println();
    }

    private void buscar(Scanner sc) {
        System.out.print("\nIdentificación a buscar: ");
        String identificacion = sc.nextLine().trim();
        Paciente p = service.buscarPorIdentificacion(identificacion);
        System.out.printf("Encontrado -> ID:%d | %s | Nac.: %s | ID:%s | Tel:%s%n%n",
                p.getId(), p.getNombreCompleto(),
                p.getFechaNacimiento(), p.getIdentificacion(), p.getTelefono());
    }

    private void actualizar(Scanner sc) {
        System.out.print("\nIdentificación del paciente: ");
        String identificacion = sc.nextLine().trim();
        System.out.print("Nuevo teléfono: ");
        String telefono = sc.nextLine().trim();
        Paciente actualizado = service.actualizarContacto(identificacion, telefono);
        System.out.printf("Actualizado -> %s (Tel: %s)%n%n",
                actualizado.getNombreCompleto(), actualizado.getTelefono());
    }

    private void eliminar(Scanner sc) {
        System.out.print("\nIdentificación del paciente a eliminar: ");
        String identificacion = sc.nextLine().trim();
        service.eliminar(identificacion);
        System.out.println("Registro eliminado.\n");
    }
}
