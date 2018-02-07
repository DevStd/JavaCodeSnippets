package fm.no4;

import java.io.File;

/**
 * ### 4. 폴더 병합
 * - 프로젝트의 하위에 MERGED 폴더를 생성한다.
 * - INPUT1폴더와 INPUT2폴더의 version.txt파일을 읽어서 최신의 버전정보를 MERGED폴더안에 version.txt로 저장한다.
 * - INPUT1폴더와 INPUT2폴더를 병합하여 최신버전정보에 해당하는 파일을 MERGED폴더에 복사한다.
 */
public class No4Runner {

	public static void main(String[] args) {
		FileManager fm = new FileManager();
		
		// 1. MERGED폴더 생성
		File merged = new File("./MERGED");
		fm.delete(merged);
		merged.mkdir();
		
		// 2. 폴더 병합
		FileVersion.merge("./MERGED", "./INPUT1", "./INPUT2");
	}

}
