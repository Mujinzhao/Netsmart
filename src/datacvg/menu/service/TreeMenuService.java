package datacvg.menu.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import core.dbcontrol.BaseDao;
import core.dbcontrol.MySessionFactory;

public class TreeMenuService {
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
	
	public TreeMenuService(){}
	
	/**
	 * 数据查询
	 * @param sql
	 * @param map
	 * @return
	 */
    public List<Map<String,Object>> getListMap(String sql,Map<String,Object> map){
		try {
			return dao.queryMapList(sql, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		 
	}
    /**
     * 执行SQL 带session 手动提交
     * @param sql
     * @param paramMap
     * @param session
     * @return
     * @throws Exception
     */
    public Integer optionSqlBySession(String sql,Map<String, Object> paramMap,Session session) throws Exception{
    	 Integer executenum = dao.exeSql(session,sql, paramMap);
		 return executenum;
    }
	    
    /**
     * 执行SQL 自动提交
     * @param sql
     * @param paramMap
     * @param session
     * @return
     * @throws Exception
     */
    public Integer optionSQL(String sql,Map<String, Object> paramMap) throws Exception{
    	 Integer executenum = dao.exeSql(sql, paramMap);
		 return executenum;
    }
}
