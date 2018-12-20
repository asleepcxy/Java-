package cn.edu.bit.cs.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lists")
public class ListWrapper {
	
	private List<Payment> payments;

    @XmlElement(name = "payment")
    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
     
    private List<Income> incomes;

    @XmlElement(name = "income")
    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }
    
    private List<Gift> gifts;

    @XmlElement(name = "gift")
    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }
    
    /**
     * @param ListWrapper 
     */ 
	
}
