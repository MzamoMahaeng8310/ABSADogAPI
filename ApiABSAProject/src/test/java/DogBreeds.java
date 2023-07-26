import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import Utils.TestData;
public class DogBreeds {
	ExtentHtmlReporter htlmReporter;
	ExtentReports extent ;
	ExtentTest  test;		
	@BeforeSuite 
	public void preconditinDeclarations() throws IOException
	{
		htlmReporter = new ExtentHtmlReporter("AllBreeds.html");
		extent = new ExtentReports();
		extent.attachReporter(htlmReporter);
	}
	@Test(enabled = true, priority = 1)
	public void AllDogBreeds() throws IOException
	{
		File src = new File("./RESTApiConfig/RESTApiConfig.property");
		FileInputStream fis = new FileInputStream(src);
		Properties pro = new Properties();
		pro.load(fis);
		Response resp = given().
				when().get(pro.getProperty("partyTypeEndPoint"));
		test = extent.createTest("All Breeds: ", resp.asString());
		int expectedStatusCode = 200;
		int actualStatusCode = resp.getStatusCode();
		String resposeCode=Integer.toString(actualStatusCode);
		test.log(Status.INFO,"Response Code: "+ resposeCode);
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected.");
	}
	@Test(enabled = true , priority = 2)
	public void SubBreedsForRretriever() throws IOException
	{
		File src = new File("./RESTApiConfig/RESTApiConfig.property");
		FileInputStream fis = new FileInputStream(src);
		Properties pro = new Properties();
		pro.load(fis);

		Response resp = given().
				when().get(pro.getProperty("partyTypeEndPoint"));

		JsonPath jsonPath = resp.jsonPath();
		ArrayList<String>englishDescription = jsonPath.get(pro.getProperty("partyTypeJSONPath"));
		String result = englishDescription.toString();
		System.out.println("Retriver Contents:"+ result);
		test = extent.createTest("List of sub-breeds for “retriever”. ", result);
		test.log(Status.INFO,"Retriver Contents: "+ result);

	}
	@Test(enabled = true, priority = 3)
	public void VerifyRetrieverInTheList() throws IOException
	{
		File src = new File("./RESTApiConfig/RESTApiConfig.property");
		FileInputStream fis = new FileInputStream(src);
		Properties pro = new Properties();
		pro.load(fis);
		Response resp = given().
				when().get(pro.getProperty("partyTypeEndPoint"));
		JsonPath jsonPath = resp.jsonPath();
		ArrayList<String>BeedListRespose = jsonPath.get(pro.getProperty("partyTypeJSONPath"));
		test = extent.createTest("Verify Retriever In The List  : ");
		String  expectedBreed = pro.getProperty("VerifyRetrieverList"); 
		boolean found = false ;
		for(String breedDog : BeedListRespose)
			if(breedDog.equals(expectedBreed))
			{
				found = true;
				break;
			}

		if (found) {
			test.log(Status.PASS,"Verify - Retriever Within List  ");
		} else {
			test.log(Status.FAIL,"Retriever is wihing the list  ");
		}

	}
	@Test(enabled = true, priority = 4)
	public void ProduceRandomImage() throws IOException
	{

		String firstUld = TestData.urlImages();
		String breed = TestData.createDictionary().get("Breed");
		String subBreed = TestData.createDictionary().get("SubBreed");
		String lastPartOfUrl = TestData.appendurl();
		String buildUrlRequest = firstUld+breed+subBreed+lastPartOfUrl;
		Response resp = given().
				when().get(buildUrlRequest);
		test = extent.createTest("Random image / link for the sub-breed “golden”: ", resp.asString());
	}
	@AfterSuite
	public void postScriptDeclarations()
	{
		extent.flush();
	}

		
}
