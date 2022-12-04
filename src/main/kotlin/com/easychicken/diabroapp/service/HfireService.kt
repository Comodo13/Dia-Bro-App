package com.easychicken.diabroapp.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.exchange
import java.time.Instant
import java.time.LocalDate

@Service
class HfireService(
    restTemplateBuilder: RestTemplateBuilder
) {

    private val restTemplate = restTemplateBuilder.build()


    fun getPatient(id: Int, apiChunk: String): Patient {
        val map = readMapFromHfire(id, apiChunk)

        val persistedId = map["id"].toString()
        val names = map["name"] as ArrayList<LinkedHashMap<String, String>>
        val name = names[0]
        val prefix = name!!.get("prefix") as ArrayList<String>
        val prefix1 = prefix[0]
        val firstName = name!!.get("family")
        val secName = name.get("given") as ArrayList<String>
        val secName1 = secName[0]
        val finalName = prefix1 + " " + secName1 + " " + firstName
        val birth = map["birthDate"] as String
        val gender = map["gender"] as String

        return Patient(persistedId, finalName, birth, gender, listOf(), listOf(), listOf(), listOf(), appointment = Appointment(
            LocalDate.now(), "Santa Monica")
        )
    }


    fun getPatientPrescriptions(id: Int, apiChunk: String): List<Prescription> {
        val map = readMapFromHfire(id, apiChunk)
        val entries = map["entry"] as ArrayList<LinkedHashMap<String, String>>
        val result = mutableListOf<Prescription>()

        val entriesFirst = entries.takeLast(3) //iterate through lately results
        entriesFirst.forEach {
            val resource = it.get("resource") as LinkedHashMap<String, String>
            val prescript = resource.get("medicationCodeableConcept") as LinkedHashMap<String, String>
            val coding = prescript.get("coding") as ArrayList<String>
            val medicine0 = coding[0] as LinkedHashMap<String, String>
            val medicine1 = medicine0.get("display")
            val requester = resource.get("requester") as LinkedHashMap<String, String>
            val doctorName = requester.get("display")


            val time = resource.get("authoredOn")
            var frequency = ""
            var period = ""
            var periodUnit = ""
            try {
                val dosageInstruction = resource.get("dosageInstruction") as ArrayList<LinkedHashMap<String, String>>
                val ff = dosageInstruction[0] as LinkedHashMap<String, String>
                val timing = ff.get("timing") as LinkedHashMap<String, String>
                val repeat = timing.get("repeat") as LinkedHashMap<String, String>
                frequency = repeat.get("frequency").toString()
                period = repeat.get("period").toString()
                periodUnit = repeat.get("periodUnit").toString()
                result.add(Prescription(medicine1, time, frequency, period, periodUnit, frequency, doctorName))
            } catch (e: Exception) {
                println("no instructions " + e)
            }
        }
        return result
    }

    fun getLaboratoryTestByPatientId(id: Int, apiChunk: String): List<LabTest> {
        val interested = mutableListOf<String>() //lab tests we are looking for
        interested.add("Total Cholesterol")
        interested.add("Low Density Lipoprotein Cholesterol")
        interested.add("High Density Lipoprotein Cholesterol")
        interested.add("Triglycerides")
        val tests = mutableListOf<LabTest>()
        val map = readMapFromHfire(id, apiChunk)
        val entries = map["entry"] as ArrayList<LinkedHashMap<String, String>>
        entries.removeAt(0)
        val entriesFirst = entries.takeLast(2)    //iterate through lately results
        entriesFirst.forEach {
            val resource = it.get("resource") as LinkedHashMap<String, String>
            val result = resource.get("result") as ArrayList<LinkedHashMap<String, String>>
            result.forEach {
                val display = it.get("display")?.trim()
                if (display in interested) {
                    val referenceParts = it.get("reference")?.trim()!!.split("/")
                    val observationId = referenceParts[1]
                    val map = readMapFromHfire(observationId.toInt(), "/Observation/")
                    val observationResult = map.get("valueQuantity") as LinkedHashMap<String, String>
                    val time = map.get("effectiveDateTime")
                    val value = observationResult.get("value") as Double
                    val unit = observationResult.get("unit")
                    tests.add(LabTest(display, unit, value, time.toString()))
                }
            }
        }
        return tests
    }
    fun getAppointmentsByPatientId(id: Int, apiChunk: String) : List<Encounter> {
        val map = readMapFromHfire(id, apiChunk)
        val entries = map["entry"] as ArrayList<LinkedHashMap<String, String>>
        val encounters = mutableListOf<Encounter>()
        val entriesLast = entries.takeLast(4)
        entriesLast.forEach {
            val resource = it.get("resource") as LinkedHashMap<String, String>
            val participant = resource.get("participant") as ArrayList<LinkedHashMap<String, String>>
            val individualMap = participant[0] as LinkedHashMap<String, String>
            val individual = individualMap.get("individual") as LinkedHashMap<String, String>
            val displayDoctor = individual.get("display")
            val period = resource.get("period") as LinkedHashMap<String, String>
            val start = period.get("start")
            val serviceProvider = resource.get("serviceProvider") as LinkedHashMap<String, String>
            val displayHospital = serviceProvider.get("display")
            encounters.add(Encounter(displayDoctor, displayHospital, start))
        }
        return encounters
    }
    fun getDevicesByPatientId(id: Int): List<Device> {
        return listOf(
            Device("DexCom 6G", Instant.now().minusMillis(40000000)),
            Device("Smart Insulin Pen", Instant.now().minusMillis(40000000)),
            Device("Insulin Pump", Instant.now().minusMillis(40000000))
        )
    }


    fun readMapFromHfire(id: Int, apiChunk: String): MutableMap<Any, Any> {
        val headers = org.springframework.http.HttpHeaders()
        val requestEntity: HttpEntity<String> = HttpEntity(headers)
        headers.set("x-api-key", "KfMlVLHZy52fnMC2DeaVp0EVMLjVbTG5gNTLAlQ4")
        val response: ResponseEntity<String> = restTemplate.exchange(
            "https://fhir.rxmtxux4mj3c.static-test-account.isccloud.io$apiChunk$id",
            HttpMethod.GET,
            requestEntity
        )
        val json = response.body!!

        return ObjectMapper().readValue<MutableMap<Any, Any>>(json)
    }
}

data class LabTest(
    val name: String?,
    val unit: String?,
    val result: Double,
    val time: String
)

data class Prescription(
    val medicine: String?,
    val time: String?,
    val frequency: String?,
    val period: String?,
    val periodUnit: String?,
    val dosage: String?,
    val doctorName: String?

)
data class Patient(
    val id: String,
    val fullName: String,
    val dateOfBirth: String,
    val gender: String,
    val prescriptions: List<Prescription> = listOf(),
    val labTests: List<LabTest> = listOf(),
    val encounters: List<Encounter> = listOf(),
    val glucoseObservations: List<GlucoseObservation> = listOf(),
    val appointment: Appointment? = null
)

data class Encounter(
    val doctorName: String?,
    val hospitalName: String?,
    val start: String?
)

data class Device(
    val name: String,
    val dateActivted: Instant? = null
)

data class GlucoseObservation(
    val value: Double,
    val time: Instant
)
data class Appointment(
    val start: LocalDate,
    val hospitalName: String

)
