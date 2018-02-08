package fm.no5.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import fm.no5.util.FileObject;
import fm.no5.util.FileUtil;

/**
 * ### 5. 파일 전송 - 서버
 * - TCP소켓통신을 이용해 파일을 전송하는 서버/클라이언트를 작성한다.
 * - 클라이언트는 MERGED폴더안의 모든 파일을 읽어 파일이름과 파일 데이터를 서버로 전송한다.
 * - 서버는 전송받은 파일을 SERVER폴더를 생성하고, SERVER폴더안에 전송받은 파일명대로 파일데이터를 저장한다.
 * - 서버의 접속주소는 IP 127.0.0.1, 포트 9090 으로 설정 한다.
 */
public class No5ServerRunner {
	
	private static final int PORT = 9090;
	private static final String REPOSITORY = "./SERVER"; 

	public static void main(String[] args) {
		new No5ServerRunner().runServer();
	}
	
	/**
	 * 파일서버 구동
	 */
	public void runServer() {
		// 소켓 클라이언트 접속 대기
		try(ServerSocket server = new ServerSocket(PORT);
			Socket sock = server.accept()){
			
			// 데이터 수신 시작
			try(ObjectInputStream ois = new ObjectInputStream(sock.getInputStream())) {
				// 저장할 폴더 정리
				FileUtil util = new FileUtil();
				util.delete(REPOSITORY);
				util.mkdir(REPOSITORY);
				
				// 파일 저장
				int recvCount = 0;
				int totalCount = 0;				
				do{
					try {
						FileObject fo = (FileObject) ois.readObject();
						recvCount++;
						totalCount = fo.totalFileNo;
						util.save(REPOSITORY+"/"+fo.fileName, fo.fileData);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						continue;
					}
				}while(recvCount<totalCount);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
