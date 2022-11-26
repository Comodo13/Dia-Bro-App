package com.easychicken.diabroapp.controller

import com.easychicken.diabroapp.controller.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/patient")
class PatientController(
    private val hfireService: HfireService
) {

    @GetMapping("/{id}")
    fun getPatientById(@PathVariable id: Int): ResponseEntity<Patient> {
        return ResponseEntity.ok(hfireService.getPatient(id, "/Patient/"))
    }
    @GetMapping("/meds/{id}")
    fun getMedsByPatientId(@PathVariable id: Int): List<Prescription> {
        return hfireService.getPatientPrescriptions(id, "/MedicationRequest?patient=")
    }
    @GetMapping("/tests/{id}")
    fun getTestsByPatientId(@PathVariable id: Int): List<LabTest> {
        return hfireService.getLaboratoryTestByPatientId(id, "/DiagnosticReport?patient=")
    }
    @GetMapping("/encounters/{id}")
    fun getAppointmentsByPatientId(@PathVariable id: Int):  List<Encounter>  {
        return hfireService.getAppointmentsByPatientId(id, "/Encounter?patient=")
    }

    @GetMapping("/devices/{id}")
    fun getDevicesByPatientId(@PathVariable id: Int):  List<Device>  {
        return hfireService.getDevicesByPatientId(id)
    }

    @GetMapping("/glucoses/{id}")
    fun getLastGlucoseByPatientId(@PathVariable id: Int):  List<GlucoseObservation>  {
        return hfireService.getLastTenGlucoseObservations(id)
    }

    @PostMapping("/recordglucose/{id}")
    fun recordLastGlucoseByPatientId(@PathVariable id: Int, @RequestBody glucose: GlucoseObservation):  GlucoseObservation  {
        return hfireService.recordGlucoseObservation(id, glucose)
    }
}