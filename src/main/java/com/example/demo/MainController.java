package com.example.demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import org.json.XML;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.io.StringWriter;

@RestController
public class MainController {
	
	public static final String REST_SERVICE_URI = "http://localhost:8080";


	@GetMapping(path = "/xmlToJSON")
	@ResponseBody
	public String xmltoJSON(@RequestParam("xmlInp") String xmlInp)
	{
		
		//converting xml to json
		System.out.println("Service being called");
		JSONObject obj = XML.toJSONObject(xmlInp);
		
		System.out.println(obj.toString());
		return obj.toString();
	}
	
	
		
	/*@GetMapping(path = "/testJSON")
	@ResponseBody
	public Document testJSON(@RequestParam("testXML") String testXML)
	{
		
		//converting xml to json
		JSONObject obj = XML.toJSONObject(testXML);
		
		System.out.println(obj.toString());
		Document doc=jsontoXML(obj.toString());
		return doc;
	}*/
	
	/*@GetMapping(path = "/xml")
	@ResponseBody
	public String xml(@RequestParam("xml") String xml)
	{
		
		Document doc = convertStringToXMLDocument( xml );
		
		System.out.println("XML DOC"+doc);
		String output=xmltoJSON(doc);
		return output;
	}*/
	
	/*@GetMapping(path = "/xmlToJSON")
	@ResponseBody
	public String xmltoJSON(@RequestParam("xmlInp") Document xmlInp)
	{
		System.out.println("doc" +xmlInp);
		String str = convertDocumentToString(xmlInp);
		//converting xml to json
		JSONObject obj = XML.toJSONObject(str);
		
		System.out.println(obj.toString());
		return obj.toString();
	}*/
	
	@GetMapping(path = "/jsonToXML")
	@ResponseBody
	public String jsontoXML(@RequestParam("jsonInp") String jsonInp)
	{
		//String json_data = "{\"student\":{\"name\":\"Neeraj Mishra\", \"age\":\"22\"}}";
		System.out.println(jsonInp);
		JSONObject obj = new JSONObject(jsonInp);
		
		//converting json to xml
		String xml_data = XML.toString(obj);
		
		System.out.println(xml_data);
		//Use method to convert XML string content to XML Document object
        Document doc = convertStringToXMLDocument( xml_data );
		
		return xml_data;
	}
	
	//Test
	@GetMapping(path = "/jsonTojson")
	@ResponseBody
	public String jsontojson(@RequestParam("jsonInp") String jsonInp)
	{
		String json_data = "{\"customerAccountStatus\":{\"accountNumber\":\"123\",\"accountStatusRecommendations\": [\r\n" + 
				"    {\r\n" + 
				"      \"accountStatusRecommendation\": \"Dormant\",\r\n" + 
				"      \"accountStatusType\": 0\r\n" + 
				"    },\r\n" + 
				"	{\r\n" + 
				"      \"accountStatusRecommendation\": \"Active\",\r\n" + 
				"      \"accountStatusType\": 3\r\n" + 
				"    }\r\n" + 
				"	\r\n" + 
				"  ],\r\n" + 
				"  \"custStatusDetails\": [\r\n" + 
				"    {\r\n" + 
				"      \"accountStatusDescription\": \"Closed Account\",\r\n" + 
				"      \"accountStatusInd\": true,\r\n" + 
				"      \"accountStatusType\": 0\r\n" + 
				"    },\r\n" + 
				"	{\r\n" + 
				"      \"accountStatusDescription\": \"test\",\r\n" + 
				"      \"accountStatusInd\": false,\r\n" + 
				"      \"accountStatusType\": 2\r\n" + 
				"    }\r\n" + 
				"  ]},\"returnCode\":1,\"returnMessage\":\"Success\"}";
		
		return json_data;
	}
	
		
	private static Document convertStringToXMLDocument(String xmlString) 
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
             
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
	
	private static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
