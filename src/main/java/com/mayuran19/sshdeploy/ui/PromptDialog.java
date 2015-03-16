/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayuran19.sshdeploy.ui;

import com.mayuran19.sshdeploy.bean.PropertyPlaceHolder;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author mayuran
 */
public class PromptDialog extends JDialog {

    private JDialog jDialog;

    public PromptDialog(JFrame jFrame) {
        this.jDialog = new JDialog(jFrame, "Prompt Dialog");
        this.jDialog.setModal(true);
    }

    public Map<String, String> getPlaceHolderValueMap(Map<String, PropertyPlaceHolder> map) {
        final JPanel p = new JPanel(new SpringLayout());
        final Map<String, Component> components = new HashMap<>();
        final Map<String, String> propKeyValue = new HashMap<>();
        List<String> sortedKeys = new ArrayList(map.keySet());
        Collections.sort(sortedKeys);
        
        
        for (int i = 0; i < sortedKeys.size(); i++) {
            JLabel l = new JLabel(map.get(sortedKeys.get(i)).getDescription(), JLabel.TRAILING);
            p.add(l);
            if (map.get(sortedKeys.get(i)).getType().equals("TextField")) {
                JTextField textField = new JTextField(10);
                l.setLabelFor(textField);
                p.add(textField);
                components.put((String) sortedKeys.get(i), textField);
            } else if (map.get(sortedKeys.get(i)).getType().equals("ComboBox")) {
                JComboBox comboBox = new JComboBox(map.get(sortedKeys.get(i)).getComboBoxValues().split(","));
                l.setLabelFor(comboBox);
                p.add(comboBox);
                components.put((String) sortedKeys.get(i), comboBox);
            }

        }

        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (String key : components.keySet()) {
                    if (components.get(key) instanceof JTextField) {
                        propKeyValue.put(key, ((JTextField) components.get(key)).getText());
                    } else if (components.get(key) instanceof JComboBox) {
                        propKeyValue.put(key, ((JComboBox) components.get(key)).getSelectedItem().toString());
                    }
                }
                jDialog.dispose();
            }
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });

        p.add(ok);
        p.add(cancel);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                map.size() + 1, 2, //rows, cols
                6, 6, //initX, initY
                6, 6);       //xPad, yPad

        p.setOpaque(true);
        jDialog.setContentPane(p);
        jDialog.pack();

        jDialog.setVisible(true);

        return propKeyValue;
    }

}
