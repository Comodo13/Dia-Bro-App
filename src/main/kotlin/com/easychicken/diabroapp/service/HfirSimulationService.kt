package com.easychicken.diabroapp.service

import org.hl7.fhir.r4.model.HumanName
import org.hl7.fhir.r4.model.Practitioner
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime

@Service
class HfirSimulationService(
    hfirSimulationRepository: HfirSimulationRepository,
) {


    // Due to internal problems in my HFIR server on AWS I can't use HfirService right now.
    // I will simulate it here for demo purposes of our app.

    val patients = hfirSimulationRepository.getAllPatients()  //init patients

    fun getPatientsById(id: Int): Patient {
        return when (id) {
            1 -> patients[0]
            2 -> patients[1]
            3 -> patients[2]
            4 -> patients[3]
            else -> {
                return patients[0]
            }
        }
    }

    fun getPatientPrescriptions(id: Int): List<Prescription> {
        return getPatientsById(id).prescriptions
    }

    fun patientEncounters(id: Int): List<Encounter> {
        return getPatientsById(id).encounters
    }
    fun getPatientLabTests(id: Int): List<LabTest> {
        return getPatientsById(id).labTests
    }
    fun getPatientAppointments(id: Int): Appointment {
        return getPatientsById(id).appointment
    }
    fun getLastTenGlucoseObservations(id: Int): List<GlucoseObservation> {
        return getPatientsById(id).glucoseObservations
    }

    fun recordGlucoseObservation(id: Int, glucose: GlucoseObservation): GlucoseObservation {
        return glucose
    }

//
//    val labtests = listOf(
//        LabTest(
//            name = "TriGlyceride",
//            unit = "mg/dL",
//            result = testValues.random(),
//            time = LocalDateTime.now().minusDays(20L).toString()
//        ),
//        LabTest(
//            name = "Total Cholesterol",
//            unit = "mg/dL",
//            result = testValues.random(),
//            time = LocalDateTime.now().minusDays(15L).toString()
//        ),
//        LabTest(
//            name = "LDL Cholesterol",
//            unit = "mg/dL",
//            result = testValues.random(),
//            time = LocalDateTime.now().minusDays(10L).toString()
//        ),
//        LabTest(
//            name = "HDL Cholesterol",
//            unit = "mg/dL",
//            result = testValues.random(),
//            time = LocalDateTime.now().minusDays(6L).toString()
//        )
//    )
//    val encounters = listOf(
//        Encounter(
//            doctorName = practitioner.name[0].givenAsSingleString,
//            hospitalName = "St Monica Hospital",
//            start = LocalDateTime.now().plusDays(30).toString()
//        ),
//        Encounter(
//            doctorName = practitioner.name[0].givenAsSingleString,
//            hospitalName = "St Monica Hospital",
//            start = LocalDateTime.now().minusDays(20).toString()
//        ),
//        Encounter(
//            doctorName = practitioner.name[0].givenAsSingleString,
//            hospitalName = "St Monica Hospital",
//            start = LocalDateTime.now().minusDays(3).toString()
//        ),
//        Encounter(
//            doctorName = practitioner.name[0].givenAsSingleString,
//            hospitalName = "St Monica Hospital",
//            start = LocalDateTime.now().minusDays(1).toString()
//        ),
//    )
//    val prescriptions = listOf<Prescription>(
//        Prescription(
//            "Hydrochlorothiazide 12.5 MG",
//            LocalDateTime.now().minusDays(30).toString(),
//            frequency = "1",
//            period = "1",
//            periodUnit = "d",
//            dosage = "1",
//            practitioner.name[0].givenAsSingleString
//        ),
//        Prescription(
//            "amLODIPine 5 MG",
//            LocalDateTime.now().minusDays(30).toString(),
//            frequency = "2",
//            period = "1",
//            periodUnit = "d",
//            dosage = "2",
//            practitioner.name[0].givenAsSingleString
//        ),
//        Prescription(
//            "Olmesartan medoxomil 20 MG Oral Tablet",
//            LocalDateTime.now().minusDays(30).toString(),
//            frequency = "2",
//            period = "3",
//            periodUnit = "d",
//            dosage = "2",
//            practitioner.name[0].givenAsSingleString
//        ),
//        Prescription(
//            "2CB 20mg",
//            LocalDateTime.now().minusDays(30).toString(),
//            frequency = "2",
//            period = "2",
//            periodUnit = "d",
//            dosage = "2",
//            practitioner.name[0].givenAsSingleString
//        ),
//        Prescription(
//            "Aspirine 5mg",
//            LocalDateTime.now().minusDays(30).toString(),
//            frequency = "1",
//            period = "1",
//            periodUnit = "d",
//            dosage = "2",
//            practitioner.name[0].givenAsSingleString
//        ),
//
//        )
//


}