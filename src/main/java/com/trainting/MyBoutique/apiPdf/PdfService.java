package com.trainting.MyBoutique.apiPdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.VerticalPositionMark;
import com.trainting.MyBoutique.dto.*;
import com.trainting.MyBoutique.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PdfService {
    @Autowired
    CartService cartService;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductService productService;



    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.white);
        cell.setPadding(3);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.black);
        Font fontTable = FontFactory.getFont(FontFactory.COURIER);
        fontTable.setColor(Color.blue);
        fontTable.setSize(10);

        cell.setPhrase(new Phrase("Prix commande ", fontTable));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Date commande ", fontTable));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Detail de la commande", fontTable));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table,Long orderId) {
        OrderDto orderDto=this.getOrderDetails(orderId);
        table.addCell(String.valueOf(orderDto.getTotalPrice())+"dt");
        String date =new SimpleDateFormat("yyyy-MM-dd").format(orderDto.getShipped());
        table.addCell(date);
        Font fontTable = FontFactory.getFont(FontFactory.COURIER);
        fontTable.setColor(Color.blue);
        fontTable.setSize(10);
        PdfPTable productTable = new PdfPTable(3);
        PdfPCell cell = new PdfPCell();
        cell.setPadding(3);
        cell.setPhrase(new Phrase("Nom Produit ",fontTable));
        productTable.addCell(cell);
        cell.setPhrase(new Phrase("Prix Unité ",fontTable));
        productTable.addCell(cell);
        cell.setPhrase(new Phrase("Quantité commandée ",fontTable));
        productTable.addCell(cell);
        List<OrderItemDto> list=this.getOrderItems(orderId);
        for (OrderItemDto orderItemDto: list) {
            productTable.addCell(String.valueOf( this.productService.findById(orderItemDto.getProductId()).getName()));
            productTable.addCell(( this.productService.findById(orderItemDto.getProductId()).getPrice())+"Dt");
            productTable.addCell(String.valueOf(orderItemDto.getQuantity()));

        }
        table.addCell(productTable);
    }

    public void export(HttpServletResponse response,Long orderId) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER_OBLIQUE);
        font.setSize(8);
        font.setColor(Color.black);

        Font fontTitle = FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE);
        fontTitle.setSize(12);
        fontTitle.setColor(Color.black);

        java.awt.Image awtImage = Toolkit.getDefaultToolkit().createImage("C:\\Users\\mohamed.skiken\\Desktop\\exemple\\MyBoutique_2.0\\src\\main\\resources\\templates\\logo.png");
        Image image = Image.getInstance(awtImage, null);
        image.scaleToFit(60, 60);
        image.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph p = new Paragraph("Facture Commande n° "+orderId,fontTitle);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        Chunk glue = new Chunk(new VerticalPositionMark());
        Date today = Calendar.getInstance().getTime();
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(today);
        Paragraph p2 = new Paragraph("Num-Tel: 71123456"+"\n"+"Email: azizmarketplace@gmail.com"+"\n"+"Adresse: 120 avenue d'afrique menzah 5", font);
        p2.add(new Chunk(glue));
        p2.add(todayAsString);
        document.add(p2);
        document.add(image);
        document.add(new Paragraph("\n"));
        document.add(p);
        document.add(new Paragraph("\n"+"\n"));
        CustomerDto customerDto=this.getCustomerInformation(orderId);
        Font fontCustomer = FontFactory.getFont(FontFactory.COURIER_OBLIQUE);
        font.setSize(11);
        Paragraph customerInfo = new Paragraph("Client: "+customerDto.getLastName()+" "+customerDto.getFirstName()+"\n"+"Username: "+customerDto.getUsername()+"\n"+"Telephone: "+customerDto.getTelephone()+"\n"+"Adresse de facturation: "+customerDto.getAddress().getAddress()+" "+customerDto.getAddress().getPostcode()+" "+customerDto.getAddress().getCity()+" "+customerDto.getAddress().getCountry()+" ",fontCustomer);
        document.add(customerInfo);
        document.add(new Paragraph("\n"+"\n"));
        Paragraph p3 = new Paragraph("Detail Commande:");
        document.add(p3);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2f, 2f, 6.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table,orderId);
        document.add(table);
        document.add(new Paragraph("\n"));
        BigDecimal commandPrice=this.orderService.findOrderById(orderId).getTotalPrice();
        Paragraph TotalPrice = new Paragraph("Prix total à payer : "+"\n"+commandPrice+"Dt + "+7+"Dt(frais de livraison)= "+commandPrice+7+"Dt",font);
        document.add(TotalPrice);
        document.close();

    }

    public CustomerDto getCustomerInformation(Long idOrder){
        CartDto cartDto=this.cartService.findByOrderId(idOrder);
        return cartDto.getCustomerDto();

    }

    public OrderDto getOrderDetails(Long idOrder){
      return this.orderService.findOrderById(idOrder);
    }

    public List<OrderItemDto> getOrderItems(Long idOrder){
        return this.orderItemService.findByIdOrder(idOrder);

    }




}
