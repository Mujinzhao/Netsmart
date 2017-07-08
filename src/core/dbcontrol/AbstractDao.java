package core.dbcontrol;


import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;


public abstract class AbstractDao {
	
	private MySessionFactory  mySessionFactory;
	
	

		/**
		 * @class:AbstractDao
		 * @method:getMySessionFactory
		 * @param 
		 * @describe 获取sessionFactory的方法
		 * @return_type:MySessionFactory
		 * @exception:
		 * @author:zhanglei
		 * @date:2012-8-21上午09:28:01
		 */
	public MySessionFactory getMySessionFactory() {
		return mySessionFactory;
	}




		/**
		 * @class:AbstractDao
		 * @method:setMySessionFactory
		 * @param mySessionFactory
		 * @describe 并不是注入dao而是注入sessionFactory,因为service层可能也需要调用session，为了保持session只从一个地方产生
		 * @return_type:void
		 * @exception:
		 * @author:zhanglei
		 * @date:2012-8-21上午09:15:33
		 */
	public void setMySessionFactory(MySessionFactory mySessionFactory) {
		this.mySessionFactory = mySessionFactory;
	}





	/**
	 * @class:AbstractDao
	 * @method:getSession
	 * @param
	 * @describe 获取提交的session的抽象方法，由其子类去具体实现
	 * @return_type:Session
	 * @exception:
	 * @author:zhanglei
	 * @date:2012-8-7下午05:49:01
	 */
	protected abstract Session openSession();
	
	
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:commit
	 * @param 
	 * @describe 用户手动开启事物时调用，事物的回滚
	 * @return_type:void
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-10上午09:37:07
	 */
	protected abstract void closeSession(Session session) throws Exception;
	
	
	

	/**
	 * @class:AbstractDao
	 * @method:selectHql
	 * @param session,hql
	 * @describe 通过传递的HQL语句查询数据库并返回List类型的数据（需要手动开启事物，传入在Service层手动创建的Session）
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:54:11
	 */
	public abstract List<?> selectHql(Session session,String hql) throws Exception;
	
	/**
	 * @class:AbstractDao
	 * @method:selectHql
	 * @param session,hql,paramMap
	 * @describe 通过传递的HQL语句和参数集合paramMap查询数据库并返回List类型的数据（需要手动开启事物，传入在Service层手动创建的Session）
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:54:06
	 */
	public abstract List<?> selectHql(Session session,String hql,Map<String,?> paramMap) throws Exception;
	
	

	/**
	 * @class:AbstractDao
	 * @method:updateHqlAuto
	 * @param hql
	 * @describe 通过传递的HQL语句更新数据库的语句（自动提交，不需要手动开启事物）
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:59:11
	 */
	public abstract Integer updateHqlAuto(String hql) throws Exception;
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:updateHqlAuto
	 * @param hql,paramMap
	 * @describe 通过传递的HQL语句更新数据库的语句（自动提交,不需要手动开启事物）
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-9上午09:45:06
	 */
	public abstract Integer updateHqlAuto(String hql,Map<String,?> paramMap) throws Exception;

	
	
	/**
	 * @class:AbstractDao
	 * @method:updateHql
	 * @param session,hql
	 * @describe 通过传入的session和hql执行更新（手动开启事物时使用的方法，所以需要传入自己手动开启的Session,AutoDao因为是Spring管理事物，无法自己手动管理，所以未实现）
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-9上午09:47:38
	 */
	public abstract Integer updateHql(Session session ,String hql) throws Exception;
	
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:updateHql
	 * @param session,hql,paramMap
	 * @describe 通过传入的session,hql和paramMap和执行更新（手动开启事物时使用的方法，所以需要传入自己手动开启的Session,AutoDao因为是Spring管理事物，无法自己手动管理，所以未实现）
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-9上午09:50:46
	 */
	public abstract Integer updateHql(Session session ,String hql,Map<String,?> paramMap) throws Exception;
	
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:deleteHqlAuto
	 * @param hql
	 * @describe 通过传递的HQL语句更新数据库的语句（自动提交，不需要手动开启事物）
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:59:11
	 */
	public abstract Integer deleteHqlAuto(String hql) throws Exception;
	
	
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:deleteHqlAuto
	 * @param hql,paramMap
	 * @describe 通过传递的HQL语句更新数据库的语句（自动提交,不需要手动开启事物）
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-9上午09:45:06
	 */
	public abstract Integer deleteHqlAuto(String hql,Map<String,?> paramMap) throws Exception;

	
	
