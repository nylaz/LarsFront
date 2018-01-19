/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.ChatUser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author LasseLasseViking
 */
@Named("userService")
@SessionScoped
public class UserService implements Serializable{
    
    private PrintWriter out;
    private final String baseUrl = "http://194.28.123.208:8080/LarsBack-war/ChatServer/resources/users";
    private ChatUser user;
    private ArrayList<ChatUser> users;
    
    public void registerUser() throws IOException {
        user = new ChatUser();
        user.setUserName("Test");
        user.setFullName("Test");
        user.setInfo("Test");
        user.setImageUrl("Test");
        
        JsonObject object = (JsonObject) user;
        
        HttpURLConnection connection = getHttpURLConnection(baseUrl, "POST");
        
        out = new PrintWriter(connection.getOutputStream());
        out.println(object);
        out.close();
        if(connection.getResponseCode()!=200){
        }
    }
    
    public void loadUsers() throws IOException {
        HttpURLConnection connection = getHttpURLConnection("http://194.28.123.208:8080/ChatServer/resources/users", "GET");
        
        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        String output;
        String responseString ="";
        while((output = br.readLine())!=null){
            responseString += output;
        }
        
    }
    
    public ChatUser getUser() {
        return this.user;
    }

    private HttpURLConnection getHttpURLConnection(String baseUrl, String crudType) throws IOException{
        URL url = new URL(baseUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-type", MediaType.APPLICATION_JSON);
        connection.setRequestMethod(crudType);
        connection.setRequestProperty("Accept",MediaType.APPLICATION_JSON);
        return connection;
    }
}
