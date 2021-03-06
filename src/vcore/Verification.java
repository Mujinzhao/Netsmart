package vcore;

import cn.smy.dama2.Dama2;

/*
 * 软件名称：万网采集平台
软件描述：万网采集验证码识别
软件ID：42572
软件KEY：bd5d6cf529d2e199fc41f18436ea8f75*/


public class Verification
{
	private long vcodeID;
	
	
	public long getVcodeID() {
		return vcodeID;
	}

	public void setVcodeID(long vcodeID) {
		this.vcodeID = vcodeID;
	}

	public Verification() {
		
	}

	/**
	 * 使用DAMA2平台人工识别验证码 http://v3.dama2.com/
	 * 识别成功返回对应验证码值，识别失败返回空字符串""
	 * @param data 存在内存中的验证码图片
	 * @param codetype 验证码类型，详见：http://wiki.dama2.com/index.php?n=ApiDoc.Pricedesc
	 * */
	public String Verificate(String username,String password, byte[] data, long codetype)
	{
		try
		{
			Dama2 dama2 = new Dama2();
			String[] vcode = new String[1];
			int ret = dama2.d2Buf("bd5d6cf529d2e199fc41f18436ea8f75", //software key
					username, password, data,//file data
					(short) 20, //timeout , seconds 
					codetype, //code type id 
					vcode); //return code text
			if (ret > 0) {
				setVcodeID(ret);
				System.out.println("d2Buf success: vcodeID="+ String.valueOf(ret) + "\r\n" + "vcode=" + vcode[0]);
				return vcode[0];
			} else {
				System.out.println("failed!");
				return "";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
}
