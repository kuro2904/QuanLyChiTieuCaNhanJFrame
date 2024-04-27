package util;

import java.util.Random;

public class OTPUtil {

	private static final int[] rangeCode = {0,1,2,3,4,5,6,7,8,9};
	private static String code = "";
	

	public static String getSecureCode() {
	    Random rand = new Random();
	    for(int i = 0; i < 4; i++) {
	        code = code.concat(String.valueOf(rangeCode[rand.nextInt(10)]));
	    }
	    return code;
	}
	
}
