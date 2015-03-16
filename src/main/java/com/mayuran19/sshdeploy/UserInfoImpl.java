/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayuran19.sshdeploy;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import com.mayuran19.sshdeploy.helper.ConfigurationHelper;

/**
 *
 * @author mayuran
 */
public class UserInfoImpl implements UserInfo, UIKeyboardInteractive {
    private ConfigurationHelper configurationHelper;
    public UserInfoImpl(){
        this.configurationHelper = new ConfigurationHelper();
    }
    @Override
    public String getPassphrase() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPassword() {
        //System.out.println("calling getPassword");
        return configurationHelper.getProperty("server1.password");
    }

    @Override
    public boolean promptPassword(String string) {
        //System.out.println("calling promptPassword");
        return true;
    }

    @Override
    public boolean promptPassphrase(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean promptYesNo(String string) {
        return true;
    }

    @Override
    public void showMessage(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] promptKeyboardInteractive(String string, String string1, String string2, String[] strings, boolean[] blns) {
        //System.out.println("destination:" + string);
        //System.out.println("name:" + string1);
        //System.out.println("instruction:" + string2);
        //System.out.println("prompt:" + Arrays.deepToString(strings));
        //System.out.println("echo:" + blns);
        String[] response = new String[1];
        if(strings.length > 0 && strings[0].equalsIgnoreCase("Are you sure you want to continue connecting (yes/no)?")){
            response[0] = "yes";
        }else{
            response[0] = "mayuran19";
        }
        
        return response;
    }

}
