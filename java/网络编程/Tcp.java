tcp的练习  和使用流模式  简化传输
客户端
Socket s=null;
	OutputStream os=null;//这里可以用包装类
	/**
	 * BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
	 * 使用包装类 需要注意客户端和服务端都需要使用相同的包装类
	 */
	try {
	    s=new Socket("127.0.0.1", 1214);
	    os=s.getOutputStream();
	    
	    //获取控制台的输入
	    Scanner sc=new Scanner(System.in);
	    while(true) {
    	    	String data=sc.nextLine();
    	    	if(0==data.compareTo("close")) {
    	    	    break;
    	    	}
    	    	os.write(data.getBytes());
	    }
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}finally {
	    try {
		s.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
  
  服务端
  	ServerSocket ss=null;
	InputStream is=null;
	/**
	 * BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
	 *利用 包装类进行  更方便的传输
	 */
	try {
	    ss = new ServerSocket(1214);
	    Socket s=ss.accept();
	    System.out.println(s.getPort()+"已连接"+s.getLocalPort());
	    is=s.getInputStream();
	    byte[] flush=new byte[1024];
	    int len=0;
	    while(-1!=(len=is.read(flush))) {
		System.out.println(new String (flush,0,len));
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}finally {
	    try {
		ss.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
