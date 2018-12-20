/**
 * 
 */
/**
 * @author CXY
 *
 */
package cn.edu.bit.cs.model;

import java.time.LocalDate; 
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cn.edu.bit.cs.util.LocalDateAdapter;
import javafx.beans.property.DoubleProperty; 
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Payment    {
 
 	private final DoubleProperty value;

 	private final ObjectProperty<LocalDate> addDate;

 	private final StringProperty thing;
 	
 	private final StringProperty remark;
	
	/**
     * Default constructor.
     */
	
    public Payment() {
        this(null, 0.0,null,"");
    }
    /**
     * Constructor with some initial data.
     * 
     * @param PaymentWindows
     */
    
    public Payment( String thing ,Double value, LocalDate  addDate,String remark) { 
    	
    	// Some initial dummy data, just for convenient testing.
    	 this.thing = new SimpleStringProperty(thing);
        this.value = new SimpleDoubleProperty(value);
        this.addDate = new SimpleObjectProperty<LocalDate>(addDate);
        this.remark = new SimpleStringProperty(remark);
    } 
    
    public String getThing() {
        return thing.get();
    }

    public void setThing(String thing) {
        this.thing.set(thing);
    }

    public StringProperty thingProperty() {
        return thing;
    }
    
    public double getValue() {
        return value.get();
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public DoubleProperty valueProperty() {
        return value;
    }
     
	  //XML正确储存addDate类型
    @XmlJavaTypeAdapter(LocalDateAdapter.class) 
    public LocalDate getaddDate() {
    	return addDate.get();
    }
    public void setaddDate(LocalDate addDate) {
        this.addDate.set(addDate);
    }

    public ObjectProperty<LocalDate> addDateProperty() {
        return addDate;
    } 
    
    
    public String getRemark() {
        return remark.get();
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public StringProperty remarkProperty() {
        return remark;
    }
    /**
     * generate getter and setter method
     * @param Payment
     */  
}