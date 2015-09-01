package net.student.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import net.student.service.IIndexService;
/**
 * 控制台信息实现类
 * @author liuqingchao
 *
 */
@Service
public class IndexService implements IIndexService {

    
    @Override
    public JSONObject getStatInfo() {
        JSONObject json = new JSONObject();
//        DetachedCriteria criteria = DetachedCriteria.forClass(Partner.class);
//        int count = partnerDao.queryEntitiesCounts(criteria);
//        Date now = new Date();
//        criteria.add(Restrictions.ge("createdDate", DateUtils.addDays(now, -7)));
//        int increment = partnerDao.queryEntitiesCounts(criteria);
//        json.put("partners", count);
//        json.put("partnerIncre", Math.round(NumberUtils.toFloat(increment + "") / count * 100));
//        
//        criteria = DetachedCriteria.forClass(Server.class);
//        count = serverDao.queryEntitiesCounts(criteria);
//        criteria.add(Restrictions.ge("createdDate", DateUtils.addDays(now, -7)));
//        increment = serverDao.queryEntitiesCounts(criteria);
//        json.put("servers", count);
//        json.put("serverIncre", Math.round(NumberUtils.toFloat(increment + "") / count * 100));
//        
//        criteria = DetachedCriteria.forClass(Ne.class);
//        count = neDao.queryEntitiesCounts(criteria);
//        criteria.add(Restrictions.ge("createdDate", DateUtils.addDays(now, -7)));
//        increment = neDao.queryEntitiesCounts(criteria);
//        json.put("nes", count);
//        json.put("neIncre", Math.round(NumberUtils.toFloat(increment + "") / count * 100));
        return json;
    }

}
