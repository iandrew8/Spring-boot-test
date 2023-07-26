package com.springboot.systems.test.models.core.security.annotations.permissions;

public final class PermissionConstants {

	private PermissionConstants() {
		throw new IllegalStateException("Utility class cannot be instantiated");
	}
	
	@PermissionAnnotation(name = "Manage System", description = "Has permission to manage the system")
	public static final String PERM_MANAGE_SYSTEM = "Manage System";
	
	@PermissionAnnotation(name = "Use System", description = "Has permission to use the system")
	public static final String PERM_USE_SYSTEM = "Use System";

	@PermissionAnnotation(name = "View Role", description = "Has permission to view role")
	public static final String PERM_VIEW_ROLE = "View Role";

	@PermissionAnnotation(name = "Add Role", description = "Has permission to add role")
	public static final String PERM_ADD_ROLE = "Add Role";

	@PermissionAnnotation(name = "Update Role", description = "Has permission to update role")
	public static final String PERM_UPDATE_ROLE = "Update Role";

	@PermissionAnnotation(name = "Delete Role", description = "Has permission to delete role")
	public static final String PERM_DELETE_ROLE = "Delete Role";

	@PermissionAnnotation(name = "View NRC Price Book", description = "Has permission to view NRC price book")
	public static final String PERM_VIEW_NRC_PRICE_BOOK = "View NRC Price Book";

	@PermissionAnnotation(name = "Add NRC Price Book", description = "Has permission to add NRC price book")
	public static final String PERM_ADD_NRC_PRICE_BOOK = "Add NRC Price Book";

	@PermissionAnnotation(name = "Update NRC Price Book", description = "Has permission to update NRC price book")
	public static final String PERM_UPDATE_NRC_PRICE_BOOK = "Update NRC Price Book";

	@PermissionAnnotation(name = "Delete NRC Price Book", description = "Has permission to delete NRC price book")
	public static final String PERM_DELETE_NRC_PRICE_BOOK = "Delete NRC Price Book";

	@PermissionAnnotation(name = "View MRC Price Book", description = "Has permission to view MRC price book")
	public static final String PERM_VIEW_MRC_PRICE_BOOK = "View MRC Price Book";

	@PermissionAnnotation(name = "Add MRC Price Book", description = "Has permission to add MRC price book")
	public static final String PERM_ADD_MRC_PRICE_BOOK = "Add MRC Price Book";

	@PermissionAnnotation(name = "Update MRC Price Book", description = "Has permission to update MRC price book")
	public static final String PERM_UPDATE_MRC_PRICE_BOOK = "Update MRC Price Book";

	@PermissionAnnotation(name = "Delete MRC Price Book", description = "Has permission to delete MRC price book")
	public static final String PERM_DELETE_MRC_PRICE_BOOK = "Delete MRC Price Book";

	@PermissionAnnotation(name = "View Email Template", description = "Has permission to view email template")
	public static final String PERM_VIEW_EMAIL_TEMPLATE = "View Email Template";

	@PermissionAnnotation(name = "Add Email Template", description = "Has permission to add email template")
	public static final String PERM_ADD_EMAIL_TEMPLATE = "Add Email Template";

	@PermissionAnnotation(name = "Update Email Template", description = "Has permission to update email template")
	public static final String PERM_UPDATE_EMAIL_TEMPLATE = "Update Email Template";

	@PermissionAnnotation(name = "Delete Email Template", description = "Has permission to delete email template")
	public static final String PERM_DELETE_EMAIL_TEMPLATE = "Delete Email Template";

	@PermissionAnnotation(name = "View ClickUp Webhook", description = "Has permission to view ClickUp Webhook")
	public static final String PERM_VIEW_CLICKUP_WEBHOOK = "View ClickUp Webhook";

	@PermissionAnnotation(name = "Add ClickUp Webhook", description = "Has permission to add ClickUp Webhook")
	public static final String PERM_ADD_CLICKUP_WEBHOOK = "Add ClickUp Webhook";

