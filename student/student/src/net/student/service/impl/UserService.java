package net.student.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import net.student.constants.CustomerException;
import net.student.model.Department;
import net.student.model.FeeItem;
import net.student.model.User;
import net.student.model.UserDepartment;
import net.student.model.UserFeeItem;
import net.student.request.JqGridQuerier;
import net.student.response.QueryResult;
import net.student.service.IUserService;

/**
 * 操作员Service实现类
 * 
 * @author liuqingchao
 *
 */
@Service
public class UserService implements IUserService {
	@Autowired
	private Dao<User, Integer> userDao;
	@Autowired
    private Dao<UserFeeItem, Integer> userFeeItemDao;
	@Autowired
    private Dao<UserDepartment, Integer> userDepartmentDao;

	@Override
	public User checkLoginUser(String userCode, String password) throws Exception {
		PreparedQuery<User> criteria = userDao.queryBuilder().where().eq("userCode", userCode).and().eq("status", 1)
				.prepare();
		User user = userDao.queryForFirst(criteria);
		if (user == null) {
			return null;
		}
		if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
			return null;
		}
		return user;
	}

	@Override
	public void modifyPassword(User user, String password) throws Exception {
		user.setPassword(DigestUtils.md5Hex(password));
		user.setLastUpdateDate(new Date());
		userDao.update(user);
	}

	@Override
	public User getUserByCode(String userCode) throws Exception {
		PreparedQuery<User> criteria = userDao.queryBuilder().where().eq("userCode", userCode).prepare();
		return userDao.queryForFirst(criteria);
	}

	@Override
	public QueryResult<User> queryUsers(JqGridQuerier<User, Integer> querier) throws Exception {
		Long page = querier.getPage();
		Long rows = querier.getRows();
		Long start = (page - 1) * rows;
		QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();
		Where<User, Integer> where = queryBuilder.where();
		if (StringUtils.isNotBlank(querier.getSidx()) && StringUtils.isNotBlank(querier.getSord())) {
			if (querier.getSord().equals(JqGridQuerier.ASC)) {
				queryBuilder.orderBy(querier.getSidx(), true);
			} else {
				queryBuilder.orderBy(querier.getSidx(), false);
			}
		} else {
			queryBuilder.orderBy("userId", false);
		}
		int count = querier.transferQueryCondition(where);
		if (count == 0) {
			where.eq("userType", 1);
		} else {
			where.and().eq("userType", 1);
		}
		
		QueryResult<User> queryResult = new QueryResult<User>();
		long totalRecord = userDao.countOf(queryBuilder.setCountOf(true).prepare());
		long totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1;
		queryResult.setTotal(totalPage);
		List<User> list = userDao.query(queryBuilder.setCountOf(false).limit(rows).offset(start).prepare());
		for (User user : list) {
			user.setPassword(null);
			ForeignCollection<UserDepartment> userDepartments = user.getUserDepartments();
			CloseableIterator<UserDepartment> iterator = userDepartments.closeableIterator();
			String[] departments = new String[0];
			while(iterator.hasNext()) {
			    UserDepartment userDepartment = iterator.next();
			    departments = (String[]) ArrayUtils.add(departments, userDepartment.getDepartment().getDepartmentId().toString());
			}
			user.setDepartments(StringUtils.join(departments, ","));
			iterator.close();
			ForeignCollection<UserFeeItem> userFeeItems = user.getUserFeeItems();
            CloseableIterator<UserFeeItem> iterator2 = userFeeItems.closeableIterator();
            String[] feeitems = new String[0];
            while(iterator2.hasNext()) {
                UserFeeItem userFeeItem = iterator2.next();
                feeitems = (String[]) ArrayUtils.add(feeitems, userFeeItem.getFeeItem().getItemId());
            }
            user.setFeeitems(StringUtils.join(feeitems, ","));
            iterator2.close();
		}
		queryResult.setRecords(totalRecord);
		queryResult.setRows(list);
		queryResult.setPage(page);
		return queryResult;
	}

	@Override
	public void saveUser(final User user) throws Exception {
		PreparedQuery<User> criteria = userDao.queryBuilder().setCountOf(true).where()
				.eq("userCode", user.getUserCode()).prepare();
		long count = userDao.countOf(criteria);
		if (count > 0) {
			throw new CustomerException("user.message.duplicateCode");
		}
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setUserType(1);
		user.setCreatedDate(new Date());
		final List<UserDepartment> departmentList = new ArrayList<UserDepartment>();
		final List<UserFeeItem> feeItemList = new ArrayList<UserFeeItem>();
		if (StringUtils.isNotBlank(user.getDepartments())) {
            String[] departmentIds = user.getDepartments().split(",");
            for(String departmentId : departmentIds) {
                UserDepartment userDepartment = new UserDepartment();
                userDepartment.setUser(user);
                Department department = new Department();
                department.setDepartmentId(Integer.valueOf(departmentId));
                userDepartment.setDepartment(department);
                departmentList.add(userDepartment);
            }
        }
        if (StringUtils.isNotBlank(user.getFeeitems())) {
            String[] feeItemIds = user.getFeeitems().split(",");
            for(String feeItemId : feeItemIds) {
                UserFeeItem userFeeItem = new UserFeeItem();
                userFeeItem.setUser(user);
                FeeItem feeItem = new FeeItem();
                feeItem.setItemId(feeItemId);
                userFeeItem.setFeeItem(feeItem);
                feeItemList.add(userFeeItem);
            }
        }
        TransactionManager.callInTransaction(userDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
                    userDao.create(user);
                    for (UserDepartment userDepartment : departmentList) {
                        userDepartmentDao.create(userDepartment);
                    }
                    for (UserFeeItem userFeeItem : feeItemList) {
                        userFeeItemDao.create(userFeeItem);
                    }
                    return null;
            }
        });
	}

	@Override
	public void updateUser(final User user) throws Exception {
		PreparedQuery<User> criteria = userDao.queryBuilder().setCountOf(true).where()
				.eq("userCode", user.getUserCode()).and().ne("userId", user.getUserId()).prepare();
		long count = userDao.countOf(criteria);
		if (count > 0) {
			throw new CustomerException("user.message.duplicateCode");
		}
		User oldUser = userDao.queryForId(user.getUserId());
		user.setPassword(oldUser.getPassword());
		user.setCreatedDate(oldUser.getCreatedDate());
		user.setUserType(oldUser.getUserType());
		user.setLastUpdateDate(new Date());
		final ForeignCollection<UserDepartment> userDepartments = oldUser.getUserDepartments();
		final ForeignCollection<UserFeeItem> userFeeItems = oldUser.getUserFeeItems();
		final List<UserDepartment> departmentList = new ArrayList<UserDepartment>();
        final List<UserFeeItem> feeItemList = new ArrayList<UserFeeItem>();
        final List<UserDepartment> delDepartmentList = new ArrayList<UserDepartment>();
        final List<UserFeeItem> delFeeItemList = new ArrayList<UserFeeItem>();
        List<UserDepartment> oldDepartmentList = new ArrayList<UserDepartment>();
        List<UserFeeItem> oldFeeItemList = new ArrayList<UserFeeItem>();
        if (StringUtils.isNotBlank(user.getDepartments())) {
            String[] departmentIds = user.getDepartments().split(",");
            for(String departmentId : departmentIds) {
                UserDepartment userDepartment = new UserDepartment();
                userDepartment.setUser(user);
                Department department = new Department();
                department.setDepartmentId(Integer.valueOf(departmentId));
                userDepartment.setDepartment(department);
                if(!userDepartments.contains(userDepartment)) {
                	departmentList.add(userDepartment);
                } else {
                	oldDepartmentList.add(userDepartment);
                }
            }
        }
        CloseableIterator<UserDepartment> iterator = userDepartments.closeableIterator();
		while(iterator.hasNext()) {
		    UserDepartment userDepartment = iterator.next();
		    if (!oldDepartmentList.contains(userDepartment)) {
		    	delDepartmentList.add(userDepartment);
		    }
		}
		iterator.close();
        if (StringUtils.isNotBlank(user.getFeeitems())) {
            String[] feeItemIds = user.getFeeitems().split(",");
            for(String feeItemId : feeItemIds) {
                UserFeeItem userFeeItem = new UserFeeItem();
                userFeeItem.setUser(user);
                FeeItem feeItem = new FeeItem();
                feeItem.setItemId(feeItemId);
                userFeeItem.setFeeItem(feeItem);
                if (!userFeeItems.contains(userFeeItem)) {
                	feeItemList.add(userFeeItem);
                } else {
                	oldFeeItemList.add(userFeeItem);
                }
            }
        }
        CloseableIterator<UserFeeItem> iterator2 = userFeeItems.closeableIterator();
		while(iterator2.hasNext()) {
			UserFeeItem userFeeItem = iterator2.next();
		    if (!oldFeeItemList.contains(userFeeItem)) {
		    	delFeeItemList.add(userFeeItem);
		    }
		}
		iterator2.close();
        TransactionManager.callInTransaction(userDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
            		userDao.update(user);
                    for (UserDepartment userDepartment : departmentList) {
                        userDepartmentDao.create(userDepartment);
                    }
                    for (UserFeeItem userFeeItem : feeItemList) {
                        userFeeItemDao.create(userFeeItem);
                    }
                    userDepartmentDao.delete(delDepartmentList);
                    userFeeItemDao.delete(delFeeItemList);
                    return null;
            }
        });
	}

	@Override
	public void delUser(String ids) throws Exception {
		String[] partnerIds = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (String partnerId : partnerIds) {
			idList.add(Integer.parseInt(partnerId));
		}
		PreparedQuery<User> criteria = userDao.queryBuilder().where().in("userId", idList).prepare();
		final List<User> list = userDao.query(criteria);
		TransactionManager.callInTransaction(userDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
            	for (User user : list) {
        			userDepartmentDao.delete(user.getUserDepartments());
        			userFeeItemDao.delete(user.getUserFeeItems());
        			userDao.delete(user);
        		}
                return null;
            }
        });
	}
}
