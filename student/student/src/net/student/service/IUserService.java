package net.student.service;

import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.QueryResult;

/**
 * 操作员Service接口类
 * @author liuqingchao
 *
 */
public interface IUserService {
    /**
     * 操作员登录校验
     * @param userCode
     * @param password
     * @return
     */
    public User checkLoginUser(String userCode, String password) throws Exception;
    /**
     * 修改密码
     * @param user
     * @param password
     * @return
     */
    public void modifyPassword(User user, String password) throws Exception;
    /**
     * 根据代码获取操作员
     * @param userCode
     * @return
     */
    public User getUserByCode(String userCode) throws Exception;
    /**
     * 查询操作员
     * @param querier
     * @return
     */
    public QueryResult<User> queryUsers(JqGridQuerier<User, Integer> querier) throws Exception;
    
    /**
     * 保存操作员
     * @param user
     * @throws Exception
     */
    public void saveUser(User user) throws Exception;
    /**
     * 修改操作员
     * @param user
     * @throws Exception
     */
    public void updateUser(User user) throws Exception;
    /**
     * 删除操作员
     * @param ids
     * @throws Exception
     */
    public void delUser(String ids) throws Exception;
}
