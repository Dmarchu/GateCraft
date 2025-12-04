import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

/**
 * CREADO POR: David Martínez
 * Hola developers, bienvenidos a las entrañas de GateMaster, a continuación os adjuntaré una serie de manuales que os serán
 * útiles a la hora de manejar este código. ¡Buena Suerte!
 * <p>
 * AÑADIR GATES
 * AÑADIR LOCALES
 * AÑADIR STRINGS
 */

//CREAR MANUAL
public class Main {

    //Declaración de Variables
    public static MyFrame frame;
    public static Locales locale;
    public static MyStartUpFrame startup;
    public static Connection conexion;

    public static MySocialLogIn sl;
    public static int userID = -1, mantainSession = 0, isInChat = -1;
    public static String userName = "", userPermission = "";

    //Método Ejecutor Main
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Usar el driver actualizado
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bd_gatecraft?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );
            System.out.println("✅ Conexión exitosa a MySQL");
        } catch (Exception e) {
            throw new RuntimeException("❌ Error en la conexión a MySQL", e);
        }

        locale = new Locales("Locale_ES");
        sl = new MySocialLogIn();
        frame = new MyFrame(new Dimension(1550, 862));
        startup = new MyStartUpFrame();

        //new MyInstallationFrame();
    }
}

