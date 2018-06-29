package grafungi.graph.bn;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author fschmidt
 */
public class StateGsonConverter implements JsonDeserializer<State>, JsonSerializer<State> {

    @Override
    public State deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jobj = je.getAsJsonObject();
        State state = new State(jobj.get("name").getAsString());
        state.setEntityName(jobj.get("entityName").getAsString());
        return state;
    }

    @Override
    public JsonElement serialize(State t, Type type, JsonSerializationContext jsc) {
        JsonObject jobj = new JsonObject();
        jobj.addProperty("name", t.getName());
        jobj.addProperty("entityName", t.getEntityName());
        return jobj;
    }

}
