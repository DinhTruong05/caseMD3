package org.example.library.Model;

import org.example.library.DBConnect.DBconnect;

import java.sql.Connection;

public class BaseModel {
    protected Connection connection = null;

    public BaseModel() {
        DBconnect dbConnect = new DBconnect();
        connection = dbConnect.getConnection();
    }

}