	@PermissionAnnotation(name = "Update ClickUp Webhook", description = "Has permission to update ClickUp Webhook")
	public static final String PERM_UPDATE_CLICKUP_WEBHOOK = "Update ClickUp Webhook";

	@PermissionAnnotation(name = "Delete ClickUp Webhook", description = "Has permission to delete ClickUp Webhook")
	public static final String PERM_DELETE_CLICKUP_WEBHOOK = "Delete ClickUp Webhook";

	@PermissionAnnotation(name = "View SmartOLT Endpoint", description = "Has permission to view SmartOLT Endpoint")
	public static final String PERM_VIEW_SMARTOLT_ENDPOINT = "View SmartOLT Endpoint";

	@PermissionAnnotation(name = "Add SmartOLT Endpoint", description = "Has permission to add SmartOLT Endpoint")
	public static final String PERM_ADD_SMARTOLT_ENDPOINT = "Add SmartOLT Endpoint";

	@PermissionAnnotation(name = "Authorise SmartOLT ONU", description = "Has permission to authorise SmartOLT ONU")
	public static final String PERM_AUTHORISE_SMARTOLT_ONU = "Authorise SmartOLT ONU";

	@PermissionAnnotation(name = "Enable SmartOLT ONU", description = "Has permission to enable SmartOLT ONU")
	public static final String PERM_ENABLE_SMARTOLT_ONU = "Enable SmartOLT ONU";

	@PermissionAnnotation(name = "Disable SmartOLT ONU", description = "Has permission to disable SmartOLT ONU")
	public static final String PERM_DISABLE_SMARTOLT_ONU = "Disable SmartOLT ONU";

	@PermissionAnnotation(name = "Update SmartOLT Endpoint", description = "Has permission to update SmartOLT Endpoint")
	public static final String PERM_UPDATE_SMARTOLT_ENDPOINT = "Update SmartOLT Endpoint";

	@PermissionAnnotation(name = "Delete SmartOLT Endpoint", description = "Has permission to delete SmartOLT Endpoint")
	public static final String PERM_DELETE_SMARTOLT_ENDPOINT = "Delete SmartOLT Endpoint";

	@PermissionAnnotation(name = "View SmartOLT Sent Request", description = "Has permission to view SmartOLT Sent Request")
	public static final String PERM_VIEW_SMARTOLT_SENT_REQUEST = "View SmartOLT Sent Request";

	@PermissionAnnotation(name = "Add SmartOLT Sent Request", description = "Has permission to add SmartOLT Sent Request")
	public static final String PERM_ADD_SMARTOLT_SENT_REQUEST = "Add SmartOLT Sent Request";

	@PermissionAnnotation(name = "Update SmartOLT Sent Request", description = "Has permission to update SmartOLT Sent Request")
	public static final String PERM_UPDATE_SMARTOLT_SENT_REQUEST = "Update SmartOLT Sent Request";

	@PermissionAnnotation(name = "Delete SmartOLT Sent Request", description = "Has permission to delete SmartOLT Sent Request")
	public static final String PERM_DELETE_SMARTOLT_SENT_REQUEST = "Delete SmartOLT Sent Request";

	@PermissionAnnotation(name = "View ClickUp Sent Request", description = "Has permission to view ClickUp Sent Request")
	public static final String PERM_VIEW_CLICKUP_SENT_REQUEST = "View ClickUp Sent Request";

	@PermissionAnnotation(name = "Add ClickUp Sent Request", description = "Has permission to add ClickUp Sent Request")
	public static final String PERM_ADD_CLICKUP_SENT_REQUEST = "Add ClickUp Sent Request";

	@PermissionAnnotation(name = "Update ClickUp Sent Request", description = "Has permission to update ClickUp Sent Request")
	public static final String PERM_UPDATE_CLICKUP_SENT_REQUEST = "Update ClickUp Sent Request";

