package com.easychicken.diabroapp.service

import org.hl7.fhir.r4.model.Appointment
import org.hl7.fhir.r4.model.HumanName
import org.hl7.fhir.r4.model.Practitioner
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class HfirSimulationRepository {

    // My amazing Women's health dataset for 4 patients and 1 doctor :)

    final val practitioner = Practitioner()
        .addName(HumanName().addGiven("Joel Evans"))


    fun getAllDevices(): List<Device> = listOf(
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
        )
    )


    fun getAllPatients(): List<Patient> {
        return listOf(
            Patient(
                id = "1",
                fullName = "Mariia Rodriguez",
                dateOfBirth = "12.05.1991",
                gender = "female",
                prescriptions = listOf(
                    Prescription(
                        "Hydrochlorothiazide 12.5 MG",
                        LocalDateTime.now().minusDays(10).toString(),
                        frequency = "1",
                        period = "1",
                        periodUnit = "d",
                        dosage = "1",
                        practitioner.name[0].givenAsSingleString
                    ),
                    Prescription(
                        "amLODIPine 5 MG",
                        LocalDateTime.now().minusDays(20).toString(),
                        frequency = "2",
                        period = "1",
                        periodUnit = "d",
                        dosage = "2",
                        practitioner.name[0].givenAsSingleString
                    )
                ),
                encounters = listOf(
                    Encounter(
                        doctorName = practitioner.name[0].givenAsSingleString,
                        hospitalName = "St Monica Hospital",
                        start = LocalDateTime.now().plusDays(4).toString()
                    ),
                    Encounter(
                        doctorName = practitioner.name[0].givenAsSingleString,
                        hospitalName = "St Monica Hospital",
                        start = LocalDateTime.now().minusDays(56).toString()
                    )
                ),
                labTests = listOf(
                    LabTest(
                        name = "TriGlyceride",
                        unit = "mg/dL",
                        result = 120.1,
                        time = LocalDateTime.now().minusDays(20L).toString()
                    ),
                    LabTest(
                        name = "Total Cholesterol",
                        unit = "mg/dL",
                        result = 77.8,
                        time = LocalDateTime.now().minusDays(19L).toString()
                    ),
                    LabTest(
                        name = "LDL Cholesterol",
                        unit = "mg/dL",
                        result = 44.5,
                        time = LocalDateTime.now().minusDays(120L).toString()
                    ),
                    LabTest(
                        name = "HDL Cholesterol",
                        unit = "mg/dL",
                        result = 55.6,
                        time = LocalDateTime.now().minusDays(120L).toString()
                    )
                ),
                glucoseObservations = listOf(
                    GlucoseObservation(4.5, Instant.now()),
                    GlucoseObservation(4.6, Instant.now().minusMillis(3600000)),
                    GlucoseObservation(4.7, Instant.now().minusMillis(3600000*2)),
                    GlucoseObservation(4.9, Instant.now().minusMillis(3600000*3)),
                    GlucoseObservation(4.8, Instant.now().minusMillis(3600000*4)),
                    GlucoseObservation(5.0, Instant.now().minusMillis(3600000*5)),
                    GlucoseObservation(4.6, Instant.now().minusMillis(3600000*6)),
                    GlucoseObservation(4.3, Instant.now().minusMillis(3600000*7)),
                    GlucoseObservation(4.0, Instant.now().minusMillis(3600000*8)),
                    GlucoseObservation(4.6, Instant.now().minusMillis(3600000*9)),
                    GlucoseObservation(4.9, Instant.now().minusMillis(3600000*10)),
                ),
                appointment = Appointment(start = LocalDate.now().plusDays(6),"St Jones Hospital" )


            ),
            Patient(
                id = "2",
                fullName = "Daisy Butler",
                dateOfBirth = "11.01.1977",
                gender = "female",
                prescriptions = listOf(
                    Prescription(
                        "Olmesartan medoxomil 20 MG Oral Tablet",
                        LocalDateTime.now().minusDays(10).toString(),
                        frequency = "3",
                        period = "1",
                        periodUnit = "d",
                        dosage = "1",
                        practitioner.name[0].givenAsSingleString
                    ),
                    Prescription(
                        "amLODIPine 5 MG",
                        LocalDateTime.now().minusDays(44).toString(),
                        frequency = "2",
                        period = "1",
                        periodUnit = "d",
                        dosage = "2",
                        practitioner.name[0].givenAsSingleString
                    )
                ),
                encounters = listOf(
                    Encounter(
                        doctorName = practitioner.name[0].givenAsSingleString,
                        hospitalName = "St Monica Hospital",
                        start = LocalDateTime.now().plusDays(30).toString()
                    ),
                    Encounter(
                        doctorName = practitioner.name[0].givenAsSingleString,
                        hospitalName = "St Monica Hospital",
                        start = LocalDateTime.now().minusDays(20).toString()
                    )
                ),
                labTests = listOf(
                    LabTest(
                        name = "TriGlyceride",
                        unit = "mg/dL",
                        result = 133.1,
                        time = LocalDateTime.now().minusDays(3L).toString()
                    ),
                    LabTest(
                        name = "Total Cholesterol",
                        unit = "mg/dL",
                        result = 55.8,
                        time = LocalDateTime.now().minusDays(3L).toString()
                    ),
                    LabTest(
                        name = "LDL Cholesterol",
                        unit = "mg/dL",
                        result = 45.5,
                        time = LocalDateTime.now().minusDays(100L).toString()
                    ),
                    LabTest(
                        name = "HDL Cholesterol",
                        unit = "mg/dL",
                        result = 65.6,
                        time = LocalDateTime.now().minusDays(103L).toString()
                    )
                ),
                glucoseObservations = listOf(
                    GlucoseObservation(4.5, Instant.now()),
                    GlucoseObservation(5.6, Instant.now().minusMillis(3600000)),
                    GlucoseObservation(6.7, Instant.now().minusMillis(3600000*2)),
                    GlucoseObservation(6.7, Instant.now().minusMillis(3600000*3)),
                    GlucoseObservation(7.3, Instant.now().minusMillis(3600000*4)),
                    GlucoseObservation(7.6, Instant.now().minusMillis(3600000*5)),
                    GlucoseObservation(7.5, Instant.now().minusMillis(3600000*6)),
                    GlucoseObservation(7.6, Instant.now().minusMillis(3600000*7)),
                    GlucoseObservation(7.9, Instant.now().minusMillis(3600000*8)),
                    GlucoseObservation(8.4, Instant.now().minusMillis(3600000*9)),
                    GlucoseObservation(8.0, Instant.now().minusMillis(3600000*10)),
                ),
                appointment = Appointment(start = LocalDate.now().plusDays(4),"St Louis Hospital" )
            ),
            Patient(
                id = "3",
                fullName = "Molly Jones",
                dateOfBirth = "30.01.1955",
                gender = "female",
                prescriptions = listOf(
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
                        "Olmesartan medoxomil 20 MG Oral Tablet",
                        LocalDateTime.now().minusDays(30).toString(),
                        frequency = "2",
                        period = "1",
                        periodUnit = "d",
                        dosage = "2",
                        practitioner.name[0].givenAsSingleString
                    )
                ),
                encounters = listOf(
                    Encounter(
                        doctorName = practitioner.name[0].givenAsSingleString,
                        hospitalName = "St Monica Hospital",
                        start = LocalDateTime.now().plusDays(30).toString()
                    ),
                    Encounter(
                        doctorName = practitioner.name[0].givenAsSingleString,
                        hospitalName = "St Monica Hospital",
                        start = LocalDateTime.now().minusDays(20).toString()
                    )
                ),
                labTests = listOf(
                    LabTest(
                        name = "TriGlyceride",
                        unit = "mg/dL",
                        result = 88.1,
                        time = LocalDateTime.now().minusDays(10L).toString()
                    ),
                    LabTest(
                        name = "Total Cholesterol",
                        unit = "mg/dL",
                        result = 68.3,
                        time = LocalDateTime.now().minusDays(10L).toString()
                    ),
                    LabTest(
                        name = "LDL Cholesterol",
                        unit = "mg/dL",
                        result = 36.3,
                        time = LocalDateTime.now().minusDays(7L).toString()
                    ),
                    LabTest(
                        name = "HDL Cholesterol",
                        unit = "mg/dL",
                        result = 54.6,
                        time = LocalDateTime.now().minusDays(88L).toString()
                    )
                ),
                glucoseObservations = listOf(
                    GlucoseObservation(4.5, Instant.now()),
                    GlucoseObservation(5.0, Instant.now().minusMillis(3600000)),
                    GlucoseObservation(4.7, Instant.now().minusMillis(3600000*2)),
                    GlucoseObservation(5.3, Instant.now().minusMillis(3600000*3)),
                    GlucoseObservation(5.5, Instant.now().minusMillis(3600000*4)),
                    GlucoseObservation(5.5, Instant.now().minusMillis(3600000*5)),
                    GlucoseObservation(6.1, Instant.now().minusMillis(3600000*6)),
                    GlucoseObservation(6.2, Instant.now().minusMillis(3600000*7)),
                    GlucoseObservation(6.3, Instant.now().minusMillis(3600000*8)),
                    GlucoseObservation(6.5, Instant.now().minusMillis(3600000*9)),
                    GlucoseObservation(6.6, Instant.now().minusMillis(3600000*10)),
                ),
                appointment = Appointment(start = LocalDate.now().plusDays(12),"St Jones Hospital" )
            ),
            Patient(
                id = "4",
                fullName = "Mary Lou",
                dateOfBirth = "13.02.1989",
                gender = "female",
                prescriptions = listOf(
                    Prescription(
                        "Olmesartan medoxomil 20 MG Oral Tablet",
                        LocalDateTime.now().minusDays(66).toString(),
                        frequency = "3",
                        period = "1",
                        periodUnit = "d",
                        dosage = "1",
                        practitioner.name[0].givenAsSingleString
                    ),
                    Prescription(
                        "amLODIPine 5 MG",
                        LocalDateTime.now().minusDays(12).toString(),
                        frequency = "2",
                        period = "1",
                        periodUnit = "d",
                        dosage = "2",
                        practitioner.name[0].givenAsSingleString
                    )
                ),
                encounters = listOf(
                    Encounter(
                        doctorName = practitioner.name[0].givenAsSingleString,
                        hospitalName = "St Monica Hospital",
                        start = LocalDateTime.now().plusDays(30).toString()
                    ),
                    Encounter(
                        doctorName = practitioner.name[0].givenAsSingleString,
                        hospitalName = "St Monica Hospital",
                        start = LocalDateTime.now().minusDays(20).toString()
                    )
                ),
                labTests = listOf(
                    LabTest(
                        name = "TriGlyceride",
                        unit = "mg/dL",
                        result = 94.1,
                        time = LocalDateTime.now().minusDays(94L).toString()
                    ),
                    LabTest(
                        name = "Total Cholesterol",
                        unit = "mg/dL",
                        result = 75.8,
                        time = LocalDateTime.now().minusDays(90L).toString()
                    ),
                    LabTest(
                        name = "LDL Cholesterol",
                        unit = "mg/dL",
                        result = 49.5,
                        time = LocalDateTime.now().minusDays(1L).toString()
                    ),
                    LabTest(
                        name = "HDL Cholesterol",
                        unit = "mg/dL",
                        result = 22.6,
                        time = LocalDateTime.now().minusDays(1L).toString()
                    )
                ),
                glucoseObservations = listOf(
                    GlucoseObservation(4.5, Instant.now()),
                    GlucoseObservation(4.6, Instant.now().minusMillis(3600000)),
                    GlucoseObservation(4.7, Instant.now().minusMillis(3600000*2)),
                    GlucoseObservation(4.9, Instant.now().minusMillis(3600000*3)),
                    GlucoseObservation(4.8, Instant.now().minusMillis(3600000*4)),
                    GlucoseObservation(5.0, Instant.now().minusMillis(3600000*5)),
                    GlucoseObservation(4.6, Instant.now().minusMillis(3600000*6)),
                    GlucoseObservation(4.3, Instant.now().minusMillis(3600000*7)),
                    GlucoseObservation(4.0, Instant.now().minusMillis(3600000*8)),
                    GlucoseObservation(4.6, Instant.now().minusMillis(3600000*9)),
                    GlucoseObservation(4.9, Instant.now().minusMillis(3600000*10)),
                ),
                appointment = Appointment(start = LocalDate.now().plusDays(17),"St Helen Hospital" )
            )
        )
    }
}