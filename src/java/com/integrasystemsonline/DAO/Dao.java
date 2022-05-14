/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.integrasystemsonline.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dpto de Redes
 */
public class Dao extends Conexion{
    Dao dao;
    Connection conn;
    public boolean insertarArchivoIni(String valor) {
        boolean sucess=false;
        try {
            this.dao=new Dao();
            this.conn=getConnection();
            String query="INSERT INTO archivo_ini VALUES (NULL,?)";
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1, valor);
            sucess=preparedStatement.execute();
            conn.close();
        } catch (InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sucess;
    }
    
    public boolean deleteArchivoIni(){
        boolean sucess=false;
        try {
            this.dao=new Dao();
            this.conn=getConnection();
            String query="DELETE FROM archivo_ini";
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            sucess=preparedStatement.execute();
            conn.close();
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        return sucess;
    }
    
    public boolean resetSecArchivoIni(){
        boolean sucess=false;
        try {
            this.dao=new Dao();
            this.conn=getConnection();
            String query="ALTER TABLE archivo_ini AUTO_INCREMENT = 1";
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            sucess=preparedStatement.execute();
            conn.close();
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        return sucess;
    }
    
    public boolean insertarArchivoFin(String valor){
        boolean sucess=false;
        try {
            this.dao=new Dao();
            this.conn=getConnection();    
            String query="INSERT INTO archivo_fin VALUES (NULL,?)";
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setString(1, valor);
            sucess=preparedStatement.execute();
            conn.close();
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        return sucess;
    }
    public boolean deleteArchivoFin(){
        boolean sucess=false;
        try {
            this.dao=new Dao();
            this.conn=getConnection(); 
            String query="DELETE FROM archivo_fin";
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            sucess=preparedStatement.execute();
            conn.close();
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sucess;
    }
    
    public boolean resetSecArchivoFin(){
        boolean sucess=false;
        try {
            this.dao=new Dao();
            this.conn=getConnection(); 
            String query="ALTER TABLE archivo_fin AUTO_INCREMENT = 1";
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            sucess=preparedStatement.execute();
            conn.close();
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sucess;
    }
    
    public List<String> generaArchivo(){
        List<String> resp=new ArrayList<>();
        ResultSet resultSet=null;
        try {
                this.dao=new Dao();
                this.conn=getConnection();
                String query="SELECT a.valor FROM archivo_fin a\n" +
                        "LEFT JOIN archivo_ini b\n" +
                        "ON a.valor=b.valor\n" +
                        "WHERE b.valor IS NULL";
                PreparedStatement preparedStatement=conn.prepareStatement(query);
                resultSet=preparedStatement.executeQuery(query);
                while(resultSet.next())
                {
                    resp.add(resultSet.getString(1));
                }
                conn.close();
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        return resp;
    }
}
