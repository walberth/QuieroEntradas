package com.cloudvision.utp.quieroentradas.helpers.enums;

/**
 * Created by Gustavo Ramos M on 10/06/2018.
 */
public enum UserSexEnum {
    MALE("male", "Masculino", "https://firebasestorage.googleapis.com/v0/b/dressmeup-2018.appspot.com/o/avatar_male_user.png?alt=media&token=2773390a-3cb4-4a8d-9441-59729dc0bb7c"),
    FEMALE("female",  "Femenino", "https://firebasestorage.googleapis.com/v0/b/dressmeup-2018.appspot.com/o/avatar_female_user.png?alt=media&token=40bc0ea3-60fd-4785-900e-e3a4707f9dd9");

    private final String name;
    private final String label;
    private final String uri;

    UserSexEnum(String name, String label, String uri){
        this.name = name;
        this.label = label;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getUri() {
        return uri;
    }

    public static String getSexLabelByName(String name){
        UserSexEnum sexEnum = null;
        for(UserSexEnum rowenum : UserSexEnum.values()){
            if(rowenum.getName().equals(name)){
                sexEnum = rowenum;
            }
        }
        if(sexEnum == null){
            return "Desconocido";
        }
        return sexEnum.getLabel();
    }
}
