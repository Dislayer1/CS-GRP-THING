
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class Writer{
	static int i = -1;
boolean bool;
int shape;
double a2;
double a3;
double a1;
int s1;
int s2;
int s3;
long time;
String Ls;
String fs;
Writer(long t, int s1, boolean b){
	this.shape = 1;
	this.time = t;
	this.s1 = s1;
	this.bool = b;
}
Writer(long t, double a1, double a2, double a3, int s1, int s2, int s3, boolean b){
	this.shape =2;
	this.time = t;
	this.a1 = a1;
	this.a2 = a2;
	this.a3 = a3;
	this.s1 = s1;
	this.s2 = s2;
	this.s3 = s3;
	this.bool = b;
}
Writer(double a1, String f, String s, double g, boolean bool){
	this.shape = 3;
	this.Ls = s;
	this.bool = bool;
	this.fs = f;
	this.a1 = a1;
	this.a2 = g;
	
}
Writer(boolean b){
	this.bool = b;
}
Writer(int s){
	this.shape = 4;
	this.bool = true;
}
void write() {
	i++;
	File logfile = new File("DataLog.txt");
	try(FileWriter w = new FileWriter(logfile, bool)){
		if(this.bool == false) {
			w.write("");
			return;
		}
		if(this.shape == 1) {
		w.write( " " + i + "# " +  "Square: "+s1 +" (time: "+time+ " seconds" + ") \n");
		}else if(this.shape == 2) {
			w.write( " " + i + "# " + "Triangle: "+s1 + ", " + s2 + ", " + s3 +" (angles: "+ a1 + ", " + a2 + ", " + a3 + "; " + "time: "+time+" seconds" + ") \n");
		}else if(this.shape == 3){
			
			w.write("\n Largest shape = " + Ls + " (" + a2 + ")" + "\n Most drawn shape = " + fs + "\n" + "\n Average drawing time = " + a1 + " seconds");
		}else if(this.shape == 4) {
			System.out.println("-----------------------------------------------------\r\n"
					+ "LOG FILE SAVED\r\n"
					+ "-----------------------------------------------------");
			System.out.println("Location:\r\n"
					+ logfile.getAbsolutePath());
		}
		
	}catch(IOException e) {
		System.out.println(e);	
	}
	
	
}
}

