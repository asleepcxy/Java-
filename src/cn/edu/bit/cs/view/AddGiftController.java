package cn.edu.bit.cs.view;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import cn.edu.bit.cs.model.Gift;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddGiftController {
	@FXML 
	TextField gift_text;
	@FXML
	TextField money_need;
	@FXML
	DatePicker deadline;
	
	private Gift gift;
	private boolean okClicked = false;
	private Stage addGiftStage ;
	
	/**
	* Initializes the controller class. This method is automatically called
	* after the fxml file has been loaded.
	*/
	 @FXML
	 private void initialize() {
	 }
	 
    public void setDialogStage(Stage addGiftStage) {
        this.addGiftStage=addGiftStage;
    }
	public boolean issubmitClicked() {
        return okClicked;
    }
	
	public void setGift(Gift gift) {
	      this.gift = gift;
	}
	 @FXML
	  private void handleOk() {
	    	//���ύ�ĵ�ʱ���ж�
		 if(this.addGiftStage == null)
	        	System.out.println("3");
	        if (this.isInputValid()) { 
	        	gift.setThing(gift_text.getText());
	        	gift.setMoney(Double.valueOf(money_need.getText()));
	        	gift.setDeadlineDate(deadline.getValue()); 
	        	gift.setDone(0);
	            okClicked = true;
	            addGiftStage.close();
				 
	        }
	    }

	    /**
	     * Called when the user clicks cancel.
	     */

	    /**
	     * Validates the user input in the text fields.
	     * 
	     * @return true if the input is valid
	     */
	    private boolean isInputValid() {
	    String errorMessage = "";
	    //������Ϊ���ڸ�ʽ��y������ݣ�M��������е��·ݣ�Ϊ������Сʱ�еķ�����m��ͻ���˴���M����d�����·��е�����
	        if (gift_text== null || gift_text.getText().length() == 0) {
	            errorMessage += "��ֻѡ��һ��ѡ��!\n"; 
	        }
	        if (money_need.getText() == null || money_need.getText().length() == 0
	        		||!isNumber(money_need.getText())
	        		||Double.valueOf(money_need.getText())<0) {
	        	errorMessage +="��������ȷ�Ľ��!\n"; 
	        }
	        if (deadline.getValue() == null || deadline.getValue().toString().length() == 0
	        		||!check(deadline.getValue().toString())) {
	            errorMessage += "��Ч������ѡ��!\n"; 
	        } 
	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.  
		    	Alert alert = new Alert(AlertType.ERROR);
		    	alert.setTitle("������");
		    	alert.setHeaderText(null);
		    	alert.setContentText(errorMessage); 
		    	alert.showAndWait();
	            return false;
	        }
	    }
	    
	    //�ж����ڵĺϷ���
	    static boolean check (String str) {
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			//������Ϊ���ڸ�ʽ��y������ݣ�M��������е��·ݣ�Ϊ������Сʱ�еķ�����m��ͻ���˴���M����d�����·��е�����
			try {
				sd.setLenient(false);
				//�˴�ָ������/ʱ������Ƿ��ϸ���true�ǲ��ϸ�falseʱΪ�ϸ�
				sd.parse(str);
				//�Ӹ����ַ����Ŀ�ʼ�����ı���������һ������
			}
			catch (Exception e) {
				return false;
			}
			return true;
		}
	    

	    
	    public static boolean isNumber(String str) {
	        //����������ʽ�ķ�ʽ���ж�һ���ַ����Ƿ�Ϊ���֣����ַ�ʽ�ж���Ƚ�ȫ
	        //�����ж�����������С��

	        boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
	        boolean isDouble = Pattern.compile("^(([1-9][0-9]*)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))$").matcher(str).find();

	        return isInt || isDouble; 
	} 
}
