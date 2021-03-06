

package simpleweb;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

/**
 * The following class implements a very simple single threaded web server. A
 * web server responds to HTTP requests over a socket by loading and
 * transmitting back the requested file.
 *
 * @author djp
 *
 */


public class Server extends Thread {
	private int port; // port to respond on
	private String root;  // root of all html pages

	public Server(int port, String root) {
		this.port = port;
		this.root = root;
	}

	public void run() {
		boolean exit=false;
		try {
			ServerSocket ss = new ServerSocket(port);
			LinkedList<WorkerThread> threads = new LinkedList<WorkerThread>();
			while (!exit) {
				Socket s = ss.accept();
				// create a thread worker and start it
				WorkerThread worker = new WorkerThread(this, s, root);
				if (threads.size() < 10){
					worker.start();
					threads.offer(worker);
				}
			} 

		} catch (IOException e) {
			// something bad happened
			log(e.getMessage());
		}
	}

	/**
	 * The following method is responsible for processing a single HTTP request
	 * command.
	 *
	 * @param s
	 *            --- the socket over which the request will be communicated.
	 */
//	public void processRequest(Socket s) {
//		try {
//			String request = readRequest(s);
//			log("RECEIVED REQUEST: " + request.length() + " bytes");
//			String httpCommand = stripHttpGetCommand(request);
//			String page = httpCommand.split(" ")[1];
//			if(page.equals("/")) {
//				// auto convert empty page request into index page.
//				page = "/index.html";
//			}
//
//			// Determine the file name, by appending the root.  Note, we need to ensure that the right "separator" is used for path names.  For example, on windows the separate char is "\", whilst on UNIX it is "/".  However, all HTTP get commands use "/".
//			String filename = (root + page).replace('/',File.separatorChar);
//			// Now, check if file exists
//			if(new File(filename).exists()) {
//				// Yes, it exists!!
//				sendFile(filename,s);
//			} else {
//				// No, the file doesn't exist.
//				send404(page,s);
//			}
//
//		} catch(IOException e) {
//			log("I/O Error - " + e);
//		}
//	}

//	public static void send404(String page, Socket s) throws IOException {
//		PrintStream output = new PrintStream(s.getOutputStream());
//		output.println("HTTP/1.1 200 OK");
//		output.println("Content-Type: text/html; charset=UTF-8\n");
//		output.println("<h1>Not Found</h1>\n\n"+
//		"The requested URL " + page + " was not found on this server.\n");
//	}


	private synchronized void log(String message) {
		System.out.println(message);
	}

	/**
	 * This method looks for the HTTP GET command, and returns that; or, null if
	 * none was found.
	 *
	 * @param request
	 * @return
	 */
	public String stripHttpGetCommand(String request) throws IOException {
		BufferedReader r = new BufferedReader(new StringReader(request));
		String line = "";
		while((line = r.readLine()) != null) {
			if(line.startsWith("GET")) {
				// found the get command
				return line;
			}
		}
		return null;
	}

	/**
	 * This method reads all possible data from the socket and returns it.
	 *
	 * @param s
	 * @return
	 * @throws IOException
	 */
//	public  String readRequest(Socket s) throws IOException {
//		Reader input = new InputStreamReader(new BufferedInputStream(s.getInputStream()));
//		String request = "";
//		char[] buf = new char[1024];
//		int nread;
//
//		// Read from socket until nothing left.
//		do {
//			nread=input.read(buf);
//			System.out.println(nread);
//			String in = new String(buf,0,nread);
//			request += in;
//		} while(nread == 1024);
//
//		return request;
//	}

	/**
	 * Transmit file to socket in 1024 byte chunks.
	 *
	 * @param filename
	 * @param s
	 * @throws IOException
	 */
//	public  void sendFile(String filename, Socket s) throws IOException {
//		OutputStream output = s.getOutputStream();
//		PrintStream pout = new PrintStream(output);
//		FileInputStream input = new FileInputStream(filename);
//
//		pout.println("HTTP/1.1 200 OK");
//		if(filename.endsWith("jpg")) {
//			pout.println("Content-Type: image/jpeg; charset=UTF-8\n");
//		} else {
//			pout.println("Content-Type: text/html; charset=UTF-8\n");
//		}
//		pout.flush();
//
//		try {
//			int n;
//			while((n = input.read(file_buffer)) > 0) {
//				output.write(file_buffer, 0, n);
//				// I have introduced the following artificial delay specifically
//				// to throttle the rate of transmission. This is necessary since
//				// the server will be running on the same machine as the
//				// web-browser and, hence, will be extremely fast compared with
//				// normal. Thus, the pause helps us to see the real problem.
//				// Removing this line is not how to solve this lab :)
//				pause(25);
//			}
//		} finally {
//			// We must close the sockets, no matter what happens.
//			output.close();
//			input.close();
//		}
//	}

//	private  byte[] file_buffer = new byte[1024];
//
//	private  void pause(int milliseconds) {
//		try {
//			Thread.sleep(milliseconds);
//		} catch(InterruptedException e) {
//
//		}
//	}
}