	@PermissionAnnotation(name = "Delete ClickUp Sent Request", description = "Has permission to delete ClickUp Sent Request")
	public static final String PERM_DELETE_CLICKUP_SENT_REQUEST = "Delete ClickUp Sent Request";

	@PermissionAnnotation(name = "View ISP Api Request", description = "Has permission to view ISP Api Request")
	public static final String PERM_VIEW_ISP_API_REQUEST = "View ISP Api Request";

	@PermissionAnnotation(name = "Add ISP Api Request", description = "Has permission to add ISP Api Request")
	public static final String PERM_ADD_ISP_API_REQUEST = "Add ISP Api Request";

	@PermissionAnnotation(name = "Update ISP Api Request", description = "Has permission to update ISP Api Request")
	public static final String PERM_UPDATE_ISP_API_REQUEST = "Update ISP Api Request";

	@PermissionAnnotation(name = "Delete ISP Api Request", description = "Has permission to delete ISP Api Request")
	public static final String PERM_DELETE_ISP_API_REQUEST = "Delete ISP Api Request";

	@PermissionAnnotation(name = "View Access Key", description = "Has permission to view access key")
	public static final String PERM_VIEW_ACCESS_KEY = "View Access Key";

	@PermissionAnnotation(name = "Add Access Key", description = "Has permission to add access key")
	public static final String PERM_ADD_ACCESS_KEY = "Add Access Key";

	@PermissionAnnotation(name = "Update Access Key", description = "Has permission to update access key")
	public static final String PERM_UPDATE_ACCESS_KEY = "Update Access Key";

	@PermissionAnnotation(name = "Delete Access Key", description = "Has permission to delete access key")
	public static final String PERM_DELETE_ACCESS_KEY = "Delete Access Key";

	@PermissionAnnotation(name = "View ISP User", description = "Has permission to view ISP user")
	public static final String PERM_VIEW_ISP_USER = "View ISP User";

	@PermissionAnnotation(name = "Add ISP User", description = "Has permission to add ISP user")
	public static final String PERM_ADD_ISP_USER = "Add ISP User";

	@PermissionAnnotation(name = "Update ISP User", description = "Has permission to update ISP user")
	public static final String PERM_UPDATE_ISP_USER = "Update ISP User";

	@PermissionAnnotation(name = "Delete ISP User", description = "Has permission to delete ISP user")
	public static final String PERM_DELETE_ISP_USER = "Delete ISP User";

	@PermissionAnnotation(name = "View Permission", description = "Has permission to view permission")
	public static final String PERM_VIEW_PERMISSION = "View Permission";

	@PermissionAnnotation(name = "Add Permission", description = "Has permission to add permission")
	public static final String PERM_ADD_PERMISSION = "Add Permission";

	@PermissionAnnotation(name = "Update Permission", description = "Has permission to update permission")
	public static final String PERM_UPDATE_PERMISSION = "Update Permission";

	@PermissionAnnotation(name = "Delete Permission", description = "Has permission to delete permission")
	public static final String PERM_DELETE_PERMISSION = "Delete Permission";

	@PermissionAnnotation(name = "View User", description = "Has permission to view user")
	public static final String PERM_VIEW_USER = "View User";

	@PermissionAnnotation(name = "Add User", description = "Has permission to add user")
	public static final String PERM_ADD_USER = "Add User";

	@PermissionAnnotation(name = "Update User", description = "Has permission to update user")
	public static final String PERM_UPDATE_USER = "Update User";

	@PermissionAnnotation(name = "Delete User", description = "Has permission to delete user")
	public static final String PERM_DELETE_USER = "Delete User";

	@PermissionAnnotation(name = "View History Item", description = "Has permission to view history item")
	public static final String PERM_VIEW_HISTORY_ITEM = "View History Item";

