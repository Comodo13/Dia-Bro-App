package com.easychicken.diabroapp.controller.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.exchange

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

        return Patient(persistedId, finalName, birth, gender)
    }


    fun getPatientPrescriptions(id: Int, apiChunk: String): List<Prescription> {
        val map = readMapFromHfire(id, apiChunk)
        val entries = map["entry"] as ArrayList<LinkedHashMap<String, String>>
        val result = mutableListOf<Prescription>()

        entries.forEach {
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
        entries.forEach {
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
                    tests.add(LabTest(display,unit,value, time.toString()))
                }
            }
        }
        return tests
    }


    fun readMapFromHfire(id: Int, apiChunk: String): MutableMap<Any, Any> {
        val headers = org.springframework.http.HttpHeaders()
        val requestEntity: HttpEntity<String> = HttpEntity(headers)
        headers.set("x-api-key", "vgeSEbV3yt1nO7BQShriO4GAnW6an7cr6dtYLxVJ")
        val response: ResponseEntity<String> = restTemplate.exchange(
            "https://fhir.nbefgrb0enyf.static-test-account.isccloud.io$apiChunk$id",
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
)
