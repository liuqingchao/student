package net.student.service;

import com.alibaba.fastjson.JSONObject;

import net.student.model.Department;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.QueryResult;

/**
 * 部门Service接口类
 * @author liuqingchao
 *
 */
public interface IDepartmentService {
    /**
     * 查询部门
     * @param querier
     * @return
     */
    public QueryResult<Department> queryDepartments(JqGridQuerier<Department, Integer> querier) throws Exception;
    
    /**
     * 保存部门
     * @param department
     * @throws Exception
     */
    public void saveDepartment(Department department) throws Exception;
    /**
     * 修改部门
     * @param department
     * @throws Exception
     */
    public void updateDepartment(Department department) throws Exception;
    /**
     * 删除部门
     * @param ids
     * @throws Exception
     */
    public void delDepartment(String ids) throws Exception;
    /**
     * 部门下拉框
     * @param user
     * @return
     */
    public JSONObject selectDepartments(User user) throws Exception;
}