	@PermissionAnnotation(name = "Add History Item", description = "Has permission to add history item")
	public static final String PERM_ADD_HISTORY_ITEM = "Add History Item";

	@PermissionAnnotation(name = "Update History Item", description = "Has permission to update history item")
	public static final String PERM_UPDATE_HISTORY_ITEM = "Update History Item";

	@PermissionAnnotation(name = "Delete History Item", description = "Has permission to delete history item")
	public static final String PERM_DELETE_HISTORY_ITEM = "Delete History Item";

	@PermissionAnnotation(name = "View User Profile", description = "Has permission to view user profile")
	public static final String PERM_VIEW_USER_PROFILE = "View User Profile";

	@PermissionAnnotation(name = "Add User Profile", description = "Has permission to add user profile")
	public static final String PERM_ADD_USER_PROFILE = "Add User Profile";

	@PermissionAnnotation(name = "Update User Profile", description = "Has permission to update user profile")
	public static final String PERM_UPDATE_USER_PROFILE = "Update User Profile";

	@PermissionAnnotation(name = "Delete User Profile", description = "Has permission to delete user profile")
	public static final String PERM_DELETE_USER_PROFILE = "Delete User Profile";
	
	@PermissionAnnotation(name = "Assign Role", description = "Has permission to assign rol")
	public static final String PERM_ASSIGN_ROLE = "Assign Role";

	@PermissionAnnotation(name = "View Currency", description = "Has permission to view currency")
	public static final String PERM_VIEW_CURRENCY = "View Currency";

	@PermissionAnnotation(name = "Add Currency", description = "Has permission to add currency")
	public static final String PERM_ADD_CURRENCY = "Add Currency";

	@PermissionAnnotation(name = "Update Currency", description = "Has permission to update currency")
	public static final String PERM_UPDATE_CURRENCY = "Update Currency";

	@PermissionAnnotation(name = "Delete Currency", description = "Has permission to delete currency")
	public static final String PERM_DELETE_CURRENCY = "Delete Currency";

	@PermissionAnnotation(name = "View ISP", description = "Has permission to view ISP")
	public static final String PERM_VIEW_ISP = "View ISP";

	@PermissionAnnotation(name = "Add ISP", description = "Has permission to add ISP")
	public static final String PERM_ADD_ISP = "Add ISP";

	@PermissionAnnotation(name = "Update ISP", description = "Has permission to update ISP")
	public static final String PERM_UPDATE_ISP = "Update ISP";

	@PermissionAnnotation(name = "Delete ISP", description = "Has permission to delete ISP")
	public static final String PERM_DELETE_ISP = "Delete ISP";

	@PermissionAnnotation(name = "View NRC", description = "Has permission to view NRC")
	public static final String PERM_VIEW_NRC = "View NRC";

	@PermissionAnnotation(name = "Add NRC", description = "Has permission to add NRC")
	public static final String PERM_ADD_NRC = "Add NRC";

	@PermissionAnnotation(name = "Update NRC", description = "Has permission to update NRC")
	public static final String PERM_UPDATE_NRC = "Update NRC";

	@PermissionAnnotation(name = "Delete NRC", description = "Has permission to delete NRC")
	public static final String PERM_DELETE_NRC = "Delete NRC";

	@PermissionAnnotation(name = "View Discount", description = "Has permission to view Discount")
	public static final String PERM_VIEW_DISCOUNT = "View Discount";

	@PermissionAnnotation(name = "Add Discount", description = "Has permission to add Discount")
	public static final String PERM_ADD_DISCOUNT = "Add Discount";

	@PermissionAnnotation(name = "Update Discount", description = "Has permission to update Discount")
	public static final String PERM_UPDATE_DISCOUNT = "Update ISP";

	@PermissionAnnotation(name = "Delete Discount", description = "Has permission to delete Discount")
	public static final String PERM_DELETE_DISCOUNT = "Delete Discount";

