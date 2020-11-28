# PDF Book Preparator
Take a pdf and split it into several pieces to be ready for print. The number of pages per section can be adjusted.

Currently, the tool is command line-only. It is tested on Windows, but should work with every OS which supports JVM. Java is required.

## Usage
There a three commands available:

`[relative path to file]` - The path to the pdf file, relative to the jar-file.<br />
`[pdfpages per section]` - How many pages each resulting pdf (quire/section) should have (16 for 4-paper-stack)<br />
`[number of white pages in front]` - How many white pages to pad in front of the PDF (missing pages will be padded at the end). The size will in both cases be determined by the size of the last page.
```Batchfile
java -jar PDFBookPreparator-0.1.2.jar [relative path to file] [pdfpages per section] [number of white pages in front]
```
Example:
```Batchfile
java -jar PDFBookPreparator-0.1.2.jar "Book.pdf" 16 0
```

---

`help` - Prints help text
```Batchfile
java -jar PDFBookPreparator-0.1.2.jar help
```

---

`version` - Prints version of the software
```Batchfile
java -jar PDFBookPreparator-0.1.2.jar version
```


## Disclaimer
The software is provided "as is". The developer makes no warranties, express or implied.
