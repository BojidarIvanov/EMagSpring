package com.emag.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.emag.db.ProductDAO;
import com.emag.model.ProductPojo;

@Controller
@MultipartConfig
public class UploadImageController {
	@Autowired
	ServletContext application;

	private static final String FILE_LOCATION = "C:\\EmagSpring\\images\\";
	private static String errorMsg = " ";

	@RequestMapping(value = "admin/uploadPicture", method = RequestMethod.POST)
	public String receiveUpload(@RequestParam("picture") MultipartFile multiPartFile,
			@RequestParam("id") Integer productId, HttpServletResponse response, Model model,
			HttpServletRequest request, HttpSession session) {
		try {
			@SuppressWarnings("unchecked")
			TreeMap<Integer, ProductPojo> products = (TreeMap<Integer, ProductPojo>) application
					.getAttribute("products");
			System.out.println(products);
			System.out.println("Product id: " + productId);
			ProductPojo product = products.get(productId);
			if (product.getImageURL() != null) {
				File productImage = new File(product.getImageURL());
				productImage.delete();
			}
			File fileOnDisk = new File(
					FILE_LOCATION + productId + "-product-pic." + multiPartFile.getContentType().split("/")[1]);
			multiPartFile.transferTo(fileOnDisk);
			try {
				ProductDAO.getInstance().setImageUrl(productId, fileOnDisk.getAbsolutePath());
				model.addAttribute("profilePic", fileOnDisk.getAbsolutePath());
				errorMsg = "Picture successfully uploaded!";
			} catch (SQLException e) {
				errorMsg = "something went wrong, please try again later";
			}
			// updating product object with new URI address
			product.setImageURL(fileOnDisk.getAbsolutePath());

		} catch (IOException e) {
			errorMsg = "Picture failed to upload!";
			e.printStackTrace();
		}
		model.addAttribute("errorMsg", errorMsg);
		errorMsg = null;
		return "/admin/edit";

	}

	@RequestMapping(value = "admin/getImage/{productID:.+}", method = RequestMethod.GET)
	@ResponseBody
	public void getImage(@PathVariable("productID") int id, HttpServletRequest req, HttpServletResponse res) {

		TreeMap<Integer, ProductPojo> products = (TreeMap<Integer, ProductPojo>) application.getAttribute("products");
		ProductPojo product = products.get(id);
		String url = product.getImageURL();
		File file;
		if (url != null) {
			file = new File(url);
		} else {
			file = new File("C:\\EmagSpring\\images\\emag.jpg");
		}
		System.out.println(url);
		try {
			Files.copy(file.toPath(), res.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/image/{fileName}", method = RequestMethod.GET)
	@ResponseBody
	public void prepareForUpload(@PathVariable("fileName") String fileName, HttpServletResponse resp, Model model)
			throws IOException {
		File file = new File(FILE_LOCATION + "emag.jpg");
		Files.copy(file.toPath(), resp.getOutputStream());
	}
}