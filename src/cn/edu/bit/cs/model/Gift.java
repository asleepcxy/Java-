package cn.edu.bit.cs.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import cn.edu.bit.cs.util.LocalDateAdapter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Gift {
	private  StringProperty thing;
	private  DoubleProperty money;
	private	 IntegerProperty done;
	private ObjectProperty<LocalDate> deadlineDate;
	
	public Gift() {
		this(null, 0.0, null ,0);
	}
	public Gift(String thing, Double money,LocalDate deadlineDate,int done) {
		this.money = new SimpleDoubleProperty(money);
		this.thing = new SimpleStringProperty(thing);
		 this.deadlineDate = new SimpleObjectProperty<LocalDate>(deadlineDate);
		this.done = new SimpleIntegerProperty(done);
	}
	//初始化
	
	public void setMoney(Double money) {
		this.money = new SimpleDoubleProperty(money);
	}
	public void setThing(String thing) {
		this.thing = new SimpleStringProperty(thing);
	}
	//赋值
	
	public Double getMoney() {
		return this.money.get();
	}
	public String getThing() {
		return this.thing.get();
	}
	//取数据
	
	public StringProperty StringProperty() {
        return this.thing;
    }
	public DoubleProperty DoubleProperty() {
        return this.money;
    }
	
	  @XmlJavaTypeAdapter(LocalDateAdapter.class)
	    public LocalDate getDeadlineDate() {
	    	return deadlineDate.get();
	    }
	    public void setDeadlineDate(LocalDate deadlineDate) {
	        this.deadlineDate.set(deadlineDate);
	    }

	    public ObjectProperty<LocalDate> deadlineDateProperty() {
	        return deadlineDate;
	    } 
	    
	    public IntegerProperty doneProperty() {
			return this.done;
		}
	    public int getDone() {
			return this.done.get();
		}
	    public void setDone(int done) {
			this.done.setValue(done);
		}
	    /**
	     * generate getter and setter method
	     * @param gift 
	     */ 
}
