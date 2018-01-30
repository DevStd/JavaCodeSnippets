package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * <b>소켓 클라이언트</b><br/>
 * 에코서버에 접속. 콘솔로 입력받은 텍스트를 서버에 전송 하고, 결과를 수신하서 화면에 표시 함.
 */
public class SocketClient {

	// 서버접속정보
	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9090;

	public static void main(String[] args) {
		Scanner sc = null;
		Socket sock = null;
		try {
			sc = new Scanner(System.in);
			
			// 1. 서버 연결 시작
			System.out.println("Connecting ...");
			sock = new Socket(SERVER_IP, SERVER_PORT);

			// 2. 서버 연결 성공
			if (sock.isConnected()) {
				System.out.println("Connected : " + sock.getRemoteSocketAddress());
				System.out.println("Command - Quit : /q");
			} else {
				throw new IOException("Connection failure");
			}

			// 3. 서버와 통신할 입출력 스트림 가져 옴
			PrintWriter pw = new PrintWriter(sock.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));

			// 4. 서버와 통신 시작
			String msg = null;
			System.out.print("send > ");
			while ((msg = sc.nextLine()) != null) {
				// 4.1 콘솔에서 /q 입력받은 경우 클라이언트 종료
				if ("/q".equals(msg)) break;
				
				// 4.2 그 이외의 경우 서버에 데이터 전송
				pw.println(msg);
				pw.flush();
				
				// 4.3 서버 처리결과 화면 표시
				System.out.println("recv > " + br.readLine());
				System.out.print("send > ");
			}

		} catch (IOException e) {
			System.out.println("Error : check network state!");
		} finally {
			try {
				if (sock != null && sock.isConnected()) sock.close();
				if (sc != null) sc.close();
			} catch (IOException e) {
			}

			System.out.println("Disconnected.");
		}
	}

}
