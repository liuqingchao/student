package net.student.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.student.constants.CustomerException;
import net.student.model.Department;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.QueryResult;
import net.student.service.IDepartmentService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
/**
 * 部门Service实现类
 * @author 果冻
 *
 */
@Service
public class DepartmentService implements IDepartmentService {
	@Autowired
	private Dao<Department, Integer> departmentDao;
	
	@Override
	public QueryResult<Department> queryDepartments(JqGridQuerier<Department, Integer> querier) throws Exception {
		Long page = querier.getPage();
		Long rows = querier.getRows();
		Long start = (page - 1) * rows;
		QueryBuilder<Department, Integer> queryBuilder = departmentDao.queryBuilder();
		Where<Department, Integer> where = queryBuilder.where();
		if (StringUtils.isNotBlank(querier.getSidx()) && StringUtils.isNotBlank(querier.getSord())) {
			if (querier.getSord().equals(JqGridQuerier.ASC)) {
				queryBuilder.orderBy(querier.getSidx(), true);
			} else {
				queryBuilder.orderBy(querier.getSidx(), false);
			}
		} else {
			queryBuilder.orderBy("departmentId", false);
		}
		int count = querier.transferQueryCondition(where);
		if (count ==0) {
			where.raw("1=1");
		}
		
		QueryResult<Department> queryResult = new QueryResult<Department>();
		long totalRecord = departmentDao.countOf(queryBuilder.setCountOf(true).prepare());
		long totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1;
		queryResult.setTotal(totalPage);
		List<Department> list = departmentDao.query(queryBuilder.setCountOf(false).limit(rows).offset(start).prepare());
		queryResult.setRecords(totalRecord);
		queryResult.setRows(list);
		queryResult.setPage(page);
		return queryResult;
	}

	@Override
	public void saveDepartment(Department department) throws Exception {
		long count = departmentDao.queryRawValue("select count(1) from department where departmentid=?", department.getDepartmentId().toString());
		if (count > 0) {
			throw new CustomerException("department.message.duplicateId");
		}
		count = departmentDao.queryRawValue("select count(1) from department where departmentname=?", department.getDepartmentName());
		if (count > 0) {
			throw new CustomerException("department.message.duplicateName");
		}
		departmentDao.create(department);
	}

	@Override
	public void updateDepartment(Department department) throws Exception {
		long count = departmentDao.queryRawValue("select count(1) from department where departmentname=? and departmentid<>?",
				department.getDepartmentName(), department.getDepartmentId().toString());
		if (count > 0) {
			throw new CustomerException("department.message.duplicateName");
		}
		departmentDao.update(department);
	}

	@Override
	public void delDepartment(String ids) throws Exception {
		String[] departmentIds = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (String departmentId : departmentIds) {
			long count = departmentDao.queryRawValue("select count(1) from student where departmentid = " + departmentId);
			if (count == 0) {
				count = departmentDao.queryRawValue("select count(1) from userdepartment where departmentid = " + departmentId);
				if (count > 0) {
					continue;
				}
			} else {
				continue;
			}
			idList.add(Integer.parseInt(departmentId));
		}
		if (idList.size() == 0) {
			throw new CustomerException("commmon.message.constraintException", "");
		}
		PreparedQuery<Department> criteria = departmentDao.queryBuilder().where().in("departmentId", idList).prepare();
		List<Department> list = departmentDao.query(criteria);
		departmentDao.delete(list);
	}

    @Override
    public JSONObject selectDepartments(User user) throws Exception {
        JSONObject selectResult = new JSONObject();
        List<Department> list = null;
        if (user.getUserType() == User.USERTYPE_ADMIN) {
            list = departmentDao.queryForAll();
        } else {
//            list = departmentDao.
        }
        if (list != null) {
            for (Department department : list) {
                selectResult.put(department.getDepartmentId().toString(), department.getDepartmentName());
            }
        }
        return selectResult;
    }
}
