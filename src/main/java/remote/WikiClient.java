package remote;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class WikiClient {
    private static final String WIKI_API = "https://en.wikipedia.org/w/api.php?action=query&list=search&srwhat=text&srsearch=";
    Client client;

    public WikiClient() {
        client = Client.create();

    }

    public String getResponse(String query) {
        WebResource webResource = client
                .resource(WIKI_API + query +"&format=json");

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        return response.getEntity(String.class);
    }
}
