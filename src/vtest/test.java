package vtest;

import cn.smy.dama2.Dama2;

public class test
{
	public static void main(String[] args)
	{
		try
		{
			Dama2 dama2 = new Dama2();
			String[] vcode = new String[1];
			int ret = dama2.d2File("bd5d6cf529d2e199fc41f18436ea8f75", //software key
					"username", "password", "C:\\work\\temp\\code.png",//file data
					(short) 20, //timeout , seconds 
					62, //code type id 
					vcode); //return code text
			if (ret > 0) {
				System.out.println("d2Buf success: vcodeID="+ String.valueOf(ret) + "\r\n" + "vcode=" + vcode[0]);
			} else {
				System.out.println("failed!");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
