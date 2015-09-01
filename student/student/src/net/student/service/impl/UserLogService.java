package net.student.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import net.student.model.User;
import net.student.model.UserLog;
import net.student.request.JqGridQuerier;
import net.student.response.QueryResult;
import net.student.service.IUserLogService;
/**
 * 操作日志Service实现类
 * @author liuqingchao
 *
 */
@Service
public class UserLogService implements IUserLogService{
    @Autowired
    private Dao<UserLog, Integer> userLogDao;
    @Autowired
    private Dao<User, Integer> userDao;

    @Override
    @Transactional(readOnly = true)
    public QueryResult<UserLog> queryUserLogs(JqGridQuerier querier) throws Exception {
        Long page = querier.getPage();
        Long rows = querier.getRows();
        Long start = (page -1) * rows;
        QueryBuilder<UserLog, Integer> queryBuilder = userLogDao.queryBuilder();
        Where<UserLog, Integer> where = queryBuilder.where();
        queryBuilder.leftJoin(userDao.queryBuilder());
        if (StringUtils.isNotBlank(querier.getSidx()) && StringUtils.isNotBlank(querier.getSord())) {
            if (querier.getSord().equals(JqGridQuerier.ASC)) {
                queryBuilder.orderBy(querier.getSidx(), true);
            } else {
                queryBuilder.orderBy(querier.getSidx(), false);
            }
        } else {
            queryBuilder.orderBy("userLogId", false);
        }
        querier.transferQueryCondition(where);
//        String groupOp = querier.getGroupOp();
//        if (StringUtils.isNotBlank(groupOp)) {
//            if (groupOp.equals(JqGridQuerier.AND)) {
////                criteria.add(Restrictions.and(querier.getCriterions()));
//            } else if (groupOp.equals(JqGridQuerier.OR)) {
////                criteria.add(Restrictions.or(querier.getCriterions()));
//            }
//        }
        QueryResult<UserLog> queryResult = new QueryResult<UserLog>();
        long totalRecord = userLogDao.countOf(queryBuilder.prepare());
        long totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1;
        queryResult.setTotal(totalPage);
        List<UserLog> list = userLogDao.query(queryBuilder.limit(rows).offset(start).prepare());
        queryResult.setRecords(totalRecord);
        queryResult.setRows(list);
        queryResult.setPage(page);
        return queryResult;
    }

}
