package dam.ad.io;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Scanner;

public class DataManager {
    //KERIN ROMERO
    /*CREAMOS CARPETA DE TRABAJO /DATA*/
    public static final Path DATA_DIR = Path.of("data");

    public static void main(String[] args) {

        //crearNota( "texto","mi texto");
        //listarArchivos();
        //mostrarNota("texto.txt");
       // borrarNota("texto - copia (2).txt");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===ADMINISTRADOR DE ARCHIVOS===");
            System.out.println("1) Listar Notas");
            System.out.println("2) Crear Nota");
            System.out.println("3) Mostrar Nota");
            System.out.println("4) Borrar Nota");
            System.out.println("5) Salir");
            System.out.print("Opcion:");

            //LEER LAS OPCIONES POR EL SCANNER
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1" -> listarArchivos();
                case "2" -> {
                    System.out.print("Nombre de la nota: ");
                    String nombre = sc.nextLine();
                    System.out.print("Contenido: ");
                    String contenido = sc.nextLine();
                    crearNota(nombre, contenido);
                }
                case "3" ->{
                    System.out.print("Nombre del archivo: ");
                    String nombre = sc.nextLine();
                    mostrarNota(nombre);
                }
                case "4" -> {
                    System.out.print("Nombre del archivo a borrar: ");
                    String nombre = sc.nextLine();
                    borrarNota(nombre);
                }
                case "5" -> {
                    System.out.println("Hasta luego");
                    return;
                }
            }
        }
    }
    static void crearNota(String nombre, String contenido){
        try {
            Files.createDirectories(DATA_DIR);
            FileWriter nota = new FileWriter(DATA_DIR.resolve(nombre+".txt").toFile());
            nota.write(contenido);
            nota.close();
            System.out.println("Nota creada con exito");
        }
        catch(IOException e){
            System.out.println("Ocurrio un error al guardar el archivo.");
            e.printStackTrace();
        }
    }
    static void listarArchivos(){
        //ESTABLECEMOS RUTA DEL DIRECTORIO
        File directorio =  new File(DATA_DIR.toString());
        File[] lista = directorio.listFiles();
        System.out.println("Lista de archivos en :"+directorio.getAbsolutePath());
        for(File file : lista){
            if(file.isFile()){
                System.out.println(file.getName());
            }
        }
    }
    static void mostrarNota(String nombreArchivo){
        //Buscar el fichero nombre.txt dentro de la carpeta data
        File directorio =  new File(DATA_DIR.toString());
        String archivo = nombreArchivo;
        File ruta = new File(directorio+"/"+archivo);
        System.out.println("UBICACION : "+ruta);
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            System.out.println("CONTENIDO : ");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    static void borrarNota(String nombre){
        File directorio =  new File(DATA_DIR.toString());
        String archivo = nombre;
        File ruta = new File(directorio+"/"+archivo);
        // Verificar si el archivo existe
        if (ruta.exists()) {
            // Intentar borrar el archivo
            if (ruta.delete()) {
                System.out.println("Archivo eliminado exitosamente: " + ruta.getName());
            } else {
                System.out.println("No se pudo eliminar el archivo.");
            }
        } else {
            System.out.println("El archivo no existe.");
        }
    }


}
