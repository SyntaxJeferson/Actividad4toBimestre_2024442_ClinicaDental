package org.zix.clinica_dental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zix.clinica_dental.persistence.entity.Cliente;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ClinicaDentalApplication implements CommandLineRunner{

	private static final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(ClinicaDentalApplication.class, args);
	}

		public static void Switch() {
			int opcion;
			do {
				mostrarMenu();
				opcion = Integer.parseInt(sc.nextLine());

				switch (opcion) {
					case 1 -> registrarPaciente();
					case 2 -> mostrarPacientes();
					case 3 -> buscarPaciente();
					case 4 -> actualizarTelefono();
					case 5 -> eliminarPaciente();
					case 0 -> System.out.println("Saliendo del sistema...");
					default -> System.out.println("Opción no válida");
				}

			} while (opcion != 0);
		}

		private static void mostrarMenu() {
			System.out.println("\n=== Menú Clínica Dental ===");
			System.out.println("1. Registrar nuevo paciente");
			System.out.println("2. Ver todos los pacientes");
			System.out.println("3. Buscar paciente por ID");
			System.out.println("4. Actualizar teléfono de paciente");
			System.out.println("5. Eliminar paciente");
			System.out.println("0. Salir");
			System.out.print("Seleccione una opción: ");
		}

		private static void registrarPaciente() {
			System.out.print("Nombre completo: ");
			String nombre = sc.nextLine();
			System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
			String fecha = sc.nextLine();
			System.out.print("Teléfono: ");
			String telefono = sc.nextLine();

			Cliente p = new Cliente(nombre, fecha, telefono);
			crud.agregarPaciente(p);
			System.out.println("Paciente registrado exitosamente.");
		}

		private static void mostrarPacientes() {
			List<Cliente> lista = crud.obtenerTodos();
			if (lista.isEmpty()) {
				System.out.println("No hay pacientes registrados.");
			} else {
				lista.forEach(System.out::println);
			}
		}

		private static void buscarPaciente() {
			System.out.print("Ingrese ID del paciente: ");
			int id = Integer.parseInt(sc.nextLine());
			Cliente p = crud.buscarPorId(id);
			if (p != null) {
				System.out.println(p);
			} else {
				System.out.println("Paciente no encontrado.");
			}
		}

		private static void actualizarTelefono() {
			System.out.print("Ingrese ID del paciente: ");
			int id = Integer.parseInt(sc.nextLine());
			System.out.print("Nuevo número de teléfono: ");
			String telefono = sc.nextLine();
			crud.actualizarTelefono(id, telefono);
			System.out.println("Teléfono actualizado.");
		}

		private static void eliminarPaciente() {
			System.out.print("Ingrese ID del paciente: ");
			int id = Integer.parseInt(sc.nextLine());
			crud.eliminarPaciente(id);
			System.out.println("Paciente eliminado del sistema.");
		}

}
