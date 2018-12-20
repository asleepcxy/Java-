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
	    	//在提交的的时候判断
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
	    //括号内为日期格式，y代表年份，M代表年份中的月份（为避免与小时中的分钟数m冲突，此处用M），d代表月份中的天数
	        if (gift_text== null || gift_text.getText().length() == 0) {
	            errorMessage += "请只选择一个选项!\n"; 
	        }
	        if (money_need.getText() == null || money_need.getText().length() == 0
	        		||!isNumber(money_need.getText())
	        		||Double.valueOf(money_need.getText())<0) {
	        	errorMessage +="请输入正确的金额!\n"; 
	        }
	        if (deadline.getValue() == null || deadline.getValue().toString().length() == 0
	        		||!check(deadline.getValue().toString())) {
	            errorMessage += "无效的日期选择!\n"; 
	        } 
	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.  
		    	Alert alert = new Alert(AlertType.ERROR);
		    	alert.setTitle("出错啦");
		    	alert.setHeaderText(null);
		    	alert.setContentText(errorMessage); 
		    	alert.showAndWait();
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
