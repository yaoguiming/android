package cn.why.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");		
//		title = new String(title.getBytes("ISO8859-1"));//使用ISO8859-1编码接收数据
		String timelength = request.getParameter("timelength");
		System.out.println("视频名称："+ title);
		System.out.println("时长："+ timelength);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//		if(isMultipart){
//			try{
//				FileItemFactory factory = new DiskFileItemFactory();//10k
//				ServletFileUpload upload = new ServletFileUpload(factory);
//				upload.setHeaderEncoding("UTF-8");//设置编码，解决中文乱码
//				List<FileItem> items = upload.parseRequest(request);
//				String dir = request.getSession().getServletContext().getRealPath("/files");
//				File dirFile = new File(dir);
//				if(!dirFile.exists()) dirFile.mkdirs();
//				for(FileItem item : items){
//					if(item.isFormField()) {//如果文本类型参数
//						String name = item.getFieldName();
//						String value = item.getString("UTF-8");
//						System.out.println(name+ "="+ value);
//					}else{//如果文件类型参数
////						System.out.println(dir);
////						File saveFile = new File(dirFile, item.getName());
////						item.write(saveFile);
//						String filename = item.getName().substring(item.getName().lastIndexOf("\\")+1);  
//						InputStream in = item.getInputStream();
//						int len = 0;
//						byte buffer[] = new byte[1024];
//						FileOutputStream out = new FileOutputStream("e:\\" + filename);
//						while((len=in.read(buffer))>0){
//							out.write(buffer, 0, len);
//						}
//						in.close();
//						out.close();
//					}
//				}
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//		}else{
//			doGet(request, response);
//		}
		upload(request, response);
	}

	/**
	 * 生成文件名
	 * @param filename
	 * @return
	 */
	public String generateFileName(String filename){
		//83434-83u483-934934
		return UUID.randomUUID().toString() + "_" + filename;
	}
	/**
	 * 生成文件保存路径
	 * @param path
	 * @param filename
	 * @return
	 */
	public String generateSavePath(String path,String filename){
		int hashcode = filename.hashCode();  //121221
		int dir1 = hashcode&15;
		int dir2 = (hashcode>>4)&0xf;
		
		String savepath = path + File.separator + dir1 + File.separator + dir2;
		File file = new File(savepath);
		if(!file.exists()){
			file.mkdirs();
		}
		return savepath;
	}
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List types = Arrays.asList("jpg","gif","avi","txt");
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();  //10k
			factory.setSizeThreshold(1024*1024);
			factory.setRepository(new File(this.getServletContext().getRealPath("/temp")));
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setProgressListener(new ProgressListener(){
				public void update(long pBytesRead, long pContentLength, int pItems) {
					System.out.println("当前已解析：" + pBytesRead);
				}
			});
			
			upload.setFileSizeMax(1024*1024*5);
			if(!upload.isMultipartContent(request)){
				//按照传统方式获取表单数据
				request.getParameter("username");
				return;
			}
			upload.setHeaderEncoding("UTF-8");
			List<FileItem> list = upload.parseRequest(request);
			
			for(FileItem item : list){
				if(item.isFormField()){
					//为普通输入项
					String inputName = item.getFieldName();
					String inputValue = item.getString("UTF-8");
					//inputValue = new String(inputValue.getBytes("iso8859-1"),"UTF-8");
					System.out.println(inputName + "="  + inputValue);
				}else{
					String filename = item.getName().substring(item.getName().lastIndexOf("\\")+1);  //""
					if(filename==null || filename.trim().equals("")){
						continue;
					}
					
					/*String ext = filename.substring(filename.lastIndexOf(".")+1);
					if(!types.contains(ext)){
						request.setAttribute("message", "本系统不支持" + ext + "这种类型");
						request.getRequestDispatcher("/message.jsp").forward(request, response);
						return;
					}*/
					InputStream in = item.getInputStream();
					int len = 0;
					byte buffer[] = new byte[1024];
					String saveFileName = generateFileName(filename);
					String savepath = generateSavePath(this.getServletContext().getRealPath("/WEB-INF/upload"),saveFileName);
					FileOutputStream out = new FileOutputStream(savepath + File.separator + saveFileName);
					while((len=in.read(buffer))>0){
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					item.delete();  //删除临时文件
				}
			}
		}catch (FileUploadBase.FileSizeLimitExceededException e) {
			request.setAttribute("message", "文件大小不能超过5m");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		request.setAttribute("message", "上传成功！！");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
}
