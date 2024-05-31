/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guesthousemanagement;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Hamza Barak
 */
public class Customer 
{
    private SimpleStringProperty srNo;
    private SimpleStringProperty customerName;
    private SimpleStringProperty cnic;
    private SimpleStringProperty service;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    
    public Customer(String srNo, String customerName)
    {
        this.srNo = new SimpleStringProperty(srNo);
        this.customerName = new SimpleStringProperty(customerName);
    }
    
    public Customer(String srNo, String customerName, String cnic)
    {
        this.srNo = new SimpleStringProperty(srNo);
        this.customerName = new SimpleStringProperty(customerName);
        this.cnic = new SimpleStringProperty(cnic);
    }
    
    public Customer(String srNo, String service, String date, String time)
    {
        this.srNo = new SimpleStringProperty(srNo);
        this.service = new SimpleStringProperty(service);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }
    
    public String getSrNo() {
        return srNo.get();
    }

    public void setSrNo(String srNo) {
        this.srNo = new SimpleStringProperty(srNo);
    }

    public String getService() {
        return service.get();
    }

    public void setService(String service) {
        this.service = new SimpleStringProperty(service);
    }
    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }
    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time = new SimpleStringProperty(time);
    }
    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(String customerName) {
        this.customerName = new SimpleStringProperty(customerName);
    }
    
    public String getCnic() {
        return cnic.get();
    }

    public void setCnic(String cnic) {
        this.cnic = new SimpleStringProperty(cnic);
    }
}