	/**
	 * @class:AbstractDao
	 * @method:deleteHql
	 * @param session,hql
	 * @describe 通过传入的session和hql执行更新（手动开启事物时使用的方法，所以需要传入自己手动开启的Session,AutoDao因为是Spring管理事物，无法自己手动管理，所以未实现）
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-9上午09:47:38
	 */
	public abstract Integer deleteHql(Session session ,String hql) throws Exception;
	
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:deleteHql
	 * @param session,hql,paramMap
	 * @describe 通过传入的session,hql和paramMap和执行更新（手动开启事物时使用的方法，所以需要传入自己手动开启的Session,AutoDao因为是Spring管理事物，无法自己手动管理，所以未实现）
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-9上午09:50:46
	 */
	public abstract Integer deleteHql(Session session ,String hql,Map<String,?> paramMap) throws Exception;
	
	
	
	
		/**
		 * @class:AbstractDao
		 * @method:saveAuto
		 * @param obj
		 * @describe 保存传入的对象，相当于insert操作
		 * @return_type:void
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-10上午09:27:32
		 */
	public abstract boolean insertObjAuto(Object obj) throws Exception;
	
	
	
	
		/**
		 * @class:AbstractDao
		 * @method:saveAuto
		 * @param obj
		 * @describe 保存传入的需要修改的对象，相当于update操作
		 * @return_type:void
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-10上午09:27:32
		 */
	public abstract void updateObjAuto(Object obj) throws Exception;
	
	
	

	
	
	
	
		/**
		 * @class:AbstractDao
		 * @method:deleteAuto
		 * @param 
		 * @describe 删除传入的对象，相当于delete操作
		 * @return_type:void
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-10上午09:29:56
		 */
	public abstract void deleteObjAuto(Object obj) throws Exception;
	
	
	
		/**
		 * @class:AbstractDao
		 * @method:saveAuto
		 * @param obj
		 * @describe 保存传入的对象，相当于insert操作，手动操作，需要传入手动开启的Session
		 * @return_type:void
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-10上午09:27:32
		 */
	public abstract Serializable insertObj(Session session,Object obj) throws Exception;




		/**
		 * @class:AbstractDao
		 * @method:saveAuto
		 * @param obj
		 * @describe 保存传入的需要修改的对象，相当于update操作，手动操作，需要传入手动开启的Session
		 * @return_type:void
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-10上午09:27:32
		 */
	public abstract void updateObj(Session session,Object obj) throws Exception;








		/**
		 * @class:AbstractDao
		 * @method:deleteAuto
		 * @param 
		 * @describe 删除传入的对象，相当于delete操作，手动操作，需要传入手动开启的Session
		 * @return_type:void
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-10上午09:29:56
		 */
	public abstract void deleteObj(Session session,Object obj) throws Exception;
	

		/**
		 * @class:AbstractDao
		 * @method:createPagingQuery
		 * @param hql,currentPage:当前页  pageSize:页面的条数的多少
		 * @describe 根据传入的hql和当前页和页面大小来查询数据库
		 * @return_type:List<?>
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-13上午10:06:09
		 */
	public abstract List<?> selectHqlPagingAuto(String hql,int currentPage,int pageSize) throws Exception;
	
	
	
	public abstract List<?> selectSqlPagingAuto(String hql, int currentPage, int pageSize) throws Exception ;

	
	public abstract List<?> selectSqlPagingAuto(String sql, Map<String, ?> paramMap, int currentPage, int pageSize) throws Exception ;

