import java.io.*;
import java.net.*;
import java.util.*;

public class HttpServer{
		
	public static void main(String[] args) throws IOException{
		ServerSocket serverSocket=null;
		final int PortNo=80;
		PrintWriter output=null; 
		BufferedReader input=null;
		Socket client=null; 
		String inputLine;
		String line;
		
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
				client=serverSocket.accept();
			}
			catch(IOException e){
				System.err.println("Failed accept");
				System.exit(1);
			}
			try{
				System.out.println("The client"+client.getInetAddress()+":"+client.getPort()+"is connected");
				output=new PrintWriter(client.getOutputStream(),true);
				input=new BufferedReader(new InputStreamReader(client.getInputStream()));
				while((inputLine=input.readLine())!=null){
					StringTokenizer tokens=new StringTokenizer(inputLine);
					if(tokens.hasMoreTokens()){
						if(tokens.nextToken().equals("GET")){
							String filename=tokens.nextToken().replace("/","");
							if(filename.equals("")){
								filename="index.html";
							}
							File newfile= new File("/"+filename);
							System.out.println("Requested file was "+filename);
							if(newfile!=null){
								try{
									FileReader freader=new FileReader(newfile);
									BufferedReader breader= new BufferedReader(freader);
									while((line=breader.readLine())!=null){
										output.write(line);
									}
									breader.close();
									System.out.println("File is written.");
								}
								catch (IOException e)
								{
								System.err.println("File could not be written.");
								}
							}
							else {
							System.out.println("File could not be found.");
							}
						}
					}
				}
				output.close();
				input.close();
				client.close();
				
			}
			catch(IOException e){
			System.err.println("IO Exception occurred.");
			}
			
		}
	}

}		
			
			
			
			