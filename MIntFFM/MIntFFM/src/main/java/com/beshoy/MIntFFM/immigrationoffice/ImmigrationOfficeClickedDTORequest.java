package com.beshoy.MIntFFM.immigrationoffice;


public record ImmigrationOfficeClickedDTORequest(
        String officeName,
        String officeAddress,
        String officeNumber,
        String officeEmail,
        String officeUrl,
        String officeOpeningHours,
        String officeImage1Url,
        String officeImage2Url,
        String officeDocumentsNeeded,
        String officeProcessingTime,
        String officeAppointmentsUrl
) {
}