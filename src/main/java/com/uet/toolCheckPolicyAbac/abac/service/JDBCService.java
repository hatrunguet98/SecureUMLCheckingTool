package com.uet.toolCheckPolicyAbac.abac.service;

import com.uet.toolCheckPolicyAbac.abac.model.RolePermisson;

import java.sql.*;
import java.util.ArrayList;

public class JDBCService {

    public String connectDB(String DB_URL,String USER,String PASS ){
        Connection conn = null;
        Statement stmt = null;
        String message = "false";
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            boolean reachable = conn.isValid(10);// 10 sec
            if(reachable){
                return "true";
            } else {
                return "false";
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            message= se.getMessage();
            se.printStackTrace();
        }catch(Exception e){
            message= e.getMessage();
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                message= se.getMessage();
                se.printStackTrace();
            }//end finally try
        }//end try
        return message;
    }

    public ArrayList<RolePermisson> getInfoUser(String DB_URL,String USER,String PASS, String sql ){
        Connection conn = null;
        Statement stmt = null;
        ArrayList rolePerList = new ArrayList<RolePermisson>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
//            STEP 4: Execute a query
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                String user = rs.getString("User");
                String role = rs.getString("role");
                rolePerList.add(new RolePermisson(user, role));
                //Display values
            }
//            STEP 6: Clean-up environment
            rs.close();
            stmt.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        return rolePerList;
    }
}

