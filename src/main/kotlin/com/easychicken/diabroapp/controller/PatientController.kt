package com.easychicken.diabroapp.controller

import com.easychicken.diabroapp.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import javax.el.ELManager

@CrossOrigin
@RestController
@RequestMapping("/patient")
class PatientController(
    private val hfireService: HfireService,
    private val irisService: IRISservice,
    private val hfirSimulationService: HfirSimulationService,

    ) {

    @GetMapping("/rest/{id}")
    fun getPatientById(@PathVariable id: Int): ResponseEntity<Patient> {
        return ResponseEntity.ok(hfireService.getPatient(id, "/Patient/"))
    }
    @GetMapping("/rest/meds/{id}")
    fun getMedsByPatientId(@PathVariable id: Int): List<Prescription> {
        return hfireService.getPatientPrescriptions(id, "/MedicationRequest?patient=")
    }
    @GetMapping("/rest/tests/{id}")
    fun getTestsByPatientId(@PathVariable id: Int): List<LabTest> {
        return hfireService.getLaboratoryTestByPatientId(id, "/DiagnosticReport?patient=")
    }
    @GetMapping("/rest/encounters/{id}")
    fun getAppointmentsByPatientId(@PathVariable id: Int):  List<Encounter>  {
        return hfireService.getAppointmentsByPatientId(id, "/Encounter?patient=")
    }

    @GetMapping("/rest/devices/{id}")
    fun getDevicesByPatientId(@PathVariable id: Int):  List<Device>  {
        return hfireService.getDevicesByPatientId(id)
    }

    @GetMapping("/rest/glucoses/{id}")
    fun getLastGlucoseByPatientId(@PathVariable id: Int): List<GlucoseObservation> {
        return hfirSimulationService.getLastTenGlucoseObservations(id)
    }

    @PostMapping("/recordglucose/{id}")
    fun recordLastGlucoseByPatientId(
        @PathVariable id: Int,
        @RequestBody glucose: GlucoseObservation
    ): GlucoseObservation {
        return hfirSimulationService.recordGlucoseObservation(id, glucose)
    }

    //IRIS
    @GetMapping("/con/")
    fun getconn(): String {
        irisService.getConnection()
        return "ok"
    }

    @GetMapping("/creat/")
    fun createTablePat(): String {
        irisService.createPatientTable()
        return "patient created"
    }

    @GetMapping("/creatnot/")
    fun createTablePatNot(): String {
        irisService.createPatienNotestTable()
        return "notes table created"
    }

    @GetMapping("/insulintable/")
    fun createTablePatInsulin(): String {
        irisService.createInsulinTable()
        return "insulin table created"
    }

    @PostMapping("/po")
    fun insertPatient(@RequestBody patient: Patient): String {
        irisService.insertPatient(patient)
        return "created"
    }

    @PostMapping("/note")
    fun createPatientNote(@RequestBody note: PatientNoteCreateRequest): String {
        irisService.insertPatientNote(note)
        return "created"
    }

    @PostMapping("/insulin/record")
    fun createInsulinRecord(@RequestBody record: InsulinCreateRequest): String {
        irisService.insertInsulin(record)
        return "created"
    }

    @GetMapping("/irispat/{id}")
    fun getIrisPatientById(@PathVariable id: Int): ResponseEntity<Patient> {
        return ResponseEntity.ok(irisService.getPatientById(id))
    }

    @GetMapping("/irisnote/{id}")
    fun getIrisPatientNotesByPatientId(@PathVariable id: Int): ResponseEntity<List<PatientNote>> {
        return ResponseEntity.ok(irisService.getPatientNotesByPatientId(id))
    }

    @GetMapping("/all")
    fun getIrisPatients(): ResponseEntity<List<Patient>> {
        return ResponseEntity.ok(irisService.getAllPatients())
    }

    @GetMapping("/insulin1/{id}")
    fun getInsulinByPatientId(@PathVariable id: Int): ResponseEntity<List<InsulinRecord>> {
        return ResponseEntity.ok(irisService.getInsulinRecordsByPatientId(id))
    }

    @GetMapping("/insulin/{id}")
    fun ins(): Element {
        return Element(
            title = "Insulin",
            headerContent = "Prescriptions",
            create = "Item",
            contents = listOf(
                Content(
                    header = "Doctors Exam",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "stone",
                        space = "spaceBetween",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Long Insulin",
                            textContent = "UN"
                        ),
                        TextHeader(
                            textHeader = "Long Insulin",
                            textContent = "UN"
                        ),
                        TextHeader(
                            textHeader = "Long Insulin",
                            textContent = "UN"
                        )
                    )
                )
            )
        )
    }

    @GetMapping("/meds/{id}")
    fun meds(@PathVariable id: Int): Element {
        val prescriptions = hfirSimulationService.getPatientPrescriptions(id)
        return Element(
            title = "Meds",
            headerContent = "Prescriptions",
            create = "Item",
            contents = listOf(
                Content(
                    header = prescriptions[0].medicine ?: "",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "red",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Dosage:",
                            textContent = "${prescriptions[0].dosage} tablet"
                        ),
                        TextHeader(
                            textHeader = "Times a day:",
                            textContent = prescriptions[0].period.toString()
                        ),
                        TextHeader(
                            textHeader = "Prescribed by:",
                            textContent = prescriptions[0].doctorName ?: ""
                        ),
                        TextHeader(
                            textHeader = "Date of prescription",
                            textContent = prescriptions[0].time?.take(10) ?: ""
                        )
                    )
                ),
                Content(
                    header = prescriptions[1].medicine ?: "",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "red",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Dosage:",
                            textContent = "${prescriptions[1].dosage} tablet"
                        ),
                        TextHeader(
                            textHeader = "Times a day:",
                            textContent = prescriptions[1].period.toString()
                        ),
                        TextHeader(
                            textHeader = "Prescribed by:",
                            textContent = prescriptions[1].doctorName ?: ""
                        ),
                        TextHeader(
                            textHeader = "Date of prescription",
                            textContent = prescriptions[1].time?.take(10) ?: ""
                        )
                    )
                )
            )
        )
    }

    @GetMapping("/encounters/{id}")
    fun encounters(@PathVariable id: Int): Element {
        val encounters = hfirSimulationService.patientEncounters(id)
        val labTests = hfirSimulationService.getPatientLabTests(id)
        val appointment = hfirSimulationService.getPatientAppointments(id)
        return Element(
            title = "Doctors",
            headerContent = "Consultations",
            create = "Item",
            contents = listOf(
                Content(
                    header = "Your next doctor appointment",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "orange",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "",
                            textContent = appointment?.start.toString()
                        ),
                        TextHeader(
                            textHeader = appointment?.hospitalName ?:"",
                            textContent = ""
                        )
                    ),

                    ),
                Content(
                    header = "Eye exam",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "orange",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = encounters[0].hospitalName ?: "",
                            textContent = ""
                        ),
                        TextHeader(
                            textHeader = encounters[0].start?.take(10) ?:"",
                            textContent = ""
                        )
                    ),
                ),
                Content(
                    header = "Influenza vaccine",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "red",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Recomendation 1 per year",
                            textContent = "0/1"
                        )
                    ),
                ),
                Content(
                    header = "Triglyceride",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "stone",
                        space = "spaceBetween",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Recomendation 1 per year",
                            textContent = "1/1"
                        ),
                        TextHeader(
                            textHeader = "Date of last test",
                            textContent = labTests[0].time.take(10) ?: ""
                        ),
                        TextHeader(
                            textHeader = "Result",
                            textContent = "${labTests[0].result} mg/dL"
                        )
                    ),
                ),
                Content(
                    header = "Total Cholesterol",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "stone",
                        space = "spaceBetween",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Recomendation 1 per year",
                            textContent = "0/1"
                        ),
                        TextHeader(
                            textHeader = "Date of last test",
                            textContent = labTests[1].time.take(10) ?: ""
                        ),
                        TextHeader(
                            textHeader = "Result",
                            textContent = "${labTests[1].result} mg/dL"
                        )
                    ),
                ),
                Content(
                    header = "LDL Cholesterol",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "stone",
                        space = "spaceBetween",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Recomendation 1 per year",
                            textContent = "0/1"
                        ),
                        TextHeader(
                            textHeader = "Date of last test",
                            textContent = labTests[2].time.take(10) ?: ""
                        ),
                        TextHeader(
                            textHeader = "Result",
                            textContent = "${labTests[2].result} mg/dL"
                        )
                    ),
                ),
                Content(
                    header = "HDL Cholesterol",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "red",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Recomendation 1 per year",
                            textContent = "0/1"
                        )
                    ),
                )
            )
        )
    }

    @GetMapping("/devices/{id}")
    fun devices(): Element {
        return Element(
            title = "Devices",
            headerContent = "",
            create = "Item",
            contents = listOf(
                Content(
                    header = "DEXCOM G6",
                    icon = null,
                    btn = true,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "green",
                        space = "spaceBetween",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Remaining days:",
                            textContent = "12"
                        ),
                        TextHeader(
                            textHeader = "Installed on a date:",
                            textContent = "2/12/2022"
                        )
                    )
                ),
                Content(
                    header = "Smart insulin pen",
                    icon = null,
                    btn = true,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "green",
                        space = "spaceBetween",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Remaining days:",
                            textContent = "12"
                        ),
                        TextHeader(
                            textHeader = "Installed on a date:",
                            textContent = "2/12/2022"
                        )
                    )
                )
            )
        )
    }

    @GetMapping("/tests/{id}")
    fun getTestsByPatientIdContent(@PathVariable id: Int): Element {
        // val tests = hfireService.getLaboratoryTestByPatientId(id, "/DiagnosticReport?patient=")
        return Element(
            title = "Doctors",
            headerContent = "Prescriptions",
            create = "Item",
            contents = listOf(
                Content(
                    header = "Doctors Exam",
                    icon = null,
                    btn = true,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "stone",
                        space = "spaceBetween",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Long Insulin",
                            textContent = "UN"
                        ),
                        TextHeader(
                            textHeader = "Long Insulin",
                            textContent = "UN"
                        ),
                        TextHeader(
                            textHeader = "Long Insulin",
                            textContent = "UN"
                        )
                    )
                )
            )
        )
    }

    @GetMapping("/today/{id}")
    fun dashBoardForPatient(@PathVariable id: Int): Element {
        val glucose = hfirSimulationService.getLastTenGlucoseObservations(id)
        val appointment = hfirSimulationService.getPatientAppointments(id)
        val lastPrescription = hfirSimulationService.getPatientPrescriptions(id).takeLast(1)[0]
        return Element(
            title = "Today",
            headerContent = "",
            create = "Item",
            contents = listOf(
                Content(
                    header = "Your last glucose check up",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "stone",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "        " + glucose.takeLast(1)[0].value.toString(),
                            textContent = "mmol/l"
                        )
                    )
                ),
                Content(
                    header = "Your next doctor visit:",
                    icon = null,
                    btn = false,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "stone",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = appointment?.start.toString(),
                            textContent = "10:45"
                        ),
                        TextHeader(
                            textHeader = "Massachussets",
                            textContent = appointment?.hospitalName ?: ""
                        )
                    )
                ),
                Content(
                    header = "Don't forget to take your medications",
                    icon = null,
                    btn = true,
                    border = true,
                    mark = Mark(
                        header = "red",
                        text = "green",
                        space = "spaceBetween",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "",
                            textContent = lastPrescription.medicine ?: ""
                        ),
                        TextHeader(
                            textHeader = "Mark as done",
                            textContent = "Done"
                        )
                    )
                )
            )
        )
    }

    @GetMapping("/reports/{id}")
    fun reportsForPatient(@PathVariable id: Int): Element {
        val glucoses = hfirSimulationService.getLastTenGlucoseObservations(id)
//        var insulins = listOf<InsulinRecord>()
//        try {
//            insulins = irisService.getInsulinRecordsByPatientId(id)
//        } catch (e: Exception) {
//            println(e)
//        }
        val glucoseGraph = mutableListOf<GraphNode>()
        val insulinGraph = mutableListOf<GraphNode>()
        for (i in 0..8) {
            glucoseGraph.add(
                GraphNode(
                pointName = glucoses[i].time.toString(),
                before = glucoses[i].value,
                after = glucoses[i].value - 0.9
            ))
        }
//        insulins.forEach {
//            insulinGraph.add(GraphNode(it.created.toString().take(10),it.insulinDose?.toDouble() ?: 0.0, 0.0))
//        }
        return Element(
            title = "Reports",
            headerContent = "Glucose",
            create = "Chart",
            graph = listOf(glucoseGraph),
            contents = listOf(
                Content(
                    header = "",
                    "",
                    mark = Mark(),
                    text = listOf()
                )
            )
        )
    }

    @GetMapping("/patient/{id}")
    fun reportsByPatientForDoctor(@PathVariable id: Int): Element {
        val glucoses = hfirSimulationService.getLastTenGlucoseObservations(id)
//        var insulins = listOf<InsulinRecord>()
//        try {
//            insulins = irisService.getInsulinRecordsByPatientId(id)
//        } catch (e: Exception) {
//            println(e)
//        }

        val glucoseGraph = mutableListOf<GraphNode>()
        val insulinGraph = mutableListOf<GraphNode>()
        for (i in 0..8) {
            glucoseGraph.add(
                GraphNode(
                    pointName = glucoses[i].time.toString().take(10),
                    before = glucoses[i].value,
                    after = glucoses[i].value-0.9
                ))
        }
//        insulins.forEach {
//            insulinGraph.add(GraphNode(it.created.toString().take(10),it.insulinDose?.toDouble() ?: 0.0, 0.0))
//        }
        return Element(
            title = "Patient$id",
            headerContent = "Glucose",
            create = "Chart",
            graph = listOf(glucoseGraph),
            contents = listOf(
                Content(
                    header = "",
                    "",
                    mark = Mark(),
                    text = listOf()
                )
            )
        )
    }

    @GetMapping("/babyJournal/{id}")
    fun babyJournal(@PathVariable id: Int): Element {
        return Element(
            title = "Baby journal",
            headerContent = "Prescriptions",
            create = "Baby journal",
            contents = listOf(
                Content(
                    header = "Doctors Exam",
                    icon = null,
                    btn = true,
                    border = true,
                    mark = Mark(
                        header = "green",
                        text = "stone",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "Long Insulin",
                            textContent = "UN"
                        ),
                        TextHeader(
                            textHeader = "Long Insulin",
                            textContent = "UN"
                        ),
                        TextHeader(
                            textHeader = "Long Insulin",
                            textContent = "UN"
                        )
                    )
                )
            )
        )
    }

    data class GraphNode(
        val pointName: String,
        val before: Double,
        val after: Double,
    )


    data class Element(
        val title: String,
        val headerContent: String,
        val create: String,
        val contents: List<Content>,
        val graph: List<List<GraphNode>> = listOf()

    )

    data class Content(
        val header: String,
        val icon: String?,
        val btn: Boolean = false,
        val border: Boolean = true,
        val mark: Mark,
        val text: List<TextHeader>
    )

    data class Mark(
        val header: String = "green",
        val text: String = "stone",
        val space: String = "spaceBetween"
    )

    data class TextHeader(
        val textHeader: String,
        val textContent: String,
    )

}