class WorkerThread extends Thread {

	Socket socket;
	Server server;
	String root;

	public WorkerThread(Server server, Socket socket, String root){
		this.socket = socket;
		this.server = server;
		this.root = root;
	}

	public void run() {
		new ProcessRequest(server, socket, root);
		try {
//			System.out.println("THREAD RUN");
			//				Server.this.processRequest(s);
			// new process request class ... socket and server 
			// ??? 
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		log("ACCEPTED CONNECTION FROM: " + socket.getInetAddress());
	}
}



class ProcessRequest {

	private Socket s;
	private String root;
	private Server serv;

	public ProcessRequest(Server serv, Socket s, String root){
		this.s = s;
		this.root = root;
		this.serv = serv;
	}

	/**
	 * The following method is responsible for processing a single HTTP request
	 * command.
	 *
	 * @param s
	 *            --- the socket over which the request will be communicated.
	 */
	public void processRequest() {
		try {
			String request = readRequest(s);
//			serv.log("RECEIVED REQUEST: " + request.length() + " bytes");
			String httpCommand = stripHttpGetCommand(request);
			String page = httpCommand.split(" ")[1];
			if(page.equals("/")) {
				// auto convert empty page request into index page.
				page = "/index.html";
			}

			// Determine the file name, by appending the root.  Note, we need to ensure that the right "separator" is used for path names.  For example, on windows the separate char is "\", whilst on UNIX it is "/".  However, all HTTP get commands use "/".
			String filename = (root + page).replace('/',File.separatorChar);
			// Now, check if file exists
			if(new File(filename).exists()) {
				// Yes, it exists!!
				sendFile(filename,s);
			} else {
				// No, the file doesn't exist.
				send404(page,s);
			}

		} catch(IOException e) {
//			serv.log("I/O Error - " + e);
		}
	}

	public void send404(String page, Socket s) throws IOException {
		PrintStream output = new PrintStream(s.getOutputStream());
		output.println("HTTP/1.1 200 OK");
		output.println("Content-Type: text/html; charset=UTF-8\n");
		output.println("<h1>Not Found</h1>\n\n"+
		"The requested URL " + page + " was not found on this server.\n");
	}


	/**
	 * This method looks for the HTTP GET command, and returns that; or, null if
	 * none was found.
	 *
	 * @param request
	 * @return
	 */
	public String stripHttpGetCommand(String request) throws IOException {
		BufferedReader r = new BufferedReader(new StringReader(request));
		String line = "";
		while((line = r.readLine()) != null) {
			if(line.startsWith("GET")) {
				// found the get command
				return line;
			}
		}
		return null;
	}

	/**
	 * This method reads all possible data from the socket and returns it.
	 *
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public String readRequest(Socket s) throws IOException {
		Reader input = new InputStreamReader(new BufferedInputStream(s.getInputStream()));
		String request = "";
		char[] buf = new char[1024];
		int nread;

		// Read from socket until nothing left.
		do {
			nread=input.read(buf);
			String in = new String(buf,0,nread);
			request += in;
		} while(nread == 1024);

		return request;
	}

	/**
	 * Transmit file to socket in 1024 byte chunks.
	 *
	 * @param filename
	 * @param s
	 * @throws IOException
	 */
	public void sendFile(String filename, Socket s) throws IOException {
		OutputStream output = s.getOutputStream();
		PrintStream pout = new PrintStream(output);
		FileInputStream input = new FileInputStream(filename);

		pout.println("HTTP/1.1 200 OK");
		if(filename.endsWith("jpg")) {
			pout.println("Content-Type: image/jpeg; charset=UTF-8\n");
		} else {
			pout.println("Content-Type: text/html; charset=UTF-8\n");
		}
		pout.flush();

		try {
			int n;
			while((n = input.read(file_buffer)) > 0) {
				output.write(file_buffer, 0, n);
				// I have introduced the following artificial delay specifically
				// to throttle the rate of transmission. This is necessary since
				// the server will be running on the same machine as the
				// web-browser and, hence, will be extremely fast compared with
				// normal. Thus, the pause helps us to see the real problem.
				// Removing this line is not how to solve this lab :)
				pause(25);
			}
		} finally {
			// We must close the sockets, no matter what happens.
			output.close();
			input.close();
		}
	}

	private byte[] file_buffer = new byte[1024];

	private void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch(InterruptedException e) {

		}
	}
}