package net.student.config;

import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import net.student.model.Department;
import net.student.model.FeeItem;
import net.student.model.OffLinePaidLog;
import net.student.model.PaidLog;
import net.student.model.PayStat;
import net.student.model.Payment;
import net.student.model.Student;
import net.student.model.User;
import net.student.model.UserDepartment;
import net.student.model.UserFeeItem;
import net.student.model.UserLog;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.spring.TableCreator;

@Configuration
@ComponentScan("net.student")
@Import({DataSourceConfig.class, MvcConfig.class})
@EnableScheduling
@EnableWebMvc
public class ApplicationContextConfig {
    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Bean(initMethod = "initialize")
    public TransactionManager txManager() throws SQLException {
        TransactionManager txManager = new TransactionManager();
        txManager.setConnectionSource(dataSourceConfig.dataSource());
        return txManager;
    }
    
    @Bean
    public Dao<User, Integer> userDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), User.class);
    }
    
    @Bean
    public Dao<UserLog, Integer> userLogDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), UserLog.class);
    }
    
    @Bean
    public Dao<Student, String> studentDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), Student.class);
    }
    
    @Bean
    public Dao<Department, Integer> departmentDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), Department.class);
    }
    
    @Bean
    public Dao<FeeItem, String> feeItemDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), FeeItem.class);
    }
    
    @Bean
    public Dao<UserFeeItem, Integer> userFeeItemDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), UserFeeItem.class);
    }
    
    @Bean
    public Dao<UserDepartment, Integer> userDepartmentDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), UserDepartment.class);
    }
    
    @Bean
    public Dao<Payment, Integer> paymentDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), Payment.class);
    }
    
    @Bean
    public Dao<PaidLog, Integer> paidLogDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), PaidLog.class);
    }
    
    @Bean
    public Dao<PayStat, Integer> payStatDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), PayStat.class);
    }
    
    @Bean
    public Dao<OffLinePaidLog, Integer> offLinePaidLogDao() throws SQLException {
        return DaoManager.createDao(dataSourceConfig.dataSource(), OffLinePaidLog.class);
    }
    
    @Bean
    public TableCreator tableCreator() throws SQLException {
//    	TableUtils.dropTable(dataSourceConfig.dataSource(), Payment.class, true);
//    	TableUtils.dropTable(dataSourceConfig.dataSource(), Student.class, true);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), User.class);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), Department.class);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), Student.class);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), FeeItem.class);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), UserDepartment.class);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), UserFeeItem.class);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), Payment.class);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), PaidLog.class);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), PayStat.class);
//        TableUtils.createTableIfNotExists(dataSourceConfig.dataSource(), OffLinePaidLog.class);
        if (userDao().countOf() == 0) {
            User user = new User();
            user.setUserName("管理员");
            user.setUserCode("admin");
            user.setUserType(User.USERTYPE_ADMIN);
            user.setStatus(1);
            user.setPassword(DigestUtils.md5Hex("admin"));
            user.setCreatedDate(new Date());
            user.setUserDesc("默认管理员");
            userDao().create(user);
        }
        return null;
    }
    
    @Bean
    public Producer captchaProducer() {
    	Properties properties = new Properties();
    	properties.put("kaptcha.border", "yes");
    	properties.put("kaptcha.border.color", "105,179,90");
    	properties.put("kaptcha.textproducer.font.color", "blue");
    	properties.put("kaptcha.image.width", "120");
    	properties.put("kaptcha.image.height", "40");
    	properties.put("kaptcha.textproducer.font.size", "30");
    	properties.put("kaptcha.session.key", "kaptchacode");
    	properties.put("kaptcha.textproducer.char.length", "4");
    	properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
    	Config config = new Config(properties);
    	DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
    	defaultKaptcha.setConfig(config);
    	return defaultKaptcha;
    }
}
