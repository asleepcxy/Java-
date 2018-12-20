package cn.edu.bit.cs.view;

import java.text.SimpleDateFormat;
import java.util.function.IntConsumer;
import java.util.regex.Pattern;

import cn.edu.bit.cs.model.Income;
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

public class EditPaymentController {
	 @FXML
	 private DatePicker addDateDatePicker ;
	 @FXML
//	 可以选择的类型
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
	    public void setDialogStage(Stage incomeStage) {
	        this.paymentStage=incomeStage;
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
			   		 if(payment.getThing().equals(Item.change(Item.toItem(toggleButton.getId()))))
			   		 {
			   			 
			   			 toggleButton.setSelected(true);;
			   		 }
				}
	    valueField.setText( String.valueOf(payment.getValue()) );
	    addDateDatePicker.setValue(payment.getaddDate() );  
	    remarkField.setText(payment.getRemark());
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
	    	//在提交的的时候判断 
	   	 ToggleButton[] thingsButton = {
		   			food, clothes, gift,   entertainment, digital, transport,  
		  			  book,  daily_use,  game,  hotel, heathcare,  other};
	    	for (ToggleButton temp:thingsButton) { 

	    		System.out.println(temp.getId()+temp.isSelected());
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
	            errorMessage += "请输入正确的金额!\n"+valueField.getText(); 
	            //输入为小数或者整数
	        } 
	        if (addDateDatePicker.getValue() == null || addDateDatePicker.getValue().toString().length() == 0
	        		||!check(addDateDatePicker.getValue().toString())) {
	            errorMessage += "请选择正确的日期!\n"; 
	        }  
	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.   
		    		 // Nothing selected.  
			    	Alert alert = new Alert(AlertType.ERROR);
			    	alert.setTitle("出错啦");
			    	alert.setHeaderText(null);
			    	alert.setContentText(errorMessage);  
			    	alert.show();
	            return false;
	        }
	    }
	    
	  //判断日期的合法性
	    
	    static boolean check (String str) {
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			//括号内为日期格式，y代表年份，M代表年份中的月份（为避免与小时中的分钟数m冲突，此处用M），d代表月份中的天数
			 
			try {
				sd.setLenient(false);
				//此处指定日期/时间解析是否不严格，在true是不严格，false时为严格
				sd.parse(str);
				//从给定字符串的开始解析文本，以生成一个日期
			}
			catch (Exception e) {
				return false;
			}
			return true;
		}
	    
	    
	    public static boolean isNumber(String str) {
	        //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
	        //可以判断正负、整数小数

	        boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
	        boolean isDouble = Pattern.compile("^(([1-9][0-9]*)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))$").matcher(str).find();

	        return isInt || isDouble; 
	} 
}
