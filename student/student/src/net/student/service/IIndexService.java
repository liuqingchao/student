package net.student.service;

import java.util.Locale;

import com.alibaba.fastjson.JSONObject;

/**
 * 控制台信息接口类
 * @author liuqingchao
 *
 */
public interface IIndexService {
    /**
     * 获取统计信息
     * @return
     */
    public JSONObject getStatInfo(Locale locale) throws Exception;
    /**
     * 获取学生统计信息
     * @return
     */
    public JSONObject getStudentInfo(String type) throws Exception;
    /**
     * 获取收费项目统计信息
     * @return
     */
    public JSONObject getFeeItemInfo(Locale locale) throws Exception;
}
