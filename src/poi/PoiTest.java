package poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import poi.dao.impl.Dao;
import poi.po.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PoiTest {
    public static void queryStudent() {
        //查询学生数
        Dao dao = new Dao();
        List<Student> list = dao.query("select * from student");
        //建立控制对象
        Workbook workbook = new HSSFWorkbook();
        //建立表
        Sheet sheet = workbook.createSheet("one");
        //添加学生信息
        for (int i = 0; i <= list.size(); i++) {
            if (i == 0) {
                build(sheet, i, "姓名", "年龄", "性别");
            } else {
                build(sheet, i, list.get(i - 1).getName(), list.get(i - 1).getAge() + "", list.get(i - 1).getSex());
            }
        }
        //写出表
        OutputStream os = null;
        try {
            os = new FileOutputStream("F:/aa/student.xls");
            workbook.write(os);
            System.out.println("导入完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static void build(Sheet sheet, int i, String str1, String str2, String str3) {
        //建立行
        Row row = sheet.createRow(i);
        //建立列
        Cell cell1 = row.createCell(0);
        Cell cell2 = row.createCell(1);
        Cell cell3 = row.createCell(2);
        //添加数据
        cell1.setCellValue(str1);
        cell2.setCellValue(str2);
        cell3.setCellValue(str3);
    }

    public static void readXls(InputStream is) {
        Workbook workbook = null;
        try {
            workbook = new HSSFWorkbook(is);
            Sheet sheet = workbook.getSheet("one");
            int count = sheet.getPhysicalNumberOfRows();
            System.out.println(count);
            List<Student> list = new ArrayList<>();
            Dao dao = new Dao();
            for (int i = 1; i < count; i++) {
                Row row = sheet.getRow(i);
                String name = row.getCell(0).getStringCellValue();
                int age = Integer.parseInt(row.getCell(1).getStringCellValue());
                String sex = row.getCell(2).getStringCellValue();
                String sql = "insert into students values(0,'" + name + "'," + age + ",'" + sex + "')";
                System.out.println(sql);
                dao.insert(sql);
            }
            System.out.println("完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
