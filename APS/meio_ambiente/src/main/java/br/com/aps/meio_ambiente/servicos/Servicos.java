package br.com.aps.meio_ambiente.servicos;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import br.com.aps.meio_ambiente.modelos.Cidade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class Servicos {
    Gson gson = new Gson();
    
    public String carregarInformacoes(String cidade, String tp) {
        cidade = cidade.replace("%20", " ");
        String cidadeEncoded = URLEncoder.encode(cidade, StandardCharsets.UTF_8);

        List<Cidade> cidades;

        try {
            URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + cidadeEncoded + "&limit=1&appid=702727f1aa17bf110845df95ffcb3acf");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonElement json = gson.fromJson(response.toString(), JsonElement.class);

            Type type = new TypeToken<List<Cidade>>(){}.getType();
            cidades = gson.fromJson(json, type);

            if (tp.equals("AB")) {
                return ambasInformacoes(cidades);
            } else if (tp.equals("CA")) {
                return qualidadeAr(cidades);
            } else {
                return informacoesMeteorologicas(cidades);
            }
        } catch (Exception e) {
            return "Erro ao fazer a requisição HTTP: " + e.getMessage();
        }
    }

    public String ambasInformacoes(List<Cidade> cidades) {
        try {
            URL urlAr = new URL("http://api.openweathermap.org/data/2.5/air_pollution?lat=" + cidades.get(0).getLat() + "&lon=" + cidades.get(0).getLon() + "&appid=702727f1aa17bf110845df95ffcb3acf");

            HttpURLConnection connAr = (HttpURLConnection) urlAr.openConnection();
            connAr.setRequestMethod("GET");

            BufferedReader inAr = new BufferedReader(new InputStreamReader(connAr.getInputStream(), Charset.forName("UTF-8")));
            String inputLineAr;
            StringBuffer responseAr = new StringBuffer();
            while ((inputLineAr = inAr.readLine()) != null) {
                responseAr.append(inputLineAr);
            }
            inAr.close();

            URL urlMeteorologia = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + cidades.get(0).getLat() + "&lon=" + cidades.get(0).getLon() + "&appid=702727f1aa17bf110845df95ffcb3acf&lang=pt_br");
            
            HttpURLConnection connMeteorologia = (HttpURLConnection) urlMeteorologia.openConnection();
            connMeteorologia.setRequestMethod("GET");

            BufferedReader inMeteorologia = new BufferedReader(new InputStreamReader(connMeteorologia.getInputStream(), Charset.forName("UTF-8")));
            String inputLineMeteorologia;
            StringBuffer responseMeteorologia = new StringBuffer();
            while ((inputLineMeteorologia = inMeteorologia.readLine()) != null) {
                responseMeteorologia.append(inputLineMeteorologia);
            }
            inMeteorologia.close();

            return responseAr.toString() + " %20 " + responseMeteorologia.toString();

        } catch (Exception e) {
            return "Erro ao fazer a requisição HTTP: " + e.getMessage();
        }
    }

    public String qualidadeAr(List<Cidade> cidades) {
        try {
            URL urlAr = new URL("http://api.openweathermap.org/data/2.5/air_pollution?lat=" + cidades.get(0).getLat() + "&lon=" + cidades.get(0).getLon() + "&appid=702727f1aa17bf110845df95ffcb3acf");

            HttpURLConnection connAr = (HttpURLConnection) urlAr.openConnection();
            connAr.setRequestMethod("GET");

            BufferedReader inAr = new BufferedReader(new InputStreamReader(connAr.getInputStream(), Charset.forName("UTF-8")));
            String inputLineAr;
            StringBuffer responseAr = new StringBuffer();
            while ((inputLineAr = inAr.readLine()) != null) {
                responseAr.append(inputLineAr);
            }
            inAr.close();

            return responseAr.toString();
        } catch (Exception e) {
            return "Erro ao fazer a requisição HTTP: " + e.getMessage();
        }
    }

    public String informacoesMeteorologicas(List<Cidade> cidades) {
        try {
            URL urlMeteorologia = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + cidades.get(0).getLat() + "&lon=" + cidades.get(0).getLon() + "&appid=702727f1aa17bf110845df95ffcb3acf&lang=pt_br");
            
            HttpURLConnection connMeteorologia = (HttpURLConnection) urlMeteorologia.openConnection();
            connMeteorologia.setRequestMethod("GET");

            BufferedReader inMeteorologia = new BufferedReader(new InputStreamReader(connMeteorologia.getInputStream(), Charset.forName("UTF-8")));
            String inputLineMeteorologia;
            StringBuffer responseMeteorologia = new StringBuffer();
            while ((inputLineMeteorologia = inMeteorologia.readLine()) != null) {
                responseMeteorologia.append(inputLineMeteorologia);
            }
            inMeteorologia.close();

            return responseMeteorologia.toString();
        } catch (Exception e) {
            return "Erro ao fazer a requisição HTTP: " + e.getMessage();
        }
    }
}
