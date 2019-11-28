package views;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import domain.UserVO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import main.MainApp;
import util.Util;

public class MainController extends MasterController {
	private static final KeyCode ENTER = null;
	@FXML
	private Button btnPrev;
	@FXML
	private Button btnNext;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblDay;
	@FXML
	private Label loginInfo;
	@FXML
	private GridPane gridCalendar;

	private UserVO user;

	public void setLoginInfo(UserVO user) {
		this.user = user;
		loginInfo.setText(user.getName() + "[" + user.getId() + "]");
	}

	public void logout() {
		send("SYSTEM::LISTDEL::"+user.getName());
		this.user = null;
		MainApp.app.loadPane("login");
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

//--------------------------------------------------------------------------------------------------------
	Socket socket;
	public TextArea textArea;
	public Button connectB;
	public TextField talkField;
	public Button sendBt;

	public String a;
	ArrayList<String> arr = new ArrayList<String>();
	
	int myId;
	
	public long sec = 10;
	public Label time;
	// 클라이언트 프로그램 동작매소드
	public void startClient(String IP, int port) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(IP, port);
					//send("[서버 메세지]: " + user.getName() + "님이 입장하셨습니다.\n");
					send("SYSTEM::LOGIN::" + user.getName());
					receive();
				} catch (Exception e) {
					if (!socket.isClosed()) {
						stopClient();
						System.out.println("[서버 접속 실패]");
						Platform.exit();
					}
				}
			}
		};
		thread.start();
	}

	// 클라이언트 종료 메소드
	public void stopClient() {
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean resetBolean = false;

	// 서버로 메세지를 전달 받는 메소드
	public void receive() {
		while (true) {
			try {
				InputStream in = socket.getInputStream();
				byte[] buffer = new byte[512];
				int length = in.read(buffer);
				if (length == -1)
					throw new IOException();
				String message = new String(buffer, 0, length, "UTF-8");
				
				String[] cmdArr = message.split("::");
				
				switch (cmdArr[0]) {
				case "SYSTEM":
					if(cmdArr[1].equals("LOGIN")) {
						myId = Integer.parseInt(cmdArr[2]);
					}
					if(cmdArr[1].equals("START")) {
						try {
							Thread thread = new Thread() {
								public void run() {
									while(true) {
										try {
											Thread.sleep(1000);
											sec--;
										} catch (Exception e) {
											e.printStackTrace();
											System.out.println("쓰레드 오류 발생");
										}
										
										Long s = sec;
										Platform.runLater(new Runnable() {

											@Override
											public void run() {
												time.setText(s.toString());
												
											}
											
										});
										if(sec==0) {
											sec = 10;
											break;
										}
									}
								}
							};
							thread.start();
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("스타트 쓰레드 오류 발생");
						}
					}else if(cmdArr[1].equals("RETURN")) {
						sec = 10;
					}else if(cmdArr[1].equals("STOPSERVER")){
						stopClient();
					}
					break;
				case "MSG":
					textArea.appendText(cmdArr[1]);
					break;
				}
				
//				if (message.startsWith("[서버 메세지]: 지금부터 게임이 시작됩니다.")) {
//					textArea.appendText(message);
//					resetBolean = true;
//					userNext = max();
//					endWordOver = true;
//				} else if (resetBolean) {
//					reset++;
//					textArea.appendText(message);
//				} else {
//					textArea.appendText(message);
//				}
			} catch (Exception e) {
				stopClient();
				break;
			}
		}
	}

	// 서버로 메세지를 전송하는 메소드
	public void send(String message) {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer = message.getBytes("UTF-8");
					out.write(buffer);
					out.flush();

				} catch (Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}

	public boolean first = false;
	
	public void bsend() {
		String msg = talkField.getText();
		talkField.setText("");
		
		if(msg.startsWith("/")) {
			if(msg.startsWith("/끝말잇기")) {
				send("SYSTEM::ENDWORDGAME");
			}else if(msg.startsWith("/명령어")){
				send("SYSTEM::COMMAND");
			}else if(msg.startsWith("/사용자")) {
				send("SYSTEM::USER");
			}
		}else {
			if(msg.isEmpty()) {
				return;
			}else {
				
			}send("MSG::" + msg+"::"+user.getName());
		}
		
	}
	
	public TextField ip;
	public void connect() {
		if(first) {
			return;
		}else {
			String IP = ip.getText();
			int port = 9910;
			startClient(IP, port);
			first = true;
			
		}
	}
	
	public void dConnect() {
		String a = textArea.getText();
		if(!first) {
			return;
		}else {
			send("SYSTEM::LISTDEL::"+user.getName());
			first = false;
		}
	}

	public void keyHandler(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			bsend();
		}
	}
	public void initialize() {
		textArea.setText("[서버 메세지]: 먼저 밑에 칸에 ip주소를 입력한 후 접속하기 버튼을 눌러주세요.\n접속하기를 눌러도 안됀다면 ip주소가 잘못 입력됬거나 서버가 열리지 않은것입니다.\n한번에 접속이 안된경우 접속끊기를 누르고 다시 한번 시도해주세요.\n");
		textArea.setEditable(false);
	}
	
	public void emoticon() {
		MainApp.app.openPopup();
	}
//--------------------------------------------------------------------------------------------------------
}
