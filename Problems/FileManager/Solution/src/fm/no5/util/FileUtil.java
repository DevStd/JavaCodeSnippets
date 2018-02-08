package fm.no5.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 파일처리를 위한 유틸리티 클래스
 */
public class FileUtil {
	// 폴더 및 포함한 파일 삭제
	public void delete(File path) {
		if(path.exists()) {
			if(path.isDirectory()) for(File f : path.listFiles()) this.delete(f);			
			path.delete();
		}
	}
	
	public void delete(String path) {
		this.delete(new File(path));
	}
	
	// 폴더 생성
	public void mkdir(String dir) {
		File path = new File(dir);
		if(!path.exists()) path.mkdirs();
	}
	
	// 파일 저장
	public void save(String path, byte[] data) {
		final int BUF_SIZE = 1024;
		try(FileOutputStream fo = new FileOutputStream(path)) {
			int offset = 0;
			while(offset < data.length) {
				int len = Math.min(data.length-offset, BUF_SIZE);
				fo.write(data, offset, len);
				offset += BUF_SIZE;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}