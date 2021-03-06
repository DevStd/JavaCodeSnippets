package fm.no3;

/**
 * ### 3. version.txt  파일 갱신
 * - INPUT1 폴더의 파일목록을 읽어 해당 폴더안에 포함된 버전관리 파일인 version.txt파일의 내용과 비교한다.
 * - 버전관리 파일에 등록되지 않은 파일은 해당파일의 상태틑 '추가'로 설정하여 버전관리파일에 추가한다.
 * - 버전관리 파일에 등록되어있으나, 실제 INPUT1폴더안에 존재하지 않는 파일의 경우 파일의 상태를 '삭제' 로 변경한다.
 * - 버전관리 파일의 MD5해시와 실제 파일의 MD5해시가 다른 경우 파일의 상태를 '수정'으로 변경한다.
 * - 최초 추가한 파일의 버전은 0으로, 수정/삭제가 일어난 파일의 버전은 해당 파일의 원래버전+1 값으로 지정한다.
 * - 변경된 내용을 반영하여 INPUT1폴더의 version.txt 파일을 업데이트 한다.
 */
public class No3Runner {

	public static void main(String[] args) {
		String path = "./INPUT1";

		// 1. version.txt 읽기 
		FileVersion fv = FileVersion.load(path);
		
		// 2. 버전정보 갱신
		fv.refresh();
		
		// 3. version.txt 업데이트
		fv.update();
	}

}
