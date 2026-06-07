package com.company.ticket_booking_backend.EmailTemplates;

import com.company.ticket_booking_backend.model.Booking;

import java.time.Year;

/**
 * EmailTemplates — Production-Ready HTML Email Template Suite
 *
 * Design Language : Luxury Refined — deep navy, warm gold, crisp white
 * Compatibility   : Gmail · Outlook 2016+ · Apple Mail · Yahoo Mail · iOS Mail
 * Accessibility   : role="presentation" tables · aria-labels · sufficient contrast ratios
 * Architecture    : Shared header/footer + individual body sections
 */
public class EmailTemplates {

    // ─────────────────────────────────────────────────────────────────────────────
    // SHARED CONSTANTS
    // ─────────────────────────────────────────────────────────────────────────────

    private static final String BRAND_NAME   = "Ticket Booking System";
    private static final String BRAND_URL    = "https://project-qt6jb.vercel.app/";
    private static final String SUPPORT_URL  = "https://project-qt6jb.vercel.app/contact";
    private static final String PRIVACY_URL  = "https://project-qt6jb.vercel.app/privacy";
    private static final String LOGO_URL     = "https://res.cloudinary.com/dnofm4zb9/image/upload/v1777711678/logo_syitom.png";

    // Palette tokens (used inline — kept as constants for maintainability)
    private static final String C_BG         = "#F0F4FA";   // Page background
    private static final String C_CARD       = "#FFFFFF";   // Card surface
    private static final String C_NAVY_DARK  = "#0B1629";   // Footer / darkest navy
    private static final String C_NAVY       = "#132238";   // Header gradient start
    private static final String C_NAVY_MID   = "#1B3A5C";   // Header gradient end
    private static final String C_GOLD       = "#C9922A";   // Primary accent / CTA
    private static final String C_GOLD_LIGHT = "#E8B84B";   // Accent highlight
    private static final String C_TEXT_DARK  = "#1A2B40";   // Body headings
    private static final String C_TEXT_BODY  = "#4A5568";   // Body copy
    private static final String C_TEXT_MUTED = "#8898AA";   // Sub-labels
    private static final String C_BORDER     = "#E2E8F0";   // Dividers
    private static final String C_SUCCESS    = "#1A7A4A";   // Success green
    private static final String C_SUCCESS_BG = "#EBF8F1";   // Success green light bg


    // ─────────────────────────────────────────────────────────────────────────────
    // HEADER
    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * Renders the shared email header with brand logo, name, and tagline.
     * Uses a deep navy-to-midnight gradient with a gold accent bar.
     */
    public static String header() {
        return """
            <!-- ═══ TOP ACCENT BAR ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="height:4px; background:linear-gradient(90deg,%s 0%%,%s 50%%,%s 100%%); font-size:0; line-height:0;">&nbsp;</td>
              </tr>
            </table>

            <!-- ═══ HEADER BANNER ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                   style="background:%s;">
              <tr>
                <td align="center" style="padding:48px 32px 40px 32px;">

                  <!-- Logo container -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                      <td align="center"
                          style="background:rgba(255,255,255,0.08);
                                 border:1.5px solid rgba(201,146,42,0.4);
                                 border-radius:18px;
                                 padding:14px 22px;
                                 margin-bottom:20px;">
                        <img src="%s"
                             alt="%s"
                             width="46"
                             height="46"
                             style="display:block; border:0; outline:none; text-decoration:none;" />
                      </td>
                    </tr>
                  </table>

                  <!-- Spacer -->
                  <div style="height:18px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Brand name -->
                  <div style="
                    font-family:'Georgia',serif;
                    font-size:26px;
                    font-weight:700;
                    letter-spacing:0.5px;
                    color:#FFFFFF;
                    line-height:1.2;
                  ">%s</div>

                  <!-- Spacer -->
                  <div style="height:10px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Gold rule -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                         style="margin:0 auto;">
                    <tr>
                      <td style="width:40px; height:1px; background:%s; font-size:0; line-height:0;">&nbsp;</td>
                      <td style="width:8px;">&nbsp;</td>
                      <td style="width:6px; height:6px; border-radius:50%%; background:%s; font-size:0; line-height:0;">&nbsp;</td>
                      <td style="width:8px;">&nbsp;</td>
                      <td style="width:40px; height:1px; background:%s; font-size:0; line-height:0;">&nbsp;</td>
                    </tr>
                  </table>

                  <!-- Spacer -->
                  <div style="height:14px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Tagline badge -->
                  <div style="
                    display:inline-block;
                    font-family:'Trebuchet MS','Arial',sans-serif;
                    font-size:10px;
                    font-weight:700;
                    letter-spacing:3px;
                    text-transform:uppercase;
                    color:%s;
                    background:rgba(201,146,42,0.12);
                    border:1px solid rgba(201,146,42,0.35);
                    border-radius:20px;
                    padding:6px 18px;
                  ">FAST &nbsp;&bull;&nbsp; SECURE &nbsp;&bull;&nbsp; RELIABLE</div>

                </td>
              </tr>
            </table>

            <!-- ═══ HEADER / CARD BRIDGE ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="height:32px; background:%s; font-size:0; line-height:0;">&nbsp;</td>
              </tr>
            </table>
            """.formatted(
                C_GOLD, C_GOLD_LIGHT, C_GOLD,    // top bar gradient
                C_NAVY,                            // header bg
                LOGO_URL, BRAND_NAME,              // logo
                BRAND_NAME,                        // brand name text
                C_GOLD, C_GOLD_LIGHT, C_GOLD,     // gold rule pieces
                C_GOLD_LIGHT,                      // tagline text color
                C_BG                               // bridge bg
        );
    }


