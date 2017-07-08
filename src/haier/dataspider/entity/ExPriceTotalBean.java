package haier.dataspider.entity;
/**
 * 存储价格汇总表格信息
 * @author admin
 *
 */
public class ExPriceTotalBean {
	private String hrid;
	private String hrsheetname;
	private String hrsubittitle;
	private String hrmarket;
	private String hrsteelmills;
	private String hrspecifications;
	private String hrmaterial;
	private String hrweekprice;
	private String hrlastweekprice;
	private String hrapplies;
	private String hrproductname;
	private String hraddres;
	private String hrhighestprice;
	private String hrlowestprice;
	private String hrbrand;
	public String getHrid() {
		return hrid;
	}
	public void setHrid(String hrid) {
		this.hrid = hrid;
	}
	public String getHrsheetname() {
		return hrsheetname;
	}
	public void setHrsheetname(String hrsheetname) {
		this.hrsheetname = hrsheetname;
	}
	public String getHrsubittitle() {
		return hrsubittitle;
	}
	public void setHrsubittitle(String hrsubittitle) {
		this.hrsubittitle = hrsubittitle;
	}
	public String getHrmarket() {
		return hrmarket;
	}
	public void setHrmarket(String hrmarket) {
		this.hrmarket = hrmarket;
	}
	public String getHrsteelmills() {
		return hrsteelmills;
	}
	public void setHrsteelmills(String hrsteelmills) {
		this.hrsteelmills = hrsteelmills;
	}
	public String getHrspecifications() {
		return hrspecifications;
	}
	public void setHrspecifications(String hrspecifications) {
		this.hrspecifications = hrspecifications;
	}
	public String getHrmaterial() {
		return hrmaterial;
	}
	public void setHrmaterial(String hrmaterial) {
		this.hrmaterial = hrmaterial;
	}
	public String getHrweekprice() {
		return hrweekprice;
	}
	public void setHrweekprice(String hrweekprice) {
		this.hrweekprice = hrweekprice;
	}
	public String getHrlastweekprice() {
		return hrlastweekprice;
	}
	public void setHrlastweekprice(String hrlastweekprice) {
		this.hrlastweekprice = hrlastweekprice;
	}
	public String getHrapplies() {
		return hrapplies;
	}
	public void setHrapplies(String hrapplies) {
		this.hrapplies = hrapplies;
	}
	public String getHrproductname() {
		return hrproductname;
	}
	public void setHrproductname(String hrproductname) {
		this.hrproductname = hrproductname;
	}
	public String getHraddres() {
		return hraddres;
	}
	public void setHraddres(String hraddres) {
		this.hraddres = hraddres;
	}
	public String getHrhighestprice() {
		return hrhighestprice;
	}
	public void setHrhighestprice(String hrhighestprice) {
		this.hrhighestprice = hrhighestprice;
	}
	public String getHrlowestprice() {
		return hrlowestprice;
	}
	public void setHrlowestprice(String hrlowestprice) {
		this.hrlowestprice = hrlowestprice;
	}
	public String getHrbrand() {
		return hrbrand;
	}
	public void setHrbrand(String hrbrand) {
		this.hrbrand = hrbrand;
	}
	public ExPriceTotalBean(){}
	public ExPriceTotalBean(Integer flag){
		switch (flag) { 
		    case 1:
		        break;
		    case 2:
		        break;
		    case 3:
		    	
		    case 4:
		        break;
		    case 5:
		    	break;
		    case 6:
		    	break;
		    case 7:
		    	break;
		    default:
		    	
	        break;
	    }
	}
	//1市场	钢厂	规格	材质	本周均价	上周均价	涨跌幅
	public ExPriceTotalBean getExPriceTotalBean(String[] beanarray){
		ExPriceTotalBean expricetotalbean = new ExPriceTotalBean();
		expricetotalbean.setHrmarket(beanarray[0]);
		expricetotalbean.setHrsteelmills(beanarray[1]);
		expricetotalbean.setHrspecifications(beanarray[2]);
		expricetotalbean.setHrmaterial(beanarray[3]);
		expricetotalbean.setHrweekprice(beanarray[4]);
		expricetotalbean.setHrlastweekprice(beanarray[5]);
		expricetotalbean.setHrapplies(beanarray[6]);
		return expricetotalbean;
	}
	//2市场	钢厂	规格	本周均价	上周均价	涨跌幅		
	public ExPriceTotalBean getExPriceTotalBean2(String[] beanarray){
			ExPriceTotalBean expricetotalbean = new ExPriceTotalBean();
			expricetotalbean.setHrmarket(beanarray[0]);
			expricetotalbean.setHrsteelmills(beanarray[1]);
			expricetotalbean.setHrspecifications(beanarray[2]);
			expricetotalbean.setHrweekprice(beanarray[3]);
			expricetotalbean.setHrlastweekprice(beanarray[4]);
			expricetotalbean.setHrapplies(beanarray[5]);
			return expricetotalbean;
	}
	//3 市场	钢厂	规格	材质	最高价格	最低价格	本周均价	上周均价	涨跌幅	
	public ExPriceTotalBean getExPriceTotalBean3(String[] beanarray){
			ExPriceTotalBean expricetotalbean = new ExPriceTotalBean();
			expricetotalbean.setHrmarket(beanarray[0]);
			expricetotalbean.setHrsteelmills(beanarray[1]);
			expricetotalbean.setHrspecifications(beanarray[2]);
			expricetotalbean.setHrmaterial(beanarray[3]);
			expricetotalbean.setHrhighestprice(beanarray[4]);
			expricetotalbean.setHrlowestprice(beanarray[5]);
			expricetotalbean.setHrweekprice(beanarray[6]);
			expricetotalbean.setHrlastweekprice(beanarray[7]);
			expricetotalbean.setHrapplies(beanarray[8]);
			return expricetotalbean;
	}
	//4市场	牌号	产地	本周均价	上周均价	周均价涨跌
	public ExPriceTotalBean getExPriceTotalBean4(String[] beanarray){
		ExPriceTotalBean expricetotalbean = new ExPriceTotalBean();
		expricetotalbean.setHrmarket(beanarray[0]);
		expricetotalbean.setHrbrand(beanarray[1]);
		expricetotalbean.setHraddres(beanarray[2]);
		expricetotalbean.setHrweekprice(beanarray[6]);
		expricetotalbean.setHrlastweekprice(beanarray[7]);
		expricetotalbean.setHrapplies(beanarray[8]);
		return expricetotalbean;
    }
	
