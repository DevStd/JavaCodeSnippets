package fm.no5.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import fm.no5.util.FileObject;

/**
 * ### 5. 파일 전송 - 클라이언트
 * - TCP소켓통신을 이용해 파일을 전송하는 서버/클라이언트를 작성한다.
 * - 클라이언트는 MERGED폴더안의 모든 파일을 읽어 파일이름과 파일 데이터를 서버로 전송한다.
 * - 서버는 전송받은 파일을 SERVER폴더를 생성하고, SERVER폴더안에 전송받은 파일명대로 파일데이터를 저장한다.
 * - 서버의 접속주소는 IP 127.0.0.1, 포트 9090 으로 설정 한다.
 */
public class No5ClientRunner {
	
	private static final String IP_ADDR = "127.0.0.1";
	private static final int PORT = 9090;

	public static void main(String[] args) {
		new No5ClientRunner().runClient();
	}
	
	/**
	 * 파일 클라이언트 구동
	 */
	public void runClient() {
		// 서버 접속
		try(Socket sock = new Socket(IP_ADDR, PORT);
			ObjectOutputStream ois = new ObjectOutputStream(sock.getOutputStream())) {
			 
			// 전송할 파일이 있는 경로
			File merged = new File("./MERGED");
			
			// 파일이 저장된 경로가 존재할 경우
			if(merged.exists()) {
				// 파일 목록을 불러와서
				File[] fileList = merged.listFiles();
				final int totalCount = fileList.length;
				for(int i = 0 ; i < totalCount ; i++) {
					// 파일 정보를 객체에 기록하고
					File file = fileList[i];
					FileObject fo = new FileObject();
					fo.currentFileNo = i;
					fo.totalFileNo = totalCount;
					fo.fileName = file.getName();

					// 파일 데이터를 객체안에 바이트 배열로 저장 한 다음
					try(FileInputStream fi = new FileInputStream(file)) {
						fo.fileData = new byte[(int) file.length()];
						fi.read(fo.fileData);
					}
					
					// 해당 객체를 소켓에 전달
					ois.writeObject(fo);
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
