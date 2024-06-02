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
public class Employee {
    
    private SimpleStringProperty srNo;
    private SimpleStringProperty employeeName;
    private SimpleStringProperty cnic;

    public Employee(String srNo, String employeeName, String cnic) 
    {
        this.srNo = new SimpleStringProperty(srNo);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.cnic = new SimpleStringProperty(cnic);
    }

    public String getSrNo() {
        return srNo.get();
    }

    public void setSrNo(String srNo) {
        this.srNo = new SimpleStringProperty(srNo);
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = new SimpleStringProperty(employeeName);
    }

    public String getCnic() {
        return cnic.get();
    }

    public void setCnic(String cnic) {
        this.cnic = new SimpleStringProperty(cnic);
    }
    
    
}
