package Backend;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RequestController extends HttpRequest {

    @RequestMapping(value = "/get/{hashKey}/{key}", method = RequestMethod.GET, produces = "application/json")
    public String get(@PathVariable("hashKey") int hashKey, @PathVariable("key") String key
            , HttpServletResponse response) {
        System.out.println("[Request] GET hashKey = " + hashKey + ", key = " + key);

        JsonObject redirect;
        if ((redirect = redirect(hashKey)) == null) {
            JsonObject data = Driver.dataStorage.get(hashKey, key);
            if (data == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            } else {
                return data.toString();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);

            return redirect.toString();
        }
    }

    @RequestMapping(value = "/put/{hashKey}/{key}", method = RequestMethod.POST, produces = "application/json")
    public String put(@PathVariable("hashKey") int hashKey, @PathVariable("key") String key
            , @RequestBody String requestBody, HttpServletResponse response) {
        System.out.println("[Request] POST hashKey = " + hashKey + ", key = " + key + ", requestBody = " +
                requestBody.replaceAll(System.lineSeparator(), "").replaceAll("\t", ""));

        JsonObject redirect;
        if ((redirect = redirect(hashKey)) == null) {
            JsonObject body = parseJson(requestBody).getAsJsonObject();
            Driver.dataStorage.put(hashKey, key, body);
            return null;
        } else {
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);

            return redirect.toString();
        }
    }

    private JsonObject redirect(int hashKey) {
        return Driver.ring.checkRedirect(hashKey);
    }
}