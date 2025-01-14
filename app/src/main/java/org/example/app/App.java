/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example.app;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.example.list.LinkedList;

import static org.example.utilities.StringUtils.join;
import static org.example.utilities.StringUtils.split;
import static org.example.app.MessageUtils.getMessage;

import org.apache.commons.text.WordUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) {
        LinkedList tokens;
        tokens = split(getMessage());
        String result = join(tokens);
        System.out.println(WordUtils.capitalize(result));
        System.out.println("doGet: ");
        doGet();
    }

    public static void doGet() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get("http://httpbin.org/get").build();
            httpClient.execute(httpGet, response -> {
                System.out.println("response: code: " + response.getCode());
                final HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);

                // entity.getContent是InputStream，读取出来转为字符串
                System.out.println("EntityUtils.toString(entity): " + responseString);
                EntityUtils.consume(entity);


                ObjectMapper mapper = new ObjectMapper();
                Foo foo = mapper.readValue(responseString, Foo.class);
                System.out.println("foo: " + foo.toString());

                return null;
            });
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    // 未定义的直接忽略
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Foo {
        public String origin;
        public String url;
        public String name;

        @Override
        public String toString() {
            return "Foo{" +
                    "origin='" + origin + '\'' +
                    ", url='" + url + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
