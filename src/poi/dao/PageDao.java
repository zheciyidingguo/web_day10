package poi.dao;

import poi.po.Student;

import java.util.List;
import java.util.Map;

public interface PageDao {
    List<Student> query(String sql);

    int querySum(String LikeName);

    List<String> Stu();
}
