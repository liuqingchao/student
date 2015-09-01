package net.student.service;

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
    public JSONObject getStatInfo();
}
