package bookstore.business;

/**
 * Created by Christopher on 3/12/2015.
 */
public class UserCategory {
    private String category;
    private int count;
    private User user;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
