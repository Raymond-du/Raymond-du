package solar;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class test {
    public static void fileTraver(File str) {
	System.out.println(str.getPath());
	if(str.isDirectory()) {
	    File s[]=str.listFiles();
	    for(int i=0;i<s.length;i++) {
		fileTraver(s[i]);
	    }
	}
    }
    public static void main(String[] args) throws IOException {
	File f=new File("C:\\Users\\26368\\Documents\\Visual Studio 2013\\Ô´Âë\\tip\\sysopt\\bin\\debug");
	File fList[]=f.listFiles(new FilenameFilter() {
	    
	    @Override
	    public boolean accept(File dir, String name) {
		return name.endsWith(".lib");
	    }
	});
	for(File temp:fList) {
	    System.out.println(temp.getAbsolutePath());
	}
    }

}





