package com.udelvr.RESTClient.Shipment;
/**
 * Created by Prasad on 4/19/15.
 */
public class ShipmentResponse {
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
    private String imageUrl;
    private String compressedImageUrl;
    private String thumbnailUrl;
    private String customerID;
    private String status;
    private String driverID;

    public ShipmentResponse() {
    }

    public ShipmentResponse(String shipmentID, String recipientName, String sourceAddress, String sourceLat, String sourceLong, String destinationAddress, String destinationLat, String destinationLong, String packageDescription, String packageWeight, String pickupTime, String pickupDate, String imageUrl, String compressedImageUrl, String thumbnailUrl, String customerID, String status, String driverID) {
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
        this.imageUrl = imageUrl;
        this.compressedImageUrl = compressedImageUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.customerID = customerID;
        this.status = status;
        this.driverID = driverID;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCompressedImageUrl() {
        return compressedImageUrl;
    }

    public void setCompressedImageUrl(String compressedImageUrl) {
        this.compressedImageUrl = compressedImageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