	@PermissionAnnotation(name = "View Data Package", description = "Has permission to view data package")
	public static final String PERM_VIEW_DATA_PACKAGE = "View Data Package";

	@PermissionAnnotation(name = "Add Data Package", description = "Has permission to add data package")
	public static final String PERM_ADD_DATA_PACKAGE = "Add Data Package";

	@PermissionAnnotation(name = "Update Data Package", description = "Has permission to update data package")
	public static final String PERM_UPDATE_DATA_PACKAGE = "Update Data Package";

	@PermissionAnnotation(name = "Delete Data Package", description = "Has permission to delete data package")
	public static final String PERM_DELETE_DATA_PACKAGE = "Delete Data Package";

	@PermissionAnnotation(name = "View OLT", description = "Has permission to view OLT")
	public static final String PERM_VIEW_OLT = "View OLT";

	@PermissionAnnotation(name = "Add OLT", description = "Has permission to add OLT")
	public static final String PERM_ADD_OLT = "Add OLT";

	@PermissionAnnotation(name = "Update OLT", description = "Has permission to update OLT")
	public static final String PERM_UPDATE_OLT = "Update OLT";

	@PermissionAnnotation(name = "Delete OLT", description = "Has permission to delete OLT")
	public static final String PERM_DELETE_OLT = "Delete OLT";

	@PermissionAnnotation(name = "View ODB", description = "Has permission to view ODB")
	public static final String PERM_VIEW_ODB = "View ODB";

	@PermissionAnnotation(name = "Add ODB", description = "Has permission to add ODB")
	public static final String PERM_ADD_ODB = "Add ODB";

	@PermissionAnnotation(name = "Update ODB", description = "Has permission to update ODB")
	public static final String PERM_UPDATE_ODB = "Update ODB";

	@PermissionAnnotation(name = "Delete ODB", description = "Has permission to delete ODB")
	public static final String PERM_DELETE_ODB = "Delete ODB";

	@PermissionAnnotation(name = "View ServiceZone", description = "Has permission to view region")
	public static final String PERM_VIEW_REGION = "View ServiceZone";

	@PermissionAnnotation(name = "Add ServiceZone", description = "Has permission to add region")
	public static final String PERM_ADD_REGION = "Add ServiceZone";

	@PermissionAnnotation(name = "Update ServiceZone", description = "Has permission to update region")
	public static final String PERM_UPDATE_REGION = "Update ServiceZone";

	@PermissionAnnotation(name = "Delete ServiceZone", description = "Has permission to delete region")
	public static final String PERM_DELETE_REGION = "Delete ServiceZone";

	@PermissionAnnotation(name = "View Client", description = "Has permission to view client")
	public static final String PERM_VIEW_CLIENT = "View Client";

	@PermissionAnnotation(name = "View ISP Client", description = "Has permission to view ISP client")
	public static final String PERM_VIEW_ISP_CLIENT = "View ISP Client";

	@PermissionAnnotation(name = "Add Client", description = "Has permission to add client")
	public static final String PERM_ADD_CLIENT = "Add Client";

	@PermissionAnnotation(name = "Update Client", description = "Has permission to update client")
	public static final String PERM_UPDATE_CLIENT = "Update Client";

	@PermissionAnnotation(name = "Update Client Expiry", description = "Has permission to update client expiry")
	public static final String PERM_UPDATE_CLIENT_EXPIRY = "Update Client Expiry";

	@PermissionAnnotation(name = "Delete Client", description = "Has permission to delete client")
	public static final String PERM_DELETE_CLIENT = "Delete Client";

	@PermissionAnnotation(name = "View Client DataPackage", description = "Has permission to view client capacity")
	public static final String PERM_VIEW_CLIENT_PACKAGE = "View Client DataPackage";

	@PermissionAnnotation(name = "Add Client DataPackage", description = "Has permission to add client capacity")
	public static final String PERM_ADD_CLIENT_PACKAGE = "Add Client DataPackage";

