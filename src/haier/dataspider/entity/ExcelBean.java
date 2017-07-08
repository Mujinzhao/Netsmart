package haier.dataspider.entity;
/**
 * 价格走向趋势表格
 * @author admin
 *
 */
public class ExcelBean {

	private String exid;
	private String exname;
	private String exdate;
	private String exsheet;
	public String getExid() {
		return exid;
	}
	public void setExid(String exid) {
		this.exid = exid;
	}
	public String getExname() {
		return exname;
	}
	public void setExname(String exname) {
		this.exname = exname;
	}
	public String getExdate() {
		return exdate;
	}
	public void setExdate(String exdate) {
		this.exdate = exdate;
	}
	public String getExsheet() {
		return exsheet;
	}
	public void setExsheet(String exsheet) {
		this.exsheet = exsheet;
	}
	public String getExcontent() {
		return excontent;
	}
	public void setExcontent(String excontent) {
		this.excontent = excontent;
	}
	private String excontent;
}
