package org.example.library.Model;

import org.example.library.Entites.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<User> getAllUser() throws SQLException {
        String sql = "SELECT * FROM user WHERE role != 'admin'";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<User> listUser = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String address = rs.getString("address");

            User user = new User(id,username, name, email, phone, address);
            listUser.add(user);
        }
        return listUser;
    }
    public void deleteUserById(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
