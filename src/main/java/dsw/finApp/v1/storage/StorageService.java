package dsw.finApp.v1.storage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.InputStream;

public interface StorageService {
    File save(File file);

    File get(String filenName);


    File getInfo(String filenName);

    void delete(String fileName);

    File replace(String oldFile, File newFile);

    static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class File {

        @JsonProperty(value = "tamanho")
        private long size;
        private String url;
        @JsonProperty("nomeArquivo")
        private String fileName;
        private InputStream inputStream;
        private String contentType;

        public boolean hasBytes() {
            return inputStream != null;
        }

        public boolean hasUrl() {
            return url != null;
        }
    }
}
