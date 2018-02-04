package fm.no3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileVersion {
	public class VersionInfo{
		String fileName;
		int version;
		String status;
		String md5;
	}
	
	private HashMap<String, VersionInfo> verDict = null;
	private FileVersion(HashMap<String, VersionInfo> versionData) {
		verDict = versionData;
	}
	
	public FileVersion load(String path)  {
		FileVersion fv = null;
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path+"/version.txt")))){			
			HashMap<String, VersionInfo> versionData = new HashMap<>();
			String line = null;
			while((line=br.readLine())!=null) {
				String[] items = line.split(":");
			}			
			fv = new FileVersion(versionData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return fv;
	}
}
