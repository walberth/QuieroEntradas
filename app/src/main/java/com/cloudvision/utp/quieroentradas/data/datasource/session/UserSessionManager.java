package com.cloudvision.utp.quieroentradas.data.datasource.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cloudvision.utp.quieroentradas.data.model.User;
import com.cloudvision.utp.quieroentradas.data.model.UserResponse;
import com.cloudvision.utp.quieroentradas.presentation.ui.LoginActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gustavo Ramos M. on 10/06/2018.
 */

public class UserSessionManager {

    //Objetos SharedPreferences
    SharedPreferences preferences;
    Editor editor;
    //Objeto Contexto
    Context context;
    //Id modo privado
    int PRIVATE_MODE = 0;
    //Variables para llaves de sesion de login general y validacion
    private static final String USER_PREFERENCES = "user_preferences";
    private static final String EXIST_LOGIN_SESSION = "exist_login_session";
    //Variables para llaves de informacion de usuario
    public static final String KEY_ID = "idLogin";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_LASTNAME = "lastname";
    public static final String KEY_SEX_NAME = "sexName";
    public static final String KEY_SEX_URI = "sexUri";
    public static final String KEY_CREATED_USER = "userCreatedTime";

    public UserSessionManager(Context context) {
        this.context = context;
        //Instancia objeto preferencias al contexto y su objeto para edicion
        preferences = context.getSharedPreferences(USER_PREFERENCES, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public UserSessionManager(Context context, User response) {
        this.context = context;
        //Instancia objeto preferencias al contexto y su objeto para edicion
        preferences = context.getSharedPreferences(USER_PREFERENCES, PRIVATE_MODE);
        editor = preferences.edit();
        //Llama a metodo para crear sesion
        this.createSession(response);
    }

    //Crea sesion para login
    public void createSession(User user) {
        //Llave para validar que exista sesion
        editor.putBoolean(EXIST_LOGIN_SESSION, true);
        //Llaves y valores con informacion de usuario
        editor.putString(KEY_ID, user.getIdLogin());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_LASTNAME, user.getLastName());
        editor.putString(KEY_SEX_NAME, user.getSexName());
        editor.putString(KEY_SEX_URI, user.getSexUri());
        editor.putLong(KEY_CREATED_USER, user.getUserCreatedTime());
        //Guardar cambios
        editor.apply();
        Map<String, Object> map = getUserDetails();
    }

    //Valida estado de sesion
    public boolean checkLogin() {
        //Valida que sesion este activa
        if (!this.existLoginSession()) {
            //Redirecciona a actividad de Login
            Intent intent = new Intent(context, LoginActivity.class);
            //Cierra toda slas actividades de la pila
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //Agrega bandera para accion a inicio de nueva actividad
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Inicia actividad
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    //Obtiene data almacenada de sesion
    public Map<String, Object> getUserDetails() {
        Map<String, Object> user = new HashMap<>();
        //Almacena en map de retorno informacion guardado de sesion de usuario
        user.put(KEY_ID, preferences.getString(KEY_ID, null));
        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null));
        user.put(KEY_USERNAME, preferences.getString(KEY_USERNAME, null));
        user.put(KEY_NAME, preferences.getString(KEY_NAME, null));
        user.put(KEY_LASTNAME, preferences.getString(KEY_LASTNAME, null));
        user.put(KEY_SEX_NAME, preferences.getString(KEY_SEX_NAME, null));
        user.put(KEY_SEX_URI, preferences.getString(KEY_SEX_URI, null));
        user.put(KEY_CREATED_USER, preferences.getLong(KEY_CREATED_USER, 0));
        return user;
    }

    //Cierra sesion
    public void logoutSession(){
        //Limpia informacion de sesion de usuario almacenada
        editor.clear();
        editor.commit();
        //Redirecciona hacia actividad de Login
        Intent intent = new Intent(context, LoginActivity.class);
        //Limpia todas las actividades
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Agrega nuevo indicador para inicio de nueva actividad
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //Valida sesion
    public boolean existLoginSession() {
        return preferences.getBoolean(EXIST_LOGIN_SESSION, false);
    }
}
