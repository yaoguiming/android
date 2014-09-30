package cn.why.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;

public class FileService {
	private Context context;	//上下文对象
	
	public FileService(Context context) {
		super();
		this.context = context;
	}

	public void saveToSDCard(String fileName,String fileContent) throws Exception{
//		File file = new File("/mnt/sdcard",fileName);
		File file = new File(new File("/mnt/sdcard"),fileName);
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(fileContent.getBytes());
		outputStream.close();
	}
	/**
	 * 保存文件
	 * @param fileName 文件名称
	 * @param fileContent 文件内容
	 * @throws Exception 
	 */
	public void save(String fileName, String fileContent) throws Exception {
		//IO实现文件保存
		//只接受文件名称，不支持路径
		//mode:追加、覆盖
		//私有操作模式：创建出来的文件只能被本应用访问，其他应用无法访问该文件，另外采用私有模式创建的文件，写入文件中的内容会覆盖源文件内容
		//默认保存在/data/data/cn.why/files/
		FileOutputStream outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
		outStream.write(fileContent.getBytes());
		outStream.close();
	}
	/**
	 * 读取文件内容
	 * @param fileName 文件名称
	 * @return 
	 * @throws Exception
	 */
	public String read(String fileName) throws Exception{
		FileInputStream inStream = context.openFileInput(fileName);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		//缓冲流对象
		byte[] buffer = new byte[1024];
		//读完返回值为-1，为读完返回读取的数据个数
		int length = 0;
		while((length = inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, length);		//文件读取到内存
		}
		byte[] data = outStream.toByteArray();
		return new String(data);
	}

}
