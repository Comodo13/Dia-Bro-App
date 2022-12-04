package com.easychicken.diabroapp.service

import com.intersystems.jdbc.IRISDataSource
import org.springframework.stereotype.Repository
import java.lang.Exception
import java.sql.Connection
import java.sql.PreparedStatement
import java.time.Instant

@Repository
class IRISservice {

    //127.0.0.1
    fun getConnection(): Connection {
        val url = "jdbc:IRIS://127.0.0.1:1972/USER"
        val ds = IRISDataSource()
        ds.url = url
        ds.user = "SuperUser"
        ds.password = "12ecgf3t"

        return ds.connection

    }

    //DDL tables creation
    fun createPatienNotestTable() {
        val con = getConnection()
        val stmt = con.createStatement()
        val tableSql = (
                "CREATE TABLE patient_notes(note_id int PRIMARY KEY AUTO_INCREMENT, patient_id int, weight varchar(30), states varchar(100), created varchar(30), blood_press varchar(30));")
        stmt.execute(tableSql)
        con.close()
    }
    fun createPatientTable() {
        val con = getConnection()
        val stmt = con.createStatement()
        val tableSql = "CREATE TABLE patients(patient_id int PRIMARY KEY AUTO_INCREMENT, name varchar(30), birth varchar(40), gender varchar(20))"
        stmt.execute(tableSql)
        con.close()
    }
    fun createInsulinTable() {
        val con = getConnection()
        val stmt = con.createStatement()
        val tableSql = "CREATE TABLE insulin(insulin_id int PRIMARY KEY AUTO_INCREMENT, patient_id int, dose varchar(30), type varchar(20), created varchar(30))"
        stmt.execute(tableSql)
        con.close()
    }


    //inserts
    fun insertPatient(dto: Patient): String {
        val con = getConnection()
        val insertSql = "INSERT INTO patients(patient_id, name, birth, gender) VALUES(?,?,?,?)"
        val myStatement: PreparedStatement = con.prepareStatement(insertSql)
        myStatement.setInt(1, dto.id.toInt())
        myStatement.setString(2, dto.fullName)
        myStatement.setString(3, dto.dateOfBirth)
        myStatement.setString(4, dto.gender)
        myStatement.execute()
        con.close()
        return "inserted patient with id ${dto.id}"
    }
    fun insertPatientNote(dto: PatientNoteCreateRequest): String {
        val con = getConnection()
        val insertSql = "INSERT INTO patient_notes(patient_id, weight, states, created, blood_press) VALUES(?,?,?,?,?)"
        val myStatement: PreparedStatement = con.prepareStatement(insertSql)
        myStatement.setInt(1, dto.patientId)
        myStatement.setString(2, dto.weight)
        myStatement.setString(3, dto.states)
        myStatement.setString(4, dto.created.toString())
        myStatement.setString(5, dto.bloodPressure)

        myStatement.execute()
        con.close()
        return "inserted"
    }
    fun insertInsulin(dto: InsulinCreateRequest): String {
        val con = getConnection()
        val insertSql = "INSERT INTO insulin(patient_id, dose, type, created) VALUES(?,?,?,?)"
        val myStatement: PreparedStatement = con.prepareStatement(insertSql)
        myStatement.setInt(1, dto.patientId)
        myStatement.setString(2, dto.insulinDose)
        myStatement.setString(3, dto.insulinType)
        myStatement.setString(4, Instant.now().toString())

        myStatement.execute()
        con.close()
        return "inserted patient with id ${dto.patientId}"
    }
    //selects
    fun getPatientNotesByPatientId(patientId: Int): List<PatientNote> {
        val con = getConnection()
        val selectSql = "select * from patient_notes where patient_id = $patientId"
        val myStatement: PreparedStatement = con.prepareStatement(selectSql)
        val resultSet = myStatement.executeQuery()
        val notes = mutableListOf<PatientNote>()
        while (resultSet.next()) {
            val id = resultSet.getInt("note_id")
            val weight = resultSet.getString("weight")
            val states = resultSet.getString("states")
            val created = resultSet.getString("created")
            val bloodPressure = resultSet.getString("blood_press")
            notes.add(PatientNote(id, patientId, weight, states, created, bloodPressure))
        }
        return notes
    }


    fun getPatientById(id: Int): Patient {
        val con = getConnection()
        val selectSql = "select * from patients where patient_id = $id"
        val myStatement: PreparedStatement = con.prepareStatement(selectSql)
        val resultSet = myStatement.executeQuery()
        while (resultSet.next()) {
            val name = resultSet.getString("name")
            val birth = resultSet.getString("birth")
            val gender = resultSet.getString("gender")
            return  Patient(id.toString(), name, birth, gender)
        }
        throw Exception("didnt found patient with id $id")
    }
    fun getAllPatients(): List<Patient> {
        val con = getConnection()
        val selectSql = "select * from patients"
        val myStatement: PreparedStatement = con.prepareStatement(selectSql)
        val resultSet = myStatement.executeQuery()
        val patients = mutableListOf<Patient>()
        while (resultSet.next()) {
            val id = resultSet.getInt("patient_id")
            val name = resultSet.getString("name")
            val birth = resultSet.getString("birth")
            val gender = resultSet.getString("gender")
            patients.add(Patient(id.toString(), name, birth, gender))
        }
        return patients
        throw Exception("didnt found any patients")
    }

    fun getInsulinRecordsByPatientId(patientId: Int): List<InsulinRecord> {
        val con = getConnection()
        val selectSql = "select * from insulin where patient_id = $patientId"
        val myStatement: PreparedStatement = con.prepareStatement(selectSql)
        val resultSet = myStatement.executeQuery()
        val insulinRecords = mutableListOf<InsulinRecord>()
        while (resultSet.next()) {
            val insulinId = resultSet.getInt("insulin_id")
            val patientId = resultSet.getInt("patient_id")
            val created = resultSet.getString("created")
            val insulinDose = resultSet.getString("dose")
            val insulinType = resultSet.getString("type")
            insulinRecords.add(InsulinRecord(insulinId, patientId, insulinDose, insulinType, Instant.parse(created)))
        }
        return insulinRecords
        throw Exception( "didnt found patient with patientID $patientId")
    }
}

    data class PatientDto(
        val id: Int,
        val name: String?,
        val birth: String?,
        val gender: String?,
        val momNotesIds: List<Int> = listOf(),
    )

    data class PatientNote(
        val id: Int,
        val patientId: Int,
        val weight: String = "",
        val states: String = "",
        val created: String = "",
        val bloodPressure: String = ""
    )

data class PatientNoteCreateRequest(
    val patientId: Int,
    val weight: String? = "",
    val states: String? = "",
    val created: Instant? = Instant.now(),
    val bloodPressure: String? = "",
)
data class InsulinCreateRequest(
    val patientId: Int,
    val insulinDose: String? = "",
    val insulinType: String? = "short"
)
data class InsulinRecord(
    val insulinId: Int,
    val patientId: Int,
    val insulinDose: String? = "",
    val insulinType: String? = "short",
    val created: Instant = Instant.now()
)
