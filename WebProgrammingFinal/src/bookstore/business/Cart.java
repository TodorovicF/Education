package bookstore.business;

import java.util.*;
import java.io.Serializable;

public class Cart implements Serializable {

    private List<LineItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void setItems(List<LineItem> lineItems) {
        items = lineItems;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public int getCount() {
        return items.size();
    }

    public void addItem(LineItem item) {
        //If the item already exists in the cart, only the quantity is changed.
        long isbn13 = item.getProduct().getISBN13();
        int quantity = item.getQuantity();
        for (LineItem lineItem : items) {
            if (lineItem.getProduct().getISBN13() == isbn13) {
                lineItem.setQuantity(quantity);
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(LineItem item) {
        long code = item.getProduct().getISBN13();
        for (int i = 0; i < items.size(); i++) {
            LineItem lineItem = items.get(i);
            if (lineItem.getProduct().getISBN13() == code) {
                items.remove(i);
                return;
            }
        }
    }
}