	/**
	 * @class:AbstractDao
	 * @method:createPagingQuery
	 * @param session,hql,currentPage:当前页  pageSize:页面的条数的多少
	 * @describe 根据传入的hql和当前页和页面大小来查询数据库
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-13上午10:06:09
	 * */
	public abstract List<?> selectHqlPaging(Session session,String hql,int currentPage,int pageSize) throws Exception;
	
	
	
		/**
		 * @class:AbstractDao
		 * @method:selectSql
		 * @param sql
		 * @describe 根据传入的sql语句查询出Obj对象的List
		 * @return_type:List<?>
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-14下午02:32:39
		 */
	public abstract List<?> selectSqlAuto(String sql) throws Exception;
	
	
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:selectSql
	 * @param sql
	 * @describe 根据传入的sql语句和paramMap查询出Obj对象的List
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-14下午02:32:39
	 */
	public abstract List<?> selectSqlAuto(String sql,Map<String,?> paramMap) throws Exception;
	
	
		/**
		 * @class:AbstractDao
		 * @method:selectSql
		 * @param 
		 * @describe 根据传入的sql语句和Class对象查询出相应bean的List，当然要先建立映射
		 * @return_type:List<?>
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-14下午02:34:25
		 */
	public abstract List<?> selectSqlAuto(String sql,Class<?> clazz) throws Exception;
	
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:selectSql
	 * @param 
	 * @describe sql语句,paramMap和Class对象查询出相应bean的List，当然要先建立映射
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-14下午02:34:25
	 */	
	
	public abstract List<?> selectSqlAuto(String sql,Map<String,?> paramMap,Class<?> clazz) throws Exception;
	
	/**
	 * sql语句,paramMap和Class对象查询出相应bean的List，当然要先建立映射
	 * @param sql
	 * @param paramMap
	 * @param currentPage
	 * @param pageSize currentPage:当前页  pageSize:页面的条数的多少
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> selectSqlPagingAuto(String sql, Map<String, ?> paramMap,int currentPage, int pageSize, Class clazz) throws Exception ;
		
	
	
	
	
	
	
		/**
		 * @class:AbstractDao
		 * @method:selectSql
		 * @param session sql
		 * @describe 根据传入的session和sql语句查询出Obj对象的List
		 * @return_type:List<?>
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-16上午10:18:00
		 */
	public abstract List<?> selectSql(Session session,String sql) throws Exception;
	
	
	
	
		/**
		 * @class:AbstractDao
		 * @method:selectSql
		 * @param session sql paramMap
		 * @describe 根据传入的session和sql语句查询出Obj对象的List
		 * @return_type:List<?>
		 * @exception:
		 * @author:zhanglei
		 * @date:2012-8-27下午03:46:57
		 */
	public abstract List<?> selectSql(Session session,String sql,Map<String,?> paramMap) throws Exception;
	
	
	
	
	
	
	
	
	
	
		/**
		 * @class:AbstractDao
		 * @method:selectSql
		 * @param session sql clazz
		 * @describe 根据传入的session和sql语句和Class对象查询出相应bean的List，当然要先建立映射
		 * @return_type:List<?>
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-16上午10:18:46
		 */
	public abstract List<?> selectSql(Session session,String sql,Class<?> clazz) throws Exception;
	
	
	
	
	
		/**
		 * @class:AbstractDao
		 * @method:selectSql
		 * @param session sql paramMap clazz
		 * @describe 根据传入的session,sql语句,paramMap和Class对象查询出相应bean的List，当然要先建立映射
		 * @return_type:List<?>
		 * @exception:
		 * @author:zhanglei
		 * @date:2012-8-27下午03:47:39
		 */
	public abstract List<?> selectSql(Session session,String sql,Map<String,?> paramMap,Class<?> clazz) throws Exception;
	
	
		/**
		 * @class:AbstractDao
		 * @method:updateSql
		 * @param session sql
		 * @describe 传入手动开启的session和sql语句进行更新操作
		 * @return_type:Integer
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-14下午02:37:17
		 */
	public abstract Integer updateSql(Session session ,String sql) throws Exception;
	
	
		/**
		 * @class:AbstractDao
		 * @method:updateSqlAuto
		 * @param sql
		 * @describe 传入sql语句，进行更新操作
		 * @return_type:Integer
		 * @exception:
		 * @author:zhanglei
		 * @throws Exception 
		 * @date:2012-8-14下午02:38:20
		 */
	public abstract Integer updateSqlAuto(String sql) throws Exception;
	
