package scanner;
import jdk.internal.platform.Container;
import java.sql.*;
import java.util.Scanner;

public class Main {


    String nombre;
    String grupo;
    int id;
    String mensaje;
    String text;




    public static void main(String[] args) {
        Main mensajero = new Main();
        mensajero.pedirMenu();

    }


    public void pedirMenu() {

        Scanner sd = new Scanner(System.in);

        try {

            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("El controlador de MySQL no esta funcionando correctamente.");
            return;
        }



        int bandera = 0;
        int seleccion = 0;

        do {
            do {
                System.out.println("...........................................");
                System.out.println(" - -  B i e n v e n i d o  - -   \n - -  escoge una opcion - -  ");
                System.out.println("...........................................\n");
                System.out.println("1. Registrar");
                System.out.println("2. Buscar alumnos");
                System.out.println("3. Modificar");
                System.out.println("4. Salir");
                seleccion = sd.nextInt();
                if (seleccion >= 1 && seleccion <= 4) {
                    bandera = 1;
                } else {
                    System.out.println("......................................");
                    System.out.println("Ingresaste una opción incorrecta.");
                    System.out.println("......................................");
                }

            } while (bandera == 0);

            //switch - case
            switch (seleccion){
                case 1: {
                    nombre_uno();
                    grupo_uno();
                    registro();
                    break;
                } case 2: {
                    buscar();
                    break;
                } case 3: {
                    modificar("3","Camilo","48");
                    break;

                } case 4: {
                    System.out.println("....................................................");
                    System.out.println("¡Gracias por habernos escogido, vuelve pronto!");
                    System.out.println("....................................................");
                    bandera = 2;
                }
            }

        } while (bandera != 2);

    }

    private void nombre_uno(){
        Scanner sd = new Scanner(System.in);

        System.out.println("Dame tu nombre");
        nombre = sd.nextLine();

        System.out.println("Tu nombre es: " + getNombre());

    }

    private void grupo_uno(){
        Scanner sd = new Scanner(System.in);



        System.out.println("Dame tu Grupo");
        grupo = sd.nextLine();

        System.out.println("Tu grupo es: " + getGrupo());

        System.out.println("Se ha registrado exitosamente.");
    }

    private void modificar(String idAlumno, String nuevoNombre, String nuevoGrupo) {


        try {
            Connection sn = DriverManager.getConnection("jdbc:mysql://localhost/bd_institucion", "root", "");


            // Consultar la tabla para obtener el registro del alumno con el ID proporcionado
            PreparedStatement pstSelect = sn.prepareStatement("SELECT * FROM alumnos WHERE ID = ?");


            System.out.println("¿Que alumno desea modificar?");
            pstSelect.setString(1, idAlumno);
            ResultSet rs = pstSelect.executeQuery();



            if (rs.next()) {
                // Si se encontró un registro con el ID proporcionado, actualizar los valores de nombre y grupo para el alumno
                PreparedStatement pstUpdate = sn.prepareStatement("UPDATE alumnos SET NombreAlumno = ?, Grupo = ? WHERE ID = ?");
                pstUpdate.setString(1, nuevoNombre);
                pstUpdate.setString(2, nuevoGrupo);
                pstUpdate.setString(3, idAlumno);
                int filasActualizadas = pstUpdate.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Se actualizaron los valores del alumno con ID " + idAlumno);
                } else {
                    System.out.println("No se pudo actualizar el registro del alumno con ID " + idAlumno);
                }
            } else {
                // Si no se encontró un registro con el ID proporcionado, mostrar un mensaje indicando que el alumno no está registrado
                System.out.println("El alumno con ID " + idAlumno + " no está registrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar los alumnos en la base de datos: " + e.getMessage());
        }
    }

    //metodo para buscar el usuario atravez del id.
    private void buscar() {


        Scanner sd = new Scanner(System.in);
        System.out.println("Ingresa el ID del alumno:");
        String idAlumno = sd.nextLine().trim();

        try {
            Connection sn = DriverManager.getConnection("jdbc:mysql://localhost/bd_institucion", "root", "");
            PreparedStatement pst = sn.prepareStatement("select * from alumnos where ID = ?");
            pst.setString(1, idAlumno); // Establecer el valor de búsqueda a partir del valor de ID proporcionado por el usuario
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
              //  Si se encontró un registro con el ID proporcionado, establecer los valores de nombre y grupo para el alumno
                setNombre(rs.getString("NombreAlumno"));
                setGrupo(rs.getString("Grupo"));
                System.out.println("El alumno " + getNombre() + " y en el grupo " + getGrupo() +  " si se encuentra registrado");
            } else {
                // Si no se encontró un registro con el ID proporcionado, mostrar un mensaje indicando que el alumno no está registrado
                System.out.println("El alumno con ID " + idAlumno + " no está registrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar los alumnos en la base de datos: " + e.getMessage());
        }

    }




    //metodo para registrar el usuario que desees añadir a la base de datos(MySql).
    private void registro() {

        try {

            Connection sn = DriverManager.getConnection("jdbc:mysql://localhost/bd_institucion", "root", "");
            PreparedStatement pst = sn.prepareStatement("insert into alumnos values(?, ?, ?)");

            pst.setString(1, "0");
            pst.setString(2, getNombre().trim());
            pst.setString(3, getGrupo().trim());
            pst.executeUpdate();

            setNombre("");
            setGrupo("");
            setMensaje("¡Registro exitoso!");

        } catch (Exception e) {
            System.out.println("Ocurrió un error al intentar realizar la conexión con la base de datos.");
            e.printStackTrace();
        }


    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Main{" +
                "nombre='" + nombre + '\'' +
                ", grupo='" + grupo + '\'' +
                '}';
    }


}