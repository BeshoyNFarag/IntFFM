package com.beshoy.MIntFFM.immigrationoffice;

import jakarta.persistence.*;

@Entity
@Table(name = "immigration_offices")
public class ImmigrationOfficeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long immigrationOfficeId;

    @Column(name = "office_name", nullable = false, unique = true)
    private String officeName;

    @Column(name = "office_address", nullable = false, unique = true, length = 300)
    private String officeAddress;

    @Column(name = "office_number", nullable = false, unique = true, length = 30)
    private String officeNumber;

    @Column(name = "office_email", nullable = false, unique = true, length = 200)
    private String officeEmail;

    @Column(name = "office_url", nullable = false, unique = true, length = 2038)
    private String officeUrl;

    @Column(name = "office_opening_hours", nullable = false, unique = true, length = 255)
    private String officeOpeningHours;

    @Column(name = "office_image1_url", nullable = false, unique = true, length = 2038)
    private String officeImage1Url;

    @Column(name = "office_image2_url", unique = true, length = 2038)
    private String officeImage2Url;

    @Column(name = "office_documents_needed", nullable = false, length = 1000)
    private String officeDocumentsNeeded;

    @Column(name = "office_processing_time", nullable = false, length = 100)
    private String officeProcessingTime;

    @Column(name = "office_appointments_url", nullable = false, unique = true, length = 2038)
    private String officeAppointmentsUrl;

    // Constructors
    public ImmigrationOfficeEntity() {
    }

    public ImmigrationOfficeEntity(String officeName, String officeAddress, String officeNumber, String officeEmail,
                             String officeUrl, String officeOpeningHours, String officeImage1Url,
                             String officeDocumentsNeeded, String officeProcessingTime, String officeAppointmentsUrl) {
        this.officeName = officeName;
        this.officeAddress = officeAddress;
        this.officeNumber = officeNumber;
        this.officeEmail = officeEmail;
        this.officeUrl = officeUrl;
        this.officeOpeningHours = officeOpeningHours;
        this.officeImage1Url = officeImage1Url;
        this.officeDocumentsNeeded = officeDocumentsNeeded;
        this.officeProcessingTime = officeProcessingTime;
        this.officeAppointmentsUrl = officeAppointmentsUrl;
    }

    // Getters and Setters
    public Long getImmigrationOfficeId() {
        return immigrationOfficeId;
    }

    public void setImmigrationOfficeId(Long immigrationOfficeId) {
        this.immigrationOfficeId = immigrationOfficeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getOfficeEmail() {
        return officeEmail;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    public String getOfficeUrl() {
        return officeUrl;
    }

    public void setOfficeUrl(String officeUrl) {
        this.officeUrl = officeUrl;
    }

    public String getOfficeOpeningHours() {
        return officeOpeningHours;
    }

    public void setOfficeOpeningHours(String officeOpeningHours) {
        this.officeOpeningHours = officeOpeningHours;
    }

    public String getOfficeImage1Url() {
        return officeImage1Url;
    }

    public void setOfficeImage1Url(String officeImage1Url) {
        this.officeImage1Url = officeImage1Url;
    }

    public String getOfficeImage2Url() {
        return officeImage2Url;
    }

    public void setOfficeImage2Url(String officeImage2Url) {
        this.officeImage2Url = officeImage2Url;
    }

    public String getOfficeDocumentsNeeded() {
        return officeDocumentsNeeded;
    }

    public void setOfficeDocumentsNeeded(String officeDocumentsNeeded) {
        this.officeDocumentsNeeded = officeDocumentsNeeded;
    }

    public String getOfficeProcessingTime() {
        return officeProcessingTime;
    }

    public void setOfficeProcessingTime(String officeProcessingTime) {
        this.officeProcessingTime = officeProcessingTime;
    }

    public String getOfficeAppointmentsUrl() {
        return officeAppointmentsUrl;
    }

    public void setOfficeAppointmentsUrl(String officeAppointmentsUrl) {
        this.officeAppointmentsUrl = officeAppointmentsUrl;
    }
}