	//5厂商	牌号	价格	涨跌	
	public ExPriceTotalBean getExPriceTotalBean5(String[] beanarray){
		ExPriceTotalBean expricetotalbean = new ExPriceTotalBean();
		expricetotalbean.setHrproductname(beanarray[0]);
		expricetotalbean.setHrbrand(beanarray[1]);
		expricetotalbean.setHrweekprice(beanarray[2]);
		expricetotalbean.setHrapplies(beanarray[3]);
		return expricetotalbean;
    }
	//6生产厂家（产地）	牌号	规格	上周末	本周末	备注	
	public ExPriceTotalBean getExPriceTotalBean6(String[] beanarray){
		ExPriceTotalBean expricetotalbean = new ExPriceTotalBean();
		expricetotalbean.setHrproductname(beanarray[0]);
		expricetotalbean.setHrbrand(beanarray[1]);
		expricetotalbean.setHrweekprice(beanarray[2]);
		expricetotalbean.setHrspecifications(beanarray[3]);
		expricetotalbean.setHrweekprice(beanarray[4]);
		expricetotalbean.setHrlastweekprice(beanarray[5]);
		expricetotalbean.setHrapplies(beanarray[6]);
		return expricetotalbean;
    }
	
	/********************************************************/
	//1市场	钢厂	规格	材质	本周均价	上周均价	涨跌幅
	public ExPriceTotalBean(String hrmarket,String hrsteelmills,
			String hrspecifications,String hrmaterial,
			String hrweekprice,String hrlastweekprice,String hrapplies){
		this.hrmarket = hrmarket;
		this.hrsteelmills =hrsteelmills;
		this.hrspecifications =hrspecifications;
		this.hrmaterial=hrmaterial;
		this.hrweekprice =hrweekprice;
		this.hrlastweekprice =hrlastweekprice;
		this.hrapplies = hrapplies;
		 
	}
	//2市场	钢厂	规格	本周均价	上周均价	涨跌幅							
	public ExPriceTotalBean(String hrmarket,String hrsteelmills,
				String hrspecifications,
				String hrweekprice,String hrlastweekprice,
				String hrhighestprice,String hrlowestprice,
				String hrapplies){
			this.hrmarket = hrmarket;
			this.hrsteelmills =hrsteelmills;
			this.hrspecifications =hrmaterial;
			this.hrhighestprice = hrhighestprice;
			this.hrlowestprice =hrlowestprice;
			this.hrweekprice =hrweekprice;
			this.hrlastweekprice =hrlastweekprice;
			this.hrapplies = hrapplies;
			
		}
	//3 市场	钢厂	规格	材质	最高价格	最低价格	本周均价	上周均价	涨跌幅				
	public ExPriceTotalBean(String hrmarket,String hrsteelmills,
			String hrspecifications,String hrmaterial,
			String hrhighestprice,String hrlowestprice,
			String hrweekprice,String hrlastweekprice,
			String hrapplies){
		this.hrmarket = hrmarket;
		this.hrsteelmills =hrsteelmills;
		this.hrspecifications =hrspecifications;
		this.hrmaterial=hrmaterial;
		this.hrhighestprice = hrhighestprice;
		this.hrlowestprice =hrlowestprice;
		this.hrweekprice =hrweekprice;
		this.hrlastweekprice =hrlastweekprice;
		this.hrapplies = hrapplies;
		
	}
	//4市场	牌号	产地	本周均价	上周均价	周均价涨跌					
	public ExPriceTotalBean(String hrmarket,
			String hrproductname,
			String hraddres,
			String hrweekprice,String hrlastweekprice,
			String hrapplies,String a,String b,String c,String d){
		this.hrmarket = hrmarket;
		this.hrproductname =hrproductname;
		this.hraddres = hraddres;
		this.hrweekprice =hrweekprice;
		this.hrlastweekprice =hrlastweekprice;
		this.hrapplies = hrapplies;
	}
	//5厂商	牌号	价格	涨跌							
	public ExPriceTotalBean(String hraddres,String hrproductname,
			String hrweekprice,
			String hrapplies){
		this.hrproductname =hrproductname;
		this.hraddres = hraddres;
		this.hrweekprice =hrweekprice;
		this.hrapplies = hrapplies;
		
	}
	//5生产厂家（产地）	牌号	规格	上周末	本周末	备注	
	public ExPriceTotalBean(String hraddres,String hrproductname,
			String hrspecifications,
			String hrweekprice,String hrlastweekprice,
			String hrapplies){
		this.hraddres = hraddres;
		this.hrproductname =hrproductname;
		this.hrspecifications = hrspecifications;
		this.hrweekprice =hrweekprice;
		this.hrlastweekprice =hrlastweekprice;
		this.hrapplies = hrapplies;
		
	}
}
