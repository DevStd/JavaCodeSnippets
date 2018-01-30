package server.advanced;

import java.util.Scanner;

/** <pre>
 * <b>소켓 서버실행 및 제어기능을 제공하는 클래스</b>
 * 콘솔 입력으로 소켓서버를 시작/정지/서비스상태조회 등을 수행하는 클래스
 * </pre>
 * @see EchoServer EchoServer.java
 * */
public class ServerRunner {

	// 서버의 서비스 포트
	private static final int SERVER_PORT = 9090;

	public static void main(String[] args) {
		EchoServer server = new EchoServer(SERVER_PORT);

		// 명령어 안내
		System.out.println(">> Available Commands : start, stop, status, quit");
		
		Scanner sc = new Scanner(System.in);
		String cmd = null;
		while ((cmd = sc.nextLine()) != null) {
			if ("start".equals(cmd)) {
				// 서버 시작
				server.start();
			} else if ("status".equals(cmd)) {
				// 서비스 상태 조회
				System.out.println("Service status : " + server.isAlive());
			} else if ("stop".equals(cmd)) {
				// 서버 종료
				server.stop();
			} else if ("quit".equals(cmd)) {
				// 프로그램 종료
				server.stop();
				break;
			}
		}

		sc.close();
	}

}
