package com.emag.model;

public class ReviewPojo implements Comparable<ReviewPojo> {

	private int reviewID;
	private int reviewingRating;
	private String reviewText;
	private int reviewerID;
	private int productID;

	public ReviewPojo(int reviewingRating, String reviewText, int reviewer, int productID) {
		this.reviewingRating = reviewingRating;
		this.reviewText = reviewText;
		this.reviewerID = reviewer;
		this.productID = productID;
	}

	public ReviewPojo(int reviewID, int reviewingRating, String reviewText, int reviewer, int productID) {
		this(reviewingRating, reviewText, reviewer, productID);
		this.reviewID = reviewID;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getReviewID() {
		return reviewID;
	}

	public int getReviewingRating() {
		return reviewingRating;
	}

	public int getReviewerID() {
		return this.reviewerID;
	}

	@Override
	public String toString() {
		return "ReviewPojo [reviewID=" + reviewID + ", reviewingRating=" + reviewingRating + ", reviewText="
				+ reviewText + ", reviewer=" + reviewerID + ", productID=" + productID + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productID;
		result = prime * result + reviewID;
		result = prime * result + ((reviewText == null) ? 0 : reviewText.hashCode());
		result = prime * result + reviewerID;
		result = prime * result + reviewingRating;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewPojo other = (ReviewPojo) obj;
		if (productID != other.productID)
			return false;
		if (reviewID != other.reviewID)
			return false;
		if (reviewText == null) {
			if (other.reviewText != null)
				return false;
		} else if (!reviewText.equals(other.reviewText))
			return false;
		if (reviewerID != other.reviewerID)
			return false;
		if (reviewingRating != other.reviewingRating)
			return false;
		return true;
	}

	@Override
	public int compareTo(ReviewPojo o) {
		return o.reviewID - this.reviewID;
	}
}
