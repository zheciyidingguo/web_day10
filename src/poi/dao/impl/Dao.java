package poi.dao.impl;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import poi.dao.PageDao;
import poi.po.Student;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Dao implements PageDao {

    //private static ComboPooledDataSource com = new ComboPooledDataSource("mysql-config");
/*    public static void main(String[] args) {
		ComboPooledDataSource com = new ComboPooledDataSource("mysql-config");
		try {
			Connection connection=com.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from student");
			while (resultSet.next()){
				System.out.println(resultSet.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
    }
}*/
    private Connection getCon() throws Exception {
        Properties pro = new Properties();
        InputStream is = Dao.class.getClassLoader().getResourceAsStream("dbcp_config.properties");
        pro.load(is);
        DataSource ds = BasicDataSourceFactory.createDataSource(pro);
        Connection con = ds.getConnection();
        return con;
    }

    @Override
    public List<Student> query(String sql) {

        List<Student> list = new ArrayList<>();
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            Connection con = getCon();
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Student stu = new Student();
                stu.setName(rs.getString("name"));
                stu.setAge(rs.getInt("age"));
                stu.setSex(rs.getString("sex"));
                list.add(stu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public int querySum(String LikeName) {
        int count = 0;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            Connection con = getCon();
            pre = con.prepareStatement("select count(*) from student where name like ?");
            pre.setString(1, "%" + LikeName + "%");
            rs = pre.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<String> Stu() {
        PreparedStatement pre = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<>();
        try {
            Connection con = getCon();
            pre = con.prepareStatement("select * from student");
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pre.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void insert(String sql) {
        Statement state = null;
        try {
            Connection con = getCon();
            state = con.createStatement();
            state.execute(sql);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
