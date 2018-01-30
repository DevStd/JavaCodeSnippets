package server.advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**<pre>
 * 서버와 클라이언트 사이의 1:1통신을 지원하는 에코 서버
 * 
 * 별도의 스레드에서 소켓서버가 동작하며,
 * 서버 시작/정지/서비스상태조회 등을 처리할 수 있는 API 제공 
 * 
 * 서버 시작/종료 등의 제어는 {@link ServerRunner#main(String[])} 참고
 * </pre> */
public class EchoServer {
	
	// 서버 포트 
	private int _port = -1;
	
	// 서버 인스턴스
	private ServerInstance _instance = null;
	
	/**<pre>
	 * 에코 서버 생성
	 * 클라이언트와 1:1 연결만 지원 함
	 * @param port 서비스할 서버 포트
	 * </pre> */
	public EchoServer(int port) {
		this._port = port;
	}
	
	/**<pre>
	 * 에코 서버 시작 
	 * 이미 실행중인 서버인스턴스가 있을 경우 해당 인스턴스를 종료하고, 새로운 서버를 시작 함
	 * </pre> */
	public void start() {
		if(isAlive()) {
			System.out.println("Stop previous server instance.");
			stop();
		}
		
		_instance = new ServerInstance(_port);
		_instance.start();
	}
	
	/**
	 * 에코서버 정지
	 */
	public void stop() {
		if(isAlive()) _instance.stop();
	}
	
	/**
	 * 서비스 상태 확인
	 * @return 서비스 실행 여부. 서버가 생성되어 있어도 서비스 중이 아닌경우 false반환.
	 */
	public boolean isAlive() {
		return _instance !=null && _instance.isAlive();
	}
	
	/** 클라이언트 요청 처리를 위한 클래스 */
	private class Request{
		public String echo(String msg) {
			return "[ECHO] "+msg;
		}
	}
	
	/** 별도의 쓰레드에서 동작하는 소켓 서버 인스턴스 */
	private class ServerInstance implements Runnable{
		// 서버 실행상태를 제어할 변수
		private boolean isAlive = false;
		
		// 서버 소켓
		private ServerSocket _serverSocket = null;
		private Socket _sock = null;
		private Thread _serverThread = null;
		
		// 입출력 버퍼
		private BufferedReader br = null;
		private PrintWriter pw  = null;
		
		/**
		 * 에코서버 초기화
		 * @param port 서비스 포트
		 */
		public ServerInstance(int port) {
			try {
				_serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
				_serverSocket = null;
				isAlive = false;
			}
		}
		
		/**
		 * <b>서비스 상태 조회</b>
		 * @return 서비스 상태
		 */
		public boolean isAlive() {
			return _serverSocket !=null && _serverSocket.isBound();
		}
		
		/**
		 * <b>서비스 시작</b>
		 */
		public void start() {
			_serverThread = new Thread(this);
			_serverThread.start();
		}
		
		/**<pre>
		 * <b>서비스 종료</b>
		 * '서비스 flag변경' > 'socket 강제종료' > 'thread 정지' 순으로 서버 종료를 시도함
		 * </pre>*/
		public void stop() {
			if(isAlive()) {
				isAlive = false;
				System.out.println("Terminate server requested ...");			
				
				Thread t = new Thread(new Runnable() {					
					@Override
					public void run() {
						try {
							if(ServerInstance.this.isAlive()) {
								System.out.println("Waiting for terminating server ...");
								Thread.sleep(1000);
							} else {
								return;
							}
							
							if(ServerInstance.this.isAlive()) {								
								if(_sock !=null) _sock.close();
								if(_serverSocket !=null) _serverSocket.close();
								Thread.sleep(3000);
							}
							
							if(ServerInstance.this.isAlive()) {
								System.out.println("Force Terminate requested.");
								_serverThread.interrupt();
								Thread.sleep(3000);
							}
							
							if(ServerInstance.this.isAlive()) {
								System.out.println("[Error] cannot terminate server");
							}
						} catch (InterruptedException | IOException e) {
							e.printStackTrace();
						}					
					}
				});
				
				try {
					t.start();
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}		
		}
		
		/** <pre>
		 * <b>소켓 서버 시작</b>
		 * 클라이언트에서 메시지 수신 시 {@link EchoServer.Request#echo(String)}함수 호출. 
		 * 추가 작업이 필요한 경우 {@link EchoServer.Request}에 작업 등록하여 확장 가능
		 * </pre>*/
		@Override
		public void run() {			
			if(_serverSocket == null) {isAlive = false; return;} 
			else isAlive = true;
			
			System.out.println(">>> Start server");
			 
			// 서비스상태(isAlive=true)인 경우 클라이언트 연결이 끊어져도 다음 연결을 위해 서버소켓 감시를 계속 함 
			while(isAlive) {
				try {
					// 1. 클라이언트 연결 대기.
					_sock = _serverSocket.accept();
					System.out.println(">>> Client connected : "+_sock.getRemoteSocketAddress());
					
					// 2. 클라이언트와 연결되면, 입/출력 스크림을 가져 옴
					String reqMessage = null;
					br = new BufferedReader(new InputStreamReader(_sock.getInputStream()));
					pw = new PrintWriter(_sock.getOutputStream());
					
					// 3. 통신 작업 시작. 서비스가 종료됫거나 입력스트림이 닫힐 때 까지 데이터 수신 계속 진행
					Request req = new Request();
					while (isAlive && (reqMessage = br.readLine()) != null) {
						System.out.println("Msg Recv : "+reqMessage);
						pw.println(req.echo(reqMessage));
						pw.flush();
					}
					
					// 4.1 서비스중 입력스트림이 닫힌 경우, 클라이언트가 명시적으로 소켓을 닫은 것. 
					if(isAlive) System.out.println(">>> Client disconnected");
				} catch (IOException e1) { 
					try {
						if(_sock!=null) {
							// 4.2 서버소켓이 살아 있는데, 예외가 발생 한 경우, 클라이언트 연결이 강제종료된 것.
							System.out.println(">>> Client disconnected (forced)");
							if(!_sock.isClosed()) _sock.close();
							_sock = null;
						}
					} catch (IOException e) { }
				}
				
				// 5. 명시적으로 서비스를 종료하지 않은 경우, 다음 클라이언트의 연결을 위해 while문 상부로 돌아가 서버 대기 함. 
			}
			
			// 6. 서비스종료상태(isAlive=false)인 경우 소켓 자원을 반납하고 서버스르데 종료 함.
			System.out.println(">>> Stop server");
			
			try {
				if(_sock!=null) {
					if(!_sock.isClosed()) _sock.close();
					_sock = null;
				}
			} catch (IOException e) { }
			
			try {
				if(_serverSocket!=null) {
					if(!_serverSocket.isClosed()) _serverSocket.close();
					_serverSocket = null;
				}
			} catch (IOException e) { }
			isAlive = false;
		}
	}

}
