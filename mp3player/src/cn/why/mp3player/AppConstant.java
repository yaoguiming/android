package cn.why.mp3player;
/**
 * 用于得到下载地址的基地址
 * @author Administrator
 */
public interface AppConstant {
	public class PlayerMsg{
		public static final int PLAY_MSG = 1;
		public static final int PAUSE_MSG = 2;
		public static final int STOP_MSG = 3;
	}

	public class URL {
		public static final String BASE_URL = "http://169.254.68.73:8080/mp3/";
	}

	public static final String LRC_MESSAGE_ACTION = "cn.why.mp3player.lrcmessage.action";
}

    class URLl {
	public static final String BASE_URL = "http://169.254.68.73:8080/mp3/";
}
