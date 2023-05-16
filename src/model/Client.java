package model;

/**
 * Methods for Client Class
 * @author Aaron Rose
 */
public class Client {
    /**
     * variables for Client
     */
    private int clientID;
    private String clientName;
    private String contactName;
    private String contactEmail;
    private String contactPhone;

    public Client(int clientID, String clientName, String contactName, String contactEmail, String contactPhone){
        this.clientID = clientID;
        this.clientName = clientName;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * override for combobox displays
     */
    @Override
    public String toString(){
        return (clientName);
    }
}
