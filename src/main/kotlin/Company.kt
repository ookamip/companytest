class Company(
    private val employees: MutableSet<EmployeeModel> = mutableSetOf(),
    private val currentReports: MutableMap<EmployeeModel, MutableList<Report>> = mutableMapOf()
) {

    fun addOrChangeEmployeeRecord(employee: EmployeeModel) {
        employees.remove(employee)
        employees.add(employee)
    }

    fun findEmployee(name: String? = null, surname: String? = null): List<EmployeeModel> = employees.filter {
        (it.name == name ?: it.name) && (it.surname == surname ?: it.surname)
    }

    fun addReport(employee: EmployeeModel, report: Report) {
        if (!employees.contains(employee)) {
            throw RuntimeException("There is no such employee")
        }
        currentReports.putIfAbsent(employee, mutableListOf(report))?.add(report)
    }

    fun getAllReports() = currentReports.values.toList()

    fun getAllReportsForEmployee(employee: EmployeeModel): List<Report>? = currentReports[employee]?.toList()

    fun printCompanyStructure() {
        employees.forEach {
            println(it.toString())
            println("Ilość raportów pracownika ${currentReports[it]?.size ?: 0}")
        }
    }
}