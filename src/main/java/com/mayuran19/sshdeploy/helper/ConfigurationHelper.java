/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayuran19.sshdeploy.helper;

import com.mayuran19.sshdeploy.bean.PropertyPlaceHolder;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayuran
 */
public class ConfigurationHelper {

    private static final Map<String, String> map = new HashMap<String, String>();

    static {
        try {
            loadProperties();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return map.get(key);
    }

    public Map<String, PropertyPlaceHolder> getPropertiesPlaceHolders(String key) throws ParseException {
        Map<String,PropertyPlaceHolder> map = new HashMap<String,PropertyPlaceHolder>();
        Pattern pattern = Pattern.compile("\\{\\d+\\}");
        String property = this.getProperty(key);
        Matcher matcher = pattern.matcher(property);
        while(matcher.find()){
            String group = matcher.group();
            PropertyPlaceHolder propertyPlaceHolder = new PropertyPlaceHolder();
            propertyPlaceHolder.setDescription(this.getProperty(key + "." + group.replace("{", "").replace("}", "") + ".description"));
            propertyPlaceHolder.setType(this.getProperty(key + "." + group.replace("{", "").replace("}", "") + ".type"));
            if(propertyPlaceHolder.getType().equals("ComboBox")){
                propertyPlaceHolder.setComboBoxValues(this.getProperty(key + "." + group.replace("{", "").replace("}", "") + ".type.values"));
            }
            map.put(group.replace("{", "").replace("}", ""), propertyPlaceHolder);
        }

        return map;
    }

    public static void loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(ConfigurationHelper.class.getResourceAsStream("/conf.properties"));
        for (Object key : properties.keySet()) {
            map.put((String) key, properties.getProperty((String) key));
        }
    }

    public static void main(String[] args) throws ParseException {
        ConfigurationHelper helper = new ConfigurationHelper();
        System.out.println(helper.getPropertiesPlaceHolders("server1.build.directory"));
    }
}
