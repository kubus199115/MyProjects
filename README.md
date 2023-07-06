# python_physics
A few projects in python with physics simulations in pygame.

# invoiceJavaBackend
Invoice management backend application written in Spring boot and JPA
With invoiceSharpApp they are make application to manage invoices in small company. It is very simple program, but you can create incoming and outgoing invoices, create contractors and print invoices in PDF.

It requires jdk >=11

To run project, please go to folder /invoiceJavaBackend and type <code>mvnw spring-boot:run</code>
It uses in memory data base (H2)

<b>Api is available in localhost:8080</b>

# invoiceSharpWPFApp
Invoice management frontend application written in WPF (.NET)

To run project, please open solution in Visual Studio. <b>You need a backend app runing to use this application. This program is available in invoiceJavaBackend.</b>

Application use RestSharp to connect to API and PDFSharp to generate PDF. <b>Please don't update RestSharp, new version is completely different!</b>

# springWorkOrder (PL)
Work order managment application written in Java full stack. It uses Spring Framework and Thymeleaf. Project created in Netbeans IDE.
This is demo project created for my friend in 2017. It only shows how that application could be work and look for presentation purpose. Despite this, this app offers features like:
 - managing work orders (start, pause, end, add comments)
 - searching work orders
 - adding employers to work orders
 - checking status of work orders (info about time remaining, delay etc.)
 - import/export from/to excel

To run this app i recomend use docker. Docker files are in project folder in /Docker. It contains glasfish server and WAR.
In /Docker please type:

<code>docker build -t YOUR_NAME .</code>

<code>docker run -dp 8000:8080 YOUR_NAME</code>

In docker file it is script that creates a few users. For example admin with password admin.

</b>App is available in localhost:8000/</b>

<b> This app is in polish, so if you don't polish, probalbly you will not be able to use this program;)</b>

# kwir
Web page created for company "Kwalifikacje - Inteligentny Rozwój. Now, this page doesn't exist, but code is here. Used technology: HTML, CSS, Bootstrap, PHP, mysql. I created graphics in Inkscape and gimp. Page has admin panel for adding a news and editing one part of page.
