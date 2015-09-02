package net.student.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.student.model.Student;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.QueryResult;

/**
 * 学生Service接口类
 * @author liuqingchao
 *
 */
public interface IStudentService {
    /**
     * 学生登录校验
     * @param sid
     * @param idNum
     * @return
     */
    public Student checkLoginStudent(String sid, String idNum) throws Exception;
    /**
     * 根据学号获取学生
     * @param sid
     * @return
     */
    public Student getStudentBySId(String sid) throws Exception;
    /**
     * 查询学生
     * @param querier
     * @return
     */
    public QueryResult<Student> queryStudents(JqGridQuerier<Student, String> querier) throws Exception;
    
    /**
     * 导入学生信息
     * @param is
     * @throws Exception
     */
    public JsonResult importStudents(InputStream is, User user, HttpSession session) throws Exception;
    /**
     * 确认导入学生信息
     * @param is
     * @throws Exception
     */
    public JsonResult importStudents(List<Student> students) throws Exception;
    /**
     * 保存学生信息
     * @param student
     * @throws Exception
     */
    public void saveStudent(Student student) throws Exception;
    /**
     * 修改学生信息
     * @param student
     * @throws Exception
     */
    public void updateStudent(Student student) throws Exception;
}
