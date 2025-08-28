import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

void printLogs(String loggingFile, String serviceName) {
  String line = "====================================================================================================";

  try {
    def file = new File(loggingFile)
    boolean fileExists = file.exists()
    file.getParentFile().mkdirs()
    file.withWriterAppend(StandardCharsets.UTF_8.toString()) { pw ->
      if (!fileExists) {
        pw.writeLine("Service name: $serviceName")
        pw.writeLine(line)
      }
      pw.writeLine("Request URL: " + (prev?.getUrlAsString() ?: "N/A"))
      pw.writeLine("Request body: " + (prev?.getSamplerData()?.split("\\r?\\n\\r?\\n", 2)?.getAt(1) ?: "N/A"))
      pw.writeLine("HTTP code: " + (prev?.getResponseCode()  ?: "N/A"))
      pw.writeLine("Response body: " + (prev?.getResponseDataAsString() ?: "N/A"))
      pw.writeLine("TraceParent: " + vars.get("TRACE_PARENT"))
      pw.writeLine("Date: " + new SimpleDateFormat("d-MMM-yyyy HHmmss").format(new Date()))
      pw.writeLine("Response time: " + (prev?.getTime() ?: 0) + " ms")
      pw.writeLine("Latency: " + (prev?.getLatency() ?: 0) + " ms")
      pw.writeLine(line)
    }
  } catch (ex) {
    log.error("ERROR writing log file: " + ex.getMessage())
  }
}
