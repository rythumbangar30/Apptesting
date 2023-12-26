package DynamicModel;

import java.util.Properties;

public enum ApplicationProperties {
    INSTANCE;
private final Properties properties;


    ApplicationProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

        }catch(Exception e){

        }

    }
    public String getBaseURI(){
        return properties.getProperty("service.url");
    }
    public String getToken(){
        return properties.getProperty("service.token");
    }

}
