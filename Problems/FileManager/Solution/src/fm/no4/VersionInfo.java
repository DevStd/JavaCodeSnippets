package fm.no4;

/**
 * 버전정보를 취급할 객체
 */
public class VersionInfo{
	String fileName;
	int version;
	String status;
	String md5;
	public VersionInfo(String fileName, int version, String status, String md5) {
		super();
		this.fileName = fileName;
		this.version = version;
		this.status = status;
		this.md5 = md5;
	}
	@Override
	public String toString() {
		return fileName+":"+version+":"+status+":"+md5;
	}
}