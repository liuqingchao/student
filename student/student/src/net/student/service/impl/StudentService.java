package net.student.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.Department;
import net.student.model.Student;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.QueryResult;
import net.student.service.IStudentService;

/**
 * 学生Service实现类
 * 
 * @author liuqingchao
 *
 */
@Service
public class StudentService implements IStudentService {
	@Autowired
	private Dao<Student, String> studentDao;
	@Autowired
	private Dao<Department, Integer> departmentDao;

	@Override
	public Student checkLoginStudent(String sid, String idNum) throws Exception {
		Student student = studentDao.queryForId(sid);
		if (student == null) {
			return null;
		}
		if (!idNum.equals(student.getIdCardNum())) {
			return null;
		}
		return student;
	}

	@Override
	public Student getStudentBySId(String sid) throws Exception {
		return studentDao.queryForId(sid);
	}

	@Override
	public QueryResult<Student> queryStudents(JqGridQuerier<Student, String> querier, boolean limit) throws Exception {
		Long page = querier.getPage();
		Long rows = querier.getRows();
		Long start = (page - 1) * rows;
		QueryBuilder<Student, String> queryBuilder = studentDao.queryBuilder();
		Where<Student, String> where = queryBuilder.where();
		if (StringUtils.isNotBlank(querier.getSidx()) && StringUtils.isNotBlank(querier.getSord())) {
			if (querier.getSord().equals(JqGridQuerier.ASC)) {
				queryBuilder.orderBy(querier.getSidx(), true);
			} else {
				queryBuilder.orderBy(querier.getSidx(), false);
			}
		} else {
			queryBuilder.orderBy("createddate", false);
		}
		int count = querier.transferQueryCondition(where);
		if (count == 0) {
			where.raw("1=1");
		}
		QueryResult<Student> queryResult = new QueryResult<Student>();
		long totalRecord = studentDao.countOf(queryBuilder.setCountOf(true).prepare());
		long totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1;
		queryResult.setTotal(totalPage);
		List<Student> list = null;
		if (limit) {
			list = studentDao.query(queryBuilder.setCountOf(false).limit(rows).offset(start).prepare());
		} else {
			list = studentDao.query(queryBuilder.setCountOf(false).prepare());
		}
		queryResult.setRecords(totalRecord);
		queryResult.setRows(list);
		queryResult.setPage(page);
		return queryResult;
	}

