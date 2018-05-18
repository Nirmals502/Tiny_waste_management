package Service_handler;

public interface SERVER {

    String Server = "http://112.196.3.42:8298/";

    String LOGIN = Server + "v1/Users/LoginDriver";
    String JOBS_ = Server + "http://112.196.3.42:8298/v1/Job/GetAllJobByDriverid/149/Assigned";
    String FORGOT_PASS = Server + "/api/users/forgotpassword";
    String HISTORY = Server + "/api/deliverystatus";
    String CHANGE_PASSWORD = Server +"/api/users/changepassword";
    String LOGOUT = Server + "/api/users/logout";
    String OUT_FOR_DELIVERY =  Server + "/api/deliverystatus/create";
    String add_status =  Server + "/api/deliverystatus/mycourier";
    String Update_Delivery = Server + "/api/deliverystatus/update";
    String Send_feed_back = Server + "/api/users/feedback";
    String SEARCH_Dockect =  Server + "/api/deliverystatus/create";
    String Get_Property_Detail_By_ID = "http://app.jvhub.co.uk/api/properties/show/";
    String UPDATE_PROFILE = "http://app.jvhub.co.uk/api/users/profileupdate";
    String Contact_Us ="http://app.jvhub.co.uk/api/users/contactus";

}