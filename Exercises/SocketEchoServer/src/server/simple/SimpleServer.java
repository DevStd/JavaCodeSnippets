package server.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import server.advanced.EchoServer;

/**<pre>
 * <b>간소화된 버전의 에코 서버</b>
 * 프로그램 시작시 자동으로 소켓서버를 시작하며,
 * 클라이언트와 1회연결후 연결이 종료되면 프로그램 종료.
 * 소켓서버제어 및 여러번 연결 가능한 서버는 {@link EchoServer} 클래스 참고
 *</pre>
 */
public class SimpleServer {

	// 서버의 서비스 포트
	private static final int SERVER_PORT = 9090;

	public static void main(String[] args) {
		try {
			// 서버 시작
			startServer(SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <b>에코 서버 시작</b><br/>
	 * 1회 연결만 가능한 소켓 서버. 한번 연결된 이후 클라이언트와 연결이 끊긴 경우 서버 종료.
	 * @param port 서비스 포트
	 * @throws IOException 입출력 오류 (소켓 강제종료시 발생)
	 */
	private static void startServer(int port) throws IOException {
		// 1. 서버 생성
		ServerSocket server = new ServerSocket(port);
		System.out.println(">>> Start Server");

		// 2. 클라이언트 접속 대기
		Socket sock = server.accept();
		System.out.println(">>> Client Connected : " + sock.getRemoteSocketAddress());

		// 3. 클라이언트 접속 후 입출력 스크림 가져 옴 
		BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		PrintWriter pw = new PrintWriter(sock.getOutputStream());

		// 4. 통신 작업 시작. 입력스트림이 닫힐 때 까지 데이터 수신 계속 진행
		String reqMessage = null;
		while ((reqMessage = br.readLine()) != null) {
			System.out.println("Msg Recv : " + reqMessage);
			pw.println("[ECHO] " + reqMessage);
			pw.flush();
		}

		// 5. 통신 종료
		sock.close();
		server.close();
		System.out.println(">>> Stop Server");
	}

}
