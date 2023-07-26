package Utils;
import java.util.HashMap;
import java.util.Map;

public class TestData {


	public static Map<String, String> createDictionary() {
		Map<String, String> dictionary = new HashMap<String, String>();
		dictionary.put("Breed", "retriever/");
		dictionary.put("SubBreed", "golden");
		return dictionary;
	}
	public static String urlImages()
	{
		String  urlImages = "https://dog.ceo/api/breed/";
		return urlImages;
	}
	
	public static String appendurl()
	{
		String appendUrl = "/images/random";
		return appendUrl;
		
	}
	

}