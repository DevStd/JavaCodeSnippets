package fm.no2;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ### 2. 파일의 MD5해시 출력
 * - INPUT1 폴더 안에 포함된 파일 목록을 출력한다.
 * - 파일이름의 오름차순대로 정렬하여 한줄에 파일하나씩 파일이름과 해당 파일의 MD5해시를 출력한다
 * - 파일이름과 MD5해시는 ## 으로 구분해서 출력하며, MD5해시의 값은 숫자와 알파벳대문자로 표시한다.
 * - version.txt 파일은 출력하지 않는다.
 */
public class No2Runner {

	public static void main(String[] args) {
		// TODO 2번 요구사항 작성
		FileManager fm = new FileManager();
		
		// 파일목록 조회
		List<File> fileList = fm.fileList("./INPUT1");
		
		// 이름순 정렬(오름차순)
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});
		
		// 출력
		for(File f : fileList) {
			System.out.println(f.getName()+"##"+fm.md5(f));
		}
	}

}
