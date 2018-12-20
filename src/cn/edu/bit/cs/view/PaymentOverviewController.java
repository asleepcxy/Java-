package cn.edu.bit.cs.view;
 
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import cn.edu.bit.cs.model.Payment;
import cn.edu.bit.cs.util.Item; 
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PaymentOverviewController   { 
	 @FXML
	 private DatePicker addDateDatePicker ;
	 @FXML
//	 ����ѡ�������
	 private ToggleButton food;
	 @FXML
	 private ToggleButton clothes;
	 @FXML
	 private ToggleButton transport; 	 
	 @FXML
	 private ToggleButton entertainment;
	 @FXML
	 private ToggleButton digital;
	 @FXML
	 private ToggleButton gift; 
	 
	 @FXML
	 private ToggleButton book;
	 @FXML
	 private ToggleButton daily_use;
	 @FXML
	 private ToggleButton game;
	 @FXML
	 private ToggleButton hotel; 
	 @FXML
	 private ToggleButton  heathcare; 
	 @FXML
	 private ToggleButton other;
	  
	 @FXML 
	 private TextField valueField; 
	 
	 @FXML
	 private TextArea remarkField;
	  //new ToggleButton[3*4],
	 
	private String strthing=new String();
	 private Stage paymentStage;
	 private Payment payment;
	 private boolean okClicked = false;
	 
	 final ToggleGroup group = new ToggleGroup();
	 
	/**
	* Initializes the controller class. This method is automatically called
	* after the fxml file has been loaded.
	*/
	 @FXML
	 private void initialize() {
	 }

	 /**
	     * Sets the stage of this dialog.
	     * 
	     * @param dialogStage
	     */
	    public void setDialogStage(Stage paymentStage) {
	        this.paymentStage=paymentStage;
	    }

	    /**
	     * Sets the income to be edited in the dialog.
	     * 
	     * @param income
	     */
	    public void setPayment(Payment payment) {
	    	 this.payment=payment;
	    	 ToggleButton[] thingsButton = {
	    			 food, clothes, gift,   entertainment, digital, transport,  
	    			  book,  daily_use,  game,  hotel, heathcare,  other}; 
	    	 for (ToggleButton toggleButton : thingsButton) {
					toggleButton.setToggleGroup(group);
				}
		    food.setSelected(true);
	        valueField.setText( String.valueOf(payment.getValue()) );
	        addDateDatePicker.setValue(payment.getaddDate() );  
	        remarkField.setText("");
	    }

	    
	    
	    /**
	     * Returns true if the user clicked OK, false otherwise.
	     * 
	     * @return
	     */
	    public boolean issubmitClicked() {
	        return okClicked;
	    }

	    /**
	     * Called when the user clicks ok.
	     */
	    @FXML
	    private void handleOk() {
	    	//���ύ�ĵ�ʱ���ж�
	    	ToggleButton[] thingsButton = {
	    			 food, clothes, gift,   entertainment, digital, transport,  
	    			  book,  daily_use,  game,  hotel, heathcare,  other}; 
		    	for (ToggleButton temp:thingsButton) { 
		    		if(temp.isSelected()==true) {
		    			strthing = temp.getId();
		    		}  
		    	}
	        if (isInputValid()) {   
				payment.setThing(Item.change(Item.toItem(strthing))); 
	        	payment.setValue(Double.valueOf(valueField.getText()));
	        	payment.setaddDate(addDateDatePicker.getValue()); 
	        	payment.setRemark(remarkField.getText());
	            okClicked = true;
	            paymentStage.close();
	        }
	    }

	    /**
	     * Called when the user clicks cancel.
	     */
	    @FXML
	    private void handleCancel() {
	    	paymentStage.close();
	    }

	    /**
	     * Validates the user input in the text fields.
	     * 
	     * @return true if the input is valid
	     */
	    private boolean isInputValid() {
	    	String errorMessage = ""; 
	        if (valueField.getText() == null || valueField.getText().length() == 0
	        		||!isNumber(valueField.getText())||Double.valueOf(valueField.getText())<0) {
	            errorMessage += "��������ȷ�Ľ��!\n"+valueField.getText(); 
	            //����ΪС����������
	        } 
	        if (addDateDatePicker.getValue() == null || addDateDatePicker.getValue().toString().length() == 0
	        		||!check(addDateDatePicker.getValue().toString())) {
	            errorMessage += "��ѡ����ȷ������!\n"; 
	        }  
	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.   
		    		 // Nothing selected.  
			    	Alert alert = new Alert(AlertType.ERROR);
			    	alert.setTitle("������");
			    	alert.setHeaderText(null);
			    	alert.setContentText(errorMessage);  
			    	alert.show();
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
 
 