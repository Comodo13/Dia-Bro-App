package com.easychicken.diabroapp.service

import org.springframework.stereotype.Service


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
    fun getPatientAppointments(id: Int): Appointment? {
        return getPatientsById(id).appointment
    }
    fun getLastTenGlucoseObservations(id: Int): List<GlucoseObservation> {
        return getPatientsById(id).glucoseObservations
    }

    fun recordGlucoseObservation(id: Int, glucose: GlucoseObservation): GlucoseObservation {
        return glucose
    }
}