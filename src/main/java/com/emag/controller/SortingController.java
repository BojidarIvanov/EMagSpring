package com.emag.controller;

import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.ProductDAO;
import com.emag.model.CategoryPojo;
import com.emag.model.ProductPojo;

@Controller
public class SortingController {
	
	@Autowired
	ServletContext application;
	
	@RequestMapping(value = "/sortCategories", method = RequestMethod.GET)
	public String sortCategories(HttpServletRequest request, HttpServletResponse response) {
		String sort = request.getParameter("sort");
		Integer categoryId = Integer.parseInt(sort);
		Map<Integer, CategoryPojo> categories = (Map<Integer, CategoryPojo>) application.getAttribute("categories");
		System.out.println(categories);
		CategoryPojo category = categories.get(categoryId);
		request.setAttribute("specificCategory", category);
		System.out.println("Category" + category);
		return "categories";
	}
	
	@RequestMapping(value = "/sortProducts", method = RequestMethod.GET)
	public String sortProductsByAscendingOrder(HttpSession session, HttpServletRequest req, Model model) {
		//Get all products
		String sort = req.getParameter("sort");
		String catId = req.getParameter("cat");
		int categoryId = Integer.parseInt(catId);
		
		TreeSet<ProductPojo> products = new TreeSet<ProductPojo>(new Comparator<ProductPojo>() {

			@Override
			public int compare(ProductPojo o1, ProductPojo o2) {
				if(sort.equals("asc")) {
					return	o1.getPrice().compareTo(o2.getPrice());
				}else {
					return	o2.getPrice().compareTo(o1.getPrice());
				}
			}
		});
		TreeMap<Integer, ProductPojo> productsFromDB = null;
		try {
			//Store them in a treeset and order them by ascending order
			productsFromDB = ProductDAO.getInstance().getAllProducts();
			for(Entry<Integer, ProductPojo> p : productsFromDB.entrySet()) {
				if(categoryId == 0) {
				products.add(p.getValue());
				} else {
					if(p.getValue().getCategory().getCategoryID() == categoryId) {
						products.add(p.getValue());
					}
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Issue loading the prducts from the database " + e.getMessage() );
		}
		//Push them in the session
		Map<Integer, CategoryPojo> categories = (Map<Integer, CategoryPojo>) application.getAttribute("categories");
		CategoryPojo category = categories.get(categoryId);
		System.out.println("categoryId" + categoryId);
		model.addAttribute("products", products);
		model.addAttribute("categoryId",categoryId);
		if(categoryId>0) {
		model.addAttribute("specificCategory", category);
		}

		//Return to the main page 
		return "sortPrices";
	}
	
	@RequestMapping(value = "/gotToSortProducts", method = RequestMethod.GET)
	public String gotToSortProducts() {
		return "sortPrices";
	}

}
