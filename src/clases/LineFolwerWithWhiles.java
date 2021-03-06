package clases;

import josx.platform.rcx.Button;
import josx.platform.rcx.ButtonListener;
import josx.platform.rcx.LCD;
import josx.platform.rcx.Motor;
import josx.platform.rcx.Sensor;
import josx.platform.rcx.SensorListener;
import josx.robotics.TimingNavigator;

public class LineFolwerWithWhiles {
	int white=0;
	int black =0;
	int gray =0;
	int contador=0,contadorMovimientos=0;
	Sensor sensor1 = Sensor.S1;
	Sensor sensor2 = Sensor.S3;
	char movimientos [] ={'D','I','I','I'};
	TimingNavigator robot = new TimingNavigator(Motor.C, Motor.A,  5.475f, 4.03f);
	
	public LineFolwerWithWhiles() throws InterruptedException{
	
	sensor1.setTypeAndMode(3, 0X80);
	sensor2.setTypeAndMode(3, 0X80);
	sensor1.activate();
	sensor2.activate();
	  Button.VIEW.addButtonListener(new ButtonListener() {
		
		@Override
		public void buttonReleased(Button b) {
			contador++;
			
		}
		
		@Override
		public void buttonPressed(Button b) {
			if(contador==0){
				white = sensor1.readValue();
				LCD.showNumber(white);
			}else if(contador==1){
				black = sensor1.readValue();
				LCD.showNumber(black);
			}else if(contador==2){
				gray = (white+black)/2;
				LCD.showNumber(gray);
			}else if(contador==3){
					start();				
			}//clase addSensorListener
			
		}
		
	});
	  
	  
	  Button.RUN.waitForPressAndRelease();
	  
	}//Fin del m�todo constructor
	public void start(){
		

		    sensor1.addSensorListener(new SensorListener() {
				
				@Override
				public void stateChanged(Sensor aSource, int aOldValue, int aNewValue) {
					try{
						
					
					int error = aNewValue - gray;
					if(sensor2.readValue()-gray<0){
						getMovimientos();
					}else{
						 if(error>2){
							    Motor.A.setPower(3);
					     		Motor.C.setPower(2);
					     		Motor.A.forward();
					     		Motor.C.backward();
					     		
					     		
					         }else
					         if(error>-2){
					        	Motor.A.setPower(6);
					     		Motor.C.setPower(6);
					     		robot.forward();
					        	
					         }else{
					        	    Motor.C.setPower(3);
						     		Motor.A.setPower(2);
						     		Motor.C.forward();
						     		Motor.A.backward();
						     		
						     	 
					         }
					}
			        
			     Thread.sleep(2);    
				}catch (Exception e) {
					
				}
				
			}});
		    
		    
    
	}//Fin metodo start
	
	/****Zona de movmientos********/
	//Derecha
	public void derecha(){
		try {
			Motor.A.setPower(7);
	    	Motor.C.setPower(7);
	    	robot.travel(8);
	    	robot.stop();
	    	robot.rotate(-90);
	        Thread.sleep(20);
	    	
		} catch (Exception e) {
			
			start();
		}
    	
	}
	//Izquierda
	public void izquierda(){
		try {

			Motor.A.setPower(7);
	    	Motor.C.setPower(7);
	    	robot.travel(8);
	    	robot.stop();
	    	robot.rotate(90);
	    	Thread.sleep(20);
		} catch (Exception e) {
		
			start();
		}
	}
	//Abajo
	public void abajo(){
		try {
	
			robot.stop();
			Motor.A.setPower(7);
			Motor.C.setPower(7);
			robot.travel(-4);
			robot.stop();
			robot.rotate(-180);
			Thread.sleep(20);
		} catch (Exception e) {
			
			start();
		}
	}
	//Arriba
	public void arriba(){
		try {
		
			Motor.A.setPower(6);
			Motor.C.setPower(6);
			robot.travel(9);
			Thread.sleep(20);
		} catch (Exception e) {
			
			start();
		}
	} 
	
	//M�todo que devuelve el movimiento
	public void getMovimientos(){
		char orden = movimientos[contadorMovimientos];
    	contadorMovimientos++;
    	if(contadorMovimientos==movimientos.length){
    		robot.stop();
    	}else
    	if(orden=='I'){
    		izquierda();
    	}else if(orden=='D'){
    		derecha();
    	}else if(orden=='A'){
    		abajo();
    	}else if(orden=='T'){
    		arriba();
    	}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new LineFolwerWithWhiles();
	}

}
