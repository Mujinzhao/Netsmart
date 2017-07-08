package datacvg.gather.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;


import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;

public class GatherTaskService {
	
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
	public GatherTaskService(){};
	
	 /**
	   * 
	   * @param sql
	   * @param paramMap
	   * @return
	   */
	  public Integer OpGathertaskAndSession(String sql,Map<String, String> paramMap,Session session)throws Exception{
		     Integer executenum = dao.exeSql(session,sql, paramMap);
			 return executenum;
	  }
	  public Integer OpGathertask(String sql,Map<String, String> paramMap)throws Exception{
		  
		  System.out.println("third   third  "+sql);
		     Integer executenum = dao.exeSql(sql, paramMap);
			 return executenum;
	  }
	  public Integer delGatherTask(String sql,Map<String, String> paramMap)throws Exception{
		     Integer executenum = dao.exeSql(sql, paramMap);
			 return executenum;
	  }
	  /**
	   * 通过SQL获取信息
	   * @param sql
	   * @return
	   * @throws Exception
	   */
	  public List<Map<String, Object>>  getToSqlByInfo(String sql,Map<String, String> paramMap )throws Exception{
		  List<Map<String, Object>> infomap = dao.queryMapList(sql,paramMap);
		  return infomap;
	  }
	  
}
