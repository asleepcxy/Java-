package cn.edu.bit.cs.view;

import java.text.DecimalFormat;
import java.util.List;
import cn.edu.bit.cs.model.Payment;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent; 

public class ShowPaymentPieChartController {
	@FXML
    private PieChart paymentPieChartData;
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
     *  set the pie chart about payment 
     * @param payments
     */
    public void setPaymentPieChartData(List<Payment> payment) { 
    	double sumpayment=0;
        for (int i = 0; i < payment.size(); i++) { 
        	boolean only =true;
        	sumpayment+=payment.get(i).getValue();
        	for(int j=0;j<paymentPieChartData.getData().size();++j) { 
        		if(paymentPieChartData.getData().get(j).getName().equals(  (payment.get(i).getThing()))){
        			only=false;
        			double t=paymentPieChartData.getData().get(j).getPieValue();
        			paymentPieChartData.getData().get(j).setPieValue(t+payment.get(i).getValue());
        			break;
        		} 
        	}
        	if(only==true)
        		paymentPieChartData.getData().add(new PieChart.Data(payment.get(i).getThing(), payment.get(i).getValue()));
        	
        }   
        
 
        final double sum=sumpayment;
        //�����������ʱ����ʾ��Ӧ�ı���
        for (final PieChart.Data data : paymentPieChartData.getData()) {
    		data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
    			new EventHandler<MouseEvent>() {
    				@Override
    				public void handle(MouseEvent e) {  
    					caption.setTranslateX(e.getSceneX());
    					caption.setTranslateY(e.getSceneY());
    					//������λС��
    					DecimalFormat df = new DecimalFormat("#.00"); 
    					caption.setText(  df.format(data.getPieValue()/sum *100 )+ "%");
    					System.out.println("the scale is : "+caption.getText());
    				}
    			}); 
    		}
    }
    
    /**
     *  �����������ʱ����ʾ��Ӧ�ı���
     * @param caption
     */ 
     
    	  
}
