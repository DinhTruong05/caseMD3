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
        if (uri == null)
            uri = "";
        HttpSession session = req.getSession(false); // không tạo session mới
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;
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
            case "/addbook":
                if (currentUser == null) {
                    resp.sendRedirect(req.getContextPath() + "/library");
                } else {
                    if ("/books".equals(uri)) showBookList(req, resp);
                    else showAddBookPage(req, resp);
                }
                break;
            case "/logout":
                doLogout(req, resp);
                break;
            case "/deleteB":
                deleteBook(req, resp);
                break;
            case "/bookdelete":
                showBook(req, resp);
                break;
            case "/listuser":
                showListUser(req, resp);
                break;
            case "/delete":
                deleteUser(req, resp);
                break;
            case "/rent":
                rentBook(req, resp);
                break;
            case "/return":
                returnBook(req, resp);
                break;
            case "/user":
                showRentBook(req, resp);
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
                break;
            case "/addbook":
                doAddbook(req, resp);
                break;

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

            if (user == null) {
                req.setAttribute("error", "Sai username hoặc password");
                req.getRequestDispatcher("/display/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("currentUser", user);

            if ("admin".equals(user.getRole())) {
                List<Book> listBook = bookModel.getAllBooks();
                session.setAttribute("listbooks", listBook);
                resp.sendRedirect(req.getContextPath() + "/display/user/admin.jsp");
            } else {
                List<Book> rentedBooks = bookModel.getRentBook(user.getId());
                session.setAttribute("rentedBooks", rentedBooks);
                resp.sendRedirect(req.getContextPath() + "/display/user/user.jsp");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showBookList(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Book> listBook = bookModel.getAllBooks();
            req.setAttribute("listbooks", listBook);
            req.getRequestDispatcher("/display/book/listbook.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void showAddBookPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/display/book/addbook.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doAddbook(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession(false);
            User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;
            if (currentUser == null || !"admin".equals(currentUser.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/library");
                return;
            }

            String name = req.getParameter("name");
            String genre = req.getParameter("genre");
            String priceStr = req.getParameter("price");

            if (name == null || name.isEmpty() || genre == null || genre.isEmpty() || priceStr == null || priceStr.isEmpty()) {
                req.setAttribute("error", "Vui lòng điền đầy đủ thông tin sách.");
                req.getRequestDispatcher("/display/book/addbook.jsp").forward(req, resp);
                return;
            }

            int price = Integer.parseInt(priceStr);
            Book book = new Book(0, name, genre, price);
            bookModel.addBook(book);

            List<Book> listBook = bookModel.getAllBooks();
            req.setAttribute("listbooks", listBook);
            req.getRequestDispatcher("/display/user/admin.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("error", "Giá sách phải là số nguyên.");
            try {
                req.getRequestDispatcher("/display/book/addbook.jsp").forward(req, resp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/library");
    }

    private void deleteBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        try {
            bookModel.deleteBookById(Integer.parseInt(id));
            resp.sendRedirect(req.getContextPath() + "/library/bookdelete");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Book> listBook = bookModel.getAllBooks();
            req.setAttribute("listbooks", listBook);

            req.getRequestDispatcher("/display/user/admin.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showListUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> listUser = userModel.getAllUser();
            req.setAttribute("listuser", listUser);
            req.getRequestDispatcher("/display/user/listuser.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteUser(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            userModel.deleteUserById(id);
            resp.sendRedirect(req.getContextPath() + "/library/listuser");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void rentBook(HttpServletRequest rep, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = rep.getSession(false);
            User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;
            if (currentUser == null) {
                resp.sendRedirect(rep.getContextPath() + "/library");
                return;
            }
            int bookId = Integer.parseInt(rep.getParameter("id"));
            bookModel.addRent(currentUser.getId(), bookId);
            List<Book> rentedBooks = bookModel.getRentBook(currentUser.getId());
            session.setAttribute("rentedBooks", rentedBooks);
            resp.sendRedirect(rep.getContextPath() + "/display/user/user.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void returnBook(HttpServletRequest rep, HttpServletResponse resp) {
        try {
            HttpSession session = rep.getSession();
            User currentUser = (User) session.getAttribute("currentUser");

            if (currentUser == null || !"user".equals(currentUser.getRole())) {
                resp.sendRedirect(rep.getContextPath() + "/library");
                return;
            }
            int bookId = Integer.parseInt(rep.getParameter("id"));
            bookModel.returnBook(currentUser.getId(), bookId);
            List<Book> rentedBooks = bookModel.getRentBook(currentUser.getId());
            session.setAttribute("rentedBooks", rentedBooks);

            resp.sendRedirect(rep.getContextPath() + "/display/user/user.jsp");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void showRentBook(HttpServletRequest req, HttpServletResponse resp) {
        try {
            HttpSession session = req.getSession();
            User currentUser = (User) session.getAttribute("currentUser");
            if (currentUser == null) {
                resp.sendRedirect(req.getContextPath() + "/library/login");
                return;
            }
            List<Book> rentedBooks = bookModel.getRentBook(currentUser.getId());
            session.setAttribute("rentedBooks", rentedBooks);
            req.setAttribute("rentedBooks", rentedBooks);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/display/user/user.jsp");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
