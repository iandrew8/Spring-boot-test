package com.springboot.systems.system_wide.models.core.emailtemplate.annotations;

import com.springboot.systems.system_wide.models.enums.TemplateType;

public final class EmailTemplateConstants {

	@EmailTemplateAnnotation(templateType = TemplateType.MONTHLY_BILL_DELIVERY, template = "Hello {isp},<br /> Please receive " +
			"your monthly bill for {dateAddressed}.")
	public static final TemplateType MONTHLY_BILL_DELIVERY_EMAIL_TEMPLATE = TemplateType.MONTHLY_BILL_DELIVERY;

	@EmailTemplateAnnotation(templateType = TemplateType.OUTSTANDING_BILL_REMINDER, template = "Hello {isp},<br />  " +
			"You are reminded to please address the outstanding bill for {dateAddressed}.")
	public static final TemplateType OUTSTANDING_BILL_REMINDER_EMAIL_TEMPLATE = TemplateType.OUTSTANDING_BILL_REMINDER;

	@EmailTemplateAnnotation(templateType = TemplateType.USER_ACCOUNT_CREATION, template = "Hello {user},<br />  " +
			"An account has been created for you on the Billing system. Here are you credentials;<br />" +
			"Username: {username} <br />" +
			"Temporary Password: {password} <br />" +
			"Contact Admin to get access. Please change your password on your first login.")
	public static final TemplateType USER_ACCOUNT_CREATION_EMAIL_TEMPLATE = TemplateType.USER_ACCOUNT_CREATION;
}
