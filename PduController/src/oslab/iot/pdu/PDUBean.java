package oslab.iot.pdu;
import java.io.*;
import java.net.*;

/**
 * @author cg
 * PDU controler
 * functions:
 * 		set pdu switch
 * 		get switch status
 * 		get anology including: voltage,current,humidty,inner&outer temperature 
 */


public class PDUBean {
   private Socket s = null;
   private InputStream  in = null;
   private OutputStream out = null;
   private static final byte[] signal = { 0x00, 0x00,0x00 , 0x00  ,
	   		                   0x00 , 
	   		                   0x06 , // 5 number of bytes following 
	   		                   0x01 ,  
	   		                   0x05 , // 7 Function flag : 01 get on/off ; 05 set on/off; 03 get sensor  
	                           0x00 , // 8 lower address
	                           0x03 , // 9 address 
	                           (byte)0x00 , //10 on/off 
	                           0x00        // 11 the number of registers reading
	                         };
   // 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17
   //00 00 00 00 00 06 01 05 00 00 FF 00          -- 0x05 seting return value the same as set value
   //00 00 00 00 00 04 01 01 01 B6
   //00 00 00 00 00 05 01 03 02 00 DD
   
   //00 00 00 00 00 0D 01 03 0A 00 EB 00 00 00 16 00 2A 00 1A --
   //00 00 00 00 00 06 01 03 00 00 00 05                      -- get V,A,T,h,T
   
   public void getTest(int flag){
	   signal[7] = 0x01;
	   signal[9] = (byte)flag;
	   signal[10]= 0x00;
	   signal[11]= 0x09;
	   try{	
	       out.write(signal);
	       out.flush();
	       // get back
	       byte[] rec = new byte[1024];
	       //System.out.println("Sending completed!" );
   	    
   	       in.read(rec);
   	    
   	      
        // System.out.println( new Integer(256*(rec[10-1])+(rec[10]+256)%256).toString() );
         System.out.println(Integer.toString((256+rec[9])%256) );  
          int iSwch = (256+rec[9])%256;
         for (int i = 0 ;i<8 ;i++){
  		   
  		   System.out.print(Integer.toHexString(iSwch%2));
  		   iSwch = iSwch /2;
  		}
  		System.out.println();  
	   }catch (Exception e) { System.out.println(e.toString());
	   						  
	   	}
   }
   /** get switch status 
 * @return
 */
public int getSwitch(){
	   signal[7] = 0x01;
	   signal[9] = 0x00;
	   signal[10]= 0x00;
	   signal[11]= 0x08;
	   try{
	       out.write(signal);
	       out.flush();
	       // get back
	       byte[] rec = new byte[1024];
	       //System.out.println("Sending completed!" );
   	    
   	       in.read(rec);
   	    
   	      
   	        System.out.println(Integer.toString((256+rec[9])%256) );
   	      return (256+rec[9])%256;
	       
	   }catch (Exception e) { System.out.println(e.toString());
	   						  return 0;
	   	}
   }

   /// get anology
   private int getAnology(int flag){
	   signal[7] = 0x03;
	   signal[9] = 0x00;
	   signal[10]= 0x00;
	   signal[11]= 0x05;
	   try{
	       out.write(signal);
	       out.flush();
	       // get back
	       byte[] rec = new byte[1024];
	       //System.out.println("Sending completed!" );
   	    
   	       in.read(rec);
   	    
   	      
          //System.out.println(new Integer(256*(rec[flag-1])+(rec[flag]+256)%256).toString() );
   	      
          return 256*(rec[flag-1])+(rec[flag]+256)%256;
	       
	   }catch (Exception e) { System.out.println(e.toString());
	   						  return 0;
	   	}
   }
  
   //get voltage 
   public int getVoltage(){
	   return getAnology(10);
   }
   
   //get current
   public int getCurrent(){
	   return getAnology(12);
   }
   //get temperature outside
   public int getOutTemperature(){
	   return getAnology(14);
   }
   //get humidty
   public int getHumidty(){
	   return getAnology(16);
   }
   //get temperature inside
   public int getInnerTemperature(){
	   return getAnology(18);
   }
   ///////////////////////
   
  /** construction 
 * @param add
 * @param port
 */
PDUBean(String add,int port){
	   try{
	       InetSocketAddress sa = new InetSocketAddress(add,port);
	       s = new Socket();
	       s.connect(sa);
	       in = s.getInputStream();
	       out = s.getOutputStream();
	   }catch(Exception e){
		   System.out.println("Connecting Error " + e.toString());
	   }
   }


   public void closePdu(){
	   try{
		   in.close();
		   out.close();
		   s.close();
	   }catch(Exception e){
		   System.out.println("Connecting Error " + e.toString());
	   }
	   
   } 
   
   public void setPdu(int num , boolean on_off){
	   signal[7] = 0x05;
	   signal[9] = (byte) num;
	   if ( on_off ){
	       signal[10] = (byte)0xFF;
	   }else {signal[10] = 0x00;}
	   signal[11] = 0x00;
	   try{
	       out.write(signal);
	       out.flush();
	       // get back
	       byte[] rec = new byte[1024];
	       System.out.println("Sending completed!" );
   	    
   	       in.read(rec);
   	    
   	      for (int i =0 ;i<10;i++){
   	        System.out.print(Byte.toString(rec[i]) );
   	      }
   	      System.out.println();
	       
	   }catch (Exception e) { System.out.println(e.toString());}
   }
   
}
