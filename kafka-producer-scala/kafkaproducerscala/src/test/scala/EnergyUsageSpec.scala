import org.scalatest.funsuite.AnyFunSuite
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EnergyUsageSpec extends AnyFunSuite {

  private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS")
  private def parseDate(dateStr: String): LocalDateTime = LocalDateTime.parse(dateStr, dateFormatter)

  test("Test duplicateRecords function") {
    val date = parseDate("2024-08-30 12:00:00.0000000")
    val records = Seq(
      EnergyUsage("1", "A", date, 10.0),
      EnergyUsage("2", "B", date, 20.0)
    )
    
    val duplicatedRecords = duplicateRecords(records, numDuplicates = 1)
    assert(duplicatedRecords.length == 3)
    assert(duplicatedRecords.count(_ == EnergyUsage("1", "A", date, 10.0)) == 2)
  }

  test("Test dropRecords function") {
    val date = parseDate("2024-08-30 12:00:00.0000000")
    val records = Seq(
      EnergyUsage("1", "A", date, 10.0),
      EnergyUsage("2", "B", date, 20.0),
      EnergyUsage("3", "C", date, 30.0)
    )
    
    val droppedRecords = dropRecords(records, numDrop = 1)
    assert(droppedRecords.length == 2)
  }

  test("Test insertDelayedRecords function") {
    val date = parseDate("2024-08-30 12:00:00.0000000")
    val records = Seq(
      EnergyUsage("1", "A", date, 10.0),
      EnergyUsage("2", "B", date, 20.0)
    )
    
    val delayedRecords = insertDelayedRecords(records, numDelayed = 1)
    assert(delayedRecords.length == 3)
  }

  test("Test sortData function") {
    val date1 = parseDate("2024-08-30 12:00:00.0000000")
    val date2 = parseDate("2024-08-30 13:00:00.0000000")
    val records = Seq(
      EnergyUsage("2", "B", date2, 20.0),
      EnergyUsage("1", "A", date1, 10.0)
    )
    
    val sortedRecords = sortData(records)
    assert(sortedRecords.head == EnergyUsage("1", "A", date1, 10.0))
  }
}

