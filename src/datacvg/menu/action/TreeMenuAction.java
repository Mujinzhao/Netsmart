package datacvg.menu.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.core.tag.util.SystemConstant;

import core.BasePagingQueryAction;
import core.util.MyURLDecoder;
import core.util.WDWUtil;
import datacvg.menu.service.TreeMenuService;

/**
 * 针对页面功能菜单树操作
 * 
 * @author admin
 * 
 */
public class TreeMenuAction extends BasePagingQueryAction
{

	TreeMenuService treemenuService;

	public TreeMenuService getTreemenuService()
	{
		return treemenuService;
	}

	public void setTreemenuService(TreeMenuService treemenuService)
	{
		this.treemenuService = treemenuService;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String menutreePage()
	{
		return SUCCESS;
	}

	/**
	 * 获取左侧菜单树节点信息
	 * @throws UnsupportedEncodingException 
	 */
	public void loadTreeInfo() throws UnsupportedEncodingException
	{

		String loadsql = MyURLDecoder.decode(request.getParameter("loadsql"), "UTF-8");
		String sqlkey = MyURLDecoder.decode(request.getParameter("sqlkey"), "UTF-8");
		String treeflag = MyURLDecoder.decode(request.getParameter("treeflag"), "UTF-8");
		String[] treefalgstr = treeflag.split("[|]");

		if ("true".equalsIgnoreCase(loadsql) && sqlkey != null)
		{

			String sql = SystemConstant.get(sqlkey);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("searchflag", treefalgstr[0]);
			map.put("searchflag1", treefalgstr[1]);
			System.out.println("i am here " +sql);
			List<Map<String, Object>> listm = treemenuService.getListMap(sql, map);
			System.out.println(JSONArray.fromObject(listm).toString());
			write(JSONArray.fromObject(listm).toString());
		}
	}

	/**
	 * 保存菜单功能树节点信息
	 * @throws UnsupportedEncodingException 
	 */
	public void savemenuTree() throws UnsupportedEncodingException
	{
		String sqlkey = "insert into  resource_res ( " + "res_pkid,res_id,res_clname,res_parentid,res_rootid,"
				+ "res_showtype,res_url,res_value," + "res_selkeyword,res_description,res_opentype" + ") values ("
				+ ":id,:res_id,:res_clname,:res_parentid, " + ":res_rootid,:res_showtype,:res_url,:res_value,"
				+ ":res_selkeyword,:res_description,:res_opentype" + ") ";
		// 执行SQL 所需参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", WDWUtil.getSeqNextval());
		paramMap.put("res_id", getStringParameter("resId"));
		paramMap.put("res_clname", getStringParameter("resClname"));
		paramMap.put("res_parentid", getStringParameter("resParentid"));
		paramMap.put("res_rootid", getStringParameter("resId"));
		paramMap.put("res_showtype", getStringParameter("resPtype"));
		paramMap.put("res_url", getStringParameter("resUrl"));
		paramMap.put("res_value", getStringParameter("resValue"));

		paramMap.put("res_selkeyword", getStringParameter("resSelkeyword"));
		paramMap.put("res_description", getStringParameter("resDescription"));
		paramMap.put("res_opentype", getStringParameter("sltopentype"));

		try
		{
			// System.out.println(paramMap);
			treemenuService.optionSQL(sqlkey, paramMap);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 删除菜单功能树节点信息
	 * @throws UnsupportedEncodingException 
	 */
	public void delmenuTree() throws UnsupportedEncodingException
	{
		String sqlkey = "delete from resource_res  r where r.res_id in (:res_id)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("res_id", getStringParameter("resId"));
		try
		{
			treemenuService.optionSQL(sqlkey, paramMap);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 修改菜单功能树节点信息
	 * @throws UnsupportedEncodingException 
	 */
	public void modfiymenuTree() throws UnsupportedEncodingException
	{

		String sqlkey = "update resource_res set  res_id=:res_id,res_clname=:res_clname,res_parentid=:res_parentid,"
				+ "res_rootid=:res_rootid,res_showtype=:res_showtype,res_url=:res_url,res_value=:res_value,"
				+ "res_selkeyword=:res_selkeyword,res_description=:res_description,res_opentype=:res_opentype"
				+ " where res_pkid =:res_pkid";
		// 执行SQL 所需参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("res_pkid", getStringParameter("res_pkid"));
		paramMap.put("res_id", getStringParameter("resId"));
		paramMap.put("res_clname", getStringParameter("resClname"));
		paramMap.put("res_parentid", getStringParameter("resParentid"));
		paramMap.put("res_rootid", getStringParameter("resId"));
		paramMap.put("res_showtype", getStringParameter("resPtype"));
		paramMap.put("res_url", getStringParameter("resUrl"));
		paramMap.put("res_value", getStringParameter("resValue"));

		paramMap.put("res_selkeyword", getStringParameter("resSelkeyword"));
		paramMap.put("res_description", getStringParameter("resDescription"));
		paramMap.put("res_opentype", getStringParameter("sltopentype"));
		// System.out.println(sqlkey);
		try
		{
			// System.out.println(paramMap);
			treemenuService.optionSQL(sqlkey, paramMap);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
