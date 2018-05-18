package Service_handler;

import java.util.ArrayList;

public class Tidy_feedback {
    private String JobID;
    private String CustomerSignature;
    private String PhotoFile;
    private String ProjectSiteId;
    private String ProjectSiteChargeID;
    private String JobAdditionalCharges;
    private String SignedBy;
    private String Rating;
    private String PaymentCollected;
    private String CollectionMethod;
    private String AmountCollected;
    private String DriverNotes;
    private String driverId;
    private String JobStepId;
    private String JobNumber;
    private String Completed;
    private String BinNumber;


    public String getJobAdditionalCharges() {
        return JobAdditionalCharges;
    }

    public void setJobAdditionalCharges(String jobAdditionalCharges) {
        JobAdditionalCharges = jobAdditionalCharges;
    }

    public String getJobID() {
        return JobID;
    }

    public void setJobID(String jobID) {
        JobID = jobID;
    }

    public String getCustomerSignature() {
        return CustomerSignature;
    }

    public void setCustomerSignature(String customerSignature) {
        CustomerSignature = customerSignature;
    }

    public String getPhotoFile() {
        return PhotoFile;
    }

    public void setPhotoFile(String photoFile) {
        PhotoFile = photoFile;
    }

    public String getProjectSiteId() {
        return ProjectSiteId;
    }

    public void setProjectSiteId(String projectSiteId) {
        ProjectSiteId = projectSiteId;
    }

    public String getProjectSiteChargeID() {
        return ProjectSiteChargeID;
    }

    public void setProjectSiteChargeID(String projectSiteChargeID) {
        ProjectSiteChargeID = projectSiteChargeID;
    }


    public String getSignedBy() {
        return SignedBy;
    }

    public void setSignedBy(String signedBy) {
        SignedBy = signedBy;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getPaymentCollected() {
        return PaymentCollected;
    }

    public void setPaymentCollected(String paymentCollected) {
        PaymentCollected = paymentCollected;
    }

    public String getCollectionMethod() {
        return CollectionMethod;
    }

    public void setCollectionMethod(String collectionMethod) {
        CollectionMethod = collectionMethod;
    }

    public String getAmountCollected() {
        return AmountCollected;
    }

    public void setAmountCollected(String amountCollected) {
        AmountCollected = amountCollected;
    }

    public String getDriverNotes() {
        return DriverNotes;
    }

    public void setDriverNotes(String driverNotes) {
        DriverNotes = driverNotes;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getJobStepId() {
        return JobStepId;
    }

    public void setJobStepId(String jobStepId) {
        JobStepId = jobStepId;
    }

    public String getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(String jobNumber) {
        JobNumber = jobNumber;
    }

    public String getCompleted() {
        return Completed;
    }

    public void setCompleted(String completed) {
        Completed = completed;
    }

    public String getBinNumber() {
        return BinNumber;
    }

    public void setBinNumber(String binNumber) {
        BinNumber = binNumber;
    }
}