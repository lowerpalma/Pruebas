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
	boolean sensor1Bandera = true, sensor2Bandera = true;
	String movimientos [] ={"Derecha","Izquierda","Arriba"};
	
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
						
					TimingNavigator robot = new TimingNavigator(Motor.C, Motor.A,  5.475f, 4.03f);
					int error = aNewValue - gray;
			        if(sensor2.readValue()-gray<0){
		        		getMovimientos(robot);
		        	}else if(error>2) {
		        		if(sensor2.readValue()-gray<0){
			        		getMovimientos(robot);
			        	}
			        	Motor.A.setPower(2);
						Motor.C.setPower(2);
						robot.rotate(-9);
						pause(15);
						robot.travel(2);
					}else if(error>-2){
						if(sensor2.readValue()-gray<0){
			        		getMovimientos(robot);
			        	}
			        	Motor.A.setPower(3);
						Motor.C.setPower(3);
						robot.rotate(-2);
						pause(15);
						robot.travel(3);
						
			        }else if(error<=-2) {
			        	if(sensor2.readValue()-gray<0){
			        		getMovimientos(robot);
			        	}
			        	robot.stop();
						Motor.A.setPower(5);
						Motor.C.setPower(5);
						robot.rotate(9);
						pause(15);
					}
			        if(sensor2.readValue()-gray<0){
			        	getMovimientos(robot);
		        	}
				}catch (Exception e) {
					start();
				}
				
			}});
		}
		
	/****Zona de movmientos********/
	//Derecha
	public void derecha(TimingNavigator robot){
		try {
			Motor.A.setPower(7);
	    	Motor.C.setPower(7);
	    	robot.travel(7);
	    	robot.stop();
	    	robot.rotate(-90);
	    	pause(25);
	    	
		} catch (Exception e) {
			
			start();
		}
    	
	}
	//Izquierda
	public void izquierda(TimingNavigator robot){
		try {

			Motor.A.setPower(7);
	    	Motor.C.setPower(7);
	    	robot.travel(7);
	    	robot.stop();
	    	robot.rotate(90);
	    	pause(25);
	    	sensor1Bandera =true;
		} catch (Exception e) {
		
			start();
		}
	}
	//Abajo
	public void abajo(TimingNavigator robot){
		try {
	
			robot.stop();
			Motor.A.setPower(7);
			Motor.C.setPower(7);
			robot.travel(-4);
			robot.stop();
			pause(15);
			robot.rotate(-180);
			pause(25);
	    	sensor1Bandera =true;
		} catch (Exception e) {
			
			start();
		}
	}
	//Arriba
	public void arriba(TimingNavigator robot){
		try {
		
			Motor.A.setPower(6);
			Motor.C.setPower(6);
			robot.travel(9);
			pause(15);
			
			pause(25);
		} catch (Exception e) {
			
			start();
		}
	} 
	
	/*M�todo pause del robot*/
	public void pause (int delay){
		try{
			Thread.sleep(delay);
		}catch (InterruptedException e) {
			start();
		}
	}//Fin del m�todo pause 
	
	//M�todo que devuelve el movimiento
	public void getMovimientos(TimingNavigator robot){
		String orden = movimientos[contadorMovimientos];
    	contadorMovimientos++;
    	if(orden.equals("Izquierda")){
    		izquierda(robot);
    	}else if(orden.equals("Derecha")){
    		derecha(robot);
    	}else if(orden.equals("Abajo")){
    		abajo(robot);
    	}else if(orden.equals("Arriba")){
    		arriba(robot);
    		contadorMovimientos =0;
    	}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new LineFolwerWithWhiles();
	}

}
