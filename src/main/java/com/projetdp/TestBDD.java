package com.projetdp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestBDD {
    public static void main(String[] args) {
        ConnexionBDD cbd = ConnexionBDD.getInstance();
        String sql = "SELECT * FROM users";
        try {
            Statement smt = cbd.getCnx().createStatement();
            ResultSet rs = smt.executeQuery(sql);
            while ((rs.next())) {
                System.out.println(rs.getString("username"));
            }
            cbd.closeCnx();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}