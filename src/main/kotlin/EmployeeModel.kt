import java.util.*

data class EmployeeModel(
    var name: String,
    var surname: String,
    var age: Int,
    var address: Address,
    var boss: EmployeeModel? = null,
    val id: String = UUID.randomUUID().toString()
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is EmployeeModel -> other.id == id
        else -> false
    }
}