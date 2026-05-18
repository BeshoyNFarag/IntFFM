package com.beshoy.MIntFFM.immigrationoffice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "office_url", nullable = false, length = 2038)
    private String officeUrl;

    @Column(name = "office_opening_hours", nullable = false, unique = true)
    private String officeOpeningHours;

    @Column(name = "office_image1_url", nullable = false, length = 2038)
    private String officeImage1Url;

    @Column(name = "office_image2_url", length = 2038)
    private String officeImage2Url;

    @Column(name = "office_documents_needed", nullable = false, length = 1000)
    private String officeDocumentsNeeded;

    @Column(name = "office_processing_time", nullable = false, length = 100)
    private String officeProcessingTime;

    @Column(name = "office_appointments_url", nullable = false, length = 2038)
    private String officeAppointmentsUrl;


}