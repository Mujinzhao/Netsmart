package core.dbcontrol;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class MySessionFactory {
	private SessionFactory sessionFactory = null;
	
	



	/**
	 * @class:SessionUtils
	 * @method:setSessionFactory
	 * @param sessionFactory
	 * @describe Spring向Dao注入SessionFactory的Set方法
	 * @return_type:void
	 * @exception:
	 * @author:zhanglei
	 * @date:2012-8-7下午05:40:07
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	/**
	 * @class:SessionUtils
	 * @method:getSessionFactory
	 * @param
	 * @describe 获取注入的sessinFactory的方法
	 * @return_type:SessionFactory
	 * @exception:
	 * @author:zhanglei
	 * @date:2012-8-7下午05:46:03
	 */
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	

		/**
		 * @class:MySessionFactory
		 * @method:openSession
		 * @param 
		 * @describe 开启session的方法
		 * @return_type:Session
		 * @exception:
		 * @author:zhanglei
		 * @date:2012-8-27下午03:41:27
		 */
	public Session openSession() {
		return getSessionFactory().openSession();
	}
	
	
		/**
		 * @class:MySessionFactory
		 * @method:closeSession
		 * @param session
		 * @describe 关闭session的方法
		 * @return_type:void
		 * @exception:
		 * @author:zhanglei
		 * @date:2012-8-27下午03:42:50
		 */
	public void closeSession(Session session) throws Exception {
//		SuccessOrException sucOrExp = new SuccessOrException();
		try {
			if(session != null){
				session.clear();
				session.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
//			sucOrExp.setExpCode(Constants.ERROR_SESSION_CLOSE);
//			sucOrExp.setExpMsg(Constants.getExpMsgByCode(Constants.ERROR_SESSION_CLOSE));
			throw e;
		} 
		
	}
	



	
	 
	
	
}
