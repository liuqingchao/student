package net.student.response;

import com.alibaba.fastjson.JSONObject;

public class JsonResult {
    private boolean success;
    private JSONObject info;
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public JSONObject getInfo() {
        return info;
    }
    public void setInfo(JSONObject info) {
        this.info = info;
    }
}
