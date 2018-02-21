package tp.skt.simple.api;

/**
 * Configuration.java
 * <p>
 * Copyright (C) 2017. SK Telecom, All Rights Reserved.
 * Written 2017, by SK Telecom
 */
public class Configuration {

    /** mqtt server address **/
    private String serverAddress;
    /** mqtt server port **/
    private int serverPort;
    /** mqtt keep alive **/
    private int keepAlive;
    /** client ID **/
    private String clientId;
    /** login name **/
    private String loginName;
    /** login password **/
    private String loginPassword;
    /** automatic reconnect **/
    private boolean automaticReconnect = true;
    /** clean session **/
    private boolean cleanSession = true;
    
    /**
     *
     *
     */
    private Configuration() {
    }

    /**
     * Configuration constructor
     *
     * @param serverAddress
     * @param serverPort
     * @param keepAlive
     * @param clientId
     * @param loginName
     * @param loginPassword
     */
    public Configuration(String serverAddress, int serverPort, int keepAlive , String clientId, String loginName, String loginPassword, boolean cleanSession) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.keepAlive = keepAlive;
        this.clientId = clientId;
        this.loginName = loginName;
        this.loginPassword = loginPassword;
        this.cleanSession = cleanSession;
    }

    /**
     * get Server Address.
     *
     * @return address.
     */
    public String getServerAddress() {
        if(null != serverAddress && serverAddress.endsWith("/")){
            return serverAddress.substring(0, serverAddress.length()-1);
        }
        return serverAddress;
    }
    
    /**
     * get Server Port.
     * @return port
     */
    public int getServerPort() {
        return serverPort;
    }
    
    /**
     * get Socket Keep alive interval
     * @return 
     */
    public int getKeepAlive() {
        return keepAlive;
    }
    
    /**
     * get Automatic Reconnect
     * @return 
     */
    public boolean getAutomaticReconnect() {
        return automaticReconnect;
    }

    public void setAutomaticReconnect(boolean automaticReconnect){
        this.automaticReconnect = automaticReconnect;
    }
    
    
    /**
     * get Client Id (Device Token)
     *
     * @return clientId
     */
    public String getClientId() {
        return clientId;
    }


    /**
     * get login name(Device Token)
     *
     * @return loginName
     */
    public String getLoginName() {
        return loginName;
    }


    /**
     * get login Password
     *
     * @return loginPassword
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * is cleanSession
     * @return  cleanSession
     */
    public boolean isCleanSession(){
        return cleanSession;
    }
}
