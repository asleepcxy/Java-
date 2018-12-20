package cn.edu.bit.cs.view;

import cn.edu.bit.cs.MainApp; 
import cn.edu.bit.cs.model.Income;
import cn.edu.bit.cs.model.Payment;
import cn.edu.bit.cs.util.TooltipTableRow;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.event.TreeWillExpandListener;

import org.omg.CORBA.PRIVATE_MEMBER; 


public class AppOverviewController  implements Initializable{

	//实时更新总支出和总收入
	@FXML
    Label totalPayments;
    @FXML
    Label totalIncomes; 
	
	//Reference to the main application 
	  /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */ 
	public AppOverviewController() {
		// TODO Auto-generated constructor stub
	}  
		

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize the  table with the  columns.
    	thingColumn.setCellValueFactory(cellData -> cellData.getValue().thingProperty());
    	valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty().asObject());
    	addDateColumn.setCellValueFactory(cellData -> cellData.getValue().addDateProperty().asString()  );
    	thing2Column.setCellValueFactory(cellData -> cellData.getValue().thingProperty());
    	value2Column.setCellValueFactory(cellData -> cellData.getValue().valueProperty().asObject());
    	addDate2Column.setCellValueFactory(cellData -> cellData.getValue().addDateProperty().asString()  );
    	
     }   
		private MainApp mainApp;
		//连接表格1
		@FXML
	    private TableView<Income> incomeTable ;//
	    @FXML
	    private TableColumn<Income, String> thingColumn;
	    @FXML
	    private TableColumn<Income, Double> valueColumn;
	    @FXML
	    private TableColumn<Income,String> addDateColumn;  
	    //connect tableview 2
		@FXML
	    private TableView<Payment> paymentTable;
	    @FXML
	    private TableColumn<Payment, String> thing2Column;
	    @FXML
	    private TableColumn<Payment, Double> value2Column;
	    @FXML
	    private TableColumn<Payment,String> addDate2Column;  
	    @FXML
	    private TextArea details;
	    @FXML
	    private AnchorPane temp_window;
	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	    	temp_window.setVisible(false);
	        this.mainApp = mainApp;

	        // Add observable list data to the table
	        incomeTable.setItems(mainApp.getIncomeData());
	        paymentTable.setItems(mainApp.getPaymentData());
	        
	        incomeTable.setRowFactory((tableView) -> {
	    		return new TooltipTableRow<Income>((Income income) -> {
	            return "项目"+" : "+income.getThing()+"\n金额 : "+income.getValue()
	            +"\n日期 : "+income.getaddDate()+"\n备注 : "+income.getRemark()     ;
	    		});
	        });
	    	paymentTable.setRowFactory((tableView) -> {
			return new TooltipTableRow<Payment>((Payment payment) -> {
				return "项目"+" : "+payment.getThing()+"\n金额 : "+payment.getValue()
				+"\n日期 : "+payment.getaddDate()+"\n备注 : "+payment.getRemark()     ;
				});   
	    	});
	        //保留两位小数  
	    	totalIncomes.textProperty().bind(   mainApp.totalIncome.asString());
	    	totalPayments.textProperty().bind(   mainApp.totalPayment.asString()); 
		
	    }
	    //unsure ...
	    /**
	     * Called when the user clicks the new button. Opens a dialog to edit
	     * details for a new person.
	     * 对增加收入和支出进行操作
	     */
	    @FXML
	    private void handleNewPayment() {
	        Payment tempPayment= new Payment("food", 0.0,LocalDate.of(2018, 11, 21),"" );; 
	        boolean okClicked = mainApp.PaymentWindows(tempPayment);
	        if (okClicked) {
	            mainApp.getPaymentData().add(tempPayment);
	        }
	    } 
	    
	    @FXML
	    private void handleNewIncome() {
	        Income tempIncome= new Income("salary", 0.0,LocalDate.now(),"" );; 
	        boolean okClicked = mainApp.IncomeWindows(tempIncome);
	        if (okClicked) {
	            mainApp.getIncomeData().add(tempIncome);
	        }
	    } 
	    
	    /**
	     * Called when the user clicks the edit button. Opens a dialog to edit
	     * details for the selected person.
	     */    
	    
	    /**
	     * Called when the user clicks on the delete button.
	     * ArrayIndexOutOfBoundsException undone
	     */
	    @FXML
	    private void handleDeleteIncome() {
	    	int selectedIndex = incomeTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {
	    	incomeTable.getItems().remove(selectedIndex);
	    } else {
	        // Nothing selected. 
	    	JOptionPane.showMessageDialog(null, "标题【请选中您要修改的栏】", "删除错误", JOptionPane.ERROR_MESSAGE);
	    	}
	    }
	    @FXML
	    private void handleDeletePayment() {
	     	int selectedIndex = paymentTable.getSelectionModel().getSelectedIndex();
		    if (selectedIndex >= 0) {
		    	paymentTable.getItems().remove(selectedIndex);
		    } else {
		        // Nothing selected. 
		    	JOptionPane.showMessageDialog(null, "标题【请选中您要修改的栏】", "删除错误", JOptionPane.ERROR_MESSAGE);
		    }
		 }
	    @FXML
	    private void handleEditIncome() {
	    	int selectedIndex = incomeTable.getSelectionModel().getSelectedIndex();
		    if (selectedIndex >= 0) {
		    	mainApp.EditIncomeWindow(incomeTable.getItems().get(selectedIndex));
		    } else {
		        // Nothing selected. 
		    	JOptionPane.showMessageDialog(null, "标题【请选中您要修改的栏】", "删除错误", JOptionPane.ERROR_MESSAGE);
		    }
		    incomeTable.setRowFactory((tableView) -> {
	    		return new TooltipTableRow<Income>((Income income) -> {
	            return "项目"+" : "+income.getThing()+"\n金额 : "+income.getValue()
	            +"\n日期 : "+income.getaddDate()+"\n备注 : "+income.getRemark()     ;
	    		});
	        });
	    }
	    @FXML
	    private void handleEditPayment() {
	    	int selectedIndex = paymentTable.getSelectionModel().getSelectedIndex();
		    if (selectedIndex >= 0) {
		    	mainApp.EditPaymentWindow(paymentTable.getItems().get(selectedIndex));
		    } else {
		        // Nothing selected. 
		    	JOptionPane.showMessageDialog(null, "标题【请选中您要修改的栏】", "删除错误", JOptionPane.ERROR_MESSAGE);
		    }
	    	paymentTable.setRowFactory((tableView) -> {
			return new TooltipTableRow<Payment>((Payment payment) -> {
				return "项目"+" : "+payment.getThing()+"\n金额 : "+payment.getValue()
				+"\n日期 : "+payment.getaddDate()+"\n备注 : "+payment.getRemark()     ;
				});   
	    	});
	    }
	    
	    /**
	     * Opens the birthday statistics. 统计的部分
	     */ 
	    @FXML
	    private void handleAnalysWindows() {
	    	mainApp.AnalysWindows();
	    }
	    @FXML
	    private void handleSaveForWindow() {
	    	mainApp.SaveForWindows();
	    }
	    
	    
	    
 }
