package cn.edu.bit.cs.view;

import java.text.DecimalFormat;
import java.util.List;

import cn.edu.bit.cs.model.Income;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ShowIncomePieChartController {
	@FXML
    private PieChart incomePieChartData; 
	@FXML
    private Label caption  ;  
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() { 
    }

    /**
     *  set the pie chart about income
     * 
     * @param income
     */
    public void setIncomePieChartData(List<Income> income) { 
    	double sumincome=0;
        for (int i = 0; i < income.size(); i++) { 
        	boolean only =true;
        	sumincome+=income.get(i).getValue();
        	for(int j=0;j<incomePieChartData.getData().size();++j) { 
        		if(incomePieChartData.getData().get(j).getName().equals(  (income.get(i).getThing()))){
        			only=false;
        			double t=incomePieChartData.getData().get(j).getPieValue();
        			incomePieChartData.getData().get(j).setPieValue(t+income.get(i).getValue());
        			break;
        		} 
        	}
        	if(only==true)
        		incomePieChartData.getData().add(new PieChart.Data(income.get(i).getThing(), income.get(i).getValue()));
        	
        }   
        
 
        final double sum=sumincome;
        //点击扇形区域时候显示相应的比例
        for (final PieChart.Data data : incomePieChartData.getData()) {
    		data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
    			new EventHandler<MouseEvent>() {
    				@Override
    				public void handle(MouseEvent e) {  
    					caption.setTranslateX(e.getSceneX());
    					caption.setTranslateY(e.getSceneY());
    					//保留两位小数
    					DecimalFormat df = new DecimalFormat("#.00"); 
    					caption.setText(  df.format(data.getPieValue()/sum *100 )+ "%");
    					System.out.println("the scale is : "+caption.getText());
    				}
    			}); 
    		}
        
    }
}
