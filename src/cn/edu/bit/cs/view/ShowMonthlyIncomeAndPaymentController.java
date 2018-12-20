package cn.edu.bit.cs.view;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cn.edu.bit.cs.model.Income;
import cn.edu.bit.cs.model.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

//统计每个月份收入
public class ShowMonthlyIncomeAndPaymentController {

    @FXML
    private BarChart<String, Double> incomeAndpaymentBarChart;
 

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Get an array with the English month names.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths() ;
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));
        
        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
    }

    /**
     * Sets the payments to show the statistics for.
     * 
     * @param payments 
     */
    public void setIncomeAndPaymentData(List<Income> income, List<Payment> payment) {
        // Count the total of  income around each month 
    	XYChart.Series<String, Double> incomeSeries = new XYChart.Series<>();
    	incomeSeries.setName("收入");
        double[] incomeMonthCounter = new double[12];
        for (Income i : income) {
            int month = i.getaddDate().getMonthValue() - 1;
            incomeMonthCounter[month]+=i.getValue();
        } 
         
        
        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < incomeMonthCounter.length; i++) {
            incomeSeries.getData().add(new XYChart.Data<>(monthNames.get(i), incomeMonthCounter[i]));
        }
        
        incomeAndpaymentBarChart.getData().add(incomeSeries);
        

        XYChart.Series<String, Double> paymentSeries = new XYChart.Series<>();
        paymentSeries.setName("支出");
     // Count the total of  payment around each month
        double[] paymentMonthCounter = new double[12];
        for (Payment i : payment) {
            int month = i.getaddDate().getMonthValue() - 1;
            paymentMonthCounter[month]+=i.getValue();
        }  
        
        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < paymentMonthCounter.length; i++) {
        	paymentSeries.getData().add(new XYChart.Data<>(monthNames.get(i), paymentMonthCounter[i]));
        }
        
        incomeAndpaymentBarChart.getData().add(paymentSeries); 
    }
}
