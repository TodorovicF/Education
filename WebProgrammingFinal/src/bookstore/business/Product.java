package bookstore.business;

import java.text.NumberFormat;
import java.io.Serializable;

public class Product implements Serializable {

    private Long ISBN13;
    private Long  ISBN10;
    private String author;
    private String description;
    private String category;
    private int stock;
    private double price;

    public Product() {}

    public Long getISBN13() {
        return ISBN13;
    }

    public void setISBN13(Long isbn13) {
        this.ISBN13 = isbn13;
    }

    public Long getISBN10() {
        return ISBN10;
    }

    public void setISBN10(Long isbn10) {
        this.ISBN10 = isbn10;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }

    public String getImageURL() {
        String imageURL = "/images/" + ISBN13 + ".jpg";
        return imageURL;
    }

    public String getProductType() {
        return "Book";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
