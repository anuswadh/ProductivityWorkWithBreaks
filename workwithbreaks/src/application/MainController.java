package application;

import java.awt.Checkbox;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.animation.PauseTransition;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class MainController implements Initializable{

	boolean disabledFlag = false;
	boolean runFlag = true;
	boolean incDec = true;
	int timeout = 100;
	int initTtimeout = 500;

	@FXML
	private TextField repeatTF;
	@FXML
	private Button plusButton;
	@FXML
	private Button minusButton;
	@FXML
	private ComboBox<Integer> taskTime	;
	@FXML
	private ComboBox<Integer> breakTime	;
	

	ObservableList<Integer> taskArray = FXCollections.observableArrayList() ;
	ObservableList<Integer> breakArray = FXCollections.observableArrayList() ;
		
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		taskArray.add(10);
		taskArray.add(15);
		taskArray.add(20);
		taskArray.add(25);
		taskArray.add(30);
		taskArray.add(40);
		taskArray.add(45);
		taskArray.add(50);
		taskArray.add(60);
		taskArray.add(90);
		taskArray.add(120);
		
		breakArray.add(5);
		breakArray.add(10);
		breakArray.add(15);
		breakArray.add(20);
		breakArray.add(25);
		breakArray.add(30);

		taskTime.setItems(taskArray);
		breakTime.setItems(breakArray);
		
	}
	
	
	@FXML
	public void increaseOrDecreasePressed(MouseEvent event) {
		//System.out.println(repeatTF.getText());
	//	if(((Button)event.getSource()).getText().equals("+")) {
			incDec = ((Button)event.getSource()).getText().equals("+");
	//	}
		runFlag = true;
		Thread t = new Thread(new IncreaseOrDecrease(), "inc/dec");
		t.setPriority(Thread.MIN_PRIORITY);
		t.start();
	}	
	public void increaseOrDecreaseNumberReleased(MouseEvent event) {
		//System.out.println(repeatTF.getText());
		Thread t = new Thread() {
			@Override
			public void run() {
				runFlag = false;
			}
		};
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}	

	
	public void untilRestartChecked(ActionEvent event) {
	//	if(((Checkbox)event.getSource()))
	//	System.out.println(((Checkbox)event.getSource()).getLabel());
		disabledFlag = !disabledFlag;
		minusButton.setDisable(disabledFlag);
		plusButton.setDisable(disabledFlag);		
		repeatTF.setDisable(disabledFlag);
	}
	
	
	
	class IncreaseOrDecrease implements Runnable{

		@Override
		public void run() {
			increaseOrDecreaseVal();
			try {
				TimeUnit.MILLISECONDS.sleep(initTtimeout);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Thread.yield();

			 while (runFlag) {
				increaseOrDecreaseVal();

				try {
					TimeUnit.MILLISECONDS.sleep(timeout);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("inside while runflag");
					e.printStackTrace();
					
				}
				Thread.yield();

			}

		}//run

		private void increaseOrDecreaseVal() {
			int curVal = Integer.parseInt(repeatTF.getText());
			if(incDec) {
				repeatTF.setText(Integer.toString(curVal +1));
			}else {
				if(curVal > 0 ) {
					repeatTF.setText(Integer.toString(curVal - 1));
				}
			}
		}
		
	}//IncreaseOrDecrease Class



	 
}