    // ─────────────────────────────────────────────────────────────────────────────
    // FOOTER
    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * Renders the shared email footer with brand identity, navigation links,
     * trust stats, and legal / copyright notice.
     */
    public static String footer() {
        return """
            <!-- ═══ CARD / FOOTER BRIDGE ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="height:32px; background:%s; font-size:0; line-height:0;">&nbsp;</td>
              </tr>
            </table>

            <!-- ═══ FOOTER ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                   style="background:%s;">
              <tr>
                <td align="center" style="padding:44px 32px 36px 32px;">

                  <!-- ── Brand lockup ── -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                      <td align="center"
                          style="background:linear-gradient(135deg,%s,%s);
                                 border-radius:16px;
                                 padding:12px 18px;">
                        <img src="%s"
                             alt="%s logo"
                             width="36" height="36"
                             style="display:block; border:0; outline:none; text-decoration:none;" />
                      </td>
                    </tr>
                  </table>

                  <div style="height:14px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Georgia',serif;
                    font-size:19px;
                    font-weight:700;
                    color:#FFFFFF;
                    letter-spacing:0.3px;
                  ">%s</div>

                  <div style="height:6px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Trebuchet MS','Arial',sans-serif;
                    font-size:10px;
                    letter-spacing:3px;
                    text-transform:uppercase;
                    color:rgba(255,255,255,0.30);
                  ">FAST &nbsp;&bull;&nbsp; SECURE &nbsp;&bull;&nbsp; RELIABLE</div>

                  <!-- Gold ornament rule -->
                  <div style="height:24px; font-size:0; line-height:0;">&nbsp;</div>
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                         style="margin:0 auto;">
                    <tr>
                      <td style="width:60px; height:1px; background:rgba(201,146,42,0.4); font-size:0;">&nbsp;</td>
                      <td style="width:6px;">&nbsp;</td>
                      <td style="width:5px; height:5px; border-radius:50%%; background:%s; font-size:0;">&nbsp;</td>
                      <td style="width:6px;">&nbsp;</td>
                      <td style="width:60px; height:1px; background:rgba(201,146,42,0.4); font-size:0;">&nbsp;</td>
                    </tr>
                  </table>
                  <div style="height:24px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- ── Navigation links ── -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                      <td style="padding:0 5px;">
                        <a href="%s"
                           style="font-family:'Arial',sans-serif;
                                  font-size:11px; font-weight:600; letter-spacing:0.5px;
                                  color:%s; text-decoration:none;
                                  border:1px solid rgba(201,146,42,0.30);
                                  border-radius:20px; padding:7px 16px;
                                  background:rgba(201,146,42,0.08);
                                  display:inline-block;">&#127760;&nbsp; Website</a>
                      </td>
                      <td style="padding:0 5px;">
                        <a href="%s"
                           style="font-family:'Arial',sans-serif;
                                  font-size:11px; font-weight:600; letter-spacing:0.5px;
                                  color:%s; text-decoration:none;
                                  border:1px solid rgba(201,146,42,0.30);
                                  border-radius:20px; padding:7px 16px;
                                  background:rgba(201,146,42,0.08);
                                  display:inline-block;">&#128172;&nbsp; Support</a>
                      </td>
                      <td style="padding:0 5px;">
                        <a href="%s"
                           style="font-family:'Arial',sans-serif;
                                  font-size:11px; font-weight:600; letter-spacing:0.5px;
                                  color:%s; text-decoration:none;
                                  border:1px solid rgba(201,146,42,0.30);
                                  border-radius:20px; padding:7px 16px;
                                  background:rgba(201,146,42,0.08);
                                  display:inline-block;">&#128274;&nbsp; Privacy</a>
                      </td>
                    </tr>
                  </table>

                  <div style="height:28px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- ── Trust stats ── -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                         style="background:rgba(255,255,255,0.04);
                                border:1px solid rgba(255,255,255,0.09);
                                border-radius:14px;">
                    <tr>
                      <td align="center"
                          style="padding:18px 32px;
                                 border-right:1px solid rgba(255,255,255,0.09);">
                        <div style="font-family:'Georgia',serif; font-size:18px;
                                    font-weight:700; color:#FFFFFF;">100%%</div>
                        <div style="font-family:'Arial',sans-serif; font-size:9px;
                                    letter-spacing:1.5px; text-transform:uppercase;
                                    color:rgba(255,255,255,0.32); margin-top:4px;">Secure</div>
                      </td>
                      <td align="center"
                          style="padding:18px 32px;
                                 border-right:1px solid rgba(255,255,255,0.09);">
                        <div style="font-family:'Georgia',serif; font-size:18px;
                                    font-weight:700; color:#FFFFFF;">24/7</div>
                        <div style="font-family:'Arial',sans-serif; font-size:9px;
                                    letter-spacing:1.5px; text-transform:uppercase;
                                    color:rgba(255,255,255,0.32); margin-top:4px;">Support</div>
                      </td>
                      <td align="center" style="padding:18px 32px;">
                        <div style="font-family:'Georgia',serif; font-size:18px;
                                    font-weight:700; color:#FFFFFF;">&#127470;&#127472; LK</div>
                        <div style="font-family:'Arial',sans-serif; font-size:9px;
                                    letter-spacing:1.5px; text-transform:uppercase;
                                    color:rgba(255,255,255,0.32); margin-top:4px;">Sri Lanka</div>
                      </td>
                    </tr>
                  </table>

                  <div style="height:28px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- ── Hairline divider ── -->
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                      <td style="height:1px; background:rgba(255,255,255,0.07); font-size:0;">&nbsp;</td>
                    </tr>
                  </table>

                  <div style="height:20px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- ── Copyright ── -->
                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:11px;
                    color:rgba(255,255,255,0.25);
                    line-height:1.6;
                  ">&copy; %s %s. All rights reserved.</div>

                  <div style="height:6px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:10px;
                    color:rgba(255,255,255,0.15);
                    font-style:italic;
                  ">This is an automated email &mdash; please do not reply directly to this message.</div>

                </td>
              </tr>
            </table>

            <!-- ═══ BOTTOM ACCENT BAR ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="height:4px; background:linear-gradient(90deg,%s 0%%,%s 50%%,%s 100%%); font-size:0; line-height:0;">&nbsp;</td>
              </tr>
            </table>
            """.formatted(
                C_BG,                                           // bridge bg
                C_NAVY_DARK,                                    // footer bg
                C_NAVY, C_NAVY_MID,                            // logo container gradient
                LOGO_URL, BRAND_NAME,                           // logo
                BRAND_NAME,                                     // brand name text
                C_GOLD_LIGHT,                                   // ornament dot
                BRAND_URL, C_GOLD_LIGHT,                        // website link
                SUPPORT_URL, C_GOLD_LIGHT,                      // support link
                PRIVACY_URL, C_GOLD_LIGHT,                      // privacy link
                Year.now().getValue(), BRAND_NAME,              // copyright
                C_GOLD, C_GOLD_LIGHT, C_GOLD                   // bottom bar gradient
        );
    }


