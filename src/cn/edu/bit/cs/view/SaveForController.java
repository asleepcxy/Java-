package cn.edu.bit.cs.view;



import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

import cn.edu.bit.cs.MainApp;
import cn.edu.bit.cs.model.Gift;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SaveForController{
	@FXML
	TableView<Gift> giftTable;
	@FXML
	TableColumn< Gift, String> thingColumn;
	@FXML
	TableColumn<Gift, Double> moneyColumn;
	@FXML
	TableColumn<Gift,String> deadlineColumn;
	 
	ObservableList<Button> buttons;
	
	private Stage saveForStage;
	private	MainApp mainApp;
	/**
	* Initializes the controller class. This method is automatically called
	* after the fxml file has been loaded.
	*/
	public  SaveForController() {
		;
	} 
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	@FXML
    private void initialize() {
		thingColumn.setCellValueFactory(cellData -> cellData.getValue().StringProperty());
 		moneyColumn.setCellValueFactory(cellData -> cellData.getValue().DoubleProperty().asObject());
 		deadlineColumn.setCellValueFactory(cellData -> cellData.getValue().deadlineDateProperty().asString());
     	}
	/**
	     * Sets the stage of this dialog.
	     * 
	     * @param dialogStage
	     */
    public void setDialogStage(Stage saveForStage) {
        this.saveForStage= saveForStage;
    }
    
    private void produceButton() {
    	TableColumn<Gift, Boolean> singleCol
    				= new TableColumn<Gift, Boolean>("完成?");
    	giftTable.setEditable(true);
    	 singleCol.setCellValueFactory(new Callback<CellDataFeatures<Gift, Boolean>, ObservableValue<Boolean>>() {
    		 
        @Override
        public ObservableValue<Boolean> call(CellDataFeatures<Gift, Boolean> param) {
          Gift gift = param.getValue();
  
           SimpleBooleanProperty booleanProp = new SimpleBooleanProperty( gift.getDone()==1?true:false);
  
           // Note: singleCol.setOnEditCommit(): Not work for
           // CheckBoxTableCell.
  
                 // When  column change.
                 booleanProp.addListener(new ChangeListener<Boolean>() {
  
                     @Override
                     public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                             Boolean newValue) { 
                    	 gift.setDone(newValue == true ? 1:0);
                    	 
                    	 Collections.sort(mainApp.getGiftData(),new Comparator<Object>() {
         	        		@Override
         	        		 public int compare(Object o1, Object o2) {
         	                   Gift u1 = (Gift)o1;
         	                   Gift u2 = (Gift)o2; 
         	                   Boolean res= u1.getDeadlineDate().isBefore(u2.getDeadlineDate());
         	                   if(res==true)
         	                	   return  1;
         	                   else
         	                	   return -1;
         	               }
         	        	});
                     }
                 });
                 return booleanProp;
             }
         });
  
         singleCol.setCellFactory(new Callback<TableColumn<Gift, Boolean>, //
         TableCell<Gift, Boolean>>() {
             @Override
             public TableCell<Gift, Boolean> call(TableColumn<Gift, Boolean> p) {
                 CheckBoxTableCell<Gift, Boolean> cell = new CheckBoxTableCell<Gift, Boolean>();
                 cell.setAlignment(Pos.CENTER);
                 return cell;
             }
         });
         giftTable.getColumns().add(singleCol);
         giftTable.setItems(mainApp.getGiftData());
      // Add observable list data to the table
         
  
    }
    public void setGift(ObservableList<Gift> gifts) {
    	
    	 produceButton();
    } 
    public boolean showAddGiftWindow(Gift gift) {
        try {
            // Load the fxml file and create a new stage for the pop up.
        	
        	FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(MainApp.class.getResource("view/AddGift.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("添加心愿");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(saveForStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("file:resources/icon/MainAppicon.png"));
    		
            AddGiftController controller = loader.getController(); 
            controller.setDialogStage(dialogStage);
            controller.setGift(gift);
             
            dialogStage.showAndWait();
            
            return controller.issubmitClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
    }
    @FXML
    private void handleAddGiftWindow() {
    	String string = "";
    	Double money = 0.0;
    	Gift gift = new Gift(string,money,LocalDate.now(),0);
    	if(this.showAddGiftWindow(gift) == true) {
    		mainApp.getGiftData().add(gift);
    	}
    }
    
}
