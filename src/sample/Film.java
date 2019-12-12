package sample;

public class Film {
    private int id;
    private String title;
    private String genre;
    private double rating;
    private int year;
    private String review;

    public Film(int id, String title, String genre, double rating, int year, String review) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.review=review;
    }
    public Film() {
        this.id =  0;
        this.title = "";
        this.genre = "";
        this.rating = 0;
        this.year = 0;
        this.review="";
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
