/*
altitude max2m:	java ARDrone 192.168.1.1 AT*CONFIG=1,\"control:altitude_max\",\"2000\"
Takeoff:	java ARDrone 192.168.1.1 AT*REF=101,290718208
Landing:	java ARDrone 192.168.1.1 AT*REF=102,290717696
Hovering:	java ARDrone 192.168.1.1 AT*PCMD=201,1,0,0,0,0
gaz 0.1:	java ARDrone 192.168.1.1 AT*PCMD=301,1,0,0,1036831949,0
gaz -0.1:	java ARDrone 192.168.1.1 AT*PCMD=302,1,0,0,-1110651699,0
pitch 0.1:	java ARDrone 192.168.1.1 AT*PCMD=303,1,1036831949,0,0,0
pitch -0.1:	java ARDrone 192.168.1.1 AT*PCMD=304,1,-1110651699,0,0,0
yaw 0.1:	java ARDrone 192.168.1.1 AT*PCMD=305,1,0,0,0,1036831949
yaw -0.1:	java ARDrone 192.168.1.1 AT*PCMD=306,1,0,0,0,-1110651699
roll 0.1:	java ARDrone 192.168.1.1 AT*PCMD=307,1,0,1036831949,0,0
roll -0.1:	java ARDrone 192.168.1.1 AT*PCMD=308,1,0,-1110651699,0,0
pitch -30 deg:	java ARDrone 192.168.1.1 AT*ANIM=401,0,1000
pitch 30 deg:	java ARDrone 192.168.1.1 AT*ANIM=402,1,1000
*/

import java.net.*;
import java.util.*;
import java.io.*;

class ARDroneFly {
    private static DatagramSocket socket;
    private static int NUM_COMMANDS = 1200;

    private static String right = "AT*PCMD=1,1,1056964608,0,0,0\r";
    private static String left = "AT*PCMD=1,1,-1090519040,0,0,0\r";
    private static String backward = "AT*PCMD=1,1,0,1056964608,0,0\r";
    private static String forward = "AT*PCMD=1,1,0,-1090519040,0,0\r";
    private static String land = "AT*REF=102,290717696\r";
    private static String takeoff = "AT*REF=101,290718208\r";

    private static byte[] ip_bytes;
    private static String prompt = "Please enter one of the following commands:\n" +
	                    "F to move drone forwards\n" +
	                    "B to move drone backwards\n" +
	                    "R to move drone right\n" +
	                    "L to move drone left\n" +
	                    "LD to land\n" +
	                    "T to takeoff\n" +
	                    "Q to quit\n";

    private static void move (String command) throws Exception {
	byte[] buffer = (command).getBytes();
	for (int i = 0; i < NUM_COMMANDS; i ++) {
	    DatagramPacket packet = 
		new DatagramPacket(buffer,
				   buffer.length,
				   InetAddress.getByAddress(ip_bytes), 
				   5556);
	    socket.send(packet);
	}
    }

    private static void moveOne (String command) throws Exception {
	byte[] buffer = (command).getBytes();
	DatagramPacket packet = 
	    new DatagramPacket(buffer,
			       buffer.length,
			       InetAddress.getByAddress(ip_bytes), 
			       5556);
	socket.send(packet);
    }

    public static void main(String args[]) throws Exception {

	if (args.length<1) {
	    System.out.println("Usage: java ARDrone <IP>");
	    System.exit(-1);
	}

	StringTokenizer st = new StringTokenizer(args[0], ".");
	ip_bytes = new byte[4];

	if (st.countTokens() == 4){
 	    for (int i = 0; i < 4; i++){
		ip_bytes[i] = (byte)Integer.parseInt(st.nextToken());
	    }
	}
	else {
	    System.out.println("Incorrect IP address format: " + args[0]);
	    System.exit(-1);
	}

	socket = new DatagramSocket();
	socket.setSoTimeout(3000);

	Scanner input = new Scanner(System.in);

	
	while(true) {
	    //System.out.print(prompt);
	    
	    //String in = input.nextLine();
		FileInputStream fstream = new FileInputStream("myfile.txt");
		DataInputStream inn = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(inn));
		String in = "shivam";
		
		String strLine;
		while ((strLine = br.readLine()) != null) {

		System.out.println (strLine);
		}
		
		br.close();
		inn.close();
		fstream.close();
		
	    if (in.toLowerCase().equals("f")) {
		move(forward);
	    }
	    else if (in.toLowerCase().equals("b")) {
		move(backward);
	    }
	    else if (in.toLowerCase().equals("r")) {
		move(right);
	    }
	    else if (in.toLowerCase().equals("l")) {
		move(left);
	    }
	    else if (in.toLowerCase().equals("ld")) {
		moveOne(land);
	    }
	    else if (in.toLowerCase().equals("t")) {
		moveOne(takeoff);
	    }
	    else if (in.toLowerCase().equals("q")) {
		break;
	    }
	}
   }
}
