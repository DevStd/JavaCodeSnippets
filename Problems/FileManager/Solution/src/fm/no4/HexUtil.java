package fm.no4;

public class HexUtil {
	/**
	 * byte배열을 문자열로 변환
	 * @param in byte배열
	 * @return 변환한 문자열
	 */
	public static String bytesToHex(byte[] in) {
	    final StringBuilder builder = new StringBuilder();
	    for(byte b : in) {
	        builder.append(String.format("%02X", b));
	    }
	    return builder.toString();
	}
}
