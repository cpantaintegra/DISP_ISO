/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cpanta
 */
public class Conexion {
    private Connection conn;
    private String url,username,password;
    
    public Connection getConnection() throws InstantiationException, IllegalAccessException{
        this.username="root";
        this.password="admin123";
        this.url="jdbc:mysql://localhost:3306/baseloteria";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE,null,e);
        }
        try {
            this.conn=DriverManager.getConnection(url,username,password);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.conn;
    }
}
