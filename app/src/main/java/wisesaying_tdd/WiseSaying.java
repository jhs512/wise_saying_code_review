package wisesaying_tdd;

public class WiseSaying {
    int id;
    String wiseSaying;
    String authur;

    public WiseSaying (int id, String wiseSaying, String author) {
        this.id = id;
        this.wiseSaying = wiseSaying;
        this.authur = author;
    }
        
    // Getters    
    public int getId() {
        return id;    
    }
                
    public String getWiseSaying() {
        return wiseSaying;
    }

    public String getAuthor() {
        return authur;
    }
    
}
