package nathja.finalproject.baitap09;

import java.util.List;

public class UploadResponse {
    private boolean success;
    private String message;
    private List<ImageUpload> result;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<ImageUpload> getResult() { return result; }
}