    // ─────────────────────────────────────────────────────────────────────────────
    // PRIVATE HELPERS
    // ─────────────────────────────────────────────────────────────────────────────

    /** Wraps a body section in the full email shell (doctype, head, outer table). */
    private static String wrapEmail(String preheader, String bodyContent) {
        return """
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
              "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
            <html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
            <head>
              <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
              <meta name="viewport" content="width=device-width, initial-scale=1.0" />
              <meta name="x-apple-disable-message-reformatting" />
              <!--[if !mso]><!-->
              <meta http-equiv="X-UA-Compatible" content="IE=edge" />
              <!--<![endif]-->
              <title>%s — %s</title>
              <!--[if mso]>
              <style type="text/css">
                body, table, td { font-family: Arial, Helvetica, sans-serif !important; }
              </style>
              <![endif]-->
            </head>
            <body style="margin:0; padding:0; background-color:%s;
                         -webkit-text-size-adjust:100%%; -ms-text-size-adjust:100%%;"
                  bgcolor="%s">

              <!-- Preheader (hidden preview text) -->
              <div style="display:none; max-height:0; overflow:hidden; mso-hide:all;
                          font-size:1px; color:%s; line-height:1px;">%s</div>

              <!-- ═══ OUTER WRAPPER ═══ -->
              <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                     style="background-color:%s;" bgcolor="%s">
                <tr>
                  <td align="center" style="padding:32px 16px 40px 16px;">

                    <!-- ═══ INNER CARD (max 600px) ═══ -->
                    <table role="presentation" width="600" cellpadding="0" cellspacing="0" border="0"
                           style="max-width:600px; width:100%%;
                                  background:%s;
                                  border-radius:16px;
                                  overflow:hidden;
                                  box-shadow:0 8px 40px rgba(11,22,41,0.14);"
                           bgcolor="%s">
                      <tr>
                        <td>
                          %s
                          %s
                          %s
                        </td>
                      </tr>
                    </table>
                    <!-- /INNER CARD -->

                  </td>
                </tr>
              </table>
              <!-- /OUTER WRAPPER -->

            </body>
            </html>
            """.formatted(
                preheader, BRAND_NAME,                          // <title>
                C_BG, C_BG,                                    // body bg
                C_BG, preheader,                               // hidden preheader div
                C_BG, C_BG,                                    // outer table bg
                C_CARD, C_CARD,                                // inner card bg
                header(), bodyContent, footer()
        );
    }

