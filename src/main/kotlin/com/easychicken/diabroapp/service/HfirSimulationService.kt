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

    val prescriptions = listOf<Prescription>(
        Prescription(
            "Purina",
            LocalDateTime.now().minusDays(30).toString(),
            frequency = "2",
            period = "1",
            periodUnit = "d",
            dosage = "2",
            practitioner.name[0].givenAsSingleString
        )
    )
    fun getDoctor(): Practitioner {
        return practitioner
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

    fun getPatientPrescriptions(id: Int): List<Prescription> {
        return listOf(
            Prescription(
                "Purina",
                LocalDateTime.now().minusDays(30).toString(),
                frequency = "2",
                period = "1",
                periodUnit = "d",
                dosage = "2",
                practitioner.name[0].givenAsSingleString
            )
        )
    }

}