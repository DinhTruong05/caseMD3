package org.example.library.Model;

import org.example.library.Entites.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel extends BaseModel{
    public boolean insertUser(User user) throws SQLException {
        String sql = "INSERT INTO user (username, password, name, email, phone, address) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getName());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPhone());
        ps.setString(6, user.getAddress());


        return ps.executeUpdate() > 0;
    }
    public User getUserByUsername(String username , String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ? AND password =?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            return new User(rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("role"));
        }
        return null;
    }
}
