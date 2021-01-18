/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Invoice;

import java.sql.Date;


public class Invoice {
    private final Integer ID;
    private final String service;
    private final Double amount;
    private final Date issueDate;
    
        public Invoice(Integer ID, String service, Double amount, Date issueDate){
        this.ID = ID;
        this.service = service;
        this.amount = amount;
        this.issueDate = issueDate;
    }
    
    public Integer getID() {
        return ID;
    }
    
    public String getService() {
        return service;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public Date getIssueDate() {
        return issueDate;
    }
}
