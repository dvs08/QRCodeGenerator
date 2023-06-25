 package com.qr.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qr.pojo.QrPojo;

@Controller
@RequestMapping("/qr")
public class QrController {
	
	@ModelAttribute("qr")
	public QrPojo qrPojo() {
		
		return new QrPojo();
	}
	
	@GetMapping
	public String homePage() {
		
		return "index";
	}
	
	@PostMapping
	public String genrateQRCode(@ModelAttribute("qr") QrPojo qrPojo, Model model) {
		
		try {
			BufferedImage bufferedImage = genrateQRCodeImage(qrPojo);
			
			File output = new File("C:\\Users\\Dell\\Desktop\\SpringBootProjects\\QR Code Output"+qrPojo.getFirstName()+".jpg");
			
			ImageIO.write(bufferedImage, "jpg", output);
			
			model.addAttribute("qr", qrPojo);
			
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/qr?success";
	}

	public static BufferedImage genrateQRCodeImage(QrPojo qrPojo) throws WriterException {
		
		StringBuilder str = new StringBuilder();
		str = str.append("First Name:").append(qrPojo.getFirstName()).append("| |").append("Last Name:").append(qrPojo.getLastName())
				.append("| |").append("City:").append(qrPojo.getCity()).append("| |").append("State:").append(qrPojo.getState())
				.append("| |").append("Zip Code:").append(qrPojo.getZipCode());
		
		QRCodeWriter codeWriter = new QRCodeWriter();
		
		BitMatrix bitMatrix = codeWriter.encode(str.toString(), BarcodeFormat.QR_CODE, 200, 200);
		
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

}
