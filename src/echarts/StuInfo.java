package echarts;

import com.alibaba.fastjson.JSONObject;
import poi.dao.impl.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/stuInfo")
public class StuInfo extends HttpServlet {
    public Map<String, Integer> ma() {
        List<String> list = new Dao().Stu();
        Map<String, Integer> map = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String year = list.get(i).substring(0, list.get(i).indexOf("-"));
            list1.add(year);
        }
        for (int i = 0; i < list1.size(); i++) {
            String year = list1.get(i);
            int time = 0;
            for (int j = 0; j < list1.size(); j++) {
                if (year.equals(list1.get(j))) {
                    time++;
                }
            }
            map.put(year, time);
        }
        return map;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Integer> map = ma();
        String json = JSONObject.toJSONString(map);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
