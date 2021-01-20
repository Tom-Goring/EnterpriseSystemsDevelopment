/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Invoice;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class Invoice {
    private final Integer ID;
    private final String service;
    private final Double amount;
    private final String type;
    private final Date issueDate;
    private final Date dueDate;
    
        public Invoice(Integer ID, String service, Double amount, String type, Date issueDate, Date dueDate){
        this.ID = ID;
        this.service = service;
        this.amount = amount;
        this.type = type;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
    
    public Integer getID() {
        return ID;
    }
    
    public String getService() {
        return service;
    }
    
    public String getType() {
        return type;
    }
    public Double getAmount() {
        return amount;
    }
    
    public String getFormattedIssueDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM YYYY");
        String issuedateString = format.format(issueDate);
        return issuedateString;
    }
    
    public String getFormattedDueDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM YYYY");
        String duedateString = format.format(dueDate);
        return duedateString;
    }
}