    /**
     * Renders a single horizontal info row used inside booking detail tables.
     *
     * @param label  Left-side label text
     * @param value  Right-side value text
     * @param isLast Whether this is the last row (omit bottom border)
     */
    private static String infoRow(String label, String value, boolean isLast) {
        String borderStyle = isLast ? "none" : "1px solid " + C_BORDER;
        return """
            <tr>
              <td style="
                font-family:'Arial',sans-serif;
                font-size:13px;
                color:%s;
                font-weight:600;
                padding:13px 18px;
                border-bottom:%s;
                white-space:nowrap;
                width:38%%;
              ">%s</td>
              <td style="
                font-family:'Arial',sans-serif;
                font-size:13px;
                color:%s;
                font-weight:700;
                padding:13px 18px;
                border-bottom:%s;
              ">%s</td>
            </tr>
            """.formatted(C_TEXT_MUTED, borderStyle, label,
                C_TEXT_DARK, borderStyle, value);
    }

    /**
     * Renders a primary CTA button, email-client safe (table-based).
     *
     * @param href       Destination URL
     * @param label      Button label text
     * @param bgColor    Background color hex
     * @param textColor  Text color hex
     */
    private static String ctaButton(String href, String label, String bgColor, String textColor) {
        return """
            <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                   style="margin:0 auto;">
              <tr>
                <td align="center"
                    style="background:%s;
                           border-radius:50px;
                           box-shadow:0 6px 24px rgba(201,146,42,0.35);">
                  <a href="%s"
                     target="_blank"
                     style="display:inline-block;
                            font-family:'Georgia',serif;
                            font-size:15px;
                            font-weight:700;
                            letter-spacing:0.5px;
                            color:%s;
                            text-decoration:none;
                            padding:14px 36px;">%s</a>
                </td>
              </tr>
            </table>
            """.formatted(bgColor, href, textColor, label);
    }


