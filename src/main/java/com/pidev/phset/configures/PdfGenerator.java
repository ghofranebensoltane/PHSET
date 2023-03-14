package com.pidev.phset.configures;

import java.io.IOException;
import java.io.OutputStream;

import com.google.zxing.WriterException;
import com.pidev.phset.entities.Event;
import com.pidev.phset.entities.Reservation;
import com.pidev.phset.repositories.EventRepository;
import com.pidev.phset.repositories.ReservationRepository;
import com.pidev.phset.services.IEventServices;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import com.pidev.phset.configures.QRCode; // importer la classe QRCode

@RequiredArgsConstructor
public class PdfGenerator {



}