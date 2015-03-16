/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayuran19.sshdeploy;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mayuran19.sshdeploy.helper.ConfigurationHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author mayuran
 */
public class SSHCopy {

    private ConfigurationHelper configurationHelper;

    public SSHCopy() {
        this.configurationHelper = new ConfigurationHelper();
    }

    public void copy() throws JSchException, IOException {
        JSch jSch = new JSch();
        String srcIPAddress = this.configurationHelper.getProperty("server1.ip.address");
        String srcUserName = this.configurationHelper.getProperty("server1.username");
        Integer srcPort = Integer.valueOf(this.configurationHelper.getProperty("server1.port"));
        Session session = jSch.getSession(srcUserName, srcIPAddress, srcPort);
        UserInfoImpl userInfoImpl = new UserInfoImpl();
        session.setUserInfo(userInfoImpl);
        session.connect();
        session.setConfig("PreferredAuthentications", "keyboard-interactive");
        String command = "scp /home/mayuran/Documents/Transport.mdb mayuran@192.168.0.28:/home/mayuran";
        //String command = "ls -ltr";
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setPty(true);
        ((ChannelExec) channel).setCommand(command);

        //channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);

        InputStream in = channel.getInputStream();
        OutputStream out = channel.getOutputStream();
        channel.connect();

        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                System.out.print(new String(tmp, 0, i));
                out.write((this.configurationHelper.getProperty("server2.password") + "\n").getBytes());
                out.flush();
            }
            if (channel.isClosed()) {
                if (in.available() > 0) {
                    continue;
                }
                System.out.println("exit-status: " + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        channel.disconnect();
        session.disconnect();
    }

    public void executeCommand(String remoteIP, String remoteUsername, String remotePassword, Integer remotePort, String command, JTextPane textPane) throws JSchException, IOException {
        System.out.println(command);
        JSch jSch = new JSch();
        Session session = jSch.getSession(remoteUsername, remoteIP, remotePort);
        UserInfoImpl userInfoImpl = new UserInfoImpl();
        session.setUserInfo(userInfoImpl);
        session.connect();
        session.setConfig("PreferredAuthentications", "keyboard-interactive");
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setPty(true);
        ((ChannelExec) channel).setCommand(command);
        InputStream in = channel.getInputStream();
        OutputStream out = channel.getOutputStream();
        channel.connect();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                System.out.print(new String(tmp, 0, i));
                try {
                    textPane.getDocument().insertString(textPane.getDocument().getLength(), new String(tmp, 0, i), null);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                out.write((remotePassword + "\n").getBytes());
                out.flush();
            }
            if (channel.isClosed()) {
                if (in.available() > 0) {
                    continue;
                }
                System.out.println("exit-status: " + channel.getExitStatus());
                try {
                    textPane.getDocument().insertString(textPane.getDocument().getLength(), "exit-status: " + channel.getExitStatus() + "\n", null);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        channel.disconnect();
        session.disconnect();
    }

}