    // ─────────────────────────────────────────────────────────────────────────────
    // WELCOME EMAIL
    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * Sends a personalised welcome + email-verification email.
     *
     * @param name Recipient's display name
     * @param url  Email-verification URL
     * @return Complete HTML email string
     */
    public static String welcomeEmail(String name, String url) {

        String body = """
            <!-- ═══ WELCOME BODY ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="padding:44px 48px 12px 48px; text-align:center;">

                  <!-- Icon medallion -->
                  <div style="
                    display:inline-block;
                    background:linear-gradient(135deg,%s,%s);
                    border-radius:50%%;
                    width:70px; height:70px;
                    line-height:70px;
                    font-size:32px;
                    text-align:center;
                    box-shadow:0 8px 28px rgba(201,146,42,0.30);
                  ">&#127881;</div>

                  <div style="height:24px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Heading -->
                  <div style="
                    font-family:'Georgia',serif;
                    font-size:28px;
                    font-weight:700;
                    color:%s;
                    line-height:1.25;
                    letter-spacing:0.3px;
                  ">Welcome aboard, %s!</div>

                  <div style="height:16px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Gold ornament -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                         style="margin:0 auto;">
                    <tr>
                      <td style="width:30px; height:1px; background:%s; font-size:0;">&nbsp;</td>
                      <td style="width:6px;">&nbsp;</td>
                      <td style="width:5px; height:5px; border-radius:50%%; background:%s; font-size:0;">&nbsp;</td>
                      <td style="width:6px;">&nbsp;</td>
                      <td style="width:30px; height:1px; background:%s; font-size:0;">&nbsp;</td>
                    </tr>
                  </table>

                  <div style="height:20px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Body copy -->
                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:15px;
                    line-height:1.75;
                    color:%s;
                    max-width:420px;
                    margin:0 auto;
                  ">
                    Thank you for joining <strong style="color:%s;">%s</strong>.<br />
                    We are delighted to have you with us.<br />
                    To get started, please verify your email address below.
                  </div>

                  <div style="height:32px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- CTA Button -->
                  %s

                  <div style="height:20px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Fallback link notice -->
                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:11px;
                    color:%s;
                    line-height:1.6;
                  ">
                    Button not working?
                    <a href="%s"
                       style="color:%s; text-decoration:underline;">Copy and paste this link</a>
                    into your browser.
                  </div>

                  <div style="height:32px; font-size:0; line-height:0;">&nbsp;</div>

                </td>
              </tr>
            </table>

            <!-- ═══ INFO CARD ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="padding:0 48px 40px 48px;">
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                         style="background:%s;
                                border:1px solid %s;
                                border-radius:12px;
                                overflow:hidden;">
                    <tr>
                      <td style="padding:16px 22px;">
                        <div style="
                          font-family:'Arial',sans-serif;
                          font-size:11px;
                          font-weight:700;
                          letter-spacing:2px;
                          text-transform:uppercase;
                          color:%s;
                          margin-bottom:8px;
                        ">&#128274;&nbsp; Verification Link Expires In</div>
                        <div style="
                          font-family:'Georgia',serif;
                          font-size:22px;
                          font-weight:700;
                          color:%s;
                        ">24 Hours</div>
                        <div style="
                          font-family:'Arial',sans-serif;
                          font-size:12px;
                          color:%s;
                          margin-top:4px;
                        ">After expiry, request a new verification link from your account settings.</div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            """.formatted(
                C_GOLD, C_GOLD_LIGHT,                           // icon bg gradient
                C_TEXT_DARK,                                    // heading color
                name,                                           // greeting name
                C_GOLD, C_GOLD_LIGHT, C_GOLD,                  // ornament rule
                C_TEXT_BODY,                                    // body copy color
                C_NAVY, BRAND_NAME,                            // bold brand in copy
                ctaButton(url, "&#10003; &nbsp; Verify My Email", C_GOLD, C_NAVY_DARK),
                C_TEXT_MUTED,                                   // fallback notice
                url, C_GOLD,                                    // fallback link
                C_SUCCESS_BG, C_SUCCESS,                        // info card bg / border
                C_SUCCESS,                                      // info card label
                C_TEXT_DARK,                                    // info card value
                C_TEXT_MUTED                                    // info card sub-label
        );

        return wrapEmail("Welcome to " + BRAND_NAME + " — Please verify your email address.", body);
    }


