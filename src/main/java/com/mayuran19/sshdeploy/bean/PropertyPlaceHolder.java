/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mayuran19.sshdeploy.bean;

/**
 *
 * @author mayuran
 */
public class PropertyPlaceHolder {
    private String description;
    private String type;
    private String comboBoxValues;
    private String textFieldDefault;

    public String getComboBoxValues() {
        return comboBoxValues;
    }

    public void setComboBoxValues(String comboBoxValues) {
        this.comboBoxValues = comboBoxValues;
    }

    public String getTextFieldDefault() {
        return textFieldDefault;
    }

    public void setTextFieldDefault(String textFieldDefault) {
        this.textFieldDefault = textFieldDefault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
