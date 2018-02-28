package init;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import system.user.model.DictionaryModel;
import utils.Constants;



/**
 * 初始化的servlet
 * @author 李昌鹏
 *
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/** 
	 * 初始化的方法
	 *@param 
	 */
	public void init(ServletConfig config) throws ServletException {
		 ResourceBundle prop = ResourceBundle.getBundle("prop");
		 Constants.SELECT_MYSELF=prop.getString("SELECT_MYSELF");
		 Constants.SELECT_CLASS=prop.getString("SELECT_CLASS");
		 Constants.SELECT_ALL=prop.getString("SELECT_ALL");
		 Constants.PAGE_NUMBER=Integer.valueOf(prop.getString("PAGE_NUMBER"));
		 Constants.ROWS_NUMBER=Integer.valueOf(prop.getString("ROWS_NUMBER"));
		 Constants.NOW_CLASS_Y=Integer.valueOf(prop.getString("NOW_CLASS_Y"));
		 Constants.NOW_CLASS_N=Integer.valueOf(prop.getString("NOW_CLASS_N"));
		 Constants.DELETE_Y=Integer.valueOf(prop.getString("DELETE_Y"));
		 Constants.DELETE_N=Integer.valueOf(prop.getString("DELETE_N"));
		 Constants.EFFECT_Y=Integer.valueOf(prop.getString("EFFECT_Y"));
		 Constants.EFFECT_N=Integer.valueOf(prop.getString("EFFECT_N"));
		 Constants.LEAVE_SP=prop.getString("LEAVE_SP");
		 Constants.LEAVE_BH=prop.getString("LEAVE_BH");
		 Constants.LEAVE_JSTG=prop.getString("LEAVE_JSTG");
		 Constants.LEAVE_JWTG=prop.getString("LEAVE_JWTG");
		 Constants.GRADE_F=prop.getString("GRADE_F");
		 Constants.GRADE_E=prop.getString("GRADE_E");
		 Constants.GRADE_D=prop.getString("GRADE_D");
		 Constants.GRADE_C=prop.getString("GRADE_C");
		 Constants.GRADE_B=prop.getString("GRADE_B");
		 Constants.GRADE_A=prop.getString("GRADE_A");
		 selectDic();
	}
	
	public void selectDic() {
		String sql = "select * from dictionary";
		List<DictionaryModel> dictionaryList= this.getJdbcTemplate().query(sql, new DictionaryRowMapper());
		//把字典表中的放到常量类中
		for (DictionaryModel dictionaryModel : dictionaryList) {
			if("kq".equals(dictionaryModel.getPcode())) {
				Constants.KQ_LIST.add(dictionaryModel);
				continue;
			}
			if("qj".equals(dictionaryModel.getPcode())) {
				Constants.QJ_LIST.add(dictionaryModel);
			}
			if("zy".equals(dictionaryModel.getPcode())) {
				Constants.ZY_LIST.add(dictionaryModel);
			}
		}
	}
     
    public JdbcTemplate getJdbcTemplate() {  
    	ResourceBundle jdbc = ResourceBundle.getBundle("jdbc");
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(jdbc.getString("jdbc.driver"));
        dataSource.setUrl(jdbc.getString("jdbc.url"));
        dataSource.setUsername(jdbc.getString("jdbc.user"));
        dataSource.setPassword(jdbc.getString("jdbc.password"));
        return new JdbcTemplate(dataSource);  
    }  
	
	class DictionaryRowMapper implements RowMapper<DictionaryModel>{
		/**
		 * rs:结果集.
		 * rowNum:行号
		 */
		@Override
		public DictionaryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			DictionaryModel dictionary=new DictionaryModel();
			dictionary.setId(rs.getInt("id"));
			dictionary.setName(rs.getString("name"));
			dictionary.setCode(rs.getString("code"));
			dictionary.setDescr(rs.getString("descr"));
			dictionary.setType(rs.getString("type"));
			dictionary.setPcode(rs.getString("pcode"));
			return dictionary;
		}
		
	}

	
}
