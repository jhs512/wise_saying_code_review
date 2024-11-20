package baekgwa.dto;


public class ResponseDto {

    public static class FindList {

        private final String author;
        private final String content;
        private final Long id;

        public FindList(String author, String content, Long id) {
            this.author = author;
            this.content = content;
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return this.id + " / " + this.author + " / " + this.content;
        }
    }

    public static class FindSayingInfo {

        private final String author;
        private final String content;
        private final Long id;

        public FindSayingInfo(String author, String content, Long id) {
            this.author = author;
            this.content = content;
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public Long getId() {
            return id;
        }
    }

    public static class ModifyInfo {

        private final String author;
        private final String content;
        private final Long id;

        public ModifyInfo(String author, String content, Long id) {
            this.author = author;
            this.content = content;
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public Long getId() {
            return id;
        }

        @Override
        public String toString() {
            return this.id + " / " + this.author + " / " + this.content;
        }
    }
}
