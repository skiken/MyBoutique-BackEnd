package com.trainting.MyBoutique.apiPdf;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/api"+"/pdfApi")
public class PdfEndpoint {

    @Autowired
    private PdfService service;

    @GetMapping("/export/pdf/{orderId}")
    public void exportToPDF(HttpServletResponse response,@PathVariable Long orderId) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=commande" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        this.service.export(response,orderId);




    }
    }

