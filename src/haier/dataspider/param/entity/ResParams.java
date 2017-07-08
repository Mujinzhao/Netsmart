package haier.dataspider.param.entity;

import java.util.Date;


public class ResParams implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pkid;
	private String tid;
	private String tname;
	private String tdescription;
	private String tdistype;
	private String tdisFormat;
	private String tdsVal;
	private String tinitVal;
	private String tstartVal;
	private String tendVal;
	private Integer tstartValInitRule;
	private Integer tendValInitRule;
	private Date addTime;
	private String remark;
	// Constructors

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** default constructor */
	public ResParams() {
	}

	/** minimal constructor */
	public ResParams(String pkid) {
		this.pkid = pkid;
	}

	/** full constructor */
	public ResParams(String pkid, String tid, String tname,
			String tdescription, String tdistype, String tdisFormat,
			String tdsVal, String tinitVal, String tstartVal, String tendVal,
			Integer tstartValInitRule, Integer tendValInitRule,
			Date addTime) {
		this.pkid = pkid;
		this.tid = tid;
		this.tname = tname;
		this.tdescription = tdescription;
		this.tdistype = tdistype;
		this.tdisFormat = tdisFormat;
		this.tdsVal = tdsVal;
		this.tinitVal = tinitVal;
		this.tstartVal = tstartVal;
		this.tendVal = tendVal;
		this.tstartValInitRule = tstartValInitRule;
		this.tendValInitRule = tendValInitRule;
		this.addTime = addTime;
	}

	// Property accessors

	public String getPkid() {
		return this.pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}

	public String getTid() {
		return this.tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTdescription() {
		return this.tdescription;
	}

	public void setTdescription(String tdescription) {
		this.tdescription = tdescription;
	}

	public String getTdistype() {
		return this.tdistype;
	}

	public void setTdistype(String tdistype) {
		this.tdistype = tdistype;
	}

	public String getTdisFormat() {
		return this.tdisFormat;
	}

	public void setTdisFormat(String tdisFormat) {
		this.tdisFormat = tdisFormat;
	}

	public String getTdsVal() {
		return this.tdsVal;
	}

	public void setTdsVal(String tdsVal) {
		this.tdsVal = tdsVal;
	}

	public String getTinitVal() {
		return this.tinitVal;
	}

	public void setTinitVal(String tinitVal) {
		this.tinitVal = tinitVal;
	}

	public String getTstartVal() {
	
		return this.tstartVal;
	}

	public void setTstartVal(String tstartVal) {
		this.tstartVal = tstartVal;
	}

	public String getTendVal() {
	
		return this.tendVal;
	}

	public void setTendVal(String tendVal) {
		this.tendVal = tendVal;
	}

	public java.lang.Integer getTstartValInitRule() {
		return this.tstartValInitRule;
	}

	public void setTstartValInitRule(java.lang.Integer tstartValInitRule) {
		this.tstartValInitRule = tstartValInitRule;
	}

	public java.lang.Integer getTendValInitRule() {
		return this.tendValInitRule;
	}

	public void setTendValInitRule(java.lang.Integer tendValInitRule) {
		this.tendValInitRule = tendValInitRule;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}


}