package org.example.library.Controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.library.Entites.Book;
import org.example.library.Entites.User;
import org.example.library.Model.BaseModel;
import org.example.library.Model.BookModel;
import org.example.library.Model.UserModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LibraryServlet", urlPatterns = {"/library/*"})
public class LibraryServlet extends HttpServlet {
    UserModel userModel;
    BookModel bookModel;

    @Override
    public void init() throws ServletException {
        userModel = new UserModel();
        bookModel = new BookModel();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getPathInfo();
        if (uri == null) {
            uri = "";
        }
        switch (uri) {
            case "/":
            case "":
                showloginPage(req, resp);
                break;
            case "/register":
                showRegisterPage(req, resp);
                break;
            case "/books":
                showBookList(req,resp);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getPathInfo();
        if (uri == null) {
            uri = "";
        }
        switch (uri) {
            case "/register":
                doRegister(req, resp);
                break;
            case "/login":
                showProfilePage(req, resp);
        }
    }

    public void showRegisterPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/display/register.jsp");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void showloginPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/display/login.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void doRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            System.out.println("Received username = " + username);
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");

            User user = new User(0, username, password, name, email, phone, address);
            boolean success = userModel.insertUser(user);
            if (success) {
                req.setAttribute("message", "Đăng ký thành công! Mời đăng nhập.");
                req.getRequestDispatcher("/display/login.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "Đăng ký thất bại!");
                req.getRequestDispatcher("/display/register.jsp").forward(req, resp);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showProfilePage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            User user = userModel.getUserByUsername(username, password);
            List<Book> books = bookModel.getAllBooks();



            if (user == null) {
                req.setAttribute("error", "Sai username hoặc password");

                req.getRequestDispatcher("/display/login.jsp").forward(req, resp);
            } else {
                HttpSession session = req.getSession();
                session.setAttribute("currentUser", user); // Lưu user vào session

                if ("admin".equals(user.getRole())) {
                    List<Book> listBook = bookModel.getAllBooks();
                    req.setAttribute("listbooks", listBook);
                    req.getRequestDispatcher("/display/admin.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/display/user.jsp");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }
    public void showBookList(HttpServletRequest req , HttpServletResponse resp) {
        try {
            List<Book> listBook = bookModel.getAllBooks();
            req.setAttribute("listbooks", listBook);
            req.getRequestDispatcher("/display/book/listbook.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
