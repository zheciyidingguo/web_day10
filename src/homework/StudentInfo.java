package homework;

import com.alibaba.fastjson.JSONArray;
import poi.dao.impl.Dao;
import poi.po.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/studentInfo")
@MultipartConfig
public class StudentInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String mark = req.getParameter("mark");
        List<Student> list = null;
        switch (mark) {
            case "add":
                list = add(req);
                break;
            case "del":
                list = del(req);
                break;
            case "up":
                list = update(req);
                break;
            case "query":
                list = query(req);
                break;
        }
        String json = JSONArray.toJSONString(list);
        // System.out.println(json);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    public List<Student> add(HttpServletRequest req) {
        String name = req.getParameter("name");
        String sex = req.getParameter("xb");
        String age = req.getParameter("age");
        String sql = "insert into student values(0,'" + name + "'," + age + ",'" + sex + "')";
        System.out.println(sql);
        new Dao().insert(sql);
        return queryStu();
    }

    public List<Student> del(HttpServletRequest req) {
        String test = req.getParameter("test");
        String sql = "delete from student where ";
        if (test.equals("男") || test.equals("女")) {
            sql += "sex='" + test + "'";
        } else if (isNumeric(test)) {
            sql += "age=" + test;
        } else {
            sql += "name='" + test + "'";
        }
        new Dao().insert(sql);
        return queryStu();
    }

    public List<Student> update(HttpServletRequest req) {
        String test = req.getParameter("test");
        String sql = "update student set ";
        String src = test.split(":")[0];
        String str = test.split(":")[1];
        if (src.equals("男") || src.equals("女")) {
            sql += "sex='" + str + "' where sex='" + src + "'";
        } else if (isNumeric(src)) {
            sql += "age=" + str + " where age=" + src;
        } else {
            sql += "name='" + str + "' where name ='" + src + "'";
        }
        new Dao().insert(sql);
        return queryStu();
    }

    public List<Student> query(HttpServletRequest req) {
        String test = req.getParameter("test");
        List<Student> list = null;
        if (test.isEmpty()) {
            list = queryStu();
        } else if (test.contains("-")) {
            String oAge = test.split(":")[0];
            String nAge = test.split(":")[1];
            list = new Dao().query("select * from student where age between " + oAge + " and " + nAge);
        } else {
            list = new Dao().query("select * from student where name like '%" + test + "%'");
        }
        return list;
    }

    public List<Student> queryStu() {
        return new Dao().query("select * from student");
    }

    public boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
