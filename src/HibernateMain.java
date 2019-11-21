import entities.Alumno;
import entities.Modulo;
import entities.Profesor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class HibernateMain {

    // Declaraciones de Configuracion
    private static Configuration cfg;
    private static SessionFactory sf;
    private static Session session;
    private static Transaction tx;
    private static Query query;
    private static List list;

    // Declaraciones de Objetos
    private static List<Modulo> modulos;
    private static Profesor profesor;
    private static Alumno alumno;
    private static Modulo modulo06;
    private static Modulo modulo08;
    private static Modulo modulo09;

    // Definiciones de Fichero
    private static String filePath;
    private static String fileExt;



    public static void main(String[] args) throws IOException {

        // Inicializacion

        // Ficheros
        filePath = "files/";

        // Configuracion de Conexion y transaccion
        cfg = new Configuration().configure();
        sf = cfg
                .buildSessionFactory(
                        new StandardServiceRegistryBuilder()
                                .configure().
                                build()
                );
        session = sf.openSession();
        tx = session.beginTransaction();


        // Comienzo de las transacciones

        //- Profesor -//
        setProfesor();

        //- Modulo -//
        setModulos();

        //- Alumno -//
        setAlumno();

        //- Consulta -//
        setConsulta();

        // Efectuar la transaccion
        tx.commit();

        // Cerrar la sesion
        close();

    }

    // Crear Profesor
    private static void setProfesor() throws IOException {
        profesor = new Profesor();
        profesor.setNombre("Alvaro");
        profesor.setSexo("M");
        session.save(profesor);
        writeFile(profesor,"profesor");
    }

    // Crear Objetos Modulo
    private static void setModulos() throws IOException {
        modulos = new ArrayList<Modulo>();

        modulo06 = new Modulo();
        modulo06.setNombre("Acceso Datos");
        modulo06.setCodigo("M06");

        modulo08 = new Modulo();
        modulo08.setNombre("Dispositivos Móviles");
        modulo08.setCodigo("M08");

        modulo09 = new Modulo();
        modulo09.setNombre("Programación Servicios");
        modulo09.setCodigo("M09");

        modulos.add(modulo06);
        modulos.add(modulo08);
        modulos.add(modulo09);

        session.save(modulo06);
        session.save(modulo08);
        session.save(modulo09);
        writeFile(modulo06, "modulo");
    }

    // Crear Objeto Alumno
    private static void setAlumno() throws IOException {
        alumno = new Alumno();
        alumno.setNombre("Norberto Ledo");
        alumno.setNacionalidad("Argentina");
        alumno.setEdad(44);
        alumno.setSexo("M");
        alumno.setModulos(modulos);
        session.save(alumno);
        writeFile(alumno,"alumno");
    }

    // Consultar a la DB por un alumno e imprimirlo por consola
    private static void setConsulta(){
        query = session.createQuery("FROM Alumno WHERE nombre =:nombre");
        query.setParameter("nombre", "Norberto Ledo");
        list = query.list();
        for (int i=0; i<list.size();i++){
            System.out.println(list.get(i).toString());
        }
    }

    // Cerrar la sesión
    private static void close(){

        // Cerrar la sesión
        session.close();

        // Cerrar session Factory
        sf.close();
    }

    // Escribir ficheros - Recibe el objeto a imprimir y el nombre del fichero
    private static <T> void writeFile( T obj, String fileName ) {
        File fichero = new File( filePath, fileName );
        try{
            FileOutputStream file = new FileOutputStream( fichero, false);
            ObjectOutputStream object = new ObjectOutputStream(file);
            object.writeObject(obj);
        }catch (IOException e){
            System.out.println("No se ha podido escribir el fichero");
        }
        readFile(obj, fileName);
    }

    // Leer ficheros recien escritos - Recibe el objeto a imprimir y el nombre del fichero
    private static <T> void readFile( T obj, String fileName){
        File fichero = new File(filePath+fileName);
        try{
            FileInputStream fileIn = new FileInputStream(fichero);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            T o = (T) objectIn.readObject();
            objectIn.close();
            System.out.println(o.toString());
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("No se ha podido leer el fichero");
        }
    }
    
}
