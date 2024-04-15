package zen;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String option = request.getParameter("option");

        if (option.equals("insert")) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            insertData(id, name, phone);
            response.sendRedirect("index.html");
        } else if (option.equals("delete")) {
            String id = request.getParameter("id");
            deleteData(id);
            response.sendRedirect("index.html");
        } else if (option.equals("select")) {
            String id = request.getParameter("id");
            selectData(id, response);
        }
    }

    private void insertData(String id, String name, String phone) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ash", "root", "yele2208");
            PreparedStatement ps = con.prepareStatement("insert into students values(?,?,?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void deleteData(String id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ash", "root", "yele2208");
            PreparedStatement ps = con.prepareStatement("delete from students where id=?");
            ps.setString(1, id);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void selectData(String id, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ash", "root", "yele2208");
            PreparedStatement ps = con.prepareStatement("select * from students where id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>Student Information</title><style>");
            out.println("table { border-collapse: collapse; width: 100%; }");
            out.println("th, td { padding: 8px; text-align: left; border-bottom: 1px solid #ddd; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("tr:hover { background-color: #f2f2f2; }");
            out.println("body { background-image: url('https://i.postimg.cc/d3NqY51W/vecteezy-education-is-the-way-to-success-3d-render-illustration-8176789.jpg'); background-size: cover; }");
            out.println("</style></head><body>");
            out.println("<h2>Student Information</h2>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Phone</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getString("id") + "</td><td>" + rs.getString("name") + "</td><td>" + rs.getString("phone") + "</td></tr>");
            }
            out.println("</table>");
            out.println("</body></html>");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
