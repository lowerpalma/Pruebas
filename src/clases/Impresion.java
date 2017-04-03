package clases;
import josx.platform.rcx.*;
import josx.robotics.TimingNavigator;
public class Impresion {
	
	int white=0;
	int black =0;
	int gray =0;
	int contador=0;
	Sensor sensor1 = Sensor.S1;
	Sensor sensor2 = Sensor.S3;
	boolean sensor1Bandera = true, sensor2Bandera = true;
	
	
	public Impresion() throws InterruptedException{
	
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
				
				
				/*Evento del sensor uno*/
			    sensor1.addSensorListener(new SensorListener() {
					
					@Override
					public void stateChanged(Sensor aSource, int aOldValue, int aNewValue) {
						TimingNavigator robot = new TimingNavigator(Motor.C, Motor.A, 1.6f, 0.54f);
						int error = aNewValue - gray;
				        LCD.showNumber(error);
				        sensor2Bandera = false;
				        if(aNewValue<=black && sensor2.readValue()<=black){
				        	
				        	sensor2Bandera = false;
				        	sensor1Bandera = false;
				        	robot.stop();
				        	Motor.A.setPower(7);
				        	Motor.B.setPower(7);
				        	Motor.A.backward();
			        		Motor.B.forward();
			        		robot.travel(5);
				        	while(aNewValue>=white && sensor2.readValue()>=white){
				        		LCD.showNumber(69);
				        		robot.travel(15);
					        	pause(1000);
				        		Motor.A.backward();
				        		Motor.B.forward();
				        	}
				        	pause(500);
				        	sensor2Bandera = true;
				        	sensor1Bandera = true;
				        }else
				        if(sensor1Bandera){
				        	motorIzquierdo(error, robot);	
				        }
						
						
					}
					
				});
			    
			    
			    /*Evento del sensor dos*/
			    sensor2.addSensorListener(new SensorListener() {
					
					@Override
					public void stateChanged(Sensor aSource, int aOldValue, int aNewValue) {
						TimingNavigator robot = new TimingNavigator(Motor.C, Motor.A, 1.6f, 0.54f);
						int error = aNewValue - gray;
				        LCD.showNumber(error);
				        sensor1Bandera = false;
				        
				        if(sensor2Bandera){
							motorDerecho(error, robot);

				        }
						
					}
				});
			    
			    
				
				
			}//Fin el if 
			
		}
	});
	  
	  
	  Button.RUN.waitForPressAndRelease();
	  
	}//Fin del m�todo constructor
	
	/*M�todo pause del robot*/
	public void pause (int delay){
		try{
			Thread.sleep(delay);
		}catch (InterruptedException e) {
			
		}
	}//Fin del m�todo pause
	
	
	public void motorIzquierdo(int error, TimingNavigator robot){
		if(error>3){
			Motor.A.setPower(4);
			Motor.C.setPower(4);
			robot.rotate(-15);
			robot.travel(2);
		}else if(error>1){
			Motor.A.setPower(3);
			Motor.C.setPower(3);
			robot.rotate(-5);
			robot.travel(2);
		}else if(error>=-1){
			Motor.A.setPower(3);
			Motor.C.setPower(3);
			robot.travel(3);
		}else if(error>=-2){
			Motor.A.setPower(5);
			Motor.C.setPower(5);
			robot.rotate(10);
			robot.travel(3);
		}else{
			robot.stop();
			Motor.A.setPower(5);
			Motor.C.setPower(5);
			robot.rotate(15);
		}
		sensor2Bandera = true;
	}
	
	public void motorDerecho(int error, TimingNavigator robot){
		if(error>3){
			
			
			robot.stop();
			Motor.A.setPower(5);
			Motor.C.setPower(5);
			robot.rotate(15);
		}else if(error>1){
			Motor.A.setPower(3);
			Motor.C.setPower(3);
			robot.rotate(5);
			robot.travel(2);
		}else if(error>=-1){
			Motor.A.setPower(3);
			Motor.C.setPower(3);
			robot.travel(3);
		}else if(error>=-2){
			Motor.A.setPower(5);
			Motor.C.setPower(5);
			robot.rotate(-10);
			robot.travel(3);
		}else{
			Motor.A.setPower(4);
			Motor.C.setPower(4);
			robot.rotate(-15);
			robot.travel(2);
		}
		sensor1Bandera = true;
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
	      
	 new Impresion();     
	} //main()
	  
}
