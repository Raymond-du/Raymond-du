客户端 :
DatagramSocket ds=null;
	DatagramPacket dp=null;
	String s=new String();
	try {
	    ds=new DatagramSocket();
	    Scanner sc=new Scanner(System.in);
	    while(true) {
		s=sc.nextLine();
        	if(0==s.compareTo("close")) {
        	    break;
        	}
		dp=new DatagramPacket(s.getBytes(), s.getBytes().length,InetAddress.getByName("127.0.0.1"),1214);
        	ds.send(dp);
	    }
	    
	} catch (SocketException e) {
	    e.printStackTrace();
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}finally {
	    ds.close();
	}
  
  客户端和服务端 的数据转换
      
    //将double装换成字节数组
    public static byte[] convert(double num) throws IOException {
      ByteArrayOutputStream baos=new ByteArrayOutputStream();
      DataOutputStream dos=new DataOutputStream(baos);
      dos.writeDouble(num);
      dos.close();
      return baos.toByteArray();
    }
    
    //将字节数组转换成double
    public static double convert(byte[] data) throws IOException {
      DataInputStream dis=new DataInputStream(new ByteArrayInputStream(data));
      double num=dis.readDouble();
      dis.close();
      return num;
    }
    
    
    服务端 代码
    	DatagramSocket ds=null;
	DatagramPacket dp=null;
	byte[] buf=new byte[1024];
	try {
	    ds=new DatagramSocket(1214);
	    dp=new DatagramPacket(buf, buf.length);
	    while(true) {
		ds.receive(dp);
		System.out.println(dp.getAddress().getHostAddress()+dp.getPort()+new String(dp.getData(),0,dp.getLength()));		
	    }
	} catch (SocketException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}finally {	    
	    ds.close();	    
	}
