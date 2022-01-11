# invoiceJavaBackend
Invoice management backend application written in Spring boot and JPA

It requires jdk >=11

To run project, please go to folder /invoiceJavaBackend and type <code>mvnw spring-boot:run</code>
It uses in memory data base.

<b>Api is available in localhost:8080</b>

# invoiceSharpWPFApp
Invoice management frontend application written in WPF (.NET)

To run project, please open solution in Visual Studio. <b>You need a backend app runing to use this application. This program is available in invoiceJavaBackend.</b>

Application use RestSharp to connect to API and PDFSharp to generate PDF. <b>Please don't update RestSharp, new version is completely different!</b>