	/**
	 * @class:AbstractDao
	 * @method:updateSql
	 * @param session sql
	 * @describe 传入手动开启的session和sql语句进行插入操作
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-14下午02:37:17
	 */	
	public abstract Integer insertSql(Session session ,String sql) throws Exception;
	
	/**
	 * @class:AbstractDao
	 * @method:updateSqlAuto
	 * @param sql
	 * @describe 传入sql语句，进行插入操作
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-14下午02:38:20
	 */
	public abstract Integer insertSqlAuto(String sql) throws Exception;
	
	/**
	 * @class:AbstractDao
	 * @method:updateSql
	 * @param session sql
	 * @describe 传入手动开启的session和sql语句进行删除操作
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-14下午02:37:17
	 */
	public abstract Integer deleteSql(Session session ,String sql) throws Exception;
	
	/**
	 * @class:AbstractDao
	 * @method:updateSqlAuto
	 * @param sql
	 * @describe 传入sql语句，进行删除操作
	 * @return_type:Integer
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-14下午02:38:20
	 */
	public abstract Integer deleteSqlAuto(String sql) throws Exception;
	
	
	
	

	
	/**
	 * @class:AbstractDao
	 * @method:selectHqlAuto
	 * @param hql
	 * @describe 通过传递的HQL语句查询数据库并返回List类型的数据（不需要手动开启事物）
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:54:11
	 */
	public abstract List<?> selectHqlAuto(String hql) throws Exception;
	
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:selectHqlAuto
	 * @param hql,paramMap
	 * @describe 通过传递的HQL语句和参数集合paramMap查询数据库并返回List类型的数据（不需要手动开启事物）
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:54:06
	 */
	public abstract List<?> selectHqlAuto(String hql,Map<String,?> paramMap) throws Exception;
	
	/**
	 * @class:AbstractDao
	 * @method:selectHqlAuto
	 * @param hql,paramMap
	 * @describe 通过传递的HQL语句和参数集合paramMap查询数据库并返回List类型的数据（不需要手动开启事物）
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:54:06
	 */
	 public abstract List<?> selectHqlAutopage(String hql,Map<String,?> paramMap,int currentPage, int pageSize) throws Exception;
	

	/**
	 * @class:AbstractDao
	 * @method:enterValuesToQuery
	 * @param query,paramMap
	 * @describe 遍历paramMap,把值放入Query中
	 * @return_type:void
	 * @exception:
	 * @author:zhanglei
	 * @date:2012-8-9上午09:55:24
	 */
	protected void enterValuesToQuery(Query query, Map<String,?>  paramMap){
		if (paramMap != null) {
			Set<String> set = paramMap.keySet(); // 返回此映射中包含的键的 set 视图
			Iterator<String> ii = set.iterator(); // 用set创建迭代器对象
			while (ii.hasNext()) { // 循环迭代 每次取出来一个键 利用这个键去查找Map中对应的值
				String key = ii.next();
				Object value = paramMap.get(key); // 根据键查找对应的值
//				System.out.println("key:::::"+key+";;;;value::::::::"+value);
				if(value instanceof List<?>){//判断value是否是list类型的一个实例
					query.setParameterList(key, (List<?>)value);
					}else{
					String hql = query.getQueryString();
					if(hql.indexOf(":"+key) > 0) {
						query.setParameter(key, value);
					}
				}

			}
		}
	}
	
