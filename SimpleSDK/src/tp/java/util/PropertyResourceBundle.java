package tp.java.util;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Hashtable;
import org.eclipse.paho.client.mqttv3.MqttException;

public class PropertyResourceBundle {
    
    private Hashtable lookup;
    private InputStream stream;
    
    public PropertyResourceBundle (InputStream stream) {
        this.stream = stream;
        lookup = new Hashtable();
        addToHash();
    }
    
    private void addToHash() {
        boolean keyFound = false;
        boolean startLine = true;
        
        try {
            StringBuffer temp = new StringBuffer();
            String key = "";
            String value = "";
            for (int i=0; ((i = stream.read()) >= 0);) {
                if (i == '#' && startLine) {
                    // Comment line skip to the end
                    while (i != '\n' && i >= 0) {
                        i = stream.read();
                    }
                } else if (i == '\n') {
                    // We have reached the end of the line
                    value = temp.toString();
                    if (value.indexOf('\r') != -1) {  // trim off carage return
                        value = value.substring(0,value.indexOf('\r'));
                    }
                    
                    temp.delete(0, temp.length());
                    
                    if (key != null) {
                        lookup.put(key, value);
                    }
                    
                    startLine = true;
                    keyFound = false;
                    key = null;
                    continue;
                } else if (i == '=') {
                    if (!keyFound) {
                        key = temp.toString();
                        temp.delete(0, temp.length());
                        keyFound = true;
                    } else {
                        temp.append((char)i);
                    }
                } else {
                    temp.append((char)i);
                    startLine = false;
                }
            }
            // End of file, add final property
            value = temp.toString();
            if (value.indexOf('\r') != -1) {        // trim off carage return
                value = value.substring(0,value.indexOf('\r'));
            }
            
            if (key != null) {
                lookup.put(key, value);
            }
        } catch (Exception e) {
            System.out.println("[PropertyResourceBundle]: addToHash() Exception: " + e.toString());
        }
    }


    public static PropertyResourceBundle getBundle(String name, boolean nls) throws MqttException {
        Locale locale = Locale.getDefault();
        InputStream stream = null;
        
        // Search for .properties files
        name = "/" + name.replace('.', '/') + ".properties";
        
        // Retrieve the default
        stream = locale.getClass().getResourceAsStream(name);

        if (nls) {
            // Retrieve the language specific properties
            InputStream temp = locale.getClass().getResourceAsStream(name + '_' + locale.language);
            if (temp != null) {
                stream = temp;
            }
            
            // Retrieve the country specific properties for this language
            temp = locale.getClass().getResourceAsStream(name + '_' + locale.language +'_' + locale.country);
            if (temp != null) {
                stream = temp;
            }
        }
        
        // Finally create the Resource
        if (stream != null) {
            return new PropertyResourceBundle(stream);
        } else {
            throw new MqttException(MqttException.REASON_CODE_CLIENT_EXCEPTION);
        }
        
    }
    
    public String getString(String id) {
        String retVal = "";
        
        try {
            String key = id;
            if (lookup.containsKey(key)) {
                retVal = (String) lookup.get(key);
            }
        } catch (Exception e) {
            System.out.println("[PropertyResourceBundle]: getString() Exception: " + e.toString());
        }

        return retVal;
    }
    
    private static class Locale {
        private static Locale defaultLocale = null;
        private String language;
        private String country;
        
        public Locale(String language, String country) {
            this.language = language;
            this.country = country;
        }
        
        public static Locale getDefault() {
            if (defaultLocale == null) {
                String def = System.getProperty("microedition.locale");
                if (def != null) {
                    String defLanguage = def.substring(0, def.indexOf("-"));
                    String defCountry = def.substring(def.indexOf("-")+1);
                    defaultLocale = new Locale(defLanguage, defCountry);
                }
            }
            return defaultLocale;
        }
    }
}
