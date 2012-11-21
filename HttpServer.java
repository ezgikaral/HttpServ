import java.io.*;
import java.net.*;
import java.util.*;

public class HttpServer extends Thread{
	final String HTMLStart=
	"<html>"+"<title>Http Server</title>"+"<body>";
	final String HTMLEnd=
	"</body>"+"</html>";
	
	PrintWriter output=null; 
	BufferedReader input=null;
	Socket client=null;
	
	public HttpServer(Socket cclient){
		client=cclient;
	}
	
	public void run(){
		String inputLine;
		try{
			System.out.println("The client"+client.getInetAddress()+":"+client.getPort()+"is connected");
			output=new PrintWriter(client.getOutputStream(),true);
			input=new BufferedReader(new InputStreamReader(client.getInputStream()));
			output.flush();
			while(!(inputLine=input.readLine()).isEmpty()){
				StringTokenizer tokens=new StringTokenizer(inputLine);
				if(tokens.hasMoreTokens()){
					if(tokens.nextToken().equals("GET")){
						String filename=tokens.nextToken().replace("/","");
						if(filename.equals("")){
							filename="index.html";
						}
						File newfile= new File("/"+filename);
						System.out.println("Requested file was "+filename);
						write(newfile,output);
					
					}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	public void write(File newfile, PrintWriter output){
		String line;
		if(newfile.exists()){
			try{
				FileReader freader=new FileReader(newfile);
				BufferedReader breader= new BufferedReader(freader);
				while((line=breader.readLine())!=null){
					output.write(line);
				}
				output.flush();
				breader.close();
				System.out.println("File is written.");
			}
			catch (IOException e)
				{
					System.err.println("File could not be written.");
					e.printStackTrace();
					output.flush();
								
				}
		}
		else {
			output.write("File could not be found.");
			output.flush();
			System.out.println("File not found.");
		}
	}
		
			
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket=null;
		final int PortNo=80;
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
				new HttpServer(client).start();
			}
			catch(IOException e){
				System.err.println("Failed accept");
				System.exit(1);
				
			}
		}
	}
}
			
							
							
	
			
			
			
			