package baekgwa.wisesaying.dto;

public class RequestDto {

    public static class Register {
        private final String author;
        private final String content;

        public Register(String author, String content) {
            this.author = author;
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }
    }
}
