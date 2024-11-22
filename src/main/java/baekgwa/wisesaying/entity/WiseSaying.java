package baekgwa.wisesaying.entity;

import java.util.Objects;

public class WiseSaying {

    private final Long id;
    private final String content;
    private final String author;

    public WiseSaying(Long id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public String toJson() {
        return String.format(
                "{\"id\":%d,\"content\":\"%s\",\"author\":\"%s\"}",
                id, content.replace("\"", "\\\""), author.replace("\"", "\\\"")
        );
    }

    public static WiseSaying toObject(String json) {
        String[] parts = json.replace("{", "").replace("}", "").replace("\"", "").split(",");
        Long id = Long.parseLong(parts[0].split(":")[1]);
        String content = parts[1].split(":")[1];
        String author = parts[2].split(":")[1];
        return new WiseSaying(id, content, author);
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return this.id + " / " + this.author + " / " + this.content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WiseSaying that = (WiseSaying) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getContent(),
                that.getContent()) && Objects.equals(getAuthor(), that.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getAuthor());
    }
}
