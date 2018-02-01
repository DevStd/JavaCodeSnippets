package fm.no1;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ### 1. 파일목록 출력
 * - INPUT1 폴더 안에 포함된 파일 목록을 출력한다.
 * - 파일이름의 오름차순대로 정렬하여 한줄에 파일하나씩 파일이름을 출력한다 
 * - version.txt 파일은 출력하지 않는다.
 * */
public class No1Runner {

	public static void main(String[] args) {
		// TODO 1번 요구사항 작성
		
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
			System.out.println(f.getName());
		}
	}

}
