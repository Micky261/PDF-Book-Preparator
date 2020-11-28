import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage

/**
 * @author Frank Nelles
 * @since 02.09.2020
 */
fun main(args: Array<String>) {
    if (args.size <= 1) {
        if (args.isEmpty() || args[0] == "help") {
            println(
                "\nParameters:\n" +
                        "(1) File path:\t\tRelative to the calling program\n" +
                        "(2) Pages per PDF:\tHow many pages each resulting pdf should have (16 for 4-paper-stack)\n" +
                        "(3) Padding front:\tHow many pages to pad in front of the PDF (missing pages will be padded at the end)\n" +
                        "\n" +
                        "\t\t\tmore to come...\n" +
                        "----------------------------------------------------------------\n" +
                        "help \t\t\tPrint this help\n" +
                        "version \t\tPrint version of this program\n"
            )
        } else if (args[0] == "version") {
            println("Version: 0.1.2")
        }
    } else {
        var filePath = ""
        var pagesPerPdf = 16
        var paddingFront = 0

        try {
            filePath = args[0]
            pagesPerPdf = args[1].toInt()
            paddingFront = args[2].toInt()
        } catch (e: Exception) {
            println("The program produced an exception and ended. The exception is:\n $e")
        }

        val padding = paddingFront
        val intermediatePageCount: Int
        val resultingPageCount: Int

        try {
            // Load file
            val inputFile = File(filePath)
            val inputPDF = PDDocument.load(inputFile)
            // Empty page in size of last page in inputPDF
            val emptyPage = PDPage(inputPDF.getPage(inputPDF.numberOfPages - 1).mediaBox)

            intermediatePageCount = inputPDF.numberOfPages + padding
            resultingPageCount = intermediatePageCount + (pagesPerPdf - (intermediatePageCount % pagesPerPdf))

            println("Page count with front padding: $intermediatePageCount")
            println("Page count with padding: $resultingPageCount")

            var paddingFrontLeft = paddingFront
            // Iterate through new pdf documents
            for (i in 0 until (resultingPageCount / pagesPerPdf)) {
                println("Doing document " + (i + 1))
                val newPdf = PDDocument()

                // Iterate through pages in a new pdf
                for (j in 0 until pagesPerPdf) {
                    if (paddingFrontLeft > 0) {
                        paddingFrontLeft--
                        newPdf.addPage(emptyPage)
                    } else {
                        // Pages are 1-based
                        val pageNo = i * pagesPerPdf + j - paddingFront

                        if (pageNo > inputPDF.numberOfPages - 1) {
                            newPdf.addPage(emptyPage)
                        } else {
                            newPdf.addPage(inputPDF.getPage(pageNo))
                        }
                    }
                }

                newPdf.save(inputFile.name.substringBefore(".pdf") + "__" + (i + 1) + ".pdf")
                newPdf.close()
            }

            inputPDF.close()
        } catch (e: Exception) {
            println("The program produced an exception and ended. The exception is:\n $e")
        }

        println("\nFinished.")
    }
}
