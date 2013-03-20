package oslab.iot.pdu;
import java.io.*;
import java.net.*;

import oslab.iot.util.ReadProperties;

public class PDUController {

	/**
	 * @param args
	 */
	private int pdu_num ; // the available plugs of PDU
	private PDUBean pdu = null;
	
	public PDUController (){

		ReadProperties p = new ReadProperties("pdu.properties");
		String ip = p.getProp("ip");
		int port = Integer.parseInt(p.getProp("port"));
		pdu_num = Integer.parseInt(p.getProp("num"));
		
		pdu = new PDUBean(ip, port);
		
	}
	
	public void setPdu(int pos,boolean op){
		if(pos>=0 && pos <= pdu_num)
			pdu.setPdu(pos, op);
		if(pos>pdu_num)
			for(int i = 0 ;i<pdu_num;i++){
				pdu.setPdu(i, op);
			}
	}
	
	public static void main(String[] args) throws IOException {

		PDUController ctlr = new PDUController();
		
		String command = args[0];
		
		if(command.equals("-set")){
			int pos = Integer.parseInt(args[1]);
			boolean op;
			if(args[2].equals("0"))
				op = false;
			else
				op = true;
			
			ctlr.setPdu(pos, op); // set operation 
			
		}
		
       /* old test 
		PDUBean pdu = new PDUBean("192.168.3.17",502);
		System.out.println("Voltage is " +Integer.toString(pdu.getVoltage()));
		System.out.println("Current is " +pdu.getCurrent());
		System.out.println("TempOut is " +pdu.getOutTemperature());
		System.out.println("Humidty is " +pdu.getHumidty());
		System.out.println("TempInn is " +pdu.getInnerTemperature());
		pdu.getTest(0);
		pdu.setPdu(0, true);
		pdu.setPdu(1, true);
		pdu.setPdu(2, true);
		pdu.setPdu(3, true);
		pdu.setPdu(4, true);
		pdu.setPdu(5, true);
		pdu.setPdu(6, true);
		pdu.setPdu(7, true);
		int iSwch = (int)pdu.getSwitch();
		for (int i = 0 ;i<8 ;i++){
		   
		   System.out.print(Integer.toHexString(iSwch%2));
		   iSwch = iSwch /2;
		}
		System.out.println();
		
	*/

}
}