    // ─────────────────────────────────────────────────────────────────────────────
    // OTP EMAIL
    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * Sends a one-time password (OTP) for email verification or 2FA.
     *
     * @param name Recipient's display name
     * @param otp  6-digit OTP string
     * @return Complete HTML email string
     */
    public static String otpEmail(String name, String otp) {

        // Split OTP into individual digit cells for the stylised display
        String[] digits = otp.split("");
        StringBuilder digitCells = new StringBuilder();
        for (String d : digits) {
            digitCells.append("""
                <td style="
                  width:44px; height:54px;
                  background:%s;
                  border:2px solid %s;
                  border-radius:10px;
                  text-align:center;
                  vertical-align:middle;
                  padding:0 6px;
                  font-family:'Georgia',serif;
                  font-size:26px;
                  font-weight:700;
                  color:%s;
                  letter-spacing:0;
                ">%s</td>
                <td style="width:8px;">&nbsp;</td>
                """.formatted(C_NAVY_DARK, C_GOLD, C_GOLD_LIGHT, d));
        }

        String body = """
            <!-- ═══ OTP BODY ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="padding:44px 48px 12px 48px; text-align:center;">

                  <!-- Icon medallion -->
                  <div style="
                    display:inline-block;
                    background:linear-gradient(135deg,%s,%s);
                    border-radius:50%%;
                    width:70px; height:70px;
                    line-height:70px;
                    font-size:32px;
                    box-shadow:0 8px 28px rgba(201,146,42,0.30);
                  ">&#128272;</div>

                  <div style="height:24px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Georgia',serif;
                    font-size:28px;
                    font-weight:700;
                    color:%s;
                    letter-spacing:0.3px;
                  ">Your Verification Code</div>

                  <div style="height:10px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:15px;
                    line-height:1.7;
                    color:%s;
                  ">Hi <strong style="color:%s;">%s</strong>, use the one-time code below<br />
                  to verify your identity. It expires in <strong>5&nbsp;minutes</strong>.</div>

                  <div style="height:32px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- OTP digit display -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                         style="margin:0 auto;
                                background:%s;
                                border:1px solid rgba(201,146,42,0.25);
                                border-radius:16px;
                                padding:22px 28px;">
                    <tr>
                      %s
                    </tr>
                  </table>

                  <div style="height:28px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Warning strip -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                         style="margin:0 auto; max-width:400px; width:100%%;">
                    <tr>
                      <td style="
                        background:rgba(201,146,42,0.08);
                        border:1px solid rgba(201,146,42,0.25);
                        border-radius:10px;
                        padding:14px 20px;
                        font-family:'Arial',sans-serif;
                        font-size:12px;
                        line-height:1.6;
                        color:%s;
                        text-align:center;
                      ">
                        &#9888;&nbsp; <strong>Never share this code</strong> with anyone.
                        %s will never ask for it via phone or chat.
                        If you did not request this code, please ignore this email.
                      </td>
                    </tr>
                  </table>

                  <div style="height:36px; font-size:0; line-height:0;">&nbsp;</div>

                </td>
              </tr>
            </table>
            """.formatted(
                C_GOLD, C_GOLD_LIGHT,                           // icon gradient
                C_TEXT_DARK,                                    // heading
                C_TEXT_BODY, C_NAVY, name,                     // subtext + name
                C_NAVY_DARK,                                    // OTP box bg
                digitCells.toString(),                          // digit cells
                C_TEXT_BODY, BRAND_NAME                         // warning text
        );

        return wrapEmail("Your " + BRAND_NAME + " verification code — valid for 5 minutes.", body);
    }


