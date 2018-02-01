package fm.no1;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileManager {
	private static String VERSION_INFO = "version.txt";
	
	public List<File> fileList(String path) {
		File dir = new File(path);
		
		// 입력받은 경로가 디렉토리가 아닌 경우 null 반한
		if(dir == null || !dir.isDirectory()) return null;
		
		// 폴더 내 파일목록 가져오기 (version.txt파일 제외)
		File[] files = dir.listFiles(new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				return !VERSION_INFO.equals(name);
			}
		});
		
		// List객체로 결과 반환
		return files==null?null:Arrays.asList(files);
	}
}
