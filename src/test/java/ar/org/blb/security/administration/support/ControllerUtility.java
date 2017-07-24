package ar.org.blb.security.administration.support;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ControllerUtility {

    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private final static String TIME_ZONE = "GMT";

    public static List jsonToList(String json, TypeToken token) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
        return gson.fromJson(json, token.getType());
    }

    public static String objectToJson(Object obj) {
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
        return gson.toJson(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> classOf) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
        return gson.fromJson(json, classOf);
    }

    public static Date dateToParserDate(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat parser = new SimpleDateFormat(DATE_FORMAT);
        parser.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        return parser.parse(format.format(date));
    }

    public static class JsonDateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String asString = json.getAsJsonPrimitive().getAsString();
            long aLong = Long.parseLong(asString.substring(6, asString.length() - 2));
            return new Date(aLong);
        }
    }
}
