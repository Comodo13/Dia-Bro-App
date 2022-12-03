package com.easychicken.diabroapp.controller

import com.easychicken.diabroapp.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.el.ELManager

@CrossOrigin
@RestController
@RequestMapping("/patient")
class PatientController(
    private val hfireService: HfireService,
    private val irisService: IRISservice
) {

//    @GetMapping("/{id}")
//    fun getPatientById(@PathVariable id: Int): ResponseEntity<Patient> {
//        return ResponseEntity.ok(hfireService.getPatient(id, "/Patient/"))
//    }
//    @GetMapping("/meds/{id}")
//    fun getMedsByPatientId(@PathVariable id: Int): List<Prescription> {
//        return hfireService.getPatientPrescriptions(id, "/MedicationRequest?patient=")
//    }
//    @GetMapping("/tests/{id}")
//    fun getTestsByPatientId(@PathVariable id: Int): List<LabTest> {
//        return hfireService.getLaboratoryTestByPatientId(id, "/DiagnosticReport?patient=")
//    }
//    @GetMapping("/encounters/{id}")
//    fun getAppointmentsByPatientId(@PathVariable id: Int):  List<Encounter>  {
//        return hfireService.getAppointmentsByPatientId(id, "/Encounter?patient=")
//    }
//
//    @GetMapping("/devices/{id}")
//    fun getDevicesByPatientId(@PathVariable id: Int):  List<Device>  {
//        return hfireService.getDevicesByPatientId(id)
//    }

    @GetMapping("/glucoses/{id}")
    fun getLastGlucoseByPatientId(@PathVariable id: Int):  List<GlucoseObservation>  {
        return hfireService.getLastTenGlucoseObservations(id)
    }

    @PostMapping("/recordglucose/{id}")
    fun recordLastGlucoseByPatientId(@PathVariable id: Int, @RequestBody glucose: GlucoseObservation):  GlucoseObservation  {
        return hfireService.recordGlucoseObservation(id, glucose)
    }

    //IRIS
    @GetMapping("/con/")
    fun getconn(): String  {
        irisService.getConnection()
        return  "ok"
    }

    @GetMapping("/creat/")
    fun createTablePat():  String  {
        irisService.createPatientTable()
        return  "patient created"
    }
    @GetMapping("/creatnot/")
    fun createTablePatNot():  String  {
        irisService.createPatienNotestTable()
        return  "notes table created"
    }
    @GetMapping("/insulintable/")
    fun createTablePatInsulin():  String  {
        irisService.createInsulinTable()
        return  "insulin table created"
    }
    @PostMapping("/po")
    fun insertPatient( @RequestBody patient: Patient): String  {
        irisService.insertPatient(patient)
        return  "created"
    }
    @PostMapping("/note")
    fun createPatientNote(@RequestBody note: PatientNoteCreateRequest): String  {
        irisService.insertPatientNote(note)
        return  "created"
    }
    @PostMapping("/insulin/record")
    fun createInsulinRecord(@RequestBody record: InsulinCreateRequest): String  {
        irisService.insertInsulin(record)
        return  "created"
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
    @GetMapping("/insulin/{id}")
    fun getInsulinByPatientId(@PathVariable id: Int): ResponseEntity<List<InsulinRecord>> {
        return ResponseEntity.ok(irisService.getInsulinRecordsByPatientId(id))
    }

    @GetMapping("/insulin1")
    fun ins(): Element {
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

    @GetMapping("/meds/{id}")
    fun meds(): Element{
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

    @GetMapping("/encounters/{id}")
    fun encounters(): Element{
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

    @GetMapping("/devices/{id}")
    fun devices(): Element {
        return Element(
            title = "Devices",
            headerContent = "",
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
    @GetMapping("/today/{id}")
    fun dashBoardForPatient(@PathVariable id: Int) : Element{
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
                        header = "orange",
                        text = "stone",
                        space = "spaceAround",
                    ),
                    text = listOf(
                        TextHeader(
                            textHeader = "5.5",
                            textContent = "mmol/l"
                        )
                    )
                )
            )
        )
    }
    @GetMapping("/reports/{id}")
    fun reportsForPatient(@PathVariable id: Int) : Element{
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
    @GetMapping("/babyJournal/{id}")
    fun babyJournal(@PathVariable id: Int) : Element{
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




//
//    @GetMapping("/meds/{id}")
//    fun getMedsByPatientId(@PathVariable id: Int): List<Prescription> {
//        return hfireService.getPatientPrescriptions(id, "/MedicationRequest?patient=")
//    }

//    @GetMapping("/encounters/{id}")
//    fun getAppointmentsByPatientId(@PathVariable id: Int):  List<Encounter>  {
//        return hfireService.getAppointmentsByPatientId(id, "/Encounter?patient=")
//    }

//    @GetMapping("/devices/{id}")
//    fun getDevicesByPatientId(@PathVariable id: Int):  List<Device>  {
//        return hfireService.getDevicesByPatientId(id)
//    }

    data class Element(
        val title: String,
        val headerContent: String,
        val create:String,
        val contents: List<Content>
        
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
        val header: String  = "green",
        val text: String = "stone",
        val space: String = "spaceBetween"
    )
    data class TextHeader(
        val textHeader: String,
        val textContent: String,
    )

}