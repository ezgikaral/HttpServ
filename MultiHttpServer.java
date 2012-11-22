import java.io.*;
import java.net.*;
import java.util.*;

public class MultiHttpServer{
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket=null;
		final int PortNo=80;
		HttpServer a[]= new HttpServer[10];
		try{
			serverSocket=new ServerSocket(PortNo);
			
		}
		catch(IOException e)
		{
			System.err.println("Could not listen on port:"+PortNo);
			System.exit(1);
		}
		while(true)
		{
			try{
				Socket client=serverSocket.accept();
				for(int i=0; i<=9; i++){
					if(a[i]==null){
						(a[i]= new HttpServer(client)).start();
						break;
					}
				}
				
			}
			catch(IOException e){
				System.err.println("Failed accept");
				System.exit(1);
				
			}
		}
	}
}