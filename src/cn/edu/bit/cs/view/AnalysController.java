package cn.edu.bit.cs.view;

import java.io.IOException;
import java.util.List;

import cn.edu.bit.cs.MainApp;
import cn.edu.bit.cs.model.Income;
import cn.edu.bit.cs.model.Payment;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AnalysController {
 
	private Stage AnalysStage;    
	private List<Payment> storepayment;
	private List<Income> storeincome;
	public void setstorepayment(List<Payment> payment ) {
		this.storepayment=payment;
	}
	
	public void setstoreincome(List<Income> income ) {
		this.storeincome=income;
	} 
	 @FXML
	 private void initialize() {
	 }
	 /**
	     * Sets the stage of this dialog.
	     * 
	     * @param dialogStage
	     */
	public void setDialogStage(Stage  analysStage) {
	    this.AnalysStage= analysStage;
	}
	  
	 /** 
          * 我的每月收入详情
     * @param  
     */
	public void  showMonthlyIncomeAndPayment() {
	    try {
	        // Load the fxml file and create a new stage for the pop up.
 
	    	FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(MainApp.class.getResource("view/ShowMonthlyIncomeAndPayment.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("我的每月收入情况");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner( AnalysStage);
	        dialogStage.setResizable(false);
	        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
			
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the incomes into the controller. 
	        ShowMonthlyIncomeAndPaymentController controller = loader.getController(); 
	        controller.setIncomeAndPaymentData( storeincome,storepayment); 
	         
	        dialogStage.show();

	    } catch (IOException e) {
	        e.printStackTrace(); 
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("出错啦");
	    	alert.setHeaderText(null);
	    	alert.setContentText(e.getMessage()); 
	    }
	}
	
	 @FXML
	    private void handleShowMonthlyIncomeAndPayment() {
	        this.showMonthlyIncomeAndPayment();;
	    } 

	 /** 
          * 我的每月收入详情
     * @param  
 	*/
	 public void showDailyPayment() {
    try {
        // Load the fxml file and create a new stage for the pop up.

    	FXMLLoader loader = new FXMLLoader(); 
        loader.setLocation(MainApp.class.getResource("view/ShowDailyPayment.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("我的每日支出情况");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner( AnalysStage);  
        dialogStage.setResizable(false);
        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
		
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
 
        ShowDailyPaymentController controller = loader.getController(); 
        controller.setdailyData( storepayment); 
         
        dialogStage.show();

    } catch (IOException e) {
        e.printStackTrace();

    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("出错啦");
    	alert.setHeaderText(null);
    	alert.setContentText(e.getMessage()); 
    }
}

 	@FXML
    private void handleShowDailyPayment() {
        this.showDailyPayment();;
    } 
 	
 	 
	 /** 
         * 我的支出占比
    * @param  
    */
	public void showPaymentPieChart() {
	    try {
	        // Load the fxml file and create a new stage for the pop up.

	    	FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(MainApp.class.getResource("view/ShowPaymentPieChart.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("支出占比情况");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner( AnalysStage);  
	        dialogStage.setResizable(false);
	        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
			
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the payments into the controller. 
	        ShowPaymentPieChartController controller = loader.getController(); 
	         controller.setPaymentPieChartData( storepayment);  
	        
	        dialogStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();

	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("出错啦");
	    	alert.setHeaderText(null);
	    	alert.setContentText(e.getMessage()); 
	    }
	}
	
	 @FXML
	    private void handleShowPaymentPieChart() {
	        this.showPaymentPieChart();;
	    } 

	 /** 
	  * 我的收入占比
	 * @param  
	 */
	public void showIncomePieChart() {
	    try {
	        // Load the fxml file and create a new stage for the pop up.

	    	FXMLLoader loader = new FXMLLoader(); 
	        loader.setLocation(MainApp.class.getResource("view/ShowIncomePieChart.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("收入占比情况");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner( AnalysStage);
	        dialogStage.setResizable(false);
	        dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
			
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the payments into the controller. 
	        ShowIncomePieChartController controller = loader.getController(); 
	         controller.setIncomePieChartData( storeincome);  
	        
	        dialogStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();

	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("出错啦");
	    	alert.setHeaderText(null);
	    	alert.setContentText(e.getMessage()); 
	    }
	}
	
	 @FXML
	    private void handleShowIncomePieChart() {
	        this.showIncomePieChart();;
	    } 
 	
 	
}
