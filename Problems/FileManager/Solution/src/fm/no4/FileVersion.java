package fm.no4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class FileVersion {
	// 폴더 경로
	private String path = null;
	// 버전정보 목록
	private HashMap<String, VersionInfo> verInfoList = null;
	// 생성자(version.txt를 읽어오는 방식으로만 객체생성 가능)
	private FileVersion(String path, HashMap<String, VersionInfo> versionData) {
		this.path = path;
		this.verInfoList = versionData;
	}
	
	/**
	 * version.txt파일을 읽어와서 버전정보 목록객체 생성
	 * @param path 버전정보목록을 작성할 경로
	 * @return 버전정보 관리 객체
	 */
	public static FileVersion load(String path)  {
		FileVersion fv = null;
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path+"/version.txt")))){			
			HashMap<String, VersionInfo> versionData = new HashMap<>();
			String line = null;
			while((line=br.readLine())!=null) {
				String[] items = line.split(":");
				versionData.put(items[0], new VersionInfo(items[0], Integer.valueOf(items[1]), items[2], items[3]));
			}			
			fv = new FileVersion(path, versionData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return fv;
	}
	
	/**
	 * 버전정보 동기화 (version.txt와 실제 폴더 내용 동기화)
	 */
	public void refresh() {
		if(verInfoList == null) return;
		
		FileManager fm = new FileManager();
		for(String fn : verInfoList.keySet()) {
			VersionInfo vi = verInfoList.get(fn);
			File f = new File(this.path +'/'+vi.fileName);
			
			// 파일상태 > 삭제
			if(!f.exists()) {
				if(!"D".equals(vi.status)) {
					vi.version++;
					vi.status = "D";
				}
				continue;
			}
			
			// 파일상태 > 변경
			String md5 = fm.md5(f);
			if(!vi.md5.equals(md5)) {
				vi.version++;
				vi.status = "M";
				vi.md5=md5;
				continue;
			}
		}
		
		// 파일상태 > 추가
		List<File> fileList = fm.fileList(path);
		for(File f : fileList) {
			String fileName = f.getName();
			if(!verInfoList.containsKey(fileName)) {
				VersionInfo vi = new VersionInfo(fileName, 0, "A", fm.md5(f));
				verInfoList.put(fileName, vi);
			}
		}
	}
	
	/**
	 * 버전정보 파일 업데이트
	 */
	public void update() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path+"/version.txt")))){
			SortedSet<String> fileList = new TreeSet<>(verInfoList.keySet());
			for(String file : fileList) {
				bw.write(verInfoList.get(file).toString());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 폴더 병합
	 * @param outputPath 병합 결과를 저장할 경로 
	 * @param inputPaths 병합할 폴더 목록
	 */
	public static void merge(String outputPath, String... inputPaths) {
		if(outputPath==null || inputPaths == null || inputPaths.length == 0) return;
		
		FileManager fm = new FileManager();
		HashMap<String,String> fileOriginPath = new HashMap<>();
		FileVersion mergedVersion = null;

		// 1. 버전정보 갱신
		for(String inp : inputPaths) {
			FileVersion fv = FileVersion.load(inp);
			fv.refresh();
			
			// 처음으로 처리한 폴더
			if(mergedVersion == null) {				
				for(String file : fv.verInfoList.keySet()) {
					VersionInfo vi = fv.verInfoList.get(file);
					if(!"D".equals(vi.status)) fileOriginPath.put(file, inp+"/"+file);
				}
				mergedVersion = fv;
			}
			// 두번째부터는 앞서 처리한 결과와 병합
			else {
				for(String file : fv.verInfoList.keySet()) {
					VersionInfo current = fv.verInfoList.get(file);
					VersionInfo merged = null;
					// 버전갱신
					if(mergedVersion.verInfoList.containsKey(file)) {
						merged = mergedVersion.verInfoList.get(file);
						if(current.version > merged.version) {
							mergedVersion.verInfoList.put(file, current);
							if("D".equals(current.status)) fileOriginPath.remove(file);
							else fileOriginPath.put(file, inp+"/"+file);
						}
					}
					// 단순추가
					else {
						mergedVersion.verInfoList.put(file, current);
						if(!"D".equals(current.status)) fileOriginPath.put(file, inp+"/"+file);
					}
				}
			}
		}
		
		// 2. 파일 복사
		for(String fileName : fileOriginPath.keySet()) {
			fm.copy(new File(fileOriginPath.get(fileName)), new File(outputPath+"/"+fileName));
		}
		
		// 3. 병합한 결과에 대한 version.txt 생성
		mergedVersion.path = outputPath;
		mergedVersion.update(); 
	}
	
	/**
	 * 현재 작성중인 버전정보 목록 출력
	 */
	public void show(){
		if(verInfoList == null || verInfoList.size()==0) System.out.println("No version data");
		else for(String fn : verInfoList.keySet()) System.out.println(verInfoList.get(fn).toString());
	}
}
