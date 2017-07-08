package datacvg.gather.entity;
/**
 *设值参数来源类
 * @author Wang jianwei
 *
 */
public class SIMParam {
	private String invoiceCode;//发票代码
	private String invoiceNumber;//发票号码
	private String date;//开票日期
	private String buyerRegNumber;//购方税务登记号
	private String buyerName;//购方名称
	private String sellerRegNumber;//售方税务登记号
	private String sellerName;//售方名称
	private String invoiceValue;//发票金额
	private String tax;//税额
	private String password;//发票密码
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBuyerRegNumber() {
		return buyerRegNumber;
	}
	public void setBuyerRegNumber(String buyerRegNumber) {
		this.buyerRegNumber = buyerRegNumber;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getSellerRegNumber() {
		return sellerRegNumber;
	}
	public void setSellerRegNumber(String sellerRegNumber) {
		this.sellerRegNumber = sellerRegNumber;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getInvoiceValue() {
		return invoiceValue;
	}
	public void setInvoiceValue(String invoiceValue) {
		this.invoiceValue = invoiceValue;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
}
