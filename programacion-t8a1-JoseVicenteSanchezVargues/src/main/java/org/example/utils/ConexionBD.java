package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static Connection conn = null;
    // Declaramos los datos de conexion a la bd
    private static final String driver="com.mysql.cj.jdbc.Driver";
    private static final String user="root";
    private static final String pass="postgres";
    private static final String url="jdbc:mysql://localhost:3306/gestionescolar";

    // Cambiamos la visibilidad para controlar la construccion, tal como indica singleton
    private ConexionBD() {
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    // nuestro propio constructor que nos devuelve un objeto sql Connection
    public static Connection getConnection(){
        if (conn == null){
            new ConexionBD();
        }
        return conn;
    }

    // Para cerrar la conexion al acabar
    public static void closeConnection() {
        try{
            if (conn != null){
                conn.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}

