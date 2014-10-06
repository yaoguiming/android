package cn.why.test;

import android.test.AndroidTestCase;
import android.util.Log;
import cn.why.service.FileService;

public class FileServiceTest extends AndroidTestCase{
	private static final String TAG = "FileServiceTest";
	/**
	 * ≤‚ ‘Œƒº˛∂¡»°
	 * @throws Throwable
	 */
	public void testRead() throws Throwable{
		FileService service = new FileService(this.getContext());
		String result = service.read("itcast.txt");
		Log.i(TAG, result);
	}
}