	/**
	 * @class:AbstractDao
	 * @method:enterValuesToQuery
	 * @param query,paramMap
	 * @describe 遍历paramMap,把值放入Query中
	 * @return_type:void
	 * @exception:
	 * @author:xiangyuns.suow
	 * @date:2012-8-9上午09:55:24
	 */
	protected PreparedStatement changeSqlparam(Session session, 
			String sql, Map<String,?>  paramMap) throws Exception{
		List<Object> templist = new ArrayList<Object>();
		PreparedStatement preStmt = null;
		if (paramMap != null) {
			int i =1;
			String pstr = ":\\w+\\s?";
			Pattern r = Pattern.compile(pstr);
		    Matcher rm = r.matcher(sql);
		    
		    while(rm.find()){
		    	String tmprm = rm.group().trim().replace(":", "");
		    	Object value = paramMap.get(tmprm);
		    	sql = sql.replaceAll(":"+tmprm, "?");
		    	templist.add(value);
		    }
//			Set<String> set = paramMap.keySet(); // 返回此映射中包含的键的 set 视图
//			Iterator<String> ii = set.iterator(); // 用set创建迭代器对象
//			while (ii.hasNext()) {
//				String key = ii.next();
//				
//				Object value = paramMap.get(key); // 根据键查找对应的值
//				if(sql.indexOf(":"+key)>0){
//				   sql = sql.replaceAll(":"+key, "?");
//				   templist.add(value);
//				}
//			}
		    preStmt = (PreparedStatement)session.connection().prepareStatement(sql);
			for (Object object:templist ) { // 循环迭代 每次取出来一个键 利用这个键去查找Map中对应的值
					System.out.println("value::::::::"+object);
					if(object instanceof List){
						List listobj = (List)object;
						for (Object object2 : listobj) {
							preStmt.setObject(i, object2);
							preStmt.executeUpdate();
						}
					}else if (object instanceof String[]){
						String[] objStr = (String[])object;
						for (String str : objStr) {
							preStmt.setObject(i, str);
							preStmt.executeUpdate();
						}
					}else{
						preStmt.setObject(i, object);
						preStmt.executeUpdate();
					}
					i++;
			}
		}else{
			preStmt = (PreparedStatement)session.connection().prepareStatement(sql);
		}
		return preStmt;
	}
	/**
	 * @class:AbstractDao
	 * @method:enterValuesToQuery
	 * @param query,paramMap
	 * @describe 遍历paramMap,把值放入Query中
	 * @return_type:void
	 * @exception:
	 * @author:xiangyun.suo
	 * @date:2012-8-9上午09:55:24
	 */
	protected PreparedStatement changeSqlparamObj(Session session, 
			String sql, Map<String,?>  paramMap) throws Exception{
		List<Object> templist = new ArrayList<Object>();
		PreparedStatement preStmt = null;
		if (paramMap != null) {
			int i =1;
			String pstr = ":\\w+\\s?";
			Pattern r = Pattern.compile(pstr);
			System.out.println("444444444   "+sql);
			
		    Matcher rm = r.matcher(sql);
		    String tmprm ="";
		    Object value = "";
		    if(rm.find()){
		    	    tmprm = rm.group().trim().replace(":", "");
//		    	    System.out.println("第一个值："+tmprm); 
		    	    value = paramMap.get(tmprm);
		    	    // 有序
			    	templist.add(value);
		    	   while(rm.find()){
				    	tmprm = rm.group().trim().replace(":", "");
//				    	System.out.println(tmprm); 
				    	
				        value = paramMap.get(tmprm);
//				    	if(value.toString().indexOf(",")>=0)
//				    	{
//				    		value = value.toString().replace("'", "");
//				    	}
				    
				    //	sql = sql.replaceAll(":"+tmprm, "?");
				    	// 有序
				    	templist.add(value);
				    }
		     sql =  rm.replaceAll("?");
		    }
		    
		    System.out.println("5555555   "+sql);
		 
		  
		    preStmt= (PreparedStatement)session.connection().prepareStatement(sql);
			for (Object object:templist ) { // 循环迭代 每次取出来一个键 利用这个键去查找Map中对应的值
				System.out.println("value::::::::"+object);
				    if(object instanceof List){
							preStmt.setObject(i, (List)object);
					}else if (object instanceof String[]){
							preStmt.setObject(i, (String[])object);
					}
					else{
						    preStmt.setObject(i, object);
					}
					i++;
		     }
		}
		return preStmt;
	}
	/**
	 * 使用jdbc方式调用数据库函数
	 * @return
	 */
	public abstract List<?> execDBJDBCFunction(Session session,String parent_id,String user_id) throws Exception;




