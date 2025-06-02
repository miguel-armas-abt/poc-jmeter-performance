import java.security.SecureRandom

String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2)
    bytes.each { b ->
        sb.append(String.format("%02x", b & 0xFF))
    }
    return sb.toString()
}

byte[] randomBytes(int length) {
    byte[] arr = new byte[length]
    new SecureRandom().nextBytes(arr)
    return arr
}

String generateTraceParent() {
    String version = "00"
    String traceId = bytesToHex(randomBytes(16))
    String parentId = bytesToHex(randomBytes(8))
    String traceFlags = "01"

    return "${version}-${traceId}-${parentId}-${traceFlags}"
}