	@PermissionAnnotation(name = "Update Client DataPackage", description = "Has permission to update client capacity")
	public static final String PERM_UPDATE_CLIENT_PACKAGE = "Update Client DataPackage";

	@PermissionAnnotation(name = "Delete Client DataPackage", description = "Has permission to delete client capacity")
	public static final String PERM_DELETE_CLIENT_PACKAGE = "Delete Client DataPackage";

	@PermissionAnnotation(name = "View Unit of Measure", description = "Has permission to view unit of measure")
	public static final String PERM_VIEW_UNIT_OF_MEASURE = "View Unit of Measure";

	@PermissionAnnotation(name = "Add Unit of Measure", description = "Has permission to add unit of measure")
	public static final String PERM_ADD_UNIT_OF_MEASURE = "Add Unit of Measure";

	@PermissionAnnotation(name = "Update Unit of Measure", description = "Has permission to update unit of measure")
	public static final String PERM_UPDATE_UNIT_OF_MEASURE = "Update Unit of Measure";

	@PermissionAnnotation(name = "Delete Unit of Measure", description = "Has permission to delete unit of measure")
	public static final String PERM_DELETE_UNIT_OF_MEASURE = "Delete Unit of Measure";

	@PermissionAnnotation(name = "View Log", description = "Has permission to view log")
	public static final String PERM_VIEW_LOG = "View Log";

	@PermissionAnnotation(name = "Add Log", description = "Has permission to add log")
	public static final String PERM_ADD_LOG = "Add Log";

	@PermissionAnnotation(name = "Update Log", description = "Has permission to update log")
	public static final String PERM_UPDATE_LOG = "Update Log";

	@PermissionAnnotation(name = "Delete Log", description = "Has permission to delete log")
	public static final String PERM_DELETE_LOG = "Delete Log";

	@PermissionAnnotation(name = "View Currency Conversion", description = "Has permission to view currency conversion")
	public static final String PERM_VIEW_CURRENCY_CONVERSION = "View Currency Conversion";

	@PermissionAnnotation(name = "Add Currency Conversion", description = "Has permission to add currency conversion")
	public static final String PERM_ADD_CURRENCY_CONVERSION = "Add Currency Conversion";

	@PermissionAnnotation(name = "Update Currency Conversion", description = "Has permission to update currency conversion")
	public static final String PERM_UPDATE_CURRENCY_CONVERSION = "Update Currency Conversion";

	@PermissionAnnotation(name = "Delete Currency Conversion", description = "Has permission to delete currency conversion")
	public static final String PERM_DELETE_CURRENCY_CONVERSION = "Delete Currency Conversion";

	@PermissionAnnotation(name = "View Number of Clients", description = "Has permission to view number of clients")
	public static final String PERM_VIEW_NUMBER_OF_CLIENTS = "View Number of Clients";

	@PermissionAnnotation(name = "Add Number of Clients", description = "Has permission to add number of clients")
	public static final String PERM_ADD_NUMBER_OF_CLIENTS = "Add Number of Clients";

	@PermissionAnnotation(name = "Update Number of Clients", description = "Has permission to update number of clients")
	public static final String PERM_UPDATE_NUMBER_OF_CLIENTS = "Update Number of Clients";

	@PermissionAnnotation(name = "Delete Number of Clients", description = "Has permission to delete number of clients")
	public static final String PERM_DELETE_NUMBER_OF_CLIENTS = "Delete Number of Clients";

	@PermissionAnnotation(name = "View ISPBill", description = "Has permission to view bill")
	public static final String PERM_VIEW_ISP_BILL = "View ISPBill";

	@PermissionAnnotation(name = "Add ISPBill", description = "Has permission to add bill")
	public static final String PERM_ADD_ISP_BILL = "Add ISPBill";

