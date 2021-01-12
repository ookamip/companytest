import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class CompanyTest {
    lateinit var companyToTest: Company

    private val ceo = EmployeeModel(
        "Ceo",
        "CeoSurname",
        23,
        Address("testStreet", "testCity", "testCountry")
    )


    @Before
    fun before() {
        companyToTest = Company(
            mutableSetOf(
                ceo
            )
        )
    }

    @Test
    fun shouldFindCeo() {
        // when
        val ceoFromFind = companyToTest.findEmployee(name = "Ceo")

        // then
        assertEquals(1, ceoFromFind.size)
        assertEquals(ceo, ceoFromFind.first())
    }

    @Test
    fun shouldAddManager() {
        // given
        val managerNameAndSurname = "Manager"
        val manager =
            EmployeeModel(managerNameAndSurname, managerNameAndSurname, 2, Address("23", "23", "23"), ceo).also {
                companyToTest.addOrChangeEmployeeRecord(it)
            }

        // when
        val managerSearchResult = companyToTest.findEmployee(managerNameAndSurname, managerNameAndSurname)

        // then
        assertEquals(1, managerSearchResult.size)
        assertEquals(manager, managerSearchResult.first())
    }

    @Test
    fun shouldFindMultipleWorkersWithSameName() {
        // given
        val sameName = "Test"
        for (i in 0..5) {
            companyToTest.addOrChangeEmployeeRecord(EmployeeModel(sameName, "Sur$i", 23, Address("A", "a", "a")))
        }
        companyToTest.addOrChangeEmployeeRecord(EmployeeModel("differentName", "Name", 23, Address("A", "a", "a")))

        // when
        val searchResult = companyToTest.findEmployee(name = sameName)

        // then
        assertEquals(6, searchResult.size)
    }

    @Test
    fun shouldGetAllReports() {
        // given
        companyToTest.addReport(ceo, Report("Test"))

        // when
        val reports = companyToTest.getAllReports()

        // then
        assertEquals(1, reports.size)
    }

    @Test
    fun shouldReturnNullIfThereAreNoReportsForEmployee() {
        // given
        companyToTest.addReport(ceo, Report("Test"))
        companyToTest.addReport(ceo, Report("Test2"))
        val employee2 = EmployeeModel("Test", "Test", 23, Address("A", "a", "a")).also {
            companyToTest.addOrChangeEmployeeRecord(it)
        }

        // when
        val reports = companyToTest.getAllReportsForEmployee(employee2)

        // then
        assertNull(reports)
    }

    @Test
    fun shouldReturnReportsListForEmployee() {
        // given
        companyToTest.addReport(ceo, Report("Test"))
        companyToTest.addReport(ceo, Report("Test2"))
        val employee2 = EmployeeModel("Test", "Test", 23, Address("A", "a", "a")).also {
            companyToTest.addOrChangeEmployeeRecord(it)
        }
        companyToTest.addReport(employee2, Report("ASDASDAS"))

        // when
        val reports = companyToTest.getAllReportsForEmployee(employee2)

        // then
        assertNotNull(reports)
    }

    @Test
    fun shouldThrowRuntimeExceptionIfAddingReportForNotExistingEmployee() {
        // given
        companyToTest.addReport(ceo, Report("Test"))
        companyToTest.addReport(ceo, Report("Test2"))

        // when
        val employee2 = EmployeeModel("Test", "Test", 23, Address("A", "a", "a"))

        // then
        assertFails {
            companyToTest.addReport(employee2, Report("ASDASDAS"))
        }
    }
}