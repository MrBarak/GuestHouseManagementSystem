/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guesthousemanagement;

import javafx.beans.property.SimpleStringProperty;

public class HouseModalTable
{
    private SimpleStringProperty srNo;
    private SimpleStringProperty branchName;
    private SimpleStringProperty location;
    private SimpleStringProperty city;
    private SimpleStringProperty category;
    private SimpleStringProperty status;
    private SimpleStringProperty address;
    private SimpleStringProperty booking;
    private SimpleStringProperty leavingDate;
    private SimpleStringProperty bookingDate;
    
    public HouseModalTable(String srNo, String branchName, String location, String city, String category, String status) {
        this.srNo = new SimpleStringProperty(srNo);
        this.branchName = new SimpleStringProperty(branchName);
        this.location = new SimpleStringProperty(location);
        this.city = new SimpleStringProperty(city);
        this.category = new SimpleStringProperty(category);
        this.status = new SimpleStringProperty(status);
    }
    
    public HouseModalTable(String srNo, String booking, String address, String bookingDate, String leavingDate)
    {
        this.srNo = new SimpleStringProperty(srNo);
        this.booking = new SimpleStringProperty(booking);
        this.address = new SimpleStringProperty(address);
        this.bookingDate = new SimpleStringProperty(bookingDate);
        this.leavingDate = new SimpleStringProperty(leavingDate);
    }
    
    public HouseModalTable(String srNo, String branchName) 
    {
        this.srNo = new SimpleStringProperty(srNo);
        this.branchName = new SimpleStringProperty(branchName);
    }
    
    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location = new SimpleStringProperty(location);
    }

    public String getBooking() {
        return booking.get();
    }

    public void setBooking(String booking) {
        this.booking = new SimpleStringProperty(booking);
    }
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }
    public String getBookingDate() {
        return bookingDate.get();
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = new SimpleStringProperty(bookingDate);
    }
    public String getLeavingDate() {
        return leavingDate.get();
    }

    public void setLeavingDate(String leavingDate) {
        this.leavingDate = new SimpleStringProperty(leavingDate);
    }
    
    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city = new SimpleStringProperty(city);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category = new SimpleStringProperty(category);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status = new SimpleStringProperty(status);
    }
    
    public String getSrNo() {
        return srNo.get();
    }

    public void setSrNo(String srNo) {
        this.srNo = new SimpleStringProperty(srNo);
    }

    public String getBranchName() {
        return branchName.get();
    }

    public void setBranchName(String branchName) {
        this.branchName = new SimpleStringProperty(branchName);
    }
    
    
}
