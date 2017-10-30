package endpoint;

import org.codehaus.jettison.json.JSONObject;
import service.WikipediaService;
import service.WikipediaServiceMultiThreadImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Path("wikipedia")
public class WikiQuery {
    private WikipediaService wikipediaService =
            new WikipediaServiceMultiThreadImpl();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getWikipediaContent(@QueryParam("queries")
                                            final List<String> queries) throws ExecutionException, InterruptedException {

        if(queries.size() > 5) {
            throw new IllegalArgumentException("Can not submit more than 5 keywords for query");
        }


        Map<String, String> result = wikipediaService.query(queries);

        for (Map.Entry<String, String> entry : result.entrySet()) {
            System.out.println(entry.getKey()+ "::" + entry.getValue());

        }
        JSONObject json = new JSONObject(result);
        return json.toString();
    }
}