package cn.edu.bit.cs.view;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
 

import cn.edu.bit.cs.MainApp;
import cn.edu.bit.cs.model.Gift;
import cn.edu.bit.cs.model.Income;
import cn.edu.bit.cs.model.Payment;
 

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 包括打开保存另存为删除的一些系列对文件的操作
 * @author CXY
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;
    @FXML
    Label recentDeadline;

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {

    	this.mainApp = mainApp;
    	
        //显示最近心愿的ddl 
    	recentDeadline.textProperty().bind(new StringBinding() {
    	        {
    	            bind(mainApp.getGiftData());
    	        }   
    			@Override
    	        protected String computeValue() { 
    	        	//按日期顺序排序 
    				List<Gift> giftdata=mainApp.getGiftData();
    	        	Collections.sort(giftdata,new Comparator<Object>() {
    	        		@Override
    	        		 public int compare(Object o1, Object o2) {
    	                   Gift u1 = (Gift)o1;
    	                   Gift u2 = (Gift)o2; 
    	                   Boolean res= u1.getDeadlineDate().isBefore(u2.getDeadlineDate());
    	                   if(res==true)
    	                	   return -1;
    	                   else
    	                	   return 1;
    	               }
    	        	});
    	        	LocalDate today=LocalDate.now();  
    	        	for(Gift g:giftdata) {
    	        		if(g.getDone()==1||g.getDeadlineDate().isBefore(today))
    	        			continue;
    	        		else//只考虑当天唯一一个情况
    	        			return "距离最近的心愿日期"+g.getDeadlineDate().toString();
    	        	} 
    	        	return "当前无最新需要完成的心愿";
    	        } 
    	});
    }
    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        mainApp.getPaymentData().clear();
        mainApp.getGiftData().clear();
        mainApp.getIncomeData().clear();
        mainApp.setPaymentFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPaymentDataFromFile(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File paymentFile = mainApp.getPaymentFilePath();
        if (paymentFile != null) {
            mainApp.savePaymentDataToFile(paymentFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePaymentDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("关于我们");
    	alert.setHeaderText("软件名称");
    	alert.setContentText("我的记账助手");  
    	alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
     

    @FXML
    private TextField search;
    
    @FXML
    private void handleSearch() {
    	String string=search.getText();
    	String[] stringList=string.split(" ");
    	List<String> reslist= new ArrayList<String>();
    	for(String str:stringList) {
    		for(Income i:mainApp.getIncomeData()) {
    			if(str.equals(""))
    				continue;
    			String temp="\n收入项目："+i.getThing()+"\t\t收入数量： "+String.valueOf(i.getValue())+
						"\t\t添加日期："+i.getaddDate().toString()+"\t\t备注"+i.getRemark()+"\n";
    			Boolean flag=true;
    			for(String j:reslist)
    				if(temp.equals(j)) {
    					flag=false;
    					break;
    				}
    			if(!flag)
    				continue;
    			int res=i.getRemark().indexOf(str);
    			if(res!=-1) {
    				reslist.add(temp); 
    			}
    		}
    		for(Payment p:mainApp.getPaymentData()) {
    			if(str.equals(""))
    				continue;
    			String temp="\n支出项目："+p.getThing()+"\t\t支出数量： "+String.valueOf(p.getValue())+
						"\t\t添加日期："+p.getaddDate().toString()+"\t\t备注："+p.getRemark()+"\n";
    			Boolean flag=true;
    			for(String j:reslist)
    				if(temp.equals(j)) {
    					flag=false;
    					break;
    				}
    			if(!flag)
    				continue;
    			int res=p.getRemark().indexOf(str); 
    			if(res!=-1) 		
    				reslist.add(temp);       		
    		}	
    	}  
    	if(reslist.size()==0 ) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("查找结果");
	    	alert.setContentText("未查询到任何结果！");
	    	alert.showAndWait();
    	}
    	
    	else {
    		String result=new String();
    		for(String str:reslist)
    			result+=str+'\n';
    		Alert alert = new Alert(AlertType.INFORMATION);
    		
	    	alert.setTitle("查找结果");
	    	alert.setHeaderText("为您查询到:");
	    	alert.setContentText(result);
	    	alert.setResizable(true);
	    	alert.setWidth(500);
	    	alert.showAndWait();
    	}
    }
    
}