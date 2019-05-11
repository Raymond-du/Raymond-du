//仅限于https://www.runoob.com/try/try.php?filename=tryhtml5_video_all



package Test02;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Test{
	public static void main(String[] args) {
		String temp=null;
		try {
			String link="https://www.runoob.com/try/try.php?filename=tryhtml5_video_all";
			temp = getRespond(new URL(link));
			System.out.println(temp);
			List<String> list=getPhotoUrl("https://www.runoob.com/try/demo_source/",temp);
			spiderPhoto("C:\\Users\\26368\\Pictures\\baidu", list);
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
		Pattern pattern=Pattern.compile("(?<=(src|href)=\\\\\")((?!=).)+(mp4)(?=\\\\\")");
		Matcher matcher=pattern.matcher(str);
		List<String> urlList=new ArrayList<String>();
		while(matcher.find()) {
			String temp=matcher.group();
			System.out.println(temp);
			if(temp.contains("http")) {
				urlList.add(temp);
			}else {
				urlList.add(src+temp);
			}
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
			InputStream is=null;
			try {
				is=url.openStream();
			}catch (Exception e){
				System.out.println("错误");
				continue ;
			}
			
			String filePath=dirPath+"/"+urlStr.substring(urlStr.lastIndexOf("/")+1);
			bos=new BufferedOutputStream(new FileOutputStream(filePath));
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
