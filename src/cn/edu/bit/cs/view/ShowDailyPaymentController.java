package cn.edu.bit.cs.view;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import cn.edu.bit.cs.model.Payment;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class ShowDailyPaymentController {

    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private CategoryAxis xAxis;
 
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    //-------
    @FXML
    private void initialize() {
    }

    /**
     * Sets the payments to show the statistics for.
     * 
     * @param payments
     */
    public void setdailyData(List<Payment> payment) { 
 
    	//按照日期来排序
    	Collections.sort(payment, new Comparator<Payment>() {
            @Override
            public int compare(Payment o1, Payment o2) {
                return o1.getaddDate().hashCode()<o2.getaddDate().hashCode()? -1:1;
            }
        });
        XYChart.Series<String, Double> series = new XYChart.Series<>(); 
        // Create a XYChart.Data object for each month. Add it to the series.
        //注意同一天
        for (int j = 0; j <  payment.size(); j++) {  
        	boolean isnew=true;
        	for(int i=0;i<series.getData().size();++i)
        		if(series.getData().get(i).getXValue().equals(payment.get(j).getaddDate().toString())) {
        			isnew=false;
        			series.getData().get(i).setYValue(series.getData().get(i).getYValue()+payment.get(j).getValue());
        			break;
        		}
        	if(isnew==true)
        		series.getData().add(new XYChart.Data<>(   payment.get(j).getaddDate().toString(),  payment.get(j).getValue() ));  
        }  
        
        lineChart.getData().add(series);
    }
}
