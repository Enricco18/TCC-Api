package br.com.pichau.api.utils.httpClient.response;

import java.util.List;
import java.util.Map;

public class FlagsResponse {
    public  Body body;
    public class Body{
        public Integer total;
        public List<Map<String,String>> items;

        @Override
        public String toString() {
            return "Body{" +
                    "total=" + total +
                    ", items=" + items +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FlagsResponse{" +
                "body=" + body +
                '}';
    }
}
