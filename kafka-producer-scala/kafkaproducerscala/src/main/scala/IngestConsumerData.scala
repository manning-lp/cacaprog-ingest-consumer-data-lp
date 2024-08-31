import scala.io.Source
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.util.Random

case class EnergyUsage(LCLid: String, stdorToU: String, timestamp: LocalDateTime, energyUsage: Double)

def parseDate(dateStr: String): LocalDateTime = {
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS")
  LocalDateTime.parse(dateStr, formatter)
}

def parseEnergyUsage(value: String): Double = {
  if (value == "Null") 0.0 else value.toDouble
}

def readCSV(filePath: String): Seq[EnergyUsage] = {
  val source = Source.fromFile(filePath)
  val records = source.getLines().drop(1).map { line =>
    val cols = line.split(",").map(_.trim)
    EnergyUsage(
      cols(0), // LCLid
      cols(1), // stdorToU
      parseDate(cols(2)), // DateTime
      parseEnergyUsage(cols(3)) // KWH/hh (per half hour)
    )
  }.toSeq
  source.close()
  records
}

def sortData(data: Seq[EnergyUsage]): Seq[EnergyUsage] = {
  data.sortBy(record => (record.LCLid, record.timestamp))
}

def insertDelayedRecords(data: Seq[EnergyUsage], numDelayed: Int): Seq[EnergyUsage] = {
  val random = new Random()
  val delayedRecords = random.shuffle(data).take(numDelayed)
  delayedRecords.foldLeft(data) { (updatedData, record) =>
    val insertIndex = random.nextInt(updatedData.length)
    updatedData.patch(insertIndex, Seq(record), 0)
  }
}

def dropRecords(data: Seq[EnergyUsage], numDrop: Int): Seq[EnergyUsage] = {
  val random = new Random()
  val indicesToDrop = random.shuffle(data.indices).take(numDrop).toSet
  data.zipWithIndex.filterNot { case (_, index) => indicesToDrop.contains(index) }.map(_._1)
}

def duplicateRecords(data: Seq[EnergyUsage], numDuplicates: Int): Seq[EnergyUsage] = {
  val random = new Random()
  val recordsToDuplicate = random.shuffle(data).take(numDuplicates)
  data ++ recordsToDuplicate
}

def main(args: Array[String]): Unit = {
  val filePath = "/home/stream-project/data/LCL-June2015v2_0.csv"
  val data = readCSV(filePath)

  // Process data
  val sortedData = sortData(data)
  val dataWithDelays = insertDelayedRecords(sortedData, numDelayed = 5)
  val dataWithDrops = dropRecords(dataWithDelays, numDrop = 5)
  val finalDataWithDuplicates = duplicateRecords(dataWithDrops, numDuplicates = 5)

  // Print final data
  finalDataWithDuplicates.foreach(println)
}

