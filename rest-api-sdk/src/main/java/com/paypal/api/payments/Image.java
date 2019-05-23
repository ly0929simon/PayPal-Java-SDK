package com.paypal.api.payments;

import com.paypal.base.rest.PayPalModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Image extends PayPalModel {

	/**
	 * Image as base64 encoded String
	 */
	private String image;

	/**
	 * Default Constructor
	 */
	public Image() {
	}

	/**
	 * Saves the image to a file.
	 *
	 * @param fileName filename ending with .png
	 * @return boolean true if write successful. false, otherwise.
	 * @throws IOException
     */
	public boolean saveToFile(String fileName) throws IOException {
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(this.image);
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
		return ImageIO.write(img, "png", new File(fileName));
	}
}
