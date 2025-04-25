package nathja.finalproject.baitap11;

import com.cloudinary.Cloudinary;
import java.util.HashMap;

public class CloudinaryConfig {
    public static Cloudinary getCloudinary() {
        HashMap<String, String> config = new HashMap<>();
        config.put("cloud_name", "dbixg9otg");
        config.put("api_key", "885836851972942");
        config.put("api_secret", "q1H4AffyewSShvRDNTecvSGdBKE");

        return new Cloudinary(config);
    }
}

