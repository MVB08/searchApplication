package com.example.search;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Search:");
        String query = scanner.nextLine();
        scanner.close();
        System.out.println("Results:");
        getJson(query);
    }

    static void getJson(String query) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(String.format("https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=\"%s\"", query)).build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                System.out.println(content);
                Gson gson = new Gson();
                Example example = gson.fromJson(content, Example.class);
                System.out.println();
                System.out.println();
                System.out.println();

                List<Search> list = example.getQuery().getSearch();
                List<String> result = new ArrayList();

                for (Search s : list) {
                    result.add(String.format("%s\thttps://ru.wikipedia.org/?curid=%s", s.getTitle(), s.getPageid()));
                }
                System.out.println("Results:");
                int c = 1;

                for (String s : result) {
                    System.out.printf("%d) %s\n", c++, s);
                }

            }
        });

    }

}

