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
public class ComplaintModalTable 
{
    private SimpleStringProperty srNo;
    private SimpleStringProperty complaintCategory;
    private SimpleStringProperty complaint;
    private SimpleStringProperty submissionDate;
    private SimpleStringProperty status;

    public ComplaintModalTable(String srNo, String complaintCategory, String complaint, String submissionDate) 
    {
        this.srNo = new SimpleStringProperty(srNo);
        this.complaintCategory = new SimpleStringProperty(complaintCategory);
        this.complaint = new SimpleStringProperty(complaint);
        this.submissionDate = new SimpleStringProperty(submissionDate);
    }
    
    public ComplaintModalTable(String srNo, String complaintCategory, String complaint, String submissionDate, String status) 
    {
        this.srNo = new SimpleStringProperty(srNo);
        this.complaintCategory = new SimpleStringProperty(complaintCategory);
        this.complaint = new SimpleStringProperty(complaint);
        this.submissionDate = new SimpleStringProperty(submissionDate);
        this.status = new SimpleStringProperty(status);
    }

    public String getSrNo() {
        return srNo.get();
    }

    public void setSrNo(String srNo) {
        this.srNo = new SimpleStringProperty(srNo);
    }

    public String getComplaintCategory() {
        return complaintCategory.get();
    }

    public void setComplaintCategory(String complaintCategory) {
        this.complaintCategory = new SimpleStringProperty(complaintCategory);
    }

    public String getComplaint() {
        return complaint.get();
    }

    public void setComplaint(String complaint) {
        this.complaint = new SimpleStringProperty(complaint);
    }

    public String getSubmissionDate() {
        return submissionDate.get();
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = new SimpleStringProperty(submissionDate);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status = new SimpleStringProperty(status);
    }
    
    
}
