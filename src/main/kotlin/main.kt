import kotlin.random.Random

fun main(args: Array<String>) {
    val company = Company(
        EmployeeModel(
            "CEO1",
            "Kowoalski",
            61 ,
            Address("Street 1", "CityOfCity", "CountryOfCountry")
        ).let { ceo ->
            val employees = mutableSetOf<EmployeeModel>()
            employees.add(ceo)

            employees.add(EmployeeModel(
                "Manager1",
                "Kowoalski",
                Random.nextInt(20, 60),
                Address("Street 1", "CityOfCity", "CountryOfCountry"), ceo
            ).also {
                for (i in 0..5) {
                    employees.add(
                        EmployeeModel(
                            "Manager1Worker$i",
                            "Kowoalski",
                            Random.nextInt(20, 60),
                            Address("Street 1", "CityOfCity", "CountryOfCountry"), it
                        )
                    )
                }
                employees.add(
                    EmployeeModel(
                        "SameName",
                        "Popo",
                        Random.nextInt(20, 60),
                        Address("Street 1", "CityOfCity", "CountryOfCountry"), it
                    )
                )
            })

            employees.add(EmployeeModel(
                "Manager2",
                "Kowoalski",
                Random.nextInt(20, 60),
                Address("Street 1", "CityOfCity", "CountryOfCountry"), ceo
            ).also {
                for (i in 0..5) {
                    employees.add(
                        EmployeeModel(
                            "Manager2Worker$i",
                            "Kowoalski",
                            Random.nextInt(20, 60),
                            Address("Street 1", "CityOfCity", "CountryOfCountry"), it
                        )
                    )
                }
                employees.add(
                    EmployeeModel(
                        "SameName",
                        "Ikslawok",
                        Random.nextInt(20, 60),
                        Address("Street 1", "CityOfCity", "CountryOfCountry"), it
                    )
                )
            })
            return@let employees
        }
    )
    company.printCompanyStructure()
    println("Employees with name SameName")
    company.findEmployee(name = "SameName").forEach { println(it.toString()) }
    println("Employees with surname Ikslawok")
    company.findEmployee(surname = "Ikslawok").forEach { println(it.toString()) }
    println("Employees with name Manager2Worker1 surname Kowoalski")
    company.findEmployee(name = "Manager2Worker1", surname = "Kowoalski").forEach { println(it.toString()) }
    val ceo = company.findEmployee(name = "CEO1").takeIf { it.isNotEmpty() && it.size == 1 }?.let { it.first() }?.also {
        company.addOrChangeEmployeeRecord(it.apply { it.name = "Stefan" })
        company.addReport(it, Report("Test"))
    }
    company.findEmployee(surname = "Ikslawok").takeIf { it.isNotEmpty() && it.size == 1 }?.let { it.first() }?.also {
        company.addOrChangeEmployeeRecord(it.apply { boss = ceo })
    }
    println("Po zmianie CEO oraz szefa pracownika")
    company.printCompanyStructure()
}