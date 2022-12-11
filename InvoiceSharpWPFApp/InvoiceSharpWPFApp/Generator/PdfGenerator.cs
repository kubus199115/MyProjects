using InvoiceSharpWPFApp.Dto;
using PdfSharp.Drawing;
using PdfSharp.Drawing.Layout;
using PdfSharp.Pdf;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InvoiceSharpWPFApp.Generator
{
    class PdfGenerator
    {
        private PdfDocument document;
        private List<PdfPage> pages;
        private InvoiceDetailsDTO invoiceDetails;

        // constructor get incoice details to print
        public PdfGenerator(InvoiceDetailsDTO invoice)
        {
            invoiceDetails = invoice;
            document = new PdfDocument();
            pages = new List<PdfPage>();
            // adding first page
            pages.Add(addPage());
        }

        // generating invoice pdf
        public void genPdf()
        {
            PdfPage page = pages[0];
            XGraphics gfx = XGraphics.FromPdfPage(page);
            XFont font = setFont("Verdana", 16, XFontStyle.Bold);
            XPen xpen = new XPen(XColors.Black, 1);

            // Rect with invoice number, place and date of issue, date of sale
            XRect rect = setRect(10, 40, page.Width - 20, 0);
            string invoiceNumber = "Invoice Num: " + invoiceDetails.invoice.invoiceNumber;
            drawText(invoiceNumber, font, rect, XStringFormats.BaseLineLeft, gfx);
            string placeOfIssue = "Place of issue: " + invoiceDetails.invoice.placeOfIssue;
            font = setFont("Verdana", 12, XFontStyle.Regular);
            drawText(placeOfIssue, font, rect, XStringFormats.BaseLineRight, gfx);
            rect = setRect(10, 60, page.Width - 20, 0);
            string dateOfIssue = "Date of issue: " + invoiceDetails.invoice.dateOfIssue.ToString("yyyy-MM-dd");
            drawText(dateOfIssue, font, rect, XStringFormats.BaseLineRight, gfx);
            rect = setRect(10, 80, page.Width - 20, 0);
            string dateOfSale = "Date of sale: " + invoiceDetails.invoice.dateOfSale.ToString("yyyy-MM-dd");
            drawText(dateOfSale, font, rect, XStringFormats.BaseLineRight, gfx);

            // seperating line
            drawLine(10, 100, page.Width - 10, 100, gfx);

            // Rect with seller and customer
            string comName = Properties.Settings.Default["name"].ToString();
            string comAddress = Properties.Settings.Default["address"].ToString();
            string comTaxId = Properties.Settings.Default["taxId"].ToString();
            string comRegon = Properties.Settings.Default["regon"].ToString();
            string comPhone = Properties.Settings.Default["phone"].ToString();
            string comEmail = Properties.Settings.Default["email"].ToString();

            ContractorDTO contractor = invoiceDetails.contractor;

            string contractorInfo = "Name: " + contractor.name + "\n" + "Address:\n" + contractor.address + "\n" + "Tax Id: " + contractor.taxId 
                + "\n" + "REGON: " + contractor.regon + "\n" + "Phone: " + contractor.phone + "\n" + "E-mail: " + contractor.email;

            string myCompanyInfo = "Name: " + comName + "\n" + "Address:\n" + comAddress + "\n" + "Tax Id: " + comTaxId
                + "\n" + "REGON: " + comRegon + "\n" + "Phone: " + comPhone + "\n" + "E-mail: " + comEmail;

            XRect rectLeft = setRect(10, 120, page.Width / 2, 160);
            XRect rectRight = setRect(page.Width / 2, 120, page.Width / 2, 160);

            // if outgoing invoice, else incoming
            if(invoiceDetails.invoice.type.Equals("Outgoing"))
            {
                drawTextInRect("Seller:\n" + myCompanyInfo, font, rectLeft, XStringFormats.TopLeft, gfx);
                drawTextInRect("Consumer:\n" + contractorInfo, font, rectRight, XStringFormats.TopLeft, gfx);
            }
            else
            {
                drawTextInRect("Seller:\n" + contractorInfo, font, rectLeft, XStringFormats.TopLeft, gfx);
                drawTextInRect("Consumer:\n" + myCompanyInfo, font, rectRight, XStringFormats.TopLeft, gfx);
            }

            // rect with items
            XBrush xBrush = new XSolidBrush(XColor.FromGrayScale(0.7));
            double rowHeight = 25;
            font = setFont("Verdana", 10, XFontStyle.Bold);
            //"Name","PKWiU","Qty.","Unit","Net Price","Net Val.","Tax Rate","Tax Val.", "Gross Val.");
            XRect nameRect = setRect(5, 300, 100, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, nameRect);
            drawTextInRect("Name", font, nameRect, XStringFormats.TopLeft, gfx, XParagraphAlignment.Center);
            XRect pkwiuRect = setRect(105, 300, 60, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, pkwiuRect);
            drawTextInRect("PKWiU", font, pkwiuRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            XRect qtyRect = setRect(165, 300, 60, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, qtyRect);
            drawTextInRect("Qty.", font, qtyRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            XRect unitRect = setRect(225, 300, 60, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, unitRect);
            drawTextInRect("Unit", font, unitRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            XRect netPrRect = setRect(285, 300, 60, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, netPrRect);
            drawTextInRect("Net Price", font, netPrRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            XRect netValRect = setRect(345, 300, 60, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, netValRect);
            drawTextInRect("Net Value", font, netValRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            XRect taxRateRect = setRect(405, 300, 40, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, taxRateRect);
            drawTextInRect("Tax Rate", font, taxRateRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            XRect taxValRect = setRect(445, 300, 60, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, taxValRect);
            drawTextInRect("Tax Value", font, taxValRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            XRect grossRect = setRect(505, 300, 70, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, grossRect);
            drawTextInRect("Gross Value", font, grossRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);

            double nl = 300 + rowHeight;
            font = setFont("Verdana", 10, XFontStyle.Regular);
            foreach (ItemDTO item in invoiceDetails.items)
            {
                nameRect = setRect(5, nl, 100, rowHeight);
                gfx.DrawRectangle(xpen, nameRect);
                drawTextInRect(item.name, font, nameRect, XStringFormats.TopLeft, gfx, XParagraphAlignment.Center);
                pkwiuRect = setRect(105, nl, 60, rowHeight);
                gfx.DrawRectangle(xpen, pkwiuRect);
                drawTextInRect(item.pkwiu, font, pkwiuRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
                qtyRect = setRect(165, nl, 60, rowHeight);
                gfx.DrawRectangle(xpen, qtyRect);
                drawTextInRect(item.quantity.ToString(), font, qtyRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
                unitRect = setRect(225, nl, 60, rowHeight);
                gfx.DrawRectangle(xpen, unitRect);
                drawTextInRect(item.unit, font, unitRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
                netPrRect = setRect(285, nl, 60, rowHeight);
                gfx.DrawRectangle(xpen, netPrRect);
                drawTextInRect(item.netPrice.ToString("0.00"), font, netPrRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
                netValRect = setRect(345, nl, 60, rowHeight);
                gfx.DrawRectangle(xpen, netValRect);
                drawTextInRect(item.netValue.ToString("0.00"), font, netValRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
                taxRateRect = setRect(405, nl, 40, rowHeight);
                gfx.DrawRectangle(xpen, taxRateRect);
                drawTextInRect(String.Format("{0:P0}", item.taxRate), font, taxRateRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
                taxValRect = setRect(445, nl, 60, rowHeight);
                gfx.DrawRectangle(xpen, taxValRect);
                drawTextInRect(item.taxValue.ToString("0.00"), font, taxValRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
                grossRect = setRect(505, nl, 70, rowHeight);
                gfx.DrawRectangle(xpen, grossRect);
                drawTextInRect(item.grossValue.ToString("0.00"), font, grossRect, XStringFormats.Center, gfx, XParagraphAlignment.Center);

                nl += rowHeight;

            }

            decimal netValue23 = 0, taxValue23 = 0, grossValue23 = 0;
            decimal netValue8 = 0, taxValue8 = 0, grossValue8 = 0;
            decimal netValue5 = 0, taxValue5 = 0, grossValue5 = 0;
            decimal netValue0 = 0, grossValue0 = 0;
            decimal totalNetValue = 0;
            decimal totalTaxValue = 0;
            decimal totalGrossValue = 0;
            foreach (ItemDTO item in invoiceDetails.items)
            { 
                if(item.taxRate == 0.23m)
                {
                    netValue23 += item.netValue;
                    totalNetValue += item.netValue;
                    taxValue23 += item.taxValue;
                    totalTaxValue += item.taxValue;
                    grossValue23 += item.grossValue;
                    totalGrossValue += item.grossValue;
                }
                else if (item.taxRate == 0.08m)
                {
                    netValue8 += item.netValue;
                    totalNetValue += item.netValue;
                    taxValue8 += item.taxValue;
                    totalTaxValue += item.taxValue;
                    grossValue8 += item.grossValue;
                    totalGrossValue += item.grossValue;
                }
                else if (item.taxRate == 0.05m)
                {
                    netValue5 += item.netValue;
                    totalNetValue += item.netValue;
                    taxValue5 += item.taxValue;
                    totalTaxValue += item.taxValue;
                    grossValue5 += item.grossValue;
                    totalGrossValue += item.grossValue;
                }
                else if (item.taxRate == 0)
                {
                    netValue0 += item.netValue;
                    totalNetValue += item.netValue;
                    grossValue0 += item.grossValue;
                    totalGrossValue += item.grossValue;
                }
            }

            nl += 5;
            rect = setRect(345, nl, 60, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(netValue23.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(405, nl, 40, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect("23%", font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(445, nl, 60, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(taxValue23.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(505, nl, 70, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(grossValue23.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            nl += rowHeight;
            rect = setRect(345, nl, 60, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(netValue8.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(405, nl, 40, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect("8%", font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(445, nl, 60, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(taxValue8.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(505, nl, 70, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(grossValue8.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            nl += rowHeight;
            rect = setRect(345, nl, 60, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(netValue5.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(405, nl, 40, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect("5%", font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(445, nl, 60, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(taxValue5.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(505, nl, 70, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(grossValue5.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            nl += rowHeight;
            rect = setRect(345, nl, 60, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(netValue0.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(405, nl, 40, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect("0%", font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(505, nl, 70, rowHeight);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(grossValue0.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            nl += rowHeight;
            rect = setRect(345, nl, 60, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, rect);
            drawTextInRect(totalNetValue.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(445, nl, 60, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, rect);
            drawTextInRect(totalTaxValue.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);
            rect = setRect(505, nl, 70, rowHeight);
            gfx.DrawRectangle(xpen, xBrush, rect);
            drawTextInRect(totalGrossValue.ToString("0.00"), font, rect, XStringFormats.Center, gfx, XParagraphAlignment.Center);

            nl -= 75;
            rect = setRect(5, nl, 340, rowHeight);
            font = setFont("Verdana", 12, XFontStyle.Regular);
            string methodOfPay = "Method of payment: " + invoiceDetails.invoice.methodOfPayment;
            drawTextInRect(methodOfPay, font, rect, XStringFormats.TopLeft, gfx);
            nl += 25;
            rect = setRect(5, nl, 340, rowHeight);
            string dayOfPay = "Date of payment: " + invoiceDetails.invoice.dateOfPayment + " day/s";
            drawTextInRect(dayOfPay, font, rect, XStringFormats.TopLeft, gfx);
            nl += 25;
            rect = setRect(5, nl, 340, rowHeight);
            string account = "Account number: " + invoiceDetails.invoice.accountNumber;
            drawTextInRect(account, font, rect, XStringFormats.TopLeft, gfx);
            nl += 30;
            rect = setRect(5, nl, 340, rowHeight);
            font = setFont("Verdana", 14, XFontStyle.Bold);
            string total = "Total value: " + totalGrossValue.ToString("0.00");
            drawTextInRect(total, font, rect, XStringFormats.TopLeft, gfx);
            nl += 40;
            rect = setRect(5, nl, 200, 25);
            font = setFont("Verdana", 12, XFontStyle.Bold);
            drawTextInRect("Remarks", font, rect, XStringFormats.TopLeft, gfx);
            nl += 20;
            rect = setRect(5, nl, page.Width / 2, rowHeight * 3);
            font = setFont("Verdana", 12, XFontStyle.Regular);
            gfx.DrawRectangle(xpen, rect);
            drawTextInRect(invoiceDetails.invoice.remarks, font, rect, XStringFormats.TopLeft, gfx);


            saveFile("invoice.pdf");

            Process.Start("invoice.pdf");
        }

        // adding new page
        private PdfPage addPage()
        {
            PdfPage page = this.document.AddPage();
            return page;
        }
        
        // setting font
        private XFont setFont(string fontName, int size, XFontStyle style)
        {
            XFont font = new XFont(fontName, size, style);
            return font;
        }

        // setting rect to draw a string
        private XRect setRect(double x, double y, double width, double height)
        {
            return new XRect(x, y, width, height);
        } 

        // drawing string in rectangle
        private void drawTextInRect(string text, XFont font, XRect pos, XStringFormat format, XGraphics gfx,
            XParagraphAlignment xpa = XParagraphAlignment.Left)
        {
            if (text == null) text = "-";
            XTextFormatter xtf = new XTextFormatter(gfx);
            xtf.Alignment = xpa;
            format.LineAlignment = XLineAlignment.Near;
            format.Alignment = XStringAlignment.Near;
            xtf.DrawString(text, font, XBrushes.Black, pos, format);
        }

        // drawing text
        private void drawText(string text, XFont font, XRect pos, XStringFormat format, XGraphics gfx)
        {
            gfx.DrawString(text, font, XBrushes.Black, pos, format);
        }

        // drawing (standard black) line
        private void drawLine(double x1, double y1, double x2, double y2, XGraphics gfx)
        {
            XPen xpen = new XPen(XColors.Black, 2);
            gfx.DrawLine(xpen, x1, y1, x2, y2);
        }

        // saving file
        private void saveFile(string filename)
        {
            this.document.Save(filename);
        }
    }
}