    // ─────────────────────────────────────────────────────────────────────────────
    // TICKET / BOOKING CONFIRMATION EMAIL
    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * Sends a booking confirmation with full ticket details and a download link.
     *
     * @param name    Recipient's display name
     * @param booking Populated {@link Booking} entity
     * @return Complete HTML email string
     */
    public static String ticketEmail(String name, Booking booking) {

        String body = """
            <!-- ═══ TICKET BODY ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="padding:44px 48px 8px 48px; text-align:center;">

                  <!-- Success medallion -->
                  <div style="
                    display:inline-block;
                    background:linear-gradient(135deg,%s,%s);
                    border-radius:50%%;
                    width:70px; height:70px;
                    line-height:70px;
                    font-size:32px;
                    box-shadow:0 8px 28px rgba(26,122,74,0.30);
                  ">&#9989;</div>

                  <div style="height:24px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Georgia',serif;
                    font-size:28px;
                    font-weight:700;
                    color:%s;
                    letter-spacing:0.3px;
                  ">Booking Confirmed!</div>

                  <div style="height:10px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:15px;
                    line-height:1.75;
                    color:%s;
                  ">
                    Hi <strong style="color:%s;">%s</strong>, your booking was successful.<br />
                    Here is a summary of your reservation.
                  </div>

                  <div style="height:32px; font-size:0; line-height:0;">&nbsp;</div>

                </td>
              </tr>
            </table>

            <!-- ═══ BOOKING DETAILS TABLE ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="padding:0 48px 28px 48px;">

                  <!-- Section label -->
                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:10px;
                    font-weight:700;
                    letter-spacing:2.5px;
                    text-transform:uppercase;
                    color:%s;
                    margin-bottom:12px;
                  ">&#127915;&nbsp; Booking Details</div>

                  <!-- Details card -->
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                         style="background:%s;
                                border:1px solid %s;
                                border-radius:12px;
                                overflow:hidden;">
                    %s
                    %s
                    %s
                  </table>

                </td>
              </tr>
            </table>

            <!-- ═══ TOTAL PRICE HIGHLIGHT ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="padding:0 48px 32px 48px;">
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                         style="background:linear-gradient(135deg,%s,%s);
                                border-radius:12px;
                                overflow:hidden;">
                    <tr>
                      <td style="padding:20px 24px; vertical-align:middle;">
                        <div style="
                          font-family:'Arial',sans-serif;
                          font-size:11px;
                          font-weight:700;
                          letter-spacing:2px;
                          text-transform:uppercase;
                          color:rgba(255,255,255,0.60);
                        ">Total Amount Paid</div>
                        <div style="
                          font-family:'Georgia',serif;
                          font-size:26px;
                          font-weight:700;
                          color:#FFFFFF;
                          margin-top:4px;
                        ">LKR&nbsp;%.2f</div>
                      </td>
                      <td style="padding:20px 24px; text-align:right; vertical-align:middle;">
                        <div style="
                          background:rgba(255,255,255,0.15);
                          border-radius:8px;
                          padding:8px 14px;
                          display:inline-block;
                          font-family:'Arial',sans-serif;
                          font-size:11px;
                          font-weight:700;
                          letter-spacing:1px;
                          text-transform:uppercase;
                          color:#FFFFFF;
                        ">&#10003; PAID</div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>

            <!-- ═══ CTA ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="padding:0 48px 16px 48px; text-align:center;">

                  %s

                  <div style="height:20px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:12px;
                    color:%s;
                    line-height:1.6;
                  ">
                    Please download and present your ticket at the venue entrance.<br />
                    A printed or digital copy is accepted.
                  </div>

                  <div style="height:36px; font-size:0; line-height:0;">&nbsp;</div>

                </td>
              </tr>
            </table>
            """.formatted(
                // Success medallion gradient
                C_SUCCESS, "#2ECC71",
                // Heading
                C_TEXT_DARK,
                // Subtext
                C_TEXT_BODY, C_NAVY, name,
                // Section label
                C_TEXT_MUTED,
                // Details card bg & border
                C_CARD, C_BORDER,
                // Info rows
                infoRow("Booking ID",  String.valueOf(booking.getBookingId()), false),
                infoRow("Tickets",     booking.getQuantity() + " seat(s)",     false),
                infoRow("Status",      "&#10003; Confirmed",                   true),
                // Total price card gradient
                C_NAVY, C_NAVY_MID,
                // Total price value
                booking.getTotalPrice(),
                // Download CTA button
                ctaButton(booking.getTicketUrl(), "&#11015;&nbsp; Download Ticket", C_GOLD, C_NAVY_DARK),
                // Footer notice
                C_TEXT_MUTED
        );

        return wrapEmail("Your booking is confirmed — " + BRAND_NAME, body);
    }


    // ─────────────────────────────────────────────────────────────────────────────
    // PASSWORD RESET EMAIL
    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * Sends a secure password-reset link email.
     *
     * @param name       Recipient's display name
     * @param resetUrl   Unique, time-limited password-reset URL
     * @param expiryMins How many minutes the reset link remains valid
     * @return Complete HTML email string
     */
    public static String passwordResetEmail(String name, String resetUrl, int expiryMins) {

        String body = """
            <!-- ═══ PASSWORD RESET BODY ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="padding:44px 48px 12px 48px; text-align:center;">

                  <!-- Icon -->
                  <div style="
                    display:inline-block;
                    background:linear-gradient(135deg,%s,%s);
                    border-radius:50%%;
                    width:70px; height:70px;
                    line-height:70px;
                    font-size:32px;
                    box-shadow:0 8px 28px rgba(201,146,42,0.30);
                  ">&#128274;</div>

                  <div style="height:24px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Georgia',serif;
                    font-size:28px;
                    font-weight:700;
                    color:%s;
                    letter-spacing:0.3px;
                  ">Password Reset Request</div>

                  <div style="height:10px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:15px;
                    line-height:1.75;
                    color:%s;
                    max-width:420px;
                    margin:0 auto;
                  ">Hi <strong style="color:%s;">%s</strong>, we received a request to reset the
                  password for your account. Click the button below to choose a new password.</div>

                  <div style="height:32px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- CTA -->
                  %s

                  <div style="height:20px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Fallback link -->
                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:11px;
                    color:%s;
                    line-height:1.6;
                  ">
                    Button not working?
                    <a href="%s" style="color:%s; text-decoration:underline;">Use this link instead</a>.
                  </div>

                  <div style="height:28px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Expiry + security warning -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                         style="margin:0 auto; max-width:440px; width:100%%;">
                    <tr>
                      <td style="
                        background:rgba(201,146,42,0.07);
                        border:1px solid rgba(201,146,42,0.22);
                        border-radius:10px;
                        padding:16px 20px;
                        font-family:'Arial',sans-serif;
                        font-size:12px;
                        line-height:1.7;
                        color:%s;
                        text-align:left;
                      ">
                        &#8987;&nbsp; This link expires in <strong>%d minutes</strong>.<br />
                        &#128274;&nbsp; If you did not request a password reset,
                        you can safely ignore this email — your password will not change.
                      </td>
                    </tr>
                  </table>

                  <div style="height:40px; font-size:0; line-height:0;">&nbsp;</div>

                </td>
              </tr>
            </table>
            """.formatted(
                C_GOLD, C_GOLD_LIGHT,                           // icon gradient
                C_TEXT_DARK,                                    // heading
                C_TEXT_BODY, C_NAVY, name,                     // subtext + name
                ctaButton(resetUrl, "&#128274;&nbsp; Reset My Password", C_GOLD, C_NAVY_DARK),
                C_TEXT_MUTED, resetUrl, C_GOLD,                 // fallback link
                C_TEXT_BODY, expiryMins                         // warning block
        );

        return wrapEmail("Reset your " + BRAND_NAME + " password", body);
    }