	@Override
	public JsonResult importStudents(InputStream is, User user, HttpSession session) throws Exception {
		JsonResult result = new JsonResult();
		JSONObject jsonObject  = new JSONObject();
		JSONArray errorArray = new JSONArray();
		JSONArray updateArray = new JSONArray();
		final List<Student> students = new ArrayList<Student>();
		List<Department> departments = departmentDao.queryForAll();
		HSSFWorkbook wb = new HSSFWorkbook(is);
		int sheetCount = wb.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			HSSFSheet sh = wb.getSheetAt(i);
			int rowCount = sh.getLastRowNum();
			for (int j = 1; j < rowCount + 1; j++) {
				HSSFRow row = sh.getRow(j);
				if(row == null) {
					continue;
				}
				Student student = new Student();
				HSSFCell cell0 = row.getCell(0);
				HSSFCell cell1 = row.getCell(1);
				HSSFCell cell2 = row.getCell(2);
				HSSFCell cell3 = row.getCell(3);
				if (cell0 == null || cell1 == null || cell2 == null || cell3 == null) {
					errorArray.add("第" + (j + 1) + "行数据不完整");
					continue;
				}
				if (cell0.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					student.setStudentId(StringUtils.trim(BigDecimal.valueOf(cell0.getNumericCellValue()).toPlainString()));
				} else {
					student.setStudentId(StringUtils.trim(cell0.getStringCellValue()));
				}
				student.setName(StringUtils.trim(row.getCell(1).getStringCellValue()));
				if (cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					student.setIdCardNum(StringUtils.trim(BigDecimal.valueOf(cell2.getNumericCellValue()).toPlainString()));
				} else {
					student.setIdCardNum(StringUtils.trim(cell2.getStringCellValue()));
				}
				String departmentName = cell3.getStringCellValue();
				String statusName = row.getCell(4) == null ? null : row.getCell(4).getStringCellValue();
				if (StringUtils.isBlank(student.getStudentId()) || StringUtils.isBlank(student.getIdCardNum())
						|| StringUtils.isBlank(student.getName()) || StringUtils.isBlank(departmentName)) {
					errorArray.add("第" + (j + 1) + "行数据不完整");
					continue;
				}
				for (Department department : departments) {
					if (department.getDepartmentName().equals(departmentName)) {
						student.setDepartment(department);
						break;
					}
				}
				if (student.getDepartment() == null) {
					errorArray.add("第" + (j + 1) + "行第4列部门名称未找到:"+departmentName);
					continue;
				}
				student.setStatus(this.getStatus(statusName));
				if (student.getStatus() == null) {
					errorArray.add("第" + (j + 1) + "行第5列状态无效:"+statusName);
					continue;
				}
				students.add(student);
			}
		}
		wb.close();
		is.close();
		Date now = new Date();
		if (students.isEmpty()) {
			result.setSuccess(false);
			jsonObject.put("msg", "excel文件数据不合法");
		} else {
			List<Student> oldStudentList = studentDao.queryForAll();
			int newCount = 0;
			for (Student student : students) {
				int index = oldStudentList.indexOf(student);
				if (index != -1) {
					Student oldOne = oldStudentList.get(index);
					student.setCreatedDate(oldOne.getCreatedDate());
					student.setLastUpdatedDate(now);
					student.setRemark(oldOne.getRemark());
					updateArray.add(student.getStudentId());
				} else {
					student.setCreatedDate(now);
					newCount++;
				}
			}
			if (updateArray.size() > 0) {
				session.setAttribute(Constants.SESSION_IMPORT_STUDENT_NAME, students);
				result.setSuccess(false);
			} else {
				TransactionManager.callInTransaction(studentDao.getConnectionSource(), new Callable<Void>() {
		            public Void call() throws Exception {
		            	for (Student student : students) {
		            		studentDao.create(student);
		        		}
		                return null;
		            }
		        });
				result.setSuccess(true);
				jsonObject.put("msg", "新增学生信息" + newCount + "条");
			}
		}
		jsonObject.put("errors", errorArray);
		jsonObject.put("confirms", updateArray);
		result.setInfo(jsonObject);
		return result;
	}

	@Override
	public void saveStudent(Student student) throws Exception {
		long count = studentDao.queryRawValue("select count(1) from student where studentid=?", student.getStudentId());
		if (count > 0) {
			throw new CustomerException("student.message.duplicateId");
		}
		student.setCreatedDate(new Date());
		studentDao.create(student);
	}

	@Override
	public void updateStudent(Student student) throws Exception {
		Student oldStudent = studentDao.queryForId(student.getStudentId());
		student.setCreatedDate(oldStudent.getCreatedDate());
		student.setLastUpdatedDate(new Date());
		studentDao.update(student);
	}
	
	private Integer getStatus(String statusName) {
		if (StringUtils.isBlank(statusName)) {
			return 0;
		}
		if(statusName.equals("在校")) {
			return 0;
		}
		if(statusName.equals("休学")) {
			return 1;
		}
		if(statusName.equals("退学")) {
			return 2;
		}
		if(statusName.equals("毕业")) {
			return 3;
		}
		return null;
	}

	@Override
	public JsonResult importStudents(final List<Student> students) throws Exception {
	    JsonResult result = new JsonResult();
	    List<Student> oldStudentList = studentDao.queryForAll();
        int newCount = 0,updateCount = 0;
        Date now = new Date();
        for (Student student : students) {
            int index = oldStudentList.indexOf(student);
            if (index != -1) {
                Student oldOne = oldStudentList.get(index);
                student.setCreatedDate(oldOne.getCreatedDate());
                student.setLastUpdatedDate(now);
                student.setRemark(oldOne.getRemark());
                updateCount++;
            } else {
                student.setCreatedDate(now);
                newCount++;
            }
        }
        TransactionManager.callInTransaction(studentDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
                for (Student student : students) {
                    studentDao.createOrUpdate(student);
                }
                return null;
            }
        });
        result.setSuccess(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "新增学生信息" + newCount + "条, 更新学生信息" + updateCount + "条");
        result.setInfo(jsonObject);
		return result;
	}

    @Override
    public void updateStudentStatus(final String status, String studentIds, String departmentId, String year) throws Exception {
        List<Student> list = null;
        QueryBuilder<Student, String> query = studentDao.queryBuilder();
        Where<Student, String> where = query.where();
        where.raw("1=1");
        if (StringUtils.isNotBlank(departmentId)) {
        	where.and().eq("departmentid", Integer.valueOf(departmentId));
        }
        if (StringUtils.isNotBlank(year)) {
            where.and().raw(" substr(studentid,0,5)='"+year+"'");
        }
        if (StringUtils.isNotBlank(studentIds)) {
        	where.and().in("studentid", studentIds.split(","));
        }
        list = studentDao.query(query.setCountOf(false).prepare());
        if (list == null) {
        	return;
        }
        final List<Student> students = list;
        final Date now = new Date();
        TransactionManager.callInTransaction(studentDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
                for (Student student : students) {
                	if (!student.getStatus().toString().equals(status)) {
                		 student.setStatus(Integer.valueOf(status));
                		 student.setLastUpdatedDate(now);
                         studentDao.update(student);
                	}
                }
                return null;
            }
        });
    }
}
