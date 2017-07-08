package datacvg.excel.service;

import java.util.Map;

import org.hibernate.Session;

import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;

public class FileMangerService {
	public BaseDao dao = null;
	private MySessionFactory mySessionFactory;
	public MySessionFactory getMySessionFactory() {
		return mySessionFactory;
	}
	public void setMySessionFactory(MySessionFactory mySessionFactory) {
		this.mySessionFactory = mySessionFactory;
	}
	public BaseDao getDao() {
		return dao;
	}
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
	
	 /**
	   * 
	   * @param sql
	   * @param paramMap
	   * @return
	   */
	  public Integer OparserSql(String sql,Map<String, String> paramMap,Session session) throws Exception{
		     Integer executenum = dao.exeSql(session,sql, paramMap);
			 return executenum;
	  }
	  public Integer OparserSql(String sql,Map<String, String> paramMap) throws Exception{
		     Integer executenum = dao.exeSql(sql, paramMap);
			 return executenum;
	  }
	  public Integer OparserSql(String sql) throws Exception{
		     Integer executenum = dao.exeSqlQuery(sql);
			 return executenum;
	  }
}
