package servlet;

import jdbc.HomeworkJdbc;
import jdbc.StudentHomeworkJdbc;
import jdbc.StudentJdbc;
import model.Homework;
import model.StudentHomework;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/QueryHomeworkServlet")
public class QueryHomeworkServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("shoudao!!!!!!!!!!!!!!!!!!!!!");
        List<Homework> homework = HomeworkJdbc.selectAll();
        List<StudentHomework>studentHomework = StudentHomeworkJdbc.selectAll();
        int studentsum = StudentJdbc.selectAll().size();
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        for(int i=0;i<homework.size();i++){
            int sum = 0;
            for(int j=0;j<studentHomework.size();j++){
                if(studentHomework.get(j).getHomeworkId()==homework.get(i).getId()){
                    sum++;
                }
            }
            jsonObjects.add(new JSONObject());
            jsonObjects.get(i).put("作业号",homework.get(i).getId());
            jsonObjects.get(i).put("标题",homework.get(i).getHomeworkTitle());
            jsonObjects.get(i).put("已提交",sum+"/"+studentsum);
        }
        resp.setCharacterEncoding("UTF-8");
        //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(String.valueOf(jsonObjects));
        System.out.println(jsonObjects);
    }
}