    // ─────────────────────────────────────────────────────────────────────────────
    // GENERAL NOTIFICATION EMAIL
    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * Sends a flexible system notification email with an optional CTA.
     *
     * @param name       Recipient's display name
     * @param title      Bold heading shown in the card
     * @param message    HTML-safe body message (may include &lt;strong&gt; etc.)
     * @param ctaLabel   Label for the CTA button (null = no button shown)
     * @param ctaUrl     Destination URL for the CTA (null = no button shown)
     * @return Complete HTML email string
     */
    public static String notificationEmail(String name, String title,
                                           String message, String ctaLabel, String ctaUrl) {

        String ctaBlock = (ctaLabel != null && ctaUrl != null)
                ? ctaButton(ctaUrl, ctaLabel, C_GOLD, C_NAVY_DARK)
                : "";

        String body = """
            <!-- ═══ NOTIFICATION BODY ═══ -->
            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td style="padding:44px 48px 12px 48px; text-align:center;">

                  <!-- Bell icon -->
                  <div style="
                    display:inline-block;
                    background:linear-gradient(135deg,%s,%s);
                    border-radius:50%%;
                    width:70px; height:70px;
                    line-height:70px;
                    font-size:32px;
                    box-shadow:0 8px 28px rgba(201,146,42,0.30);
                  ">&#128276;</div>

                  <div style="height:24px; font-size:0; line-height:0;">&nbsp;</div>

                  <div style="
                    font-family:'Georgia',serif;
                    font-size:28px;
                    font-weight:700;
                    color:%s;
                    letter-spacing:0.3px;
                  ">%s</div>

                  <div style="height:10px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Gold ornament -->
                  <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                         style="margin:0 auto;">
                    <tr>
                      <td style="width:30px; height:1px; background:%s; font-size:0;">&nbsp;</td>
                      <td style="width:6px;">&nbsp;</td>
                      <td style="width:5px; height:5px; border-radius:50%%; background:%s; font-size:0;">&nbsp;</td>
                      <td style="width:6px;">&nbsp;</td>
                      <td style="width:30px; height:1px; background:%s; font-size:0;">&nbsp;</td>
                    </tr>
                  </table>

                  <div style="height:20px; font-size:0; line-height:0;">&nbsp;</div>

                  <!-- Personalised greeting -->
                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:15px;
                    color:%s;
                    margin-bottom:12px;
                  ">Hi <strong style="color:%s;">%s</strong>,</div>

                  <!-- Message content -->
                  <div style="
                    font-family:'Arial',sans-serif;
                    font-size:15px;
                    line-height:1.75;
                    color:%s;
                    max-width:430px;
                    margin:0 auto;
                  ">%s</div>

                  <div style="height:32px; font-size:0; line-height:0;">&nbsp;</div>

                  %s

                  <div style="height:40px; font-size:0; line-height:0;">&nbsp;</div>

                </td>
              </tr>
            </table>
            """.formatted(
                C_GOLD, C_GOLD_LIGHT,                           // icon gradient
                C_TEXT_DARK,                                    // heading
                title,                                          // heading text
                C_GOLD, C_GOLD_LIGHT, C_GOLD,                  // ornament
                C_TEXT_BODY, C_NAVY, name,                     // greeting
                C_TEXT_BODY,                                    // message color
                message,                                        // message content
                ctaBlock                                        // optional CTA
        );

        return wrapEmail(title + " — " + BRAND_NAME, body);
    }
}