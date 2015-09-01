package net.student.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.j256.ormlite.stmt.QueryBuilder;

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
	public QueryResult<Student> queryStudents(JqGridQuerier<Student, String> querier) throws Exception {
		Long page = querier.getPage();
		Long rows = querier.getRows();
		Long start = (page - 1) * rows;
		QueryBuilder<Student, String> queryBuilder = studentDao.queryBuilder();
		if (StringUtils.isNotBlank(querier.getSidx()) && StringUtils.isNotBlank(querier.getSord())) {
			if (querier.getSord().equals(JqGridQuerier.ASC)) {
				queryBuilder.orderBy(querier.getSidx(), true);
			} else {
				queryBuilder.orderBy(querier.getSidx(), false);
			}
		} else {
			queryBuilder.orderBy("studentId", false);
		}
		querier.transferQueryCondition(queryBuilder.where());
		
		QueryResult<Student> queryResult = new QueryResult<Student>();
		long totalRecord = studentDao.countOf(queryBuilder.setCountOf(true).prepare());
		long totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1;
		queryResult.setTotal(totalPage);
		List<Student> list = studentDao.query(queryBuilder.setCountOf(false).limit(rows).offset(start).prepare());
		queryResult.setRecords(totalRecord);
		queryResult.setRows(list);
		queryResult.setPage(page);
		return queryResult;
	}

	@Override
	public JsonResult importStudents(InputStream is, User user) throws Exception {
		JsonResult result = new JsonResult();
		JSONObject jsonObject  = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<Student> students = new ArrayList<Student>();
		HSSFWorkbook wb = new HSSFWorkbook(is);
		int sheetCount = wb.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			HSSFSheet sh = wb.getSheetAt(i);
			int rowCount = sh.getLastRowNum();
			for (int j = 0; j < rowCount; j++) {
				HSSFRow row = sh.getRow(j);
				Student student = new Student();
				if (row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					student.setStudentId(String.valueOf(Math.floor(row.getCell(0).getNumericCellValue())));
				} else {
					student.setStudentId(row.getCell(0).getRichStringCellValue().getString());
				}
				if (row.getCell(1).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					student.setIdCardNum(String.valueOf(Math.floor(row.getCell(1).getNumericCellValue())));
				} else {
					student.setIdCardNum(row.getCell(1).getRichStringCellValue().getString());
				}
				student.setName(row.getCell(2).getRichStringCellValue().getString());
				String sexStr = row.getCell(3).getRichStringCellValue().getString();
				if (StringUtils.isBlank(sexStr)) {
					student.setSex(null);
				} else if (sexStr.equals("男")) {
					student.setSex(1);
				} else if (sexStr.equals("女")) {
					student.setSex(0);
				} else {
					student.setSex(2);
				}
				if (StringUtils.isBlank(student.getStudentId()) || StringUtils.isBlank(student.getIdCardNum())
						|| StringUtils.isBlank(student.getName()) || student.getSex() == null) {
					jsonArray.add("第" + (i + 1) + "行数据不完整");
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
			List<Student> list = studentDao.queryForAll();
			int newCount = 0, updateCount = 0;
			try {
//				studentDao.setAutoCommit(studentDao.getConnectionSource().getReadWriteConnection(), false);
				for (Student student : students) {
					int index = list.indexOf(student);
					if (index != -1) {
						Student oldOne = list.get(index);
						student.setCreatedDate(oldOne.getCreatedDate());
						student.setCreatedBy(oldOne.getCreatedBy());
						student.setLastUpdatedBy(user);
						student.setLastUpdatedDate(now);
						student.setRemark(oldOne.getRemark());
						updateCount++;
					} else {
						student.setCreatedBy(user);
						student.setCreatedDate(now);
						newCount++;
					}
					studentDao.createOrUpdate(student);
				}
//				studentDao.commit(studentDao.getConnectionSource().getReadWriteConnection());
				result.setSuccess(true);
				jsonObject.put("msg", "新增" + newCount + "个， 更新" + updateCount + "个");
			} catch (Exception e) {
				studentDao.rollBack(studentDao.getConnectionSource().getReadWriteConnection());
				throw e;
			} finally {
//				studentDao.setAutoCommit(studentDao.getConnectionSource().getReadWriteConnection(), true);
			}
		}
		result.setInfo(jsonObject);
		return result;
	}
}