	@PermissionAnnotation(name = "Update ISPBill", description = "Has permission to update bill")
	public static final String PERM_UPDATE_ISP_BILL = "Update ISPBill";

	@PermissionAnnotation(name = "Delete ISPBill", description = "Has permission to delete bill")
	public static final String PERM_DELETE_ISP_BILL = "Delete ISPBill";

	@PermissionAnnotation(name = "View ISPBillSummary", description = "Has permission to view bill summary")
	public static final String PERM_VIEW_ISP_BILL_SUMMARY = "View ISPBillSummary";

	@PermissionAnnotation(name = "Add ISPBillSummary", description = "Has permission to add bill summary")
	public static final String PERM_ADD_ISP_BILL_SUMMARY = "Add ISPBillSummary";

	@PermissionAnnotation(name = "Update ISPBillSummary", description = "Has permission to update bill summary")
	public static final String PERM_UPDATE_ISP_BILL_SUMMARY = "Update ISPBillSummary";

	@PermissionAnnotation(name = "Delete ISPBillSummary", description = "Has permission to delete bill summary")
	public static final String PERM_DELETE_ISP_BILL_SUMMARY = "Delete ISPBillSummary";

	@PermissionAnnotation(name = "View Client Bill", description = "Has permission to view bill")
	public static final String PERM_VIEW_CLIENT_BILL = "View Client Bill";

	@PermissionAnnotation(name = "Add Client Bill", description = "Has permission to add bill")
	public static final String PERM_ADD_CLIENT_BILL = "Add Client Bill";

	@PermissionAnnotation(name = "Update Client Bill", description = "Has permission to update bill")
	public static final String PERM_UPDATE_CLIENT_BILL = "Update Client Bill";

	@PermissionAnnotation(name = "Delete Client Bill", description = "Has permission to delete bill")
	public static final String PERM_DELETE_CLIENT_BILL = "Delete Client Bill";

	@PermissionAnnotation(name = "View Application Setting", description = "Has permission to view Application Setting")
	public static final String PERM_VIEW_APPLICATION_SETTING = "View Application Setting";

	@PermissionAnnotation(name = "Add Application Setting", description = "Has permission to add Application Setting")
	public static final String PERM_ADD_APPLICATION_SETTING = "Add Application Setting";

	@PermissionAnnotation(name = "Update Application Setting", description = "Has permission to update Application Setting")
	public static final String PERM_UPDATE_APPLICATION_SETTING = "Update Application Setting";

	@PermissionAnnotation(name = "Delete Application Setting", description = "Has permission to delete Application Setting")
	public static final String PERM_DELETE_APPLICATION_SETTING = "Delete Application Setting";

	@PermissionAnnotation(name = "View Response", description = "Has permission to view Response")
	public static final String PERM_VIEW_RESPONSE = "View Response";

	@PermissionAnnotation(name = "Add Response", description = "Has permission to add Response")
	public static final String PERM_ADD_RESPONSE = "Add Response";

	@PermissionAnnotation(name = "Update Response", description = "Has permission to update Response")
	public static final String PERM_UPDATE_RESPONSE = "Update Response";

	@PermissionAnnotation(name = "Delete Response", description = "Has permission to delete Response")
	public static final String PERM_DELETE_RESPONSE = "Delete Response";

	@PermissionAnnotation(name = "View Request", description = "Has permission to view Request")
	public static final String PERM_VIEW_REQUEST = "View Request";

	@PermissionAnnotation(name = "Add Request", description = "Has permission to add Request")
	public static final String PERM_ADD_REQUEST = "Add Request";

	@PermissionAnnotation(name = "Update Request", description = "Has permission to update Request")
	public static final String PERM_UPDATE_REQUEST = "Update Request";

	@PermissionAnnotation(name = "Delete Request", description = "Has permission to delete Request")
	public static final String PERM_DELETE_REQUEST = "Delete Request";

}
