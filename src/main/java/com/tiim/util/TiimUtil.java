package com.tiim.util;

public class TiimUtil {

	public static String ValidateNull(String str)
	{
		if(str == null)
		{
			str = "";
		}
		return str;
	}
}
