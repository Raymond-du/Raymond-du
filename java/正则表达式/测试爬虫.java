//这里只限于自己的网页  其他网页存在bug


package Test02;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test{
	public static void main(String[] args) {
		String temp=null;
		try {
			String link="https://raymond-du.github.io/demo01/";
			temp = getRespond(new URL(link));
			List<String> list=getPhotoUrl(link,temp);
			//spiderPhoto("C:\\Users\\26368\\Pictures\\baidu", list);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static String getRespond(URL url) throws IOException {
		StringBuilder strBuilder=new StringBuilder();
	
		BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
		String str=null;
		while(null!=(str=br.readLine())) {
			strBuilder.append(str);				
		}
		return strBuilder.toString();
	}
	//需要指定表达式   表达式不正确  如果有http或https
	public static List<String> getPhotoUrl(String src,String str){
		Pattern pattern=Pattern.compile("(?<=src=\")(.+?\\.(jpg|png|gif))(?=\")");
		Matcher matcher=pattern.matcher(str);
		List<String> urlList=new ArrayList<String>();
		while(matcher.find()) {
			String temp=matcher.group();
			urlList.add(src+temp);
			System.out.println(temp);
		}
		return urlList;
	}
	
	public static void spiderPhoto(String dirPath,List<String>urlList) throws Exception {
		File file=new File(dirPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		BufferedOutputStream bos=null;
		URL url=null;
		for(String urlStr:urlList) {
			url=new URL(urlStr);
			String filePath=dirPath+"/"+urlStr.substring(urlStr.lastIndexOf("/")+1);
			bos=new BufferedOutputStream(new FileOutputStream(filePath));
			InputStream is=url.openStream();
			byte[] buf=new byte[1024];
			int len=0;
			while(-1!=(len=is.read(buf))) {
				bos.write(buf,0,len);
			}
			bos.flush();
			if(bos!=null) {
				bos.close();
			}
		}
	}
	
}