	/**
	 * 使用hibernate方式调用数据库函数
	 * @return
	 */
	public abstract List<?> execHqlFunction();




	public abstract List<?> execDBJDBCFunctionAuto(String parentId, String userId)throws Exception;




	/**
	 * @class:AbstractDao
	 * @method:createPagingQuery
	 * @param hql,currentPage:当前页  pageSize:页面的条数的多少
	 * @describe 根据传入的hql,paramMap和当前页和页面大小来查询数据库
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-13上午10:06:09
	 */
	public abstract List<?> selectHqlPagingAuto(String hql, Map<String, ?> paramMap,int currentPage, int pageSize) throws Exception; 
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:createPagingQuery
	 * @param session,hql,currentPage:当前页  pageSize:页面的条数的多少
	 * @describe 根据传入的hql,paramMap和当前页和页面大小来查询数据库
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-13上午10:06:09
	 * */
	public abstract List<?> selectHqlPaging(Session session,String hql, Map<String, ?> paramMap,int currentPage, int pageSize) throws Exception; 

	
	
	
	/**
	 * @class:AbstractDao
	 * @method:selectHqlCount
	 * @param session,hql
	 * @describe 通过传递的HQL语句查询数据库并返回List类型的数据（需要手动开启事物，传入在Service层手动创建的Session）
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:54:11
	 */
	public abstract Integer selectHqlCount(Session session,String hql) throws Exception;
	
	/**
	 * @class:AbstractDao
	 * @method:selectHql
	 * @param session,hql,paramMap
	 * @describe 通过传递的HQL语句和参数集合paramMap查询数据库并返回List类型的数据（需要手动开启事物，传入在Service层手动创建的Session）
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:54:06
	 */
	public abstract Integer selectHqlCount(Session session,String hql,Map<String,?> paramMap) throws Exception;
	
	/**
	 * @class:AbstractDao
	 * @method:selectHqlAuto
	 * @param hql
	 * @describe 通过传递的HQL语句查询数据库并返回List类型的数据（不需要手动开启事物）
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:54:11
	 */
	public abstract Integer selectHqlCountAuto(String hql) throws Exception;
	
	
	
	
	/**
	 * @class:AbstractDao
	 * @method:selectHqlAuto
	 * @param hql,paramMap
	 * @describe 通过传递的HQL语句和参数集合paramMap查询数据库并返回List类型的数据（不需要手动开启事物）
	 * @return_type:List<?>
	 * @exception:
	 * @author:zhanglei
	 * @throws Exception 
	 * @date:2012-8-7下午05:54:06
	 */
	public abstract Integer selectHqlCountAuto(String hql,Map<String,?> paramMap) throws Exception;
     
	
	 public abstract  List<Map<String, Object>> queryMapList(String sql)throws Exception;
	 public abstract  List<Map<String, Object>> queryMapList(String sql,Map<String, ?> paramMap)throws Exception;
	 
	 public abstract  List<Map<String, Object>> queryMapList(String sql,Map<String, ?> paramMap,int currentPage,int pageSize,String[] queryname)throws Exception;
	 
	 public abstract  Object selectHqlObjAuto(String hql) throws Exception;
	 public abstract  Object selectHqlObjAuto(String hql, Map<String, ?> paramMap) throws Exception;
	 public abstract  Object selectSqlObjAuto(String hql) throws Exception; 
	 public abstract  Object selectSqlObjAuto(String hql, Map<String, ?> paramMap) throws Exception; 
	 public abstract  Integer exeSqlQuery(String sql) throws Exception;
	 public abstract  Integer exeSqlQuery(String sql,Map<String, ?> paramMap) throws Exception;
	 public abstract  Integer exeSql(String sql,Map<String, ?> paramMap) throws Exception;
	 public abstract  Integer exeSql(Session session,String sql,Map<String, ?> paramMap) throws Exception;
	 
}
