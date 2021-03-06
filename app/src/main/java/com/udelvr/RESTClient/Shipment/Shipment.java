package com.udelvr.RESTClient.Shipment;

import java.io.File;

/**
 * Created by sophiango on 4/19/15.
 */
public class Shipment
{

    private String shipmentID;
    private String recipientName;
    private String sourceAddress;
    private String sourceLat;
    private String sourceLong;
    private String destinationAddress;
    private String destinationLat;
    private String destinationLong;
    private String packageDescription;
    private String packageWeight;
    private String pickupTime;
    private String pickupDate;
    private File shipmentImage;
    private String customerID;
    private String status;
    private String driverID;

    public Shipment() {
    }

    public Shipment(String shipmentID, String recipientName, String sourceAddress, String sourceLat, String sourceLong, String destinationAddress, String destinationLat, String destinationLong, String packageDescription, String packageWeight, String pickupTime, String pickupDate, File shipmentImage, String customerID, String status, String driverID) {
        this.shipmentID = shipmentID;
        this.recipientName = recipientName;
        this.sourceAddress = sourceAddress;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.destinationAddress = destinationAddress;
        this.destinationLat = destinationLat;
        this.destinationLong = destinationLong;
        this.packageDescription = packageDescription;
        this.packageWeight = packageWeight;
        this.pickupTime = pickupTime;
        this.pickupDate = pickupDate;
        this.shipmentImage = shipmentImage;
        this.customerID = customerID;
        this.status = status;
        this.driverID = driverID;
    }

    public String getShipmentID() {
        return shipmentID;
    }
    public void setShipmentID(String shipmentID) {
        this.shipmentID = shipmentID;
    }
    public String getRecipientName() {
        return recipientName;
    }
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    public String getSourceAddress() {
        return sourceAddress;
    }
    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }
    public String getSourceLat() {
        return sourceLat;
    }
    public void setSourceLat(String sourceLat) {
        this.sourceLat = sourceLat;
    }
    public String getSourceLong() {
        return sourceLong;
    }
    public void setSourceLong(String sourceLong) {
        this.sourceLong = sourceLong;
    }
    public String getDestinationAddress() {
        return destinationAddress;
    }
    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
    public String getDestinationLat() {
        return destinationLat;
    }
    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
    }
    public String getDestinationLong() {
        return destinationLong;
    }
    public void setDestinationLong(String destinationLong) {
        this.destinationLong = destinationLong;
    }
    public String getPackageDescription() {
        return packageDescription;
    }
    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }
    public String getPackageWeight() {
        return packageWeight;
    }
    public void setPackageWeight(String packageWeight) {
        this.packageWeight = packageWeight;
    }
    public String getPickupTime() {
        return pickupTime;
    }
    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
    public String getPickupDate() {
        return pickupDate;
    }
    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }
    public File getShipmentImage() {
        return shipmentImage;
    }
    public void setShipmentImage(File shipmentImage) {
        this.shipmentImage = shipmentImage;
    }
    public String getCustomerID() {
        return customerID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDriverID() {
        return driverID;
    }
    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }
}
