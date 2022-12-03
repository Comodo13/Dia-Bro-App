package com.easychicken.diabroapp.service

import org.hl7.fhir.r4.model.HumanName
import org.hl7.fhir.r4.model.Practitioner
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime

@Service
class HfirSimulationService {


    // Due to internal problems in my HFIR server on AWS I can't use HfirService right now.
    // I will simulate it here for demo purposes of our app.

    final val practitioner = Practitioner()
        .addName(HumanName().addGiven("Joel Evans"))

    final val testValues = listOf<Double>(133.5, 144.2, 111.2, 150.1, 78.2, 77.4, 125.3)
    val devices = listOf(
        Device(
            name = "DexCom G6",
            dateActivted = Instant.now()
        ),
        Device(
            name = "Smart Insulin Pen",
            dateActivted = Instant.now()
        ),
        Device(
            name = "Insulin Pump",
            dateActivted = Instant.now()
        ),

    )
    val labtests = listOf(
        LabTest(
            name = "TriGlyceride",
            unit = "mg/dL",
            result = testValues.random(),
            time = LocalDateTime.now().minusDays(20L).toString()
        ),
        LabTest(
            name = "Total Cholesterol",
            unit = "mg/dL",
            result = testValues.random(),
            time = LocalDateTime.now().minusDays(15L).toString()
        ),
        LabTest(
            name = "LDL Cholesterol",
            unit = "mg/dL",
            result = testValues.random(),
            time = LocalDateTime.now().minusDays(10L).toString()
        ),
        LabTest(
            name = "HDL Cholesterol",
            unit = "mg/dL",
            result = testValues.random(),
            time = LocalDateTime.now().minusDays(6L).toString()
        )
    )
    val encounters = listOf(
        Encounter(
            doctorName = practitioner.name[0].givenAsSingleString,
            hospitalName = "St Monica Hospital",
            start = LocalDateTime.now().plusDays(30).toString()
        ),
        Encounter(
            doctorName = practitioner.name[0].givenAsSingleString,
            hospitalName = "St Monica Hospital",
            start = LocalDateTime.now().plusDays(20).toString()
        ),
        Encounter(
            doctorName = practitioner.name[0].givenAsSingleString,
            hospitalName = "St Monica Hospital",
            start = LocalDateTime.now().plusDays(3).toString()
        ),
        Encounter(
            doctorName = practitioner.name[0].givenAsSingleString,
            hospitalName = "St Monica Hospital",
            start = LocalDateTime.now().plusDays(1).toString()
        ),
    )
    val prescriptions = listOf<Prescription>(
        Prescription(
            "Hydrochlorothiazide 12.5 MG",
            LocalDateTime.now().minusDays(30).toString(),
            frequency = "1",
            period = "1",
            periodUnit = "d",
            dosage = "1",
            practitioner.name[0].givenAsSingleString
        ),
        Prescription(
            "amLODIPine 5 MG",
            LocalDateTime.now().minusDays(30).toString(),
            frequency = "2",
            period = "1",
            periodUnit = "d",
            dosage = "2",
            practitioner.name[0].givenAsSingleString
        ),
        Prescription(
            "Olmesartan medoxomil 20 MG Oral Tablet",
            LocalDateTime.now().minusDays(30).toString(),
            frequency = "2",
            period = "3",
            periodUnit = "d",
            dosage = "2",
            practitioner.name[0].givenAsSingleString
        ),
        Prescription(
            "2CB 20mg",
            LocalDateTime.now().minusDays(30).toString(),
            frequency = "2",
            period = "2",
            periodUnit = "d",
            dosage = "2",
            practitioner.name[0].givenAsSingleString
        ),
        Prescription(
            "Aspirine 5mg",
            LocalDateTime.now().minusDays(30).toString(),
            frequency = "1",
            period = "1",
            periodUnit = "d",
            dosage = "2",
            practitioner.name[0].givenAsSingleString
        ),

    )
    fun getDoctor(): Practitioner {
        return practitioner
    }
    fun getPatientLabTests(id: Int) : List<LabTest> {
        return labtests
    }

    fun getPatientsByDoctorId(): List<Patient> {
        return listOf(
            Patient(
                id = "1",
                fullName = "Mariia Rodriguez",
                dateOfBirth = "12.05.1991",
                gender = "female"
            ),
            Patient(
                id = "2",
                fullName = "Daisy Butler",
                dateOfBirth = "11.01.1977",
                gender = "female"
            ),
            Patient(
                id = "3",
                fullName = "Molly Jones",
                dateOfBirth = "30.01.1955",
                gender = "female"
            ),
            Patient(
                id = "4",
                fullName = "Mary Lou",
                dateOfBirth = "13.02.1989",
                gender = "female"
            ),
        )
    }
    fun getAllDevices(id: Int): List<Device> {
        return devices
    }

    fun getPatientPrescriptions(id: Int): List<Prescription> {
        return listOf(prescriptions.random(), prescriptions.random())
    }
    fun patientEncounters(id: Int): List<Encounter> {
        return listOf(encounters.random(), encounters.random())
    }

}