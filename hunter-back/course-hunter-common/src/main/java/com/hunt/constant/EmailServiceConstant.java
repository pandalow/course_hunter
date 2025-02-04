package com.hunt.constant;

public class EmailServiceConstant {
    public static final Long VERIFICATION_TOKEN_DURATION = 3 * 24 * 60 * 60 * 1000L; //3 days
    public static final String VERIFICATION_ADDRESS = "verify@courseadvisor.work.gd";
    public static final String VERIFICATION_SUBJECT = "Confirm your email";
    public static final String VERIFICATION_TEMPLATE = "<div style=\"font-family:Arial, sans-serif; font-size:16px; color:#333333;\">\n" +
            "    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%%\" style=\"border-collapse: collapse;\">\n" +
            "        <tr>\n" +
            "            <td bgcolor=\"#F2F2F2\" style=\"padding: 20px 0;\">\n" +
            "                <table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" width=\"600\" style=\"border-collapse: collapse; background-color: #FFFFFF; border-radius: 5px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\">\n" +
            "                    <tr>\n" +
            "                        <td align=\"left\" style=\"padding: 40px;\">\n" +
            "                            <h1 style=\"color: #333333; font-size: 24px; margin-bottom: 20px;\">Welcome to Our Platform</h1>\n" +
            "                            <p style=\"font-size: 16px; line-height: 24px; margin-bottom: 20px;\">Hi,</p>\n" +
            "                            <p style=\"font-size: 16px; line-height: 24px; margin-bottom: 20px;\">Thank you for registering. To get started, please activate your account by clicking the button below:</p>\n" +
            "                            <a href=\"%s\" style=\"display: inline-block; padding: 12px 24px; background-color: #1E90FF; color: #FFFFFF; text-decoration: none; border-radius: 5px; font-size: 16px; font-weight: bold;\">Activate Account</a>\n" +
            "                            <p style=\"font-size: 16px; line-height: 24px; margin-top: 20px;\">This link will expire in 3 days. If you didn't register, you can safely ignore this email.</p>\n" +
            "                            <p style=\"font-size: 16px; line-height: 24px;\">Best regards,<br> Your Team</p>\n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "                </table>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "    </table>\n" +
            "</div>";
}
