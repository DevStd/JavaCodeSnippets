package fm.no5.util;

import java.io.Serializable;

/**
 * 파일 전송에 사용하는 데이터 구조
 */
public class FileObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 현재 파일 번호 */
	public int currentFileNo = 0;
	
	/** 전송할 파일의 총 갯수 */
	public int totalFileNo = 0;
	
	/** 파일이름 */
	public String fileName = null;
	
	/** 파일 데이터 */
	public byte[] fileData = null;
}
