package homework;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

@WebServlet("/downPhoto")
@MultipartConfig
public class DownPhoto extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("photo");
        String name = part.getSubmittedFileName();
        String photoText = name.substring(name.length() - 3);
        String photoName = new Date().getTime() + "." + photoText;
        part.write("D:/IDEA/project/web_day10/web/image/" + photoName);
        req.getRequestDispatcher("homework.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
