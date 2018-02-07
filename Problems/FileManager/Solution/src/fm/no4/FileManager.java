package fm.no4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class FileManager {
	private static String VERSION_INFO = "version.txt";
	
	/**
	 * 폴더 안의 파일 목록 구하기 
	 * @param path 파일목록을 조회할 경로
	 * @return 해당 촐더에 포함된 파일 목록(version.txt제외)
	 */
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
	
	/**
	 * 파일/디렉토리 삭제
	 * @param path 삭제할 파일/디렉토리 경로
	 */
	public void delete(File path) {
		if(path.exists()) {
			if(path.isDirectory()) for(File f : path.listFiles()) this.delete(f);			
			path.delete();
		}
	}
	
	public void copy(File fileFrom, File fileTo) {
		try(FileInputStream fis = new FileInputStream(fileFrom);
			FileOutputStream fos = new FileOutputStream(fileTo)) {
			byte[] buffer = new byte[1000];
			int length;
	        while ((length = fis.read(buffer)) > 0) {
	        	fos.write(buffer, 0, length);
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 파일의 MD5 체크섬 구하기
	 * @param f 입력파일
	 * @return 입력파일의 MD5 체크섬
	 */
	public String md5(File f) {
		byte[] hash = null;
		try {
			// MD5입력 스트림 열기
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			// 파일 끝까지 Checksum 계산
			try(DigestInputStream dis = new DigestInputStream(new FileInputStream(f), md)){				
				while(dis.read() != -1) ;		       
		        hash = md.digest();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// hash값이 있는경우 문자열로 변환해서 반환
		return hash==null?null:HexUtil.bytesToHex(hash);
	}
